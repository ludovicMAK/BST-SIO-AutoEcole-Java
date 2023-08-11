package Vues.Gerante;

import Controllers.CategorieController;
import Tools.ConnexionBDD;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class frmGeranteAjouterCategorie extends JFrame{
    private JPanel pnlRoot;
    private JTextField txtLibelle;
    private JTextField txtPrix;
    private JButton btnSoumettre;
    private ConnexionBDD cnx;
    private CategorieController categorieController;

    frmGeranteAjouterCategorie() throws SQLException, ClassNotFoundException {
        this.setTitle("Ajouter catégorie");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);

        cnx = new ConnexionBDD();

        categorieController = new CategorieController();

        btnSoumettre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //On vérifie les inputs
                if(txtLibelle.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un libellé.", "Erreur libellé", JOptionPane.ERROR_MESSAGE);
                }else if(txtPrix.getText().compareTo("") == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un prix.", "Erreur prix", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    //On vérifie que la catégorie n'existe pas déjà
                    boolean ok = true;
                    try {
                        if(categorieController.isCategorie(txtLibelle.getText())){
                            JOptionPane.showMessageDialog(null, "Cette catégorie existe déjà.", "Erreur catégorie", JOptionPane.ERROR_MESSAGE);
                            ok = false;
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    //On vérifie le format du prix
                    String[] parts = txtPrix.getText().split("");
                    for(String s: parts){
                        if(s.compareTo(",") == 0){
                            ok = false;
                            JOptionPane.showMessageDialog(null, "Si le prix contient une virgule, merci de la remplacer par un point.", "Erreur prix", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    //On ajoute la catégorie
                    if(ok){
                        String libelle = txtLibelle.getText();
                        double prix = Double.parseDouble(txtPrix.getText());
                        try {
                            categorieController.addCategorie(libelle, prix);
                            JOptionPane.showMessageDialog(null, "La catégorie a bien été ajoutée.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                            frmAccueilGerante frm = null;
                            frm = new frmAccueilGerante();
                            frm.setVisible(true);
                            dispose();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
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
