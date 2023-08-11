package Vues.Gerante;

import Controllers.MoniteurController;
import Controllers.UserController;
import Tools.ConnexionBDD;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class frmAjouterMoniteur extends JFrame{
    private JPanel pnlRoot;
    private JTextField txtNom;
    private JLabel lblNom;
    private JLabel lblSexe;
    private JComboBox cboSexe;
    private JLabel lblPrenom;
    private JLabel lblDateDeNaissance;
    private JPanel pnlDate;
    private JTextField txtPrenom;
    private JLabel lblAdresse;
    private JLabel lblCodePostal;
    private JTextField txtAdresse;
    private JTextField txtCodePostal;
    private JLabel lblVille;
    private JLabel lblTelephone;
    private JTextField txtVille;
    private JTextField txtTelephone;
    private JLabel lblLogin;
    private JLabel lblMail;
    private JTextField txtLogin;
    private JTextField txtMail;
    private JLabel lblMotDePasse;
    private JLabel lblConfirmerMotDePasse;
    private JButton btnValider;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JDateChooser dcDate;
    private ConnexionBDD cnx;
    private UserController userController;
    private MoniteurController moniteurController;

    frmAjouterMoniteur() throws SQLException, ClassNotFoundException {
        this.setTitle("Ajouter moniteur");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 340);

        cnx = new ConnexionBDD();

        userController = new UserController();
        moniteurController = new MoniteurController();

        //On rempli la cbo des genres
        cboSexe.addItem("Homme");
        cboSexe.addItem("Femme");
        cboSexe.addItem("Autre");

        //On rempli le date choser
        dcDate = new JDateChooser();
        dcDate.setDateFormatString("yyyy-MM-dd");
        pnlDate.add(dcDate);

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //On vérifie les inputs
                if(txtNom.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un nom.", "Erreur nom", JOptionPane.ERROR_MESSAGE);
                } else if(txtPrenom.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un prénom.", "Erreur prénom", JOptionPane.ERROR_MESSAGE);
                } else if(txtAdresse.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir une adresse.", "Erreur adresse", JOptionPane.ERROR_MESSAGE);
                } else if(txtVille.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir une ville.", "Erreur ville", JOptionPane.ERROR_MESSAGE);
                }else if(dcDate.getDate() == null){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir une date de naissance.", "Erreur date de naissance", JOptionPane.ERROR_MESSAGE);
                }else if(txtCodePostal.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un code postal.", "Erreur code postal", JOptionPane.ERROR_MESSAGE);
                }else if(txtTelephone.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un téléphone.", "Erreur téléphone", JOptionPane.ERROR_MESSAGE);
                }else if(txtLogin.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un login.", "Erreur login", JOptionPane.ERROR_MESSAGE);
                }else if(txtMail.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un email.", "Erreur email", JOptionPane.ERROR_MESSAGE);
                }else if(txtPassword.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un mot de passe.", "Erreur mot de passe", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    //Tout est rempli
                    boolean ok = false;
                    //On vérifie la disponibilité du login
                    try {
                        if(userController.siExisteLogin(txtLogin.getText())){
                            ok = false;
                            JOptionPane.showMessageDialog(null, "Ce login est déjà utilisé", "Erreur login", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            ok = true;
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    //On vérifie si les mots de passe sont identiques
                    if(ok){
                        if(txtPassword.getText().compareTo(txtConfirmPassword.getText()) == 0){
                            ok = true;
                        }
                        else{
                            ok = false;
                            JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas", "Erreur mot de passe", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    //Si tout est bon
                    if(ok){
                        String login = txtLogin.getText();
                        String password = txtPassword.getText();
                        //On ajoute l'user
                        try {
                            userController.addUser(login, password, 1);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        //On ajoute le moniteur
                        int idUser = 0;
                        try {
                            idUser = userController.getUserIdByLogin(login);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        String nom = txtNom.getText();
                        String prenom = txtPrenom.getText();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(dcDate.getDate()).toString();
                        String genre = cboSexe.getSelectedItem().toString();
                        String adresse = txtAdresse.getText();
                        String ville = txtVille.getText();
                        String codePostal = txtCodePostal.getText();
                        String telephone = txtTelephone.getText();
                        String email = txtMail.getText();
                        try {
                            moniteurController.addMoniteur(idUser, nom, prenom, genre, date, adresse, ville, codePostal, telephone, email);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        JOptionPane.showMessageDialog(null, "Le moniteur est inscrit.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                        frmAccueilGerante frm = null;
                        frm = new frmAccueilGerante();
                        frm.setVisible(true);
                        dispose();
                    }
                }
            }
        });

        //Retour en arrière
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                frmAccueilGerante frm = null;
                frm = new frmAccueilGerante();
                frm.setVisible(true);
                dispose();
            }
        });
    }
}
