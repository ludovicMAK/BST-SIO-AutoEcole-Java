package Vues.Gerante;

import Controllers.CategorieController;
import Controllers.LeconController;
import Controllers.MoniteurController;
import Controllers.VehiculeController;
import Entities.Moniteur;
import Entities.Vehicule;
import Tools.ModelJTableMoniteurForStatGerante;
import Tools.ModelJTableVehiculesForStatGerante;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Map;

public class frmGeranteStats extends JFrame {
    private JPanel pnlRoot;
    private JTable tblVehicules;
    private JLabel lblVehiculePlusUtilise;
    private JPanel pnlGraphique;
    private JTable tblMoniteur;
    private JLabel lblMoniteurPlusSolicite;
    private JPanel pnlGraphCateg;
    private ModelJTableVehiculesForStatGerante modelJTableVehicule;
    private VehiculeController vehiculeController;
    private LeconController leconController;
    private ModelJTableMoniteurForStatGerante modelJTableMoniteurForStatGerante;
    private MoniteurController moniteurController;
    private CategorieController categorieController;

    frmGeranteStats() throws SQLException {
        this.setTitle("Statistiques");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);
        vehiculeController = new VehiculeController();
        Integer maxNbLecon = 0;
        String leVehicule = "";
        Boolean siPlusieurs = false;
        for(Vehicule vh:vehiculeController.lstVehicules()){
            if (maxNbLecon<vh.getNbLecon()){
                maxNbLecon = vh.getNbLecon();
                leVehicule = vh.getMarque()+" "+vh.getModele()+" "+vh.getAnnee()+" avec la matriculation "+vh.getImma();
                siPlusieurs = false;
            } else if(maxNbLecon == vh.getNbLecon()) {
                leVehicule +="et la  "+vh.getMarque()+" "+vh.getModele()+" "+vh.getAnnee()+" avec la matriculation "+vh.getImma();
                siPlusieurs = true;
            }
        }
        if (siPlusieurs){
            lblVehiculePlusUtilise.setText("Les véhicules le plus utilisé sont la "+leVehicule+" avec "+maxNbLecon+" leçons");
        }else {
            lblVehiculePlusUtilise.setText("Le véhicule le plus utilisé est la "+leVehicule+" avec "+maxNbLecon+" leçons");
        }


        modelJTableVehicule = new ModelJTableVehiculesForStatGerante();
        modelJTableVehicule.loadDataVehicule(vehiculeController.lstVehicules());
        tblVehicules.setModel(modelJTableVehicule);

        //Graphique
        DefaultCategoryDataset donnees = new DefaultCategoryDataset();
        leconController = new LeconController();

        //On remplit le dataset
        //On utilise un switch case car sans ça les donnees ne sont pas dans le bon ordre
        double dataAuj = 0;
        double dataSem = 0;
        double dataMoi = 0;
        double dataTri = 0;
        for(Map.Entry valeur: leconController.gerantChiffreAffaireData().entrySet()){
            switch(valeur.getKey().toString()){
                case "auj":
                    dataAuj = Double.parseDouble(valeur.getValue().toString());
                    break;
                case "semaine":
                    dataSem = Double.parseDouble(valeur.getValue().toString());
                    break;
                case "mois":
                    dataMoi = Double.parseDouble(valeur.getValue().toString());
                    break;
                case "trimestre":
                    dataTri = Double.parseDouble(valeur.getValue().toString());
                    break;
            }
            /*donnees.setValue(Double.parseDouble(valeur.getValue().toString()), "",valeur.getKey().toString());*/
        }
        donnees.setValue(dataAuj, "", "Jour");
        donnees.setValue(dataSem, "", "Semaine");
        donnees.setValue(dataMoi, "", "Mois");
        donnees.setValue(dataTri, "", "Trimestre");

        JFreeChart chart = ChartFactory.createBarChart(
                "Chiffre d'affaire",
                "Leçons",
                "Montant des leçons",
                donnees,
                PlotOrientation.VERTICAL, false, false, false
        );
        ChartPanel graph = new ChartPanel(chart);
        pnlGraphique.add(graph);
        pnlGraphique.validate();

        modelJTableMoniteurForStatGerante = new ModelJTableMoniteurForStatGerante();
        moniteurController = new MoniteurController();
        modelJTableMoniteurForStatGerante.loadDataMoniteur(moniteurController.lstMoniteurForStatsGerante());
        tblMoniteur.setModel(modelJTableMoniteurForStatGerante);
        Integer maxNbLeconForMoniteur = 0;
        String leMoniteur = "";
        Boolean siPlusieursForMoniteur = false;
        for(Moniteur moniteur:moniteurController.lstMoniteurForStatsGerante()){
            if (maxNbLeconForMoniteur<moniteur.getNbLecon()){
                maxNbLeconForMoniteur = moniteur.getNbLecon();
                leMoniteur = moniteur.getNom()+" "+moniteur.getPrenom();
                siPlusieursForMoniteur = false;
            } else if(maxNbLeconForMoniteur == moniteur.getNbLecon()) {
                leMoniteur +="et "+moniteur.getNom()+" "+moniteur.getPrenom();
                siPlusieursForMoniteur = true;
            }
        }
        if (siPlusieursForMoniteur){
            lblMoniteurPlusSolicite.setText("Les moniteurs les plus solicités sont  "+leMoniteur+" avec "+maxNbLeconForMoniteur+" leçons");
        }else {
            lblMoniteurPlusSolicite.setText("Le moniteur le plus solicité est "+leMoniteur+" avec "+maxNbLeconForMoniteur+" leçons");
        }
        categorieController = new CategorieController();
        DefaultPieDataset donneesGraphCategorie = new DefaultPieDataset();
        RingPlot plot = new RingPlot(donneesGraphCategorie);
        for (Map.Entry valeur : categorieController.categorieStatForGerante().entrySet())
        {
            donneesGraphCategorie.setValue(valeur.getKey().toString(),Double.parseDouble(valeur.getValue().toString()));
        }

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
        JFreeChart chartCategorie = new JFreeChart("Pourcentage leçons par catégorie",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        ChartPanel graphCategorie = new ChartPanel(chartCategorie);
        graphCategorie.setMouseWheelEnabled(true);
        pnlGraphCateg.add(graphCategorie);
        pnlGraphCateg.validate();

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
