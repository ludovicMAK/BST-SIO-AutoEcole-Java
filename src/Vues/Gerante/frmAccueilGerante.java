package Vues.Gerante;

import Vues.Public.frmConnexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class frmAccueilGerante extends JFrame{
    private JButton btnAjouterVehicule;
    private JButton btnModifierVehicule;
    private JButton btnAjouterCategorie;
    private JButton btnModifierCategorie;
    private JButton btnAjouterMoniteur;
    private JButton btnPlanning;
    private JButton btnStatistiques;
    private JButton btnDeconnexion;
    private JLabel lblBonjour;
    private JPanel pnlRoot;

    public frmAccueilGerante(){
        this.setTitle("Accueil gérante");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);


        btnAjouterVehicule.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frmGeranteAjouterVehicule frm = null;
                try {
                    frm = new frmGeranteAjouterVehicule();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });

        btnModifierVehicule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmGeranteModifierVehicule frm = null;
                try {
                    frm = new frmGeranteModifierVehicule();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });

        btnAjouterCategorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmGeranteAjouterCategorie frm = null;
                try {
                    frm = new frmGeranteAjouterCategorie();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });

        btnModifierCategorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmGeranteModifierCategorie frm = null;
                try {
                    frm = new frmGeranteModifierCategorie();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });

        btnAjouterMoniteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmAjouterMoniteur frm = null;
                try {
                    frm = new frmAjouterMoniteur();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });

        btnPlanning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmGerantePlanningLecons frm = null;
                try {
                    frm = new frmGerantePlanningLecons();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });

        btnStatistiques.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmGeranteStats frm = null;
                try {
                    frm = new frmGeranteStats();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });

        btnDeconnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmConnexion frm = null;
                try {
                    frm = new frmConnexion();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });
    }
}
