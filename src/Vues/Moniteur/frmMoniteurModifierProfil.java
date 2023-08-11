package Vues.Moniteur;

import Controllers.MoniteurController;
import Controllers.UserController;
import Entities.Moniteur;
import Tools.ConnexionBDD;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class frmMoniteurModifierProfil extends JFrame {
    private JPanel pnlRoot;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtAdresse;
    private JTextField txtVille;
    private JTextField txtPostal;
    private JTextField txtTel;
    private JPasswordField txtMdp;
    private JComboBox cboSexe;
    private JButton btnValider;
    private JLabel lblNom;
    private JLabel lblPrenom;
    private JLabel lblAdresse;
    private JLabel lblPostal;
    private JLabel lblVile;
    private JLabel lblTelephone;
    private JLabel lblMdp;
    private JLabel lblGenre;
    private JPanel pnlDate;
    private JTextField txtMail;
    private JPasswordField txtConfirmerMdp;
    private JLabel lblConfirmerMdp;
    private JLabel lblMail;
    private JDateChooser cldDate;
    private Moniteur monMoniteur;
    private MoniteurController moniteurController;
    private String[][] data;

    private ConnexionBDD cnx;

    public frmMoniteurModifierProfil(int idUser) throws SQLException, ClassNotFoundException, ParseException {
        this.setTitle("Modifier profil");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);

        cldDate = new JDateChooser();
        pnlDate.add(cldDate);

        cnx = new ConnexionBDD();
        UserController monUser = new UserController();
        cboSexe.addItem("Homme");
        cboSexe.addItem("Femme");
        cboSexe.addItem("Autre");
        moniteurController = new MoniteurController();
        monMoniteur = moniteurController.getPatronymeByIdUser(idUser);
        txtMail.setText(monMoniteur.getEmail());
        txtNom.setText(monMoniteur.getNom());
        txtPrenom.setText(monMoniteur.getPrenom());
        txtAdresse.setText(monMoniteur.getAdresse());
        txtTel.setText(monMoniteur.getTelephone());
        txtPostal.setText(monMoniteur.getCodePostal());
        txtVille.setText(monMoniteur.getVille());
        txtMdp.setText(monMoniteur.getMdp());
        txtConfirmerMdp.setText(monMoniteur.getMdp());
        cboSexe.setSelectedItem(monMoniteur.getSexe());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cldDate.setDate(sdf.parse(monMoniteur.getDateDeNaissance()));

        btnValider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                boolean mdpCorrespondent = txtMdp.getText().equals(txtConfirmerMdp.getText());
                if (txtPrenom.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Votre champ prénom est vide");
                }
                else if (txtNom.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un nom.");
                }
                else if (txtAdresse.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer une adresse.");
                }
                else if (txtPostal.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un code postal.");
                }
                else if (txtVille.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez insérer le nom de votre ville de résidence.");
                }
                else if (txtTel.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un numéro de téléphone.");
                }
                else if (txtMdp.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un mot de passe.");
                } else if (txtConfirmerMdp.getText().compareTo("")==0) {
                    JOptionPane.showMessageDialog(null, "Veuillez confirmer votre mot de passe.");
                } else if (txtMail.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Votre champ email est vide");
                } else if (!mdpCorrespondent) {
                    JOptionPane.showMessageDialog(null, "Les deux mots de passe doivent être identiques.");
                } else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(cldDate.getDate()).toString();
                    String data[][] = {{"nom",txtNom.getText()},{"prenom",txtPrenom.getText()},{"adresse", txtAdresse.getText()},{"code_postal",txtPostal.getText()},{"ville",txtVille.getText()},{"telephone",txtTel.getText()},{"date_de_naissance", date},{"email", txtMail.getText()},{"sexe",cboSexe.getSelectedItem().toString()}};
                    String dataMod =" ";
                    for(String[] valeur:data){
                        if (valeur == data[8]){
                            dataMod += valeur[0]+"= '"+valeur[1]+"' ";

                        }
                        else {
                            dataMod += valeur[0]+"= '"+valeur[1]+"' , ";
                        }
                    }
                    try {
                        moniteurController.modifierInformationMoniteur(dataMod,idUser,txtMdp.getText());

                        JOptionPane.showMessageDialog(null,"Votre profil a été bien modifié");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
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
    }
}
