package Controllers;

import Entities.Categorie;
import Entities.Eleve;
import Entities.Moniteur;
import Entities.User;
import Tools.ConnexionBDD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MoniteurController {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public MoniteurController(){
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Moniteur> getLoginNomPrenom() throws SQLException {
        ArrayList<Moniteur> lesMoniteurs = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT user.login, moniteur.nom, moniteur.prenom\n" +
                "FROM moniteur\n" +
                "JOIN user ON moniteur.user_id = user.id");
        rs = ps.executeQuery();
        while(rs.next()){
            User user = new User(rs.getString("login"));
            Moniteur moniteur = new Moniteur(rs.getString("nom"), rs.getString("prenom"), user);
            lesMoniteurs.add(moniteur);
        }
        return lesMoniteurs;
    }

    public Moniteur getPatronymeByIdUser(int idUser) throws SQLException {
        Moniteur moniteur = new Moniteur("","","","","","","","","","");
        ps = cnx.prepareStatement("SELECT moniteur.nom,moniteur.prenom,moniteur.adresse,moniteur.code_postal,moniteur.ville,moniteur.telephone,user.password,moniteur.date_de_naissance,moniteur.email,moniteur.sexe\n" +
                "FROM moniteur INNER JOIN user ON moniteur.user_id=user.id WHERE moniteur.user_id=?");
        ps.setInt(1,idUser);
        rs = ps.executeQuery();
        if(rs.next()){
            moniteur.setNom(rs.getString(1));
            moniteur.setPrenom(rs.getString(2));
            moniteur.setAdresse(rs.getString(3));
            moniteur.setCodePostal(rs.getString(4));
            moniteur.setVille(rs.getString(5));
            moniteur.setTelephone(rs.getString(6));
            moniteur.setMdp(rs.getString(7));
            moniteur.setDateDeNaissance(rs.getString(8));
            moniteur.setEmail(rs.getString(9));
            moniteur.setSexe(rs.getString(10));
        }
        return moniteur;
    }
    public void modifierInformationMoniteur(String dataMod,int idUser,String unmdp) throws SQLException {
        try {
            ps = cnx.prepareStatement("UPDATE moniteur SET "+dataMod+" WHERE moniteur.user_id = ?");
            ps.setInt(1,idUser);
            ps.executeUpdate();
            ps.close();
            rs.close();
            ps = cnx.prepareStatement("UPDATE `user` SET `password` = '"+unmdp+"' WHERE `user`.`id` = ?;");
            ps.setInt(1,idUser);
            ps.executeUpdate();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null ,"Probleme requet");
        }

    }


    public ArrayList<String> getMoniteurDispo(String date, String heure, int idCategorie) throws SQLException {
        ArrayList<String> lesMoniteurs = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT moniteur.nom, moniteur.prenom\n" +
                "FROM moniteur\n" +
                "JOIN licence ON moniteur.id = licence.moniteur_id\n" +
                "JOIN categorie ON licence.categorie_id = categorie.id\n" +
                "WHERE moniteur.id NOT IN (\n" +
                "    SELECT moniteur.id\n" +
                "    FROM moniteur\n" +
                "    JOIN lecon ON moniteur.id = lecon.moniteur_id\n" +
                "    JOIN licence ON moniteur.id = licence.moniteur_id\n" +
                "    JOIN categorie ON licence.categorie_id = categorie.id\n" +
                "    WHERE lecon.date = '"+date+"'\n" +
                "    AND lecon.heure = '"+heure+"')\n" +
                "AND categorie.id = "+idCategorie+"\n" +
                "GROUP BY moniteur_id");
        rs = ps.executeQuery();
        while(rs.next()){
            lesMoniteurs.add(rs.getString("prenom")+" "+rs.getString("nom"));
        }
        return lesMoniteurs;
    }

    public int getIdMoniteurByPrenom_Nom(String prenom, String nom) throws SQLException {
        int id = 0;
        ps = cnx.prepareStatement("SELECT moniteur.id\n" +
                "FROM moniteur\n" +
                "WHERE moniteur.prenom = '"+prenom+"'\n" +
                "AND moniteur.nom = '"+nom+"'");
        rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }

    public int getIdMoniteurByIdUser(int idUser) throws SQLException {
        int id = 0;
        ps = cnx.prepareStatement("SELECT moniteur.id\n" +
                "FROM moniteur\n" +
                "WHERE moniteur.user_id = "+ idUser);
        rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }

    public void addMoniteur(int idUser, String nom, String prenom, String sexe, String date, String adresse, String codePostal, String ville, String telephone, String email) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO moniteur(user_id, nom, prenom, sexe, date_de_naissance, adresse, code_postal, ville, telephone, email)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, idUser);
        ps.setString(2, nom);
        ps.setString(3, prenom);
        ps.setString(4, sexe);
        ps.setString(5, date);
        ps.setString(6, adresse);
        ps.setString(7, codePostal);
        ps.setString(8, ville);
        ps.setString(9, telephone);
        ps.setString(10, email);
        ps.executeUpdate();
    }
    public ArrayList<Moniteur> lstMoniteurForStatsGerante() throws SQLException {
        ArrayList<Moniteur> mesMoniteurs = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT COUNT(lecon.id), moniteur.id ,moniteur.nom,moniteur.prenom FROM lecon INNER JOIN moniteur ON lecon.moniteur_id = moniteur.id GROUP BY moniteur.id");
        rs = ps.executeQuery();
        while (rs.next()){
            Moniteur monMoniteur = new Moniteur(rs.getInt("COUNT(lecon.id)"),rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"));
            mesMoniteurs.add(monMoniteur);
        }
        return mesMoniteurs;
    }

}
