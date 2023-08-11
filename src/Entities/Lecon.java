package Entities;

public class Lecon {
    private int id;
    private String date;
    private String heure;
    private boolean passee;
    private boolean payee;
    private Eleve eleve;
    private Vehicule vehicule;
    private Moniteur moniteur;

    public Lecon(String date, boolean passee, boolean payee) {
        this.date = date;
        this.passee = passee;
        this.payee = payee;
    }

    public Lecon(Eleve unEleve,Vehicule unVehicule){
        eleve = unEleve;
        vehicule = unVehicule;
    }

    public Moniteur getMoniteur() {
        return moniteur;
    }

    public Lecon(Moniteur unMoniteur, Vehicule unVehicule){
        moniteur = unMoniteur;
        vehicule = unVehicule;
    }

    public Lecon(int id, Eleve eleve, Vehicule vehicule, Moniteur moniteur) {
        this.id = id;
        this.eleve = eleve;
        this.vehicule = vehicule;
        this.moniteur = moniteur;
    }

    public String getDate() {
        return date;
    }

    public boolean isPassee() {
        return passee;
    }

    public boolean isPayee() {
        return payee;
    }

    public int getId() {
        return id;
    }

    public String getHeure() {
        return heure;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

}
