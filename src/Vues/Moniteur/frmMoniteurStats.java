package Vues.Moniteur;

import Controllers.CategorieController;
import Controllers.LeconController;
import Entities.Lecon;
import Tools.ConnexionBDD;
import Vues.Eleve.frmAccueilEleve;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class frmMoniteurStats extends JFrame {
    private JPanel pnlRoot;
    private JComboBox cboCategories;
    private JPanel pnlCa;
    private JPanel pnlLecons;

    private ConnexionBDD cnx;

    private CategorieController categorieController;
    private LeconController leconController;

    public frmMoniteurStats(int idUser) throws SQLException, ClassNotFoundException {
        this.setTitle("Statistiques");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);

        cnx = new ConnexionBDD();

        categorieController = new CategorieController();
        leconController = new LeconController();

        cboCategories.addItem("Catégories:");
        for(String c: categorieController.getCategoriesByIdMoniteur(idUser)){
            cboCategories.addItem(c);
        }

        cboCategories.addComponentListener(new ComponentAdapter() {
        });
        cboCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cboCategories.getSelectedIndex() != 0){
                    int idCategorie = 0;
                    try {
                        idCategorie = categorieController.getIdByLibelle(cboCategories.getSelectedItem().toString());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    pnlCa.removeAll();
                    //Graphique CA
                    DefaultCategoryDataset donnees = new DefaultCategoryDataset();
                    //On remplit le dataset
                    //Jour
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String now = LocalDateTime.now().format(dtf);
                    int nbLecons = 0;
                    try {
                        nbLecons = leconController.getNbLeconByIdMoniteur_IdCategorie(idUser, idCategorie, now, now);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    double prixCategorie = 0;
                    try {
                        prixCategorie = categorieController.getPrixByIdCategorie(categorieController.getIdByLibelle(cboCategories.getSelectedItem().toString()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    double ca = nbLecons*prixCategorie;
                    donnees.addValue(ca, "", "Jour");
                    //Semaine
                    String debut = LocalDateTime.now().minusDays(7).format(dtf).toString();
                    try {
                        nbLecons = leconController.getNbLeconByIdMoniteur_IdCategorie(idUser, idCategorie, debut, now);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    ca = nbLecons*prixCategorie;
                    donnees.addValue(ca, "", "Semaine");
                    //Mois
                    debut = LocalDateTime.now().minusMonths(1).format(dtf).toString();
                    try {
                        nbLecons = leconController.getNbLeconByIdMoniteur_IdCategorie(idUser, idCategorie, debut, now);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    ca = nbLecons*prixCategorie;
                    donnees.addValue(ca, "", "Mois");
                    //Trimestre
                    debut = LocalDateTime.now().minusMonths(3).format(dtf).toString();
                    try {
                        nbLecons = leconController.getNbLeconByIdMoniteur_IdCategorie(idUser, idCategorie, debut, now);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    ca = nbLecons*prixCategorie;
                    donnees.addValue(ca, "", "Trimestre");
                    //On créer le graphique
                    JFreeChart chart = ChartFactory.createBarChart(
                            "Chiffre d'affaire",
                            "Durée",
                            "Chiffres d'affaire",
                            donnees,
                            PlotOrientation.VERTICAL, false, true, false
                    );
                    ChartPanel graph = new ChartPanel(chart);
                    pnlCa.add(graph);
                    pnlCa.validate();








                    pnlLecons.removeAll();
                    //Graphique CA
                    DefaultCategoryDataset donnees2 = new DefaultCategoryDataset();
                    //On remplit le dataset
                    //Jour
                    nbLecons = 0;
                    try {
                        nbLecons = leconController.getNbLeconByIdMoniteur_IdCategorie(idUser, idCategorie, now, now);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    donnees2.addValue(nbLecons, "", "Jour");
                    //Semaine
                    debut = LocalDateTime.now().minusDays(7).format(dtf).toString();
                    try {
                        nbLecons = leconController.getNbLeconByIdMoniteur_IdCategorie(idUser, idCategorie, debut, now);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    donnees2.addValue(nbLecons, "", "Semaine");
                    //Mois
                    debut = LocalDateTime.now().minusMonths(1).format(dtf).toString();
                    try {
                        nbLecons = leconController.getNbLeconByIdMoniteur_IdCategorie(idUser, idCategorie, debut, now);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    donnees2.addValue(nbLecons, "", "Mois");
                    //Trimestre
                    debut = LocalDateTime.now().minusMonths(3).format(dtf).toString();
                    try {
                        nbLecons = leconController.getNbLeconByIdMoniteur_IdCategorie(idUser, idCategorie, debut, now);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    donnees2.addValue(nbLecons, "", "Trimestre");
                    //On créer le graphique
                    JFreeChart chart2 = ChartFactory.createBarChart(
                            "Nombre de leçons",
                            "Durée",
                            "Nombre de leçons",
                            donnees2,
                            PlotOrientation.VERTICAL, false, true, false
                    );
                    ChartPanel graph2 = new ChartPanel(chart2);
                    pnlLecons.add(graph2);
                    pnlLecons.validate();
                }
            }
        });



        //Retour en arrière
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                frmMoniteurAccueil frm = null;
                try {
                    frm = new frmMoniteurAccueil(idUser);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                frm.setVisible(true);
                dispose();
            }
        });
    }
}
