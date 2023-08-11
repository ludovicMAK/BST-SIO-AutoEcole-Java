package Vues.Gerante;

import Controllers.EleveController;
import Controllers.LeconController;
import Controllers.MoniteurController;
import Entities.DateIntervalle;
import Entities.Lecon;
import Tools.ModelJTableUser;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.entity.NodeEntity;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;

public class frmGerantePlanningLecons extends JFrame {
    private JPanel pnlRoot;
    private JTree jtreeLecons;
    private JTable tblUsers;
    private JComboBox cboRoles;
    private JButton btnValider;
    private JScrollPane scrlTblUser;
    private JPanel pnlDateFin;
    private JPanel pnlDateDebut;

    private JDateChooser dcDebut;
    private JDateChooser dcFin;

    private ModelJTableUser model;

    private LeconController leconController;
    private EleveController eleveController;
    private MoniteurController moniteurController;

    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode nodeCategorie;
    private DefaultMutableTreeNode nodeDate;
    private DefaultMutableTreeNode nodeHeure;
    private DefaultMutableTreeNode nodeLecon;
    private DefaultMutableTreeNode nodeInfo;

    frmGerantePlanningLecons() throws SQLException {
        this.setTitle("Planning");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(900, 400);

        //On initialise les controllers
        leconController = new LeconController();
        eleveController = new EleveController();
        moniteurController = new MoniteurController();

        //On initialise les dateChosers
        dcDebut = new JDateChooser();
        dcDebut.setDateFormatString("yyyy-MM-dd");
        pnlDateDebut.add(dcDebut);
        dcFin = new JDateChooser();
        dcFin.setDateFormatString("yyyy-MM-dd");
        pnlDateFin.add(dcFin);

        //On initialise la cbo
        cboRoles.addItem("Tous");
        cboRoles.addItem("Moniteurs");
        cboRoles.addItem("Eleves");

        //On initialise le tableau
        //Par défaut tous les moniteurs et élèves sont présents
        model = new ModelJTableUser();
        model.loadUser(eleveController.getLoginNomPrenom(), moniteurController.getLoginNomPrenom());
        tblUsers.setModel(model);

        //On initialise les éléments du JTree
        root = new DefaultMutableTreeNode("Leçons");
        treeModel = new DefaultTreeModel(root);
        jtreeLecons.setModel(treeModel);

        //Quand on choisi un item
        cboRoles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                model = new ModelJTableUser();
                if(cboRoles.getSelectedItem().toString().compareTo("Tous") == 0){
                    try {
                        model.loadUser(eleveController.getLoginNomPrenom(), moniteurController.getLoginNomPrenom());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if(cboRoles.getSelectedItem().toString().compareTo("Moniteurs") == 0){
                    try {
                        model.loadUser(new ArrayList<>(), moniteurController.getLoginNomPrenom());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if(cboRoles.getSelectedItem().toString().compareTo("Eleves") == 0){
                    try {
                        model.loadUser(eleveController.getLoginNomPrenom(), new ArrayList<>());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                tblUsers.setModel(model);
            }
        });

        //Quand on clique sur le bouton
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //On vérifie qu'un seul élève ou moniteur soit sélectionné au maximum
                if(tblUsers.getSelectedRowCount() > 1){
                    JOptionPane.showMessageDialog(null, "Veuillez ne sélectionner qu'une seule personne au maximum.", "Erreur nb personne", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    root = new DefaultMutableTreeNode("Leçons");
                    DateIntervalle dateIntervalle = new DateIntervalle();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //Si on ne choisi pas d'interval de date on prend tout par défaut
                    if(dcDebut.getDate() == null && dcFin.getDate() == null){
                        dateIntervalle = new DateIntervalle("", "", "");
                    }
                    //Si on ne choisi pas de date de fin on prend tout à partir de la date de début
                    if(dcDebut.getDate() != null && dcFin.getDate() == null){
                        dateIntervalle = new DateIntervalle("d", sdf.format(dcDebut.getDate()), "");
                    }
                    //Si on ne choisi pas de date de début on prend tout jusqu'à la date de fin
                    if(dcDebut.getDate() == null && dcFin.getDate() != null){
                        dateIntervalle = new DateIntervalle("f", "", sdf.format(dcFin.getDate()));
                    }
                    //Si on choisi les deux on prend tout ce qui est dans l'interval
                    if(dcDebut.getDate() != null && dcFin.getDate() != null){
                        dateIntervalle = new DateIntervalle("df", sdf.format(dcDebut.getDate()), sdf.format(dcFin.getDate()));
                    }


                    //Si un eleve ou un moniteur est sélectionné on récupère son planning
                    if(tblUsers.getSelectedRowCount() > 0){
                        dateIntervalle.setLogin(tblUsers.getValueAt(tblUsers.getSelectedRow(), 1).toString());
                        dateIntervalle.setRole(tblUsers.getValueAt(tblUsers.getSelectedRow(), 0).toString());
                    }
                    else{
                        dateIntervalle.setLogin("");
                        dateIntervalle.setRole("");
                    }
                    //On récupère le planning
                    TreeMap<String, TreeMap<String, TreeMap<String, ArrayList<Lecon>>>> planning;
                    try {
                        planning = leconController.getPlanningLecons(dateIntervalle);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    //On rempli les nodes
                    //Pour chaque catégorie
                    for(String keyFirstTree: planning.keySet()){
                        nodeCategorie = new DefaultMutableTreeNode(keyFirstTree);
                        //Pour chaque date
                        for(String keySecondTree: planning.get(keyFirstTree).keySet()){
                            nodeDate = new DefaultMutableTreeNode(keySecondTree);
                            nodeCategorie.add(nodeDate);
                            //Pour chaque heure
                            for(String keyThirdTree: planning.get(keyFirstTree).get(keySecondTree).keySet()){
                                nodeHeure = new DefaultMutableTreeNode(keyThirdTree);
                                nodeDate.add(nodeHeure);
                                //Pour chaque leçon
                                for(Lecon lecon: planning.get(keyFirstTree).get(keySecondTree).get(keyThirdTree)){
                                    nodeLecon = new DefaultMutableTreeNode("Leçon n°"+lecon.getId());
                                    nodeInfo = new DefaultMutableTreeNode("Moniteur: "+lecon.getMoniteur().getNom()+" "+lecon.getMoniteur().getPrenom());
                                    nodeLecon.add(nodeInfo);
                                    nodeInfo = new DefaultMutableTreeNode("Eleve: "+lecon.getEleve().getNom()+" "+lecon.getEleve().getPrenom());
                                    nodeLecon.add(nodeInfo);
                                    nodeInfo = new DefaultMutableTreeNode("Véhicule: "+lecon.getVehicule().getModele()+" "+lecon.getVehicule().getImma());
                                    nodeLecon.add(nodeInfo);
                                    nodeHeure.add(nodeLecon);
                                }
                            }
                        }
                        root.add(nodeCategorie);
                    }
                    treeModel = new DefaultTreeModel(root);
                    jtreeLecons.setModel(treeModel);
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
