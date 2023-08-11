package Controllers;

import Entities.User;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public UserController(){
        cnx = ConnexionBDD.getCnx();
    }

    public User getUserByLogin_Password(String login, String password) throws SQLException {
        User user = new User(0,login,0);
        ps = cnx.prepareStatement("SELECT user.id, user.role\n" +
                "FROM user\n" +
                "WHERE user.login = '"+login+"'\n" +
                "AND user.password = '"+password+"'");
        rs = ps.executeQuery();
        if(rs.next()){
            user.setId(rs.getInt("id"));
            user.setRole(rs.getInt("role"));
        }
        return user;
    }


    public int getUserIdByLogin(String login) throws SQLException {
        int id = 0;
        ps = cnx.prepareStatement("SELECT user.id FROM user " +
                "WHERE user.login = '"+login+"'");

        rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }

    public ArrayList<String> getAllGenre() throws SQLException {
        ArrayList<String> mesGenre = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT eleve.sexe FROM eleve GROUP BY eleve.sexe");
        rs = ps.executeQuery();
        while (rs.next()){
            mesGenre.add(rs.getString("eleve.sexe"));
        }
        return mesGenre;
    }

    public void inscription(String login,String password,String unNom,String unPrenom,String unSexe,String uneDate,String uneAdresse, String unPostal, String uneVille,String unTelephone,String unEmail) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO user (id, login, password, role) VALUES (NULL, '"+login+"', '"+password+"', 2)");
        ps.executeUpdate();
        int idUser = getUserIdByLogin(login);
        ps = cnx.prepareStatement("INSERT INTO eleve (id, user_id, nom, prenom, sexe, date_de_naissance, adresse, code_postal, ville, telephone,email) VALUES (NULL, "+idUser+", '"+unNom+"', '"+unPrenom+"', '"+unSexe+"', '"+uneDate+"', '"+uneAdresse+"', '"+unPostal+"', '"+uneVille+"', '"+unTelephone+"','"+unEmail+"')");
        ps.executeUpdate();
    }
    public Boolean siExisteLogin(String unLogin) throws SQLException {
        Boolean existeLogin = false;
        ps = cnx.prepareStatement("SELECT user.login FROM user WHERE user.login ='"+unLogin+"'");
        rs = ps.executeQuery();
        if (rs.next()){
            existeLogin = true;
        }
        return existeLogin;
    }

    public void addUser(String login, String password, int role) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO user(user.login, user.password, user.role)\n" +
                "VALUES(?, ?, ?)");
        ps.setString(1, login);
        ps.setString(2, password);
        ps.setInt(3, role);
        ps.executeUpdate();
    }
}
