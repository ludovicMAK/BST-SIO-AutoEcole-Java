package Vues.Moniteur;

import Controllers.CategorieController;
import Controllers.LicenceController;
import Controllers.MoniteurController;
import Tools.ConnexionBDD;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class frmMoniteurAjouterLicence extends JFrame {
    private JPanel pnlRoot;
    private JComboBox cboPermis;
    private JPanel pnlDate;
    private JLabel lblAddLicence;
    private JButton btnValider;
    private JDateChooser cldDate;

    private ConnexionBDD cnx;

    private CategorieController categorieController;
    private LicenceController licenceController;
    private MoniteurController moniteurController;

    public frmMoniteurAjouterLicence(int idUser) throws SQLException, ClassNotFoundException {
        this.setTitle("Ajouter une licence");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(300, 150);

        cnx = new ConnexionBDD();

        categorieController = new CategorieController();
        licenceController = new LicenceController();
        moniteurController = new MoniteurController();

        //On remplie le cbo avec les catégories dont le moniteur ne possède pas de licence
        if(categorieController.getCategoriesMoniteurLicence(idUser).isEmpty()){
            JOptionPane.showMessageDialog(null, "Vous possédez déjà une licence pour chaque type de permis", "Erreur licence", JOptionPane.INFORMATION_MESSAGE);
        }
        cboPermis.addItem("Permis:");
        for(String c: categorieController.getCategoriesMoniteurLicence(idUser)){
            cboPermis.addItem(c);
        }

        //On setup le dateChooser
        cldDate = new JDateChooser();
        cldDate.setDateFormatString("yyyy-MM-dd");
        pnlDate.add(cldDate);


        //Retour en arrière
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                frmMoniteurAccueil frm = null;
                try {
                    frm = new frmMoniteurAccueil(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });

        //A la validation
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cboPermis.getSelectedIndex() == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez choisir un permis", "Erreur permis", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(cldDate.getDate() == null){
                        JOptionPane.showMessageDialog(null, "Veuillez choisir une date", "Erreur date", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        int idCategorie = 0;
                        try {
                            idCategorie = categorieController.getIdByLibelle(cboPermis.getSelectedItem().toString());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(cldDate.getDate()).toString();
                        try {
                            licenceController.addLicence(moniteurController.getIdMoniteurByIdUser(idUser), idCategorie, date);
                            JOptionPane.showMessageDialog(null, "Licence enregistrée", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            frmMoniteurAccueil frm = new frmMoniteurAccueil(idUser);
                            frm.setVisible(true);
                        } catch (SQLException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
    }
}
