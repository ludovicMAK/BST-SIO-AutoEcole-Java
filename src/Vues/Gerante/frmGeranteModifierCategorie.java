package Vues.Gerante;

import Controllers.CategorieController;
import Entities.Categorie;
import Tools.ModelJTableCategorie;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class frmGeranteModifierCategorie extends JFrame{
    private JPanel pnlRoot;
    private JTable tblCategories;
    private JTextField txtLibelle;
    private JTextField txtPrix;
    private JButton btnValider;
    private ModelJTableCategorie model;
    private CategorieController categorieController;

    private void data() throws SQLException {
        model.loadDataCategorie(categorieController.getCategories());
        tblCategories.setModel(model);
    }

    frmGeranteModifierCategorie() throws SQLException {
        this.setTitle("Modifier catégorie");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);

        model = new ModelJTableCategorie();

        categorieController = new CategorieController();

        //On charge les catégories dans le tableau
        data();

        //Choix de la catégorie à modifier
        tblCategories.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String libelle = tblCategories.getValueAt(tblCategories.getSelectedRow(), 0).toString();
                String prix = tblCategories.getValueAt(tblCategories.getSelectedRow(), 1).toString();
                txtLibelle.setText(libelle);
                txtPrix.setText(prix);
            }
        });

        //Validation du formulaire
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ok = true;
                //On vérifie que le libelle ne soit pas déjà existant
                //Si le champ est différent de la ligne
                String libelletxt = txtLibelle.getText();
                String libellerow = tblCategories.getValueAt(tblCategories.getSelectedRow(), 0).toString();
                if(libelletxt.compareTo(libellerow) != 0){
                    //Si le libelle n'est pas déjà une catégorie
                    try {
                        if(categorieController.isCategorie(libelletxt)){
                            ok = false;
                            JOptionPane.showMessageDialog(null, "Cette catégorie existe déjà.", "Erreur libelle", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                //On vérifie le format du prix
                String[] parts = txtPrix.getText().split("");
                for(String s: parts){
                    if(s.compareTo(",") == 0){
                        ok = false;
                        JOptionPane.showMessageDialog(null, "Si le prix contient une virgule, merci de la remplacer par un point.", "Erreur prix", JOptionPane.ERROR_MESSAGE);
                    }
                }

                //Si c'est bon on update la catégorie
                if(ok){
                    String libelle = txtLibelle.getText();
                    double prix = Double.parseDouble(txtPrix.getText());
                    int id = 0;
                    try {
                        id = categorieController.getIdByLibelle(libellerow);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Categorie categorie = new Categorie(id, libelle, prix);
                    try {
                        categorieController.updateCategorieById(categorie);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    //On update le tableau et on confirme
                    try {
                        data();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    txtLibelle.setText("");
                    txtPrix.setText("");
                    JOptionPane.showMessageDialog(null, "La catégorie a bien été modifiée.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
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
