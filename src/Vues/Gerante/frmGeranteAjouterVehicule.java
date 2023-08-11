package Vues.Gerante;

import Controllers.CategorieController;
import Controllers.VehiculeController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class frmGeranteAjouterVehicule extends JFrame {
    private JPanel pnlRoot;
    private JTextField txtImma;
    private JTextField txtMarque;
    private JTextField txtAnnee;
    private VehiculeController vehiculeController;
    private JComboBox cboCategorie;
    private JButton btnValider;
    private JTextField txtModele;
    private CategorieController categorieController;

    public frmGeranteAjouterVehicule() throws SQLException {
        this.setTitle("Ajouter un véhicule");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);
        categorieController = new CategorieController();
        vehiculeController = new VehiculeController();

        for (String categ:categorieController.getAllCategories()){
            cboCategorie.addItem(categ);
        }
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
                            vehiculeController.insertVehicule(categorieController.getIdByLibelle(cboCategorie.getSelectedItem().toString()),txtImma.getText(),txtMarque.getText(),txtModele.getText(),txtAnnee.getText());
                            JOptionPane.showMessageDialog(null,"Votre véhicule a été bien ajouté");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
    }
}
