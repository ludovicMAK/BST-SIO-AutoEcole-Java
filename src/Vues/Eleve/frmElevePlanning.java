package Vues.Eleve;

import Controllers.CategorieController;
import Controllers.LeconController;
import Entities.Lecon;
import Tools.ConnexionBDD;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

public class frmElevePlanning extends JFrame {
    private JPanel pnlRoot;
    private JTree trPlanning;
    private JComboBox cboCateg;
    private JPanel pnlDate;
    private JComboBox cboTrancher;
    private JDateChooser cldDate;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private  DefaultMutableTreeNode noeudCategorie;
    private TreeMap<String,TreeMap<String, TreeMap<String, Lecon>>> monPlanning;
    private CategorieController categController;
    private LeconController leconController;
    private ConnexionBDD cnx;

    frmElevePlanning(int idUser) throws SQLException, ClassNotFoundException, ParseException {
        // affichage de la fenêtre Planning
        this.setTitle("Planning");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 400);
        // inatialisation de la connexion
        cnx = new ConnexionBDD();
        // affichage de ma treeMap;
        root = new DefaultMutableTreeNode("Les leçons");
        model = new DefaultTreeModel(root);
        trPlanning.setModel(model);
        // affichage de la date d'aujourd hui
        Date auj = new Date();
        cldDate = new JDateChooser();
        cldDate.setDate(auj);
        pnlDate.add(cldDate);
        monPlanning = new TreeMap<>();
        categController = new CategorieController();
        leconController = new LeconController();
        //Insertion des catégorie dans la comboBox
        for (String categ:categController.getCategoriesByIdEleve(idUser)){
            cboCateg.addItem(categ);
        }
        cboCateg.addItem("Tous");


        afficheSiExisteLecon(idUser);
        //Lors du changement de date on a l' action:
        cldDate.getDateEditor().addPropertyChangeListener(
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if ("date".equals(e.getPropertyName())) {
                            System.out.println(e.getPropertyName()
                                    + ": " + (Date) e.getNewValue());
                            try {
                                monPlanning = new TreeMap<>();
                                afficheSiExisteLecon(idUser);
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                    }
                });
        //Fermeture de la page
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
        //Lors de changement de catégorie on a l' action:
        cboCateg.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                root = new DefaultMutableTreeNode("Les leçons");
                model = new DefaultTreeModel(root);
                trPlanning.setModel(model);

                if (cboCateg.getSelectedItem().toString() !="Tous"){
                    try {
                        monPlanning=new TreeMap<>();
                        afficheSiExisteLecon(idUser);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String minDate = sdf.format(cldDate.getDate());
                        String maxDate = sdf.format(maxDate());
                        monPlanning=new TreeMap<>();
                        for (String cat : categController.getCategoriesByIdEleveBetweenDate(idUser, minDate, maxDate)) {
                            if (categController.getCategoriesByIdEleveBetweenDate(idUser, minDate, maxDate).size()>0) {
                                monPlanning.put(cat, leconController.obtenirPlanningLeconEleveTranche(idUser, cat, minDate, maxDate));
                            }
                        }
                        afficherPlanning(idUser);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }

                }

            }
        });
        cboTrancher.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    afficheSiExisteLecon(idUser);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    //Fonction qui permet l'affichage de mon planning
    public void afficherPlanning(int idUser) throws SQLException {
        DefaultMutableTreeNode noeudDate = null;
        DefaultMutableTreeNode noeudHeure = null;
        DefaultMutableTreeNode noeudinfoLecon = null;
        DefaultMutableTreeNode noeudInfoMoniteur = null;
        int i = 1;

        for(String categ : monPlanning.keySet()){
            noeudCategorie = new DefaultMutableTreeNode(categ);
            for (String date : monPlanning.get(categ).keySet()){
                noeudDate = new DefaultMutableTreeNode(date);
                noeudCategorie.add(noeudDate);
                for (String heure : monPlanning.get(categ).get(date).keySet()){
                    noeudHeure = new DefaultMutableTreeNode(heure);
                    noeudDate.add(noeudHeure);
                    noeudinfoLecon = new DefaultMutableTreeNode("Leçon "+i);
                    noeudInfoMoniteur = new DefaultMutableTreeNode("Moniteur:");
                    noeudinfoLecon.add(noeudInfoMoniteur);
                    noeudInfoMoniteur = new DefaultMutableTreeNode(monPlanning.get(categ).get(date).get(heure).getMoniteur().getNom()+" "+monPlanning.get(categ).get(date).get(heure).getMoniteur().getPrenom());
                    noeudinfoLecon.add(noeudInfoMoniteur);
                    noeudInfoMoniteur = new DefaultMutableTreeNode("Véhicule:");
                    noeudinfoLecon.add(noeudInfoMoniteur);
                    noeudInfoMoniteur = new DefaultMutableTreeNode(monPlanning.get(categ).get(date).get(heure).getVehicule().getImma());
                    noeudinfoLecon.add(noeudInfoMoniteur);
                    noeudHeure.add(noeudinfoLecon);
                    i++;

                }
            }
            root.add(noeudCategorie);
        }

        model = new DefaultTreeModel(root);
        trPlanning.setModel(model);
    }
    //Fonction permettant d'obtenir la date maximum
    public Date maxDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String minDate =sdf.format(cldDate.getDate());
        Date date = sdf.parse(minDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cboTrancher.getSelectedItem().toString() == "1 semaine"){
            cal.add(Calendar.DAY_OF_MONTH, 7); // Add 30 days
        }else if (cboTrancher.getSelectedItem().toString() == "1 mois")
            cal.add(Calendar.DAY_OF_MONTH, 30); // Add 30 days
        Date maxDate = cal.getTime();
        return maxDate;
    }
    public void afficheSiExisteLecon(int idUser) throws ParseException, SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String minDate = sdf.format(cldDate.getDate());
        String maxDate = sdf.format(maxDate());
        if (leconController.siExisteLeconEleveByDate(idUser,cboCateg.getSelectedItem().toString(),minDate,maxDate)){
            root = new DefaultMutableTreeNode("Les leçons");
            model = new DefaultTreeModel(root);
            trPlanning.setModel(model);
            monPlanning = new TreeMap<>();
            monPlanning.put(cboCateg.getSelectedItem().toString(),leconController.obtenirPlanningLeconEleveTranche(idUser,cboCateg.getSelectedItem().toString(),minDate,maxDate));
            afficherPlanning(idUser);

        }else {
            root = new DefaultMutableTreeNode("Vous n' avez pas de lecon à cette date");
            model = new DefaultTreeModel(root);
            trPlanning.setModel(model);
        }
    }
}
