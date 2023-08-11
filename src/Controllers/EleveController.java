package Controllers;

import Entities.Eleve;
import Entities.User;
import Tools.ConnexionBDD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EleveController {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public EleveController(){
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Eleve> getLoginNomPrenom() throws SQLException {
        ArrayList<Eleve> lesEleves = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT user.login, eleve.nom, eleve.prenom\n" +
                "FROM eleve\n" +
                "JOIN user ON eleve.user_id = user.id");
        rs = ps.executeQuery();
        while(rs.next()){
            User user = new User(rs.getString("login"));
            Eleve eleve = new Eleve(rs.getString("nom"), rs.getString("prenom"), user);
            lesEleves.add(eleve);
        }
        return lesEleves;
    }

    public Eleve getPatronymeByIdUser(int idUser) throws SQLException {
        Eleve eleve = new Eleve("","","","","","","","","","");
        ps = cnx.prepareStatement("SELECT eleve.nom,eleve.prenom,eleve.adresse,eleve.code_postal,eleve.ville,eleve.telephone,user.password,eleve.date_de_naissance,eleve.email,eleve.sexe\n" +
                "\tFROM eleve INNER JOIN user ON eleve.user_id = user.id\n" +
                "    WHERE user_id = "+idUser);
        rs = ps.executeQuery();
        if(rs.next()){
            eleve.setNom(rs.getString(1));
            eleve.setPrenom(rs.getString(2));
            eleve.setAdresse(rs.getString(3));
            eleve.setCodePostal(rs.getString(4));
            eleve.setVille(rs.getString(5));
            eleve.setTelephone(rs.getString(6));
            eleve.setMdp(rs.getString(7));
            eleve.setDateDeNaissance(rs.getString(8));
            eleve.setEmail(rs.getString(9));
            eleve.setSexe(rs.getString(10));
        }
        return eleve;
    }

    public void insertEleve(int idUser, String login, String password, int role, int sexe,
    String dateNaissance, String adresse, int codePostal, String ville, String telephone, User user) throws SQLException {

        ps = cnx.prepareStatement("INSERT INTO eleve (eleve.user_id, eleve.nom, eleve.prenom, \n" +
                "eleve.sexe, eleve.date_de_naissance, eleve.adresse, eleve.code_postal,\n" +
                "eleve.ville, eleve.telephone)\n" +
                "VALUES('"+login+"', '"+password+"', '"+role+"', '"+sexe+"'," +
                "'"+dateNaissance+"', '"+adresse+"', '"+codePostal+"', '"+ville+"'," +
                "'"+telephone+"', '"+user+"')");

                rs = ps.executeQuery();
    }

    public int getEleveIdByIdUser(int idUser) throws SQLException {
        int id = 0;
        ps = cnx.prepareStatement("SELECT eleve.id\n" +
                "FROM eleve\n" +
                "WHERE eleve.user_id = "+idUser);
        rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }
    public void modifierInformationEleve(String dataMod,int idUser,String unmdp) throws SQLException {
        try {
            ps = cnx.prepareStatement("UPDATE eleve SET "+dataMod+" WHERE eleve.user_id = ?");
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


}
