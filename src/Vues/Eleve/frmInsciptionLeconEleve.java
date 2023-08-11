package Vues.Eleve;

import Controllers.*;
import Tools.ConnexionBDD;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class frmInsciptionLeconEleve extends JFrame{
    private JComboBox cboPermis;
    private JComboBox cboHoraire;
    private JComboBox cboMoniteur;
    private JButton btnValider;
    private JComboBox cboVehicule;
    private JPanel pnlRoot;
    private JPanel pnlDate;
    private JButton btnDispo;
    private JDateChooser dcDateConsultation;

    private ConnexionBDD cnx;

    private CategorieController categorieController;
    private MoniteurController moniteurController;
    private VehiculeController vehiculeController;
    private LeconController leconController;
    private EleveController eleveController;

    frmInsciptionLeconEleve(int idUser) throws SQLException, ClassNotFoundException {
        this.setTitle("Inscription leçon");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(600, 400);

        cnx = new ConnexionBDD();

        categorieController = new CategorieController();
        moniteurController = new MoniteurController();
        vehiculeController = new VehiculeController();
        leconController = new LeconController();
        eleveController = new EleveController();

        dcDateConsultation = new JDateChooser();
        dcDateConsultation.setDateFormatString("yyyy-MM-dd");
        pnlDate.add(dcDateConsultation);

        //On remplit la cbo avec toutes les catégories
        cboPermis.addItem("Permis:");
        for(String c: categorieController.getAllCategories()){
            cboPermis.addItem(c);
        }

        //On remplit la cbo avec tous les horaires
        cboHoraire.addItem("Horaires:");
        cboHoraire.addItem("09:00");
        cboHoraire.addItem("10:00");
        cboHoraire.addItem("11:00");
        cboHoraire.addItem("12:00");
        cboHoraire.addItem("13:00");
        cboHoraire.addItem("14:00");
        cboHoraire.addItem("15:00");
        cboHoraire.addItem("16:00");
        cboHoraire.addItem("17:00");


        //Bouton disponibilités
        btnDispo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cboMoniteur.removeAllItems();
                cboVehicule.removeAllItems();
                //Si tous les champs requis sont remplis
                if(cboPermis.getSelectedIndex() != 0 && dcDateConsultation.isValid() && cboHoraire.getSelectedIndex() != 0){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(dcDateConsultation.getDate()).toString();
                    try {
                        for(String m: moniteurController.getMoniteurDispo(date, cboHoraire.getSelectedItem().toString(), categorieController.getIdByLibelle(cboPermis.getSelectedItem().toString()))){
                            cboMoniteur.addItem(m);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        for(String v: vehiculeController.getVehiculesDisponibles(date, cboHoraire.getSelectedItem().toString(), categorieController.getIdByLibelle(cboPermis.getSelectedItem().toString()))){
                            cboVehicule.addItem(v);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    //Dans le cas où il n'y a aucun moniteur ou aucun véhicule disponible
                    if(cboMoniteur.getItemCount() == 0 || cboVehicule.getItemCount() == 0){
                        JOptionPane.showMessageDialog(null, "Aucun moniteur et/ou véhicule n'est disponible veuillez changer la date de votre leçon.", "Erreur disponibilité", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cboPermis.getSelectedIndex() == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez choisir un permis.", "Erreur permis", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(dcDateConsultation.getDate() == null){
                        JOptionPane.showMessageDialog(null, "Veuillez choisir une date.", "Erreur Date", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(dcDateConsultation.getDate()).toString();
                        if(cboHoraire.getSelectedIndex() == 0){
                            JOptionPane.showMessageDialog(null, "Veuillez choisir un horaire.", "Erreur horaire", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            String horaire = cboHoraire.getSelectedItem().toString();
                            if(cboMoniteur.getSelectedIndex() < 0){
                                JOptionPane.showMessageDialog(null, "Veuillez choisir un moniteur", "Erreur moniteur", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                String[] moniteurParties = cboMoniteur.getSelectedItem().toString().split(" ");
                                int idMoniteur;
                                try {
                                    idMoniteur = moniteurController.getIdMoniteurByPrenom_Nom(moniteurParties[0], moniteurParties[1]);
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if(cboVehicule.getSelectedIndex() < 0){
                                    JOptionPane.showMessageDialog(null, "Veuillez choisir un véhicule", "Erreur véchiule", JOptionPane.ERROR_MESSAGE);
                                }
                                else{
                                    String[] vehiculeParties = cboVehicule.getSelectedItem().toString().split(" ");
                                    int idVehicule;
                                    try {
                                        idVehicule = vehiculeController.getIdVehiculeByMarque_Modele_Immatriculation(vehiculeParties[0], vehiculeParties[1], vehiculeParties[2]);
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    try {
                                        if(leconController.isLeconByIdMoniteur_Date_Heure(idMoniteur, date, horaire)){
                                            JOptionPane.showMessageDialog(null, "Moniteur indisponible à cet horaire !", "Erreur moniteur", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else if(leconController.isLeconByIdVehicule_Date_Heure(idVehicule, date, horaire)){
                                            JOptionPane.showMessageDialog(null, "Véhicule indisponible à cet horaire !", "Erreur véhicule", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else{
                                            leconController.addLecon(eleveController.getEleveIdByIdUser(idUser), idMoniteur, idVehicule, date, horaire);
                                            JOptionPane.showMessageDialog(null, "Inscription validée !", "Lecon enregistrée", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            }
                        }
                    }
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
