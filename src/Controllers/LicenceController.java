package Controllers;

import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LicenceController {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public LicenceController(){
        cnx = ConnexionBDD.getCnx();
    }

    public void addLicence(int idMoniteur, int idCategorie, String date) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO licence(licence.moniteur_id, licence.categorie_id, licence.date_obtention)\n" +
                "    VALUES (?, ?, ?)");
        ps.setInt(1, idMoniteur);
        ps.setInt(2, idCategorie);
        ps.setString(3, date);
        ps.executeUpdate();
    }
}
