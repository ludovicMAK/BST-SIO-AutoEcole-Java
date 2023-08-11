package Vues.Gerante;

import Controllers.CategorieController;
import Controllers.VehiculeController;
import Tools.ModelJTableVehicule;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class frmGeranteModifierVehicule extends JFrame {
    private JPanel pnlRoot;
    private JTable tblVehicule;
    private JTextField txtImma;
    private JTextField txtMarque;
    private JTextField txtModele;
    private JTextField txtAnnee;
    private JComboBox cboSonCateg;
    private JButton btnValider;
    private JComboBox cboCategorie;
    private CategorieController categorieController;
    private ModelJTableVehicule modelVehicule;

    frmGeranteModifierVehicule() throws SQLException {
        this.setTitle("Modifier véhicule");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(600, 400);
        categorieController = new CategorieController();
        txtModele.setEnabled(false);
        txtAnnee.setEnabled(false);
        txtImma.setEnabled(false);
        txtMarque.setEnabled(false);
        btnValider.setEnabled(false);
        for (String categ:categorieController.getAllCategories()){
            cboCategorie.addItem(categ);
            cboSonCateg.addItem(categ);
        }
        VehiculeController mesVehicules = new VehiculeController();
        modelVehicule = new ModelJTableVehicule();
        modelVehicule.loadDataVehicule(mesVehicules.getAllVehicule(cboCategorie.getSelectedItem().toString()));
        tblVehicule.setModel(modelVehicule);


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

        cboCategorie.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                modelVehicule = new ModelJTableVehicule();
                try {
                    modelVehicule.loadDataVehicule(mesVehicules.getAllVehicule(cboCategorie.getSelectedItem().toString()));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                tblVehicule.setModel(modelVehicule);
            }

        });


        tblVehicule.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtModele.setEnabled(true);
                txtAnnee.setEnabled(true);
                txtImma.setEnabled(true);
                txtMarque.setEnabled(true);
                btnValider.setEnabled(true);
                txtImma.setText(tblVehicule.getValueAt(tblVehicule.getSelectedRow(), 1).toString());
                txtMarque.setText(tblVehicule.getValueAt(tblVehicule.getSelectedRow(), 2).toString());
                txtModele.setText(tblVehicule.getValueAt(tblVehicule.getSelectedRow(), 3).toString());
                txtAnnee.setText(tblVehicule.getValueAt(tblVehicule.getSelectedRow(), 4).toString());
                cboSonCateg.setSelectedItem(cboCategorie.getSelectedItem());

            }
        });
        btnValider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (txtImma.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Votre immatriculation ne peut pas être vide");
                }else if (txtMarque.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"La marque ne peut pas être vide");
                } else if (txtAnnee.getText().compareTo("")==0){
                    JOptionPane.showMessageDialog(null,"Veuillez saisir une année SVP");
                }else if(txtModele.getText().compareTo("")==0) {
                    JOptionPane.showMessageDialog(null,"Veuillez saisir un modèle svp");
                }else {
                    if (txtAnnee.getText().length()!=4){
                        JOptionPane.showMessageDialog(null,"Veuillez saisir une date à 4 chiffres");
                    }else {
                        try {
                            if(mesVehicules.SiexisteMatricule(txtImma.getText(),Integer.parseInt(tblVehicule.getValueAt(tblVehicule.getSelectedRow(),0).toString()))){
                                JOptionPane.showMessageDialog(null,"Il existe déja un véhicule avec cette matricule");
                            }else {
                                mesVehicules.modifierVehicule(txtImma.getText(),txtMarque.getText(),txtModele.getText(),txtAnnee.getText(),categorieController.getIdByLibelle(cboSonCateg.getSelectedItem().toString()),Integer.parseInt(tblVehicule.getValueAt(tblVehicule.getSelectedRow(),0).toString()));
                                JOptionPane.showMessageDialog(null,"Votre véhicule a bien été modifié");
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
    }
}
