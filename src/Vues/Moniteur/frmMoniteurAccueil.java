package Vues.Moniteur;

import Controllers.MoniteurController;
import Entities.Moniteur;
import Tools.ConnexionBDD;
import Vues.Public.frmConnexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class frmMoniteurAccueil extends JFrame {
    private JPanel pnlRoot;
    private JLabel lblBonjour;
    private JButton btnLicence;
    private JButton btnProfil;
    private JButton planningButton;
    private JButton btnStats;
    private JButton btnDeconnexion;

    private ConnexionBDD cnx;

    private MoniteurController moniteurController;

    public frmMoniteurAccueil(int idUser) throws SQLException, ClassNotFoundException {
        this.setTitle("Accueil moniteur");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);

        cnx = new ConnexionBDD();

        moniteurController = new MoniteurController();
        Moniteur moniteur = moniteurController.getPatronymeByIdUser(idUser);

        lblBonjour.setText("Bonjour, "+moniteur.getNom()+" "+moniteur.getPrenom()+"!");

        //Modifier profil
        btnProfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmMoniteurModifierProfil frm = null;
                try {
                    frm = new frmMoniteurModifierProfil(idUser);
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });

        //Planning
        planningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmMoniteurPlanning frm = null;
                try {
                    frm = new frmMoniteurPlanning(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });

        //Ajouter une licence
        btnLicence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmMoniteurAjouterLicence frm = null;
                try {
                    frm = new frmMoniteurAjouterLicence(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });

        //Stats
        btnStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmMoniteurStats frm = null;
                try {
                    frm = new frmMoniteurStats(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });

        //Déconnexion
        btnDeconnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmConnexion frm = null;
                try {
                    frm = new frmConnexion();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });
    }
}
