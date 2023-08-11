package Vues.Eleve;

import Controllers.EleveController;
import Entities.Eleve;
import Tools.ConnexionBDD;
import Vues.Public.frmConnexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class frmAccueilEleve extends JFrame{
    private JPanel pnlRoot;
    private JLabel txtBonjourNom;
    private JButton btnModifierProfil;
    private JButton btnPlanning;
    private JButton btnInscriptionLecon;
    private JButton btnStatistiques;
    private JButton btnDeconnexion;
    private JLabel lblBonjour;

    private ConnexionBDD cnx;

    private EleveController eleveController;

    public frmAccueilEleve(int idUser) throws SQLException, ClassNotFoundException {
        this.setTitle("Accueil élève");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);

        cnx = new ConnexionBDD();

        eleveController = new EleveController();

        Eleve eleve = eleveController.getPatronymeByIdUser(idUser);

        lblBonjour.setText("Bonjour, "+eleve.getNom()+" "+eleve.getPrenom()+"!");

        //Deconnexion
        btnDeconnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    frmConnexion frm = new frmConnexion();
                    frm.setVisible(true);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Modifier profil
        btnModifierProfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmModifierProfil frm = null;
                try {
                    frm = new frmModifierProfil(idUser);
                    frm.setVisible(true);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Planning
        btnPlanning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmElevePlanning frm = null;
                try {
                    frm = new frmElevePlanning(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });

        //Inscription leçon
        btnInscriptionLecon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmInsciptionLeconEleve frm = null;
                try {
                    frm = new frmInsciptionLeconEleve(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });

        //Statistiques
        btnStatistiques.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frmEleveStats frm = null;
                try {
                    frm = new frmEleveStats(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
            }
        });
    }
}
