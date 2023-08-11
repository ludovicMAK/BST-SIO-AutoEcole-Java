package Vues.Eleve;

import Controllers.CategorieController;
import Controllers.LeconController;
import Entities.Lecon;
import Tools.ConnexionBDD;
import Tools.ModelJTableLecon;
import Vues.Moniteur.frmMoniteurAccueil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class frmEleveStats extends JFrame {
    private JPanel pnlRoot;
    private JComboBox<String> cboCategories;
    private JLabel lblPrixTotal;
    private JTable tblLecons;
    private JLabel lblMontantRestant;
    private JPanel pnlGraphique;
    private JLabel lblPourcentage;

    private ConnexionBDD cnx;

    private CategorieController categorieController;
    private LeconController leconController;

    private ModelJTableLecon model;

    public frmEleveStats(int idUser) throws SQLException, ClassNotFoundException {
        this.setTitle("Statistiques");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(600, 500);

        cnx = new ConnexionBDD();

        categorieController = new CategorieController();
        leconController = new LeconController();

        model = new ModelJTableLecon();

        cboCategories.addItem("Catégories:");
        for(String c: categorieController.getCategoriesByIdEleve(idUser)){
            cboCategories.addItem(c.toString());
        }

        model.loadDefautLecon();
        tblLecons.setModel(model);

        cboCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cboCategories.getSelectedIndex() != 0){
                    //Prix total
                    int idCategorie = 0;
                    try {
                        idCategorie = categorieController.getIdByLibelle(cboCategories.getSelectedItem().toString());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    double prix = 0;
                    try {
                        prix = categorieController.getPrixByIdCategorie(idCategorie) * leconController.getNbLeconByIdEleve_IdCategorie(idUser, idCategorie);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    prix = Math.round(prix * 100.0) / 100.0;
                    lblPrixTotal.setText("Montant total de votre permis: "+prix+"€");

                    //Tableau des leçons
                    try {
                        model.loadDataLecon(leconController.getLeconByIdEleve_IdCategorie(idUser, idCategorie));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    tblLecons.setModel(model);

                    //Montant restant
                    double montant = 0;
                    try {
                        montant = categorieController.getPrixByIdCategorie(idCategorie) * leconController.getNbLeconNonPayeeByIdEleve_IdCategorie(idUser, idCategorie);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    montant = Math.round(montant * 100.0) / 100.0;
                    lblMontantRestant.setText("Montant restant à payer: "+montant+"€");


                    pnlGraphique.removeAll();
                    //Graphique
                    DefaultCategoryDataset donnees = new DefaultCategoryDataset();
                    //On remplit le dataset
                    int nbLecons = 0;
                    try {
                        nbLecons = leconController.getNbLeconByIdEleve_IdCategorie(idUser, idCategorie);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    int nbLeconsNonPayees;
                    try {
                        nbLeconsNonPayees = leconController.getNbLeconNonPayeeByIdEleve_IdCategorie(idUser, idCategorie);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    int nbLeconsPayees = nbLecons-nbLeconsNonPayees;
                    donnees.addValue(nbLeconsPayees, "", "Payées");
                    donnees.addValue(nbLeconsNonPayees, "", "Non payées");
                    //On créer le graphique
                    JFreeChart chart = ChartFactory.createBarChart(
                            "Statut du payement des leçons",
                            "Leçons",
                            "Nombre de leçons",
                            donnees,
                            PlotOrientation.VERTICAL, false, false, false
                    );
                    ChartPanel graph = new ChartPanel(chart);
                    pnlGraphique.add(graph);
                    pnlGraphique.validate();

                    int nbLeconsPassees = 0;
                    try {
                        for(Lecon l: leconController.getLeconByIdEleve_IdCategorie(idUser, idCategorie)){
                            if(l.isPassee()){
                                nbLeconsPassees++;
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    double pourcent = Math.round(nbLeconsPassees*100/nbLecons);
                    lblPourcentage.setText("Vous avez passé "+pourcent+"% de votre permis");
                }
            }
        });

        //Retour en arrière
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
    }
}
