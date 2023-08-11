package Entities;

public class Categorie {
    private int id;
    private String libelle;
    private double prix;

    public Categorie(int id, String libelle, double prix) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public double getPrix() {
        return prix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
