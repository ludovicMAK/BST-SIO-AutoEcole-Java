package Controllers;

import Entities.Categorie;
import Tools.ConnexionBDD;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CategorieController {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CategorieController(){
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> getCategoriesByIdEleve(int idUserEleve) throws SQLException {
        ArrayList<String> lesCategories = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT DISTINCT categorie.libelle\n" +
                "FROM categorie\n" +
                "JOIN vehicule ON categorie.id = vehicule.categorie_id\n" +
                "JOIN lecon ON vehicule.id = lecon.vehicule_id\n" +
                "JOIN eleve ON lecon.eleve_id = eleve.id\n" +
                "WHERE eleve.user_id ="+idUserEleve);
        rs = ps.executeQuery();
        while(rs.next()){
            lesCategories.add(rs.getString("libelle"));
        }
        return lesCategories;
    }
    public ArrayList<String> getCategoriesByIdEleveBetweenDate(int idUserEleve,String min,String max) throws SQLException {
        ArrayList<String> lesCategories = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT DISTINCT categorie.libelle\n" +
                "FROM categorie\n" +
                "JOIN vehicule ON categorie.id = vehicule.categorie_id\n" +
                "JOIN lecon ON vehicule.id = lecon.vehicule_id\n" +
                "JOIN eleve ON lecon.eleve_id = eleve.id\n" +
                "WHERE eleve.user_id ="+idUserEleve+
                " and lecon.date between '"+min+"' and '"+max+"'");
        rs = ps.executeQuery();
        while(rs.next()){
            lesCategories.add(rs.getString("libelle"));
        }
        return lesCategories;
    }
    public ArrayList<String> getCategoriesByIdMoniteurBetweenDate(int idUserMoniteur,String min,String max) throws SQLException {
        ArrayList<String> lesCategories = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT DISTINCT categorie.libelle FROM categorie\n" +
                "\tINNER JOIN licence ON categorie.id = licence.categorie_id\n" +
                "    INNER JOIN moniteur ON moniteur.id = licence.moniteur_id\n" +
                "    INNER JOIN lecon ON moniteur.id = lecon.moniteur_id"+
                "    WHERE moniteur.user_id ="+idUserMoniteur+
                "    and lecon.date between '"+min+"' and '"+max+"'");
        rs = ps.executeQuery();
        while(rs.next()){
            lesCategories.add(rs.getString("libelle"));
        }
        return lesCategories;
    }

    public ArrayList<String> getCategoriesByIdMoniteur(int idUserMoniteur) throws SQLException {
        ArrayList<String> lesCategories = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT categorie.libelle FROM categorie\n" +
                "\tINNER JOIN licence ON categorie.id = licence.categorie_id\n" +
                "    INNER JOIN moniteur ON moniteur.id = licence.moniteur_id\n" +
                "    WHERE moniteur.user_id ="+idUserMoniteur);
        rs = ps.executeQuery();
        while(rs.next()){
            lesCategories.add(rs.getString("libelle"));
        }
        return lesCategories;
    }


    public double getPrixByIdCategorie(int idCategorie) throws SQLException {
        double prix = 0;
        ps = cnx.prepareStatement("SELECT categorie.prix\n" +
                "FROM categorie\n" +
                "WHERE categorie.id ="+idCategorie);
        rs = ps.executeQuery();
        if(rs.next()){
            prix = rs.getDouble("prix");
        }
        return prix;
    }

    public int getIdByLibelle(String libelleCategorie) throws SQLException {
        int id = 0;
        ps = cnx.prepareStatement("SELECT categorie.id\n" +
                "FROM categorie\n" +
                "WHERE categorie.libelle = '"+libelleCategorie+"'");
        rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }

    public ArrayList<String> getAllCategories() throws SQLException {
        ArrayList<String> lesCategories = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT categorie.libelle\n" +
                "FROM categorie");
        rs = ps.executeQuery();
        while(rs.next()){
            lesCategories.add(rs.getString("libelle"));
        }
        return lesCategories;
    }

    public ArrayList<String> getCategoriesMoniteurLicence(int idUserMoniteur) throws SQLException {
        ArrayList<String> lesCategories = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT categorie.libelle\n" +
                "FROM categorie\n" +
                "WHERE categorie.libelle NOT IN(\n" +
                "   SELECT categorie.libelle\n" +
                "   FROM categorie\n" +
                "   JOIN licence ON categorie.id = licence.categorie_id\n" +
                "   JOIN moniteur ON licence.moniteur_id = moniteur.id\n" +
                "   WHERE moniteur.user_id = "+idUserMoniteur+")");
        rs = ps.executeQuery();
        while(rs.next()){
            lesCategories.add(rs.getString("libelle"));
        }
        return lesCategories;
    }

    public boolean isCategorie(String libelle) throws SQLException {
        boolean result = false;
        ps = cnx.prepareStatement("SELECT categorie.id\n" +
                "FROM categorie\n" +
                "WHERE categorie.libelle = ?");
        ps.setString(1, libelle);
        rs = ps.executeQuery();
        if(rs.next()){
            result = true;
        }
        return result;
    }

    public void addCategorie(String libelle, Double prix) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO categorie(categorie.libelle, categorie.prix)\n" +
                "VALUES(?, ?)");
        ps.setString(1, libelle);
        ps.setDouble(2, prix);
        ps.executeUpdate();
    }

    public ArrayList<Categorie> getCategories() throws SQLException {
        ArrayList<Categorie> lesCategories = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT categorie.id, categorie.libelle, categorie.prix\n" +
                "FROM categorie");
        rs = ps.executeQuery();
        while(rs.next()){
            Categorie categorie = new Categorie(rs.getInt("id"), rs.getString("libelle"), rs.getDouble("prix"));
            lesCategories.add(categorie);
        }
        return lesCategories;
    }

    public void updateCategorieById(Categorie categorie) throws SQLException {
        ps = cnx.prepareStatement("UPDATE categorie\n" +
                "SET libelle = ?, prix = ?\n" +
                "WHERE id = ?");
        ps.setString(1, categorie.getLibelle());
        ps.setDouble(2, categorie.getPrix());
        ps.setInt(3, categorie.getId());
        ps.executeUpdate();
    }
    public HashMap<String,Integer> categorieStatForGerante() throws SQLException {
        HashMap<String,Integer> data = new HashMap<>();
        ps = cnx.prepareStatement("SELECT categorie.libelle, COUNT(lecon.id)FROM lecon INNER JOIN vehicule ON lecon.vehicule_id = vehicule.id INNER JOIN categorie ON categorie.id = vehicule.categorie_id GROUP BY categorie.libelle");
        rs = ps.executeQuery();
        while (rs.next()){
            data.put(rs.getString("libelle"),rs.getInt("COUNT(lecon.id)"));
        }
        return  data;
    }
}
