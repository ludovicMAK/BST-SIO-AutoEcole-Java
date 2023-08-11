import Vues.Public.frmConnexion;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        frmConnexion frm = new frmConnexion();
        frm.setVisible(true);
    }
}