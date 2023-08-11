package Vues.Eleve;

import Controllers.EleveController;
import Controllers.UserController;
import Entities.Eleve;
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

public class frmModifierProfil extends JFrame{
    private JPanel pnlRoot;
    private JTextField txtNom;
    private JLabel lblNom;
    private JLabel lblSexe;
    private JComboBox cboSexe;
    private JLabel lblPrénom;
    private JLabel lblDateDeNaissance;
    private JTextField txtPrenom;
    private JLabel lblAdresse;
    private JLabel lblCodePostal;
    private JTextField txtAdresse;
    private JTextField txtCodePostal;
    private JLabel lblVille;
    private JLabel lblTelephone;
    private JTextField txtVille;
    private JTextField txtTelephone;
    private JLabel lblMotDePasse;
    private JPasswordField txtMotDePasse;
    private JButton btnValider;
    private JTextField txtMail;
    private JPanel pnlDate;
    private JPasswordField txtConfirmerMotDePasse;
    private JDateChooser cldDate;
    private EleveController eleveController;
    private Eleve monEleve;

    private ConnexionBDD cnx;

    frmModifierProfil(int idUser) throws SQLException, ClassNotFoundException, ParseException {
        this.setTitle("Profil");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(700, 400);
        UserController monUser = new UserController();
        cboSexe.addItem("Homme");
        cboSexe.addItem("Femme");
        cboSexe.addItem("Autre");
        cldDate = new JDateChooser();
        pnlDate.add(cldDate);

        cnx = new ConnexionBDD();
        eleveController = new EleveController();
        monEleve = eleveController.getPatronymeByIdUser(idUser);
        txtMail.setText(monEleve.getEmail());
        txtNom.setText(monEleve.getNom());
        txtPrenom.setText(monEleve.getPrenom());
        txtAdresse.setText(monEleve.getAdresse());
        txtTelephone.setText(monEleve.getTelephone());
        txtCodePostal.setText(monEleve.getCodePostal());
        txtVille.setText(monEleve.getVille());
        txtMotDePasse.setText(monEleve.getMdp());
        txtConfirmerMotDePasse.setText(monEleve.getMdp());
        cboSexe.setSelectedItem(monEleve.getSexe());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cldDate.setDate(sdf.parse(monEleve.getDateDeNaissance()));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                frmAccueilEleve frm = null;
                try {
                    frm = new frmAccueilEleve(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });
        btnValider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                boolean mdpCorrespondent = txtMotDePasse.getText().equals(txtConfirmerMotDePasse.getText());
                if (txtPrenom.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer votre prénom.");
                }
                else if (txtNom.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer votre nom.");
                }
                else if (txtAdresse.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer une adresse.");
                }
                else if (txtCodePostal.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un code postal.");
                }
                else if (txtVille.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Votre champ ville est vide");
                }
                else if (txtTelephone.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un numéro de téléphone.");
                }
                else if (txtMotDePasse.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un mot de passe.");
                } else if (txtConfirmerMotDePasse.getText().compareTo("")==0) {
                    JOptionPane.showMessageDialog(null, "Veuillez confirmer votre mot de passe.");
                } else if (txtMail.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez entrer une adresse e-mail.");
                } else if (!mdpCorrespondent) {
                    JOptionPane.showMessageDialog(null, "Les deux mots de passe doivent être identiques.");
                } else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(cldDate.getDate()).toString();
                    String data[][] = {{"nom",txtNom.getText()},{"prenom",txtPrenom.getText()},{"adresse",txtAdresse.getText()},{"code_postal",txtCodePostal.getText()},{"ville",txtVille.getText()},{"telephone",txtTelephone.getText()},{"date_de_naissance", date},{"email", txtMail.getText()},{"sexe", cboSexe.getSelectedItem().toString()}};
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
                        eleveController.modifierInformationEleve(dataMod,idUser,txtMotDePasse.getText());
                        JOptionPane.showMessageDialog(null, "Votre profil a été bien modifié");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }





}
