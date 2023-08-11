package Controllers;

import Entities.*;
import Tools.ConnexionBDD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class LeconController {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public LeconController(){
        cnx = ConnexionBDD.getCnx();
    }

    public int getNbLeconByIdEleve_IdCategorie(int idUserEleve, int idCategorie) throws SQLException {
        int prix = 0;
        ps = cnx.prepareStatement("SELECT COUNT(lecon.id)\n" +
                "FROM lecon\n" +
                "JOIN eleve ON lecon.eleve_id = eleve.id\n" +
                "JOIN vehicule ON lecon.vehicule_id = vehicule.id\n" +
                "WHERE eleve.user_id ="+idUserEleve+"\n"+
                "AND vehicule.categorie_id ="+idCategorie);
        rs = ps.executeQuery();
        if(rs.next()){
            prix = rs.getInt(1);
        }
        return prix;
    }
    public HashMap<String,Double> gerantChiffreAffaireData() throws SQLException {
        HashMap<String,Double> data = new HashMap<>();
        ps = cnx.prepareStatement("SELECT \"auj\",SUM(t.sommePrixLeconCateg) as sommeTotal\n" +
                "    FROM (\n" +
                "        SELECT  categorie.libelle ,categorie.prix*COUNT(lecon.id) as sommePrixLeconCateg \n" +
                "            FROM lecon \n" +
                "                INNER JOIN vehicule ON vehicule.id = lecon.vehicule_id \n" +
                "                INNER JOIN categorie ON categorie.id = vehicule.categorie_id  \n" +
                "                WHERE lecon.date = DATE(NOW() )\n" +
                "                GROUP BY categorie.id\n" +
                "    ) as t\n" +
                "UNION\n" +
                "SELECT \"semaine\", SUM(p.sommePrixLeconCateg) \n" +
                "    FROM (\n" +
                "        SELECT  categorie.libelle ,categorie.prix*COUNT(lecon.id) as sommePrixLeconCateg\n" +
                "            FROM lecon \n" +
                "                INNER JOIN vehicule ON vehicule.id = lecon.vehicule_id \n" +
                "                INNER JOIN categorie ON categorie.id = vehicule.categorie_id  \n" +
                "                WHERE lecon.date BETWEEN DATE(NOW() - INTERVAL 7 DAY)  AND DATE(NOW() )\n" +
                "                GROUP BY categorie.id\n" +
                "    ) as p\n" +
                "UNION\n" +
                "SELECT \"mois\", SUM(p.sommePrixLeconCateg) \n" +
                "    FROM (\n" +
                "        SELECT  categorie.libelle ,categorie.prix*COUNT(lecon.id) as sommePrixLeconCateg\n" +
                "            FROM lecon \n" +
                "                INNER JOIN vehicule ON vehicule.id = lecon.vehicule_id \n" +
                "                INNER JOIN categorie ON categorie.id = vehicule.categorie_id  \n" +
                "                WHERE lecon.date BETWEEN DATE(NOW() - INTERVAL 1 month)  AND DATE(NOW() )\n" +
                "                GROUP BY categorie.id\n" +
                "    ) as p\n" +
                "UNION\n" +
                "SELECT \"trimestre\", SUM(p.sommePrixLeconCateg) \n" +
                "    FROM (\n" +
                "        SELECT  categorie.libelle ,categorie.prix*COUNT(lecon.id) as sommePrixLeconCateg\n" +
                "            FROM lecon \n" +
                "                INNER JOIN vehicule ON vehicule.id = lecon.vehicule_id \n" +
                "                INNER JOIN categorie ON categorie.id = vehicule.categorie_id  \n" +
                "                WHERE lecon.date BETWEEN DATE(NOW() - INTERVAL 3 month)  AND DATE(NOW() )\n" +
                "                GROUP BY categorie.id\n" +
                "    ) as p;");
        rs = ps.executeQuery();
        while (rs.next()){
            data.put(rs.getString("auj"),rs.getDouble("sommeTotal"));
        }
        return data;
    }
    public ArrayList<Lecon> getLeconByIdEleve_IdCategorie(int idUserEleve, int idCategorie) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        ArrayList<Lecon> lesLecons = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT lecon.date, lecon.heure, lecon.payee\n" +
                "FROM lecon\n" +
                "JOIN eleve ON lecon.eleve_id = eleve.id\n" +
                "JOIN vehicule ON lecon.vehicule_id = vehicule.id\n" +
                "JOIN categorie ON vehicule.categorie_id = categorie.id\n" +
                "WHERE eleve.user_id = "+idUserEleve+"\n" +
                "AND categorie.id = "+idCategorie);
        rs = ps.executeQuery();
        while(rs.next()){
            boolean passee = false;
            String date = rs.getString("date")+" "+rs.getString("heure");
            if(now.toString().compareTo(date) > 0){
                passee = true;
            }
            boolean payee = false;
            if(rs.getInt("payee") == 1){
                payee = true;
            }
            Lecon lecon = new Lecon(date, passee, payee);
            lesLecons.add(lecon);
        }
        return lesLecons;
    }

    public int getNbLeconNonPayeeByIdEleve_IdCategorie(int idUserEleve, int idCategorie) throws SQLException {
        int nb = 0;
        ps = cnx.prepareStatement("SELECT COUNT(lecon.id)\n" +
                "FROM lecon\n" +
                "JOIN eleve ON lecon.eleve_id = eleve.id\n" +
                "JOIN vehicule ON lecon.vehicule_id = vehicule.id\n" +
                "WHERE eleve.user_id = "+idUserEleve+"\n" +
                "AND vehicule.categorie_id = "+idCategorie+"\n" +
                "AND lecon.payee = 0");
        rs = ps.executeQuery();
        if(rs.next()){
            nb = rs.getInt(1);
        }
        return nb;
    }

    public int getNbLeconByIdMoniteur_IdCategorie(int idUserMoniteur, int idCategorie, String dateDebut, String dateFin) throws SQLException {
        int nb = 0;
        ps = cnx.prepareStatement("SELECT COUNT(lecon.id)\n" +
                "FROM lecon\n" +
                "JOIN moniteur ON lecon.moniteur_id = moniteur.id\n" +
                "JOIN vehicule ON lecon.vehicule_id = vehicule.id\n" +
                "JOIN categorie ON vehicule.categorie_id = categorie.id\n" +
                "WHERE moniteur.user_id = "+idUserMoniteur+"\n" +
                "AND categorie.id = "+idCategorie+"\n" +
                "AND lecon.date >= '"+dateDebut+"'\n" +
                "AND lecon.date <= '"+dateFin+"'");
        rs = ps.executeQuery();
        if(rs.next()){
            nb = rs.getInt(1);
        }
        return nb;
    }

    public void addLecon(int idEleve, int idMoniteur, int idVehicule, String date, String heure) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO lecon(lecon.eleve_id, lecon.moniteur_id, lecon.vehicule_id, lecon.date, lecon.heure, lecon.payee)\n" +
                "VALUES("+idEleve+", "+idMoniteur+", "+idVehicule+", '"+date+"', '"+heure+"', 0)");
        ps.executeUpdate();
    }

    public TreeMap<String, TreeMap<String,Lecon>> obtenirPlanningLeconMoniteurTranche(int idUserMoniteur, String unCateg,String minDate,String maxDate) throws SQLException {
        TreeMap<String,TreeMap<String,Lecon>> monPlanning = new TreeMap<>();
        try {
            ps = cnx.prepareStatement("SELECT lecon.date,lecon.heure,lecon.id,eleve.nom,eleve.prenom,vehicule.immatriculation \n" +
                    "FROM moniteur\n" +
                    "INNER JOIN licence ON licence.moniteur_id = moniteur.id" +
                    "        INNER JOIN categorie ON categorie.id = licence.categorie_id\n" +
                    "        INNER JOIN vehicule ON vehicule.categorie_id = categorie.id\n" +
                    "        INNER JOIN lecon ON vehicule.id = lecon.vehicule_id\n" +
                    "        INNER JOIN eleve ON eleve.id = lecon.eleve_id\n" +
                    "        WHERE  categorie.libelle='"+unCateg+"'" +
                    "        and moniteur.user_id ="+idUserMoniteur+
                    "        and lecon.date between '"+minDate+"' and '"+maxDate+"'");
            rs = ps.executeQuery();

            while (rs.next()){

                Eleve unEleve = new Eleve(rs.getString("eleve.nom"),rs.getString("eleve.prenom"));
                Vehicule unVehicule = new Vehicule(rs.getString("vehicule.immatriculation"));
                Lecon maLecon = new Lecon(unEleve,unVehicule);
                if (!monPlanning.containsKey(rs.getString("lecon.date")))
                {
                    TreeMap<String, Lecon> lecon = new TreeMap<>();
                    lecon.put(rs.getString("lecon.heure"), maLecon);
                    monPlanning.put(rs.getString("lecon.date"),lecon);
                }else {
                    monPlanning.get(rs.getString("lecon.date")).put(rs.getString("lecon.heure"),maLecon);
                }

            }

        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"on n' arrive pas obtenir les leçons");
        }
        return monPlanning;
    }
    public TreeMap<String, TreeMap<String,Lecon>> obtenirPlanningLeconEleveTranche(int idUserEleve, String unCateg,String minDate,String maxDate) throws SQLException {
        TreeMap<String,TreeMap<String,Lecon>> monPlanning = new TreeMap<>();
        try {
            ps = cnx.prepareStatement("SELECT lecon.date,lecon.heure,lecon.id,moniteur.nom,moniteur.prenom,vehicule.immatriculation \n" +
                    "FROM moniteur\n" +
                    "INNER JOIN licence ON licence.moniteur_id = moniteur.id" +
                    "        INNER JOIN categorie ON categorie.id = licence.categorie_id\n" +
                    "        INNER JOIN vehicule ON vehicule.categorie_id = categorie.id\n" +
                    "        INNER JOIN lecon ON vehicule.id = lecon.vehicule_id\n" +
                    "        INNER JOIN eleve ON eleve.id = lecon.eleve_id\n" +
                    "        WHERE  categorie.libelle='"+unCateg+"'" +
                    "        and eleve.user_id ="+idUserEleve +
                    "        and lecon.date between '"+minDate+"' and '"+maxDate+"'");
            rs = ps.executeQuery();

            while (rs.next()){
                Moniteur unMoniteur = new Moniteur(rs.getString("moniteur.nom"),rs.getString("moniteur.prenom"));
                Vehicule unVehicule = new Vehicule(rs.getString("vehicule.immatriculation"));
                Lecon maLecon = new Lecon(unMoniteur,unVehicule);
                if (!monPlanning.containsKey(rs.getString("lecon.date")))
                {
                    TreeMap<String, Lecon> lecon = new TreeMap<>();
                    lecon.put(rs.getString("lecon.heure"), maLecon);
                    monPlanning.put(rs.getString("lecon.date"),lecon);
                }else {
                    monPlanning.get(rs.getString("lecon.date")).put(rs.getString("lecon.heure"),maLecon);
                }

            }

        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"on n' arrive pas obtenir les leçons");
        }
        return monPlanning;
    }

    public Boolean siExisteLeconEleveByDate(int idUserEleve, String unCateg,String minDate,String maxDate) throws SQLException {
        boolean existe =false;
            ps = cnx.prepareStatement("SELECT lecon.date,lecon.heure,lecon.id,moniteur.nom,moniteur.prenom,vehicule.immatriculation \n" +
                    "FROM moniteur\n" +
                    "INNER JOIN licence ON licence.moniteur_id = moniteur.id" +
                    "        INNER JOIN categorie ON categorie.id = licence.categorie_id\n" +
                    "        INNER JOIN vehicule ON vehicule.categorie_id = categorie.id\n" +
                    "        INNER JOIN lecon ON vehicule.id = lecon.vehicule_id\n" +
                    "        INNER JOIN eleve ON eleve.id = lecon.eleve_id\n" +
                    "        WHERE  categorie.libelle='"+unCateg+"'" +
                    "        and eleve.user_id ="+idUserEleve +
                    "        and lecon.date between '"+minDate+"' and '"+maxDate+"'");
            rs = ps.executeQuery();
            if (rs.next()){
                existe = true;
            }

        return existe;
    }
    public Boolean siExisteLeconMoniteurByDate(int idUserMoniteur, String unCateg,String minDate,String maxDate) throws SQLException {
        boolean existe =false;
            ps = cnx.prepareStatement("SELECT lecon.date,lecon.heure,lecon.id,eleve.nom,eleve.prenom,vehicule.immatriculation \n" +
                    "FROM moniteur\n" +
                    "INNER JOIN licence ON licence.moniteur_id = moniteur.id" +
                    "        INNER JOIN categorie ON categorie.id = licence.categorie_id\n" +
                    "        INNER JOIN vehicule ON vehicule.categorie_id = categorie.id\n" +
                    "        INNER JOIN lecon ON vehicule.id = lecon.vehicule_id\n" +
                    "        INNER JOIN eleve ON eleve.id = lecon.eleve_id\n" +
                    "        WHERE  categorie.libelle='"+unCateg+"'" +
                    "        and moniteur.user_id ="+idUserMoniteur+
                    "        and lecon.date between '"+minDate+"' and '"+maxDate+"'");
        rs = ps.executeQuery();
        if (rs.next()){
            existe = true;
        }
        return existe;
    }


    ///////
    public TreeMap<String, TreeMap<String, TreeMap<String, ArrayList<Lecon>>>> getPlanningLecons(DateIntervalle date) throws SQLException {
        TreeMap<String, TreeMap<String, TreeMap<String, ArrayList<Lecon>>>> planning = new TreeMap<>();
        String rqt = "";
        //On vérifie si un user est sélectionné
        boolean ok = true;
        if(date.getLogin().compareTo("") == 0 && date.getRole().compareTo("") == 0){
            ok = false;
        }
        //On va adapter la requete en fonction du cas dans lequel elle a été requise
        rqt = "SELECT lecon.id, lecon.date, lecon.heure, lecon.payee, categorie.libelle, " +
                "moniteur.nom AS nomMoniteur, moniteur.prenom AS prenomMoniteur," +
                "eleve.nom AS nomEleve, eleve.prenom AS prenomEleve," +
                "vehicule.immatriculation, vehicule.modele\n" +
                "FROM lecon\n" +
                "JOIN moniteur ON lecon.moniteur_id = moniteur.id\n" +
                "JOIN eleve ON lecon.eleve_id = eleve.id\n" +
                "JOIN vehicule ON lecon.vehicule_id = vehicule.id\n" +
                "JOIN categorie ON vehicule.categorie_id = categorie.id\n";
        if(date.getCas().compareTo("") == 0){
            //On prend toutes les leçons donc pas besoin de changer la rqt
            //On ajoute la condition en fonction du role et du login
            if(ok){
                rqt = rqt + "WHERE "+date.getRole()+".user_id = (SELECT user.id\n" +
                        "FROM user\n" +
                        "WHERE user.login = '"+date.getLogin()+"')";
            }
        }
        else if(date.getCas().compareTo("d") == 0){
            //On prend les leçons à partir de la date de début
            rqt = rqt + "WHERE lecon.date >= '"+date.getDateDebut()+"'\n";
        }
        else if(date.getCas().compareTo("f") == 0){
            //On prend les leçons jusqu'à la date de fin
            rqt = rqt + "WHERE lecon.date <= '"+date.getDateFin()+"'\n";
        }
        else if(date.getCas().compareTo("df") == 0){
            //On prend les leçons dans l'intervalle
            rqt = rqt + "WHERE lecon.date BETWEEN '"+date.getDateDebut()+"' AND '"+date.getDateFin()+"'\n";
        }
        if(date.getCas().compareTo("") != 0){
            if(ok){
                rqt = rqt + "AND "+date.getRole()+".user_id = (SELECT user.id\n" +
                        "FROM user\n" +
                        "WHERE user.login = '"+date.getLogin()+"')";
            }
        }

        ps = cnx.prepareStatement(rqt);
        rs = ps.executeQuery();
        while (rs.next()){
            Moniteur moniteur = new Moniteur(rs.getString("nomMoniteur"),rs.getString("prenomMoniteur"));
            Eleve eleve = new Eleve(rs.getString("nomEleve"), rs.getString("prenomEleve"));
            Vehicule vehicule = new Vehicule(rs.getString("immatriculation"), rs.getString("modele"));
            Lecon lecon = new Lecon(rs.getInt("id"), eleve, vehicule, moniteur);
            //Si il n'y a pas cette catégorie on l'ajoute dans le TreeMap principal
            if(!planning.containsKey(rs.getString("libelle"))){
                TreeMap<String, TreeMap<String, ArrayList<Lecon>>> dateMap = new TreeMap<>();
                planning.put(rs.getString("libelle"), dateMap);
            }
            //Si il n'y a pas cette date on l'ajoute dans le TreeMap secondaire
            if(!planning.get(rs.getString("libelle")).containsKey(rs.getString("date"))){
                TreeMap<String, ArrayList<Lecon>> heureMap = new TreeMap<>();
                planning.get(rs.getString("libelle")).put(rs.getString("date"), heureMap);
            }
            //Si il n'y a pas cette heure on l'ajoute dans le TreeMap tertiaire
            if(!planning.get(rs.getString("libelle")).get(rs.getString("date")).containsKey(rs.getString("heure"))){
                ArrayList<Lecon> lecons = new ArrayList<>();
                planning.get(rs.getString("libelle")).get(rs.getString("date")).put(rs.getString("heure"), lecons);
            }
            //Enfin on ajoute la lecon dans le TreeMap tertiaire correspondant
            planning.get(rs.getString("libelle")).get(rs.getString("date")).get(rs.getString("heure")).add(lecon);
        }
        return planning;
    }

    public boolean isLeconByIdMoniteur_Date_Heure(int idMoniteur, String date, String heure) throws SQLException {
        boolean result = false;
        ps = cnx.prepareStatement("SELECT lecon.id\n" +
                "FROM lecon\n" +
                "WHERE lecon.moniteur_id = ?\n" +
                "AND lecon.date = ?\n" +
                "AND lecon.heure = ?");
        ps.setInt(1, idMoniteur);
        ps.setString(2, date);
        ps.setString(3, heure);
        rs = ps.executeQuery();
        if(rs.next()){
            result = true;
        }
        return result;
    }

    public boolean isLeconByIdVehicule_Date_Heure(int idVehicule, String date, String heure) throws SQLException {
        boolean result = false;
        ps = cnx.prepareStatement("SELECT lecon.id\n" +
                "FROM lecon\n" +
                "WHERE lecon.vehicule_id = ?\n" +
                "AND lecon.date = ?\n" +
                "AND lecon.heure = ?");
        ps.setInt(1, idVehicule);
        ps.setString(2, date);
        ps.setString(3, heure);
        rs = ps.executeQuery();
        if(rs.next()){
            result = true;
        }
        return result;
    }
}
