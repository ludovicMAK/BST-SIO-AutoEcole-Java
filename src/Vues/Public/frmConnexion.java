package Vues.Public;

import Vues.Gerante.frmAccueilGerante;
import Vues.Moniteur.frmMoniteurAccueil;
import Vues.Eleve.frmAccueilEleve;

import Controllers.UserController;
import Entities.User;
import Tools.ConnexionBDD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class frmConnexion extends JFrame{
    private JLabel lblLogin;
    private JTextField txtLogin;
    private JLabel lblMotDePasse;
    private JPanel pnlRoot;
    private JButton btnConnexion;
    private JButton btnInscription;
    private JPasswordField txtPassword;

    private ConnexionBDD cnx;

    private UserController userController;

    public frmConnexion() throws SQLException, ClassNotFoundException {
        this.setTitle("Connexion");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);

        cnx = new ConnexionBDD();

        userController = new UserController();
        //Vers frmInscriptionEleve
        btnInscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmInscriptionEleve frm = null;
                try {
                    frm = new frmInscriptionEleve();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });
        //Vers frmAccueil selon le rôle
        btnConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtLogin.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez entrer votre login", "Erreur login", JOptionPane.ERROR_MESSAGE);
                } else if (txtPassword.getText().compareTo("") == 0) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer votre mot de passe", "Erreur mot de passe", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    String login = txtLogin.getText();
                    String password = txtPassword.getText();
                    //Si les identifiants sont invalides
                    try {
                        if(userController.getUserByLogin_Password(login, password).getId() == 0){
                            JOptionPane.showMessageDialog(null, "Login et/ou mot de passe invalides", "Erreur connexion", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            User user = new User();
                            user = userController.getUserByLogin_Password(login, password);
                            //Si c'est la gérante
                            if(user.getRole() == 0){
                                frmAccueilGerante frm = new frmAccueilGerante();
                                frm.setVisible(true);
                            }
                            //Si c'est un moniteur
                            if(user.getRole() == 1){
                                frmMoniteurAccueil frm = new frmMoniteurAccueil(user.getId());
                                frm.setVisible(true);
                            }
                            //Si c'est un élève
                            if(user.getRole() == 2){
                                frmAccueilEleve frm = new frmAccueilEleve(user.getId());
                                frm.setVisible(true);
                            }
                            dispose();
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}
