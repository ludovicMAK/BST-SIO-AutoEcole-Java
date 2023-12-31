package Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConnexionBDDCopie {
    private static Connection cnx;

    public ConnexionBDDCopie() throws ClassNotFoundException, SQLException {
        String user = "userName";
        String password = "mdp";
        String pilote = "com.mysql.jdbc.Driver";
        // chargement du pilote
        Class.forName(pilote);
        // L'objet connexion à la BDD avec le nom de la base, le user et le password
        cnx = DriverManager.getConnection("jdbc:mysql://localhost/bdd?serverTimezone="
                + TimeZone.getDefault().getID(), user, password);
    }
    public static Connection getCnx() {
        return cnx;
    }
}
