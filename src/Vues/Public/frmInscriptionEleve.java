package Vues.Public;

import Controllers.UserController;
import Tools.ConnexionBDD;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class frmInscriptionEleve extends JFrame{
    private JTextField txtNom;
    private JComboBox cboSexe;
    private JTextField txtPrenom;
    private JTextField txtAdresse;
    private JTextField txtCodePostal;
    private JTextField txtVille;
    private JTextField txtTelephone;
    private JTextField txtLogin;
    private JTextField txtMail;
    private JPasswordField txtMotDePasse;
    private JPasswordField txtConfirmerMotDePasse;
    private JButton btnValider;
    private JPanel pnlRoot;
    private JLabel lblPrénom;
    private JLabel lblAdresse;
    private JLabel lblVille;
    private JLabel lblLogin;
    private JLabel lblMotDePasse;
    private JLabel lblSexe;
    private JLabel lblDateDeNaissance;
    private JLabel lblCodePostal;
    private JLabel lblTelephone;
    private JLabel lblMail;
    private JLabel lblConfirmerMotDePasse;
    private JLabel lblNom;
    private JPanel pnlDateDeNaissance;
    private JDateChooser cldDateDeNaissance;

    private ConnexionBDD cnx;

    private String nom;
    private String prenom;
    private String adresse;
    private String ville;
    private String login;
    private String motDePasse;
    private String codePostal;
    private String telephone;
    private String confirmationMotDePasse;
    private String dateDeNaissance;
    private UserController monUser;

    private String sexe;


    public frmInscriptionEleve() throws SQLException {


        this.setTitle("Inscription");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);

        cldDateDeNaissance = new JDateChooser();
        cldDateDeNaissance.setDateFormatString("dd/MM/yyyy");
        pnlDateDeNaissance.add(cldDateDeNaissance);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        monUser=new UserController();


        cboSexe.addItem("Homme");
        cboSexe.addItem("Femme");
        cboSexe.addItem("Autre");


        btnValider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                boolean frmRempli = true;
                boolean mdpCorrespondent = txtMotDePasse.getText().equals(txtConfirmerMotDePasse.getText());

                if (txtPrenom.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Votre champ prénom est vide");
                }
                else if (txtNom.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Votre champ nom est vide");
                }
                else if (txtAdresse.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez indiquer votre adresse.");
                }
                else if (txtCodePostal.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez indiquer votre code postal.");
                }
                else if (txtVille.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer votre ville de résidence.");
                }
                else if (txtTelephone.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer votre numéro de téléphone.");
                }
                else if (txtMotDePasse.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Votre champ mot de passe est vide");
                } else if (txtConfirmerMotDePasse.getText().compareTo("")==0) {
                    JOptionPane.showMessageDialog(null, "Veuillez confirmer votre mot de passe.");
                }
                else if (txtMail.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer votre courriel.");
                }else if (txtLogin.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer votre login.");
                } else if (cldDateDeNaissance.getDate() ==null){
                    JOptionPane.showMessageDialog(null,"Veuillez indiquer une date de naissance.");
                }
                else if (!mdpCorrespondent){
                    JOptionPane.showMessageDialog(null,"Les deux mots de passe doivent être identiques.");
                }else {
                    try {
                        if (monUser.siExisteLogin(txtLogin.getText())){
                            JOptionPane.showMessageDialog(null,"Votre login est déjà pris");
                        }else {
                            if (txtCodePostal.getText().toCharArray().length>5){
                                JOptionPane.showMessageDialog(null,"Le format de votre code postal dépasse 5 chiffes");
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String date = sdf.format(cldDateDeNaissance.getDate()).toString();
                            try {
                                monUser.inscription(txtLogin.getText(),txtMotDePasse.getText(),txtNom.getText(),txtPrenom.getText(),cboSexe.getSelectedItem().toString(),date,txtAdresse.getText(),txtCodePostal.getText(),txtVille.getText(),txtTelephone.getText(),txtMail.getText());
                                JOptionPane.showMessageDialog(null,"Vous êtes bien inscrit !");
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }


                }

        });
    }
}
