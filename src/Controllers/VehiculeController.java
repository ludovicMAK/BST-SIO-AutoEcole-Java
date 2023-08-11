package Controllers;

import Entities.User;
import Entities.Vehicule;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehiculeController {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public VehiculeController(){ cnx = ConnexionBDD.getCnx(); }

    public ArrayList<String> getVehiculesDisponibles(String date, String heure, int idCategorie) throws SQLException {
        ArrayList<String> lesVehicules = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT vehicule.marque, vehicule.modele, vehicule.immatriculation\n" +
                "FROM vehicule\n" +
                "JOIN categorie ON vehicule.categorie_id = categorie.id\n" +
                "WHERE vehicule.id NOT IN (\n" +
                "    SELECT vehicule.id\n" +
                "    FROM vehicule\n" +
                "    JOIN lecon ON vehicule.id = lecon.vehicule_id\n" +
                "    JOIN categorie ON vehicule.categorie_id = categorie.id\n" +
                "    WHERE lecon.date = '"+date+"'\n" +
                "    AND lecon.heure = '"+heure+"')\n" +
                "AND categorie.id = "+idCategorie+"\n" +
                "GROUP BY vehicule.id");
        rs = ps.executeQuery();
        while(rs.next()){
            lesVehicules.add(rs.getString("marque")+" "+rs.getString("modele")+" "+rs.getString("immatriculation"));
        }
        return lesVehicules;
    }

    public int getIdVehiculeByMarque_Modele_Immatriculation(String marque, String modele, String immatriculation) throws SQLException {
        int id = 0;
        ps = cnx.prepareStatement("SELECT vehicule.id\n" +
                "FROM vehicule\n" +
                "WHERE vehicule.marque = ?\n" +
                "AND vehicule.modele = ?\n" +
                "AND vehicule.immatriculation = ?");
        ps.setString(1, marque);
        ps.setString(2, modele);
        ps.setString(3, immatriculation);
        rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }
    public ArrayList<Vehicule> lstVehicules() throws SQLException {
        ArrayList<Vehicule> mesVehicules = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT COUNT(vehicule.id) as nbFoisUtilise , \n" +
                "vehicule.id,\n" +
                "vehicule.immatriculation,\n" +
                "vehicule.marque,\n" +
                "vehicule.modele,\n" +
                "vehicule.annee,\n" +
                "categorie.libelle as nomCateg\n" +
                "\tFROM lecon \n" +
                "    INNER join vehicule on lecon.vehicule_id = vehicule.id  \n" +
                "    INNER JOIN categorie on vehicule.categorie_id =categorie.id \n" +
                "    GROUP BY vehicule.id ");
        rs = ps.executeQuery();
        while (rs.next()){
            Vehicule unVehicule = new Vehicule(rs.getInt("id"),rs.getString("immatriculation"),rs.getString("marque"),rs.getString("modele"),rs.getString("annee"),rs.getString("nomCateg"),rs.getInt("nbFoisUtilise"));
            mesVehicules.add(unVehicule);
        }
        return mesVehicules;
    }
    public void insertVehicule(int idCateg, String unImma,String uneMarque,String unModel,String uneAnnee) throws SQLException {

        ps = cnx.prepareStatement("INSERT INTO `vehicule` (`id`, `categorie_id`, `immatriculation`, `marque`, `modele`, `annee`) VALUES (NULL, "+idCateg+", '"+unImma+"', '"+uneMarque+"', '"+unModel+"', '"+uneAnnee+"')");
        ps.execute();
    }

    public ArrayList<Vehicule> getAllVehicule(String uneCategorie) throws SQLException {
        ArrayList<Vehicule> mesVehicule = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT vehicule.id,vehicule.immatriculation,vehicule.marque,vehicule.modele,vehicule.annee \n" +
                "FROM vehicule INNER JOIN categorie ON vehicule.categorie_id = categorie.id \n" +
                "where categorie.libelle = '"+uneCategorie+"'");
        rs = ps.executeQuery();
        while (rs.next()){
            Vehicule unVehicule = new Vehicule(rs.getInt("vehicule.id"),rs.getString("vehicule.immatriculation"),rs.getString("vehicule.marque"),rs.getString("vehicule.modele"),rs.getString("vehicule.annee"));
            mesVehicule.add(unVehicule);
        }
        return mesVehicule;
    }
    public Boolean SiexisteMatricule(String uneImma,int idVehicule) throws SQLException {
        Boolean existe =false;
        ps = cnx.prepareStatement("SELECT vehicule.immatriculation FROM vehicule WHERE vehicule.immatriculation = '"+uneImma+"' AND vehicule.id != "+idVehicule);
        rs = ps.executeQuery();
        if (rs.next()){
            existe=true;
        }
        return existe;
    }
    public void modifierVehicule(String uneImma,String uneMarque,String unmodele,String uneAnnee,int idCategorie,int idVehicule) throws SQLException {
        ps = cnx.prepareStatement("UPDATE `vehicule` SET `immatriculation` = '"+uneImma+"',`marque` = '"+uneMarque+"',`modele` = '"+unmodele+"',`annee` = '"+uneAnnee+"',`categorie_id` = "+idCategorie+" WHERE `vehicule`.`id` = "+idVehicule);
        ps.executeUpdate();
    }
}
