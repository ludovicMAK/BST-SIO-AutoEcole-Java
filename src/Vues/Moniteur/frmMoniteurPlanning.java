package Vues.Moniteur;

import Controllers.CategorieController;
import Controllers.LeconController;
import Entities.Lecon;
import Tools.ConnexionBDD;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class frmMoniteurPlanning extends JFrame {
    private JPanel pnlRoot;
    private JTree trPlanning;
    private JComboBox cboCateg;
    private JPanel pnlDate;
    private JComboBox cboTrancher;
    private JDateChooser cldDate;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private LeconController leconController;
    private CategorieController categController;
    private  DefaultMutableTreeNode noeudCategorie;
    private TreeMap<String,TreeMap<String,TreeMap<String,Lecon>>> monPlanning;

    private ConnexionBDD cnx;

    public frmMoniteurPlanning(int idUser) throws SQLException, ClassNotFoundException, ParseException {
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
        cldDate = new JDateChooser();
        Date auj = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateAuj = sdf.format(auj);
        cldDate.setDate(sdf.parse(dateAuj));
        pnlDate.add(cldDate);
        monPlanning = new TreeMap<>();
        categController = new CategorieController();
        leconController = new LeconController();
        //Ajout des catégories dans le comboBox
        for (String cat:categController.getCategoriesByIdMoniteur(idUser)){
            cboCateg.addItem(cat);

        }
        cboCateg.addItem("Tous");
        monPlanning = new TreeMap<>();
        afficheSiExisteLecon(idUser);
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
                        for (String cat : categController.getCategoriesByIdMoniteurBetweenDate(idUser, minDate, maxDate)) {
                            if (leconController.obtenirPlanningLeconMoniteurTranche(idUser, cat, minDate, maxDate).size()>0){
                                monPlanning.put(cat, leconController.obtenirPlanningLeconMoniteurTranche(idUser, cat, minDate, maxDate));
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
        //Lors du changement de date on a l' action:
        cldDate.getDateEditor().addPropertyChangeListener(
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if ("date".equals(e.getPropertyName())) {
                            System.out.println(e.getPropertyName()
                                    + ": " + (Date) e.getNewValue());
                            try {
                                afficheSiExisteLecon(idUser);
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                    }
                });
    }
    public void afficherPlanning(int idUser) throws SQLException {
        DefaultMutableTreeNode noeudDate = null;
        DefaultMutableTreeNode noeudHeure = null;
        DefaultMutableTreeNode noeudinfoLecon = null;
        DefaultMutableTreeNode noeudInfoEleve = null;
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
                    noeudInfoEleve = new DefaultMutableTreeNode("Elève:");
                    noeudinfoLecon.add(noeudInfoEleve);
                    noeudInfoEleve = new DefaultMutableTreeNode(monPlanning.get(categ).get(date).get(heure).getEleve().getNom()+" "+monPlanning.get(categ).get(date).get(heure).getEleve().getPrenom());
                    noeudinfoLecon.add(noeudInfoEleve);
                    noeudInfoEleve = new DefaultMutableTreeNode("Véhicule:");
                    noeudinfoLecon.add(noeudInfoEleve);
                    noeudInfoEleve = new DefaultMutableTreeNode(monPlanning.get(categ).get(date).get(heure).getVehicule().getImma());
                    noeudinfoLecon.add(noeudInfoEleve);
                    noeudHeure.add(noeudinfoLecon);
                    i++;

                }
            }
            root.add(noeudCategorie);
        }

        model = new DefaultTreeModel(root);
        trPlanning.setModel(model);
    }
    public void afficheSiExisteLecon(int idUser) throws ParseException, SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String minDate = sdf.format(cldDate.getDate());
        String maxDate = sdf.format(maxDate());
        if (leconController.siExisteLeconMoniteurByDate(idUser,cboCateg.getSelectedItem().toString(),minDate,maxDate)){
            root = new DefaultMutableTreeNode("Les leçons");
            model = new DefaultTreeModel(root);
            trPlanning.setModel(model);
            monPlanning = new TreeMap<>();
            monPlanning.put(cboCateg.getSelectedItem().toString(),leconController.obtenirPlanningLeconMoniteurTranche(idUser,cboCateg.getSelectedItem().toString(),minDate,maxDate));
            afficherPlanning(idUser);

        }else {
            root = new DefaultMutableTreeNode("Vous n' avez pas de lecon à cette date");
            model = new DefaultTreeModel(root);
            trPlanning.setModel(model);
        }
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
}

