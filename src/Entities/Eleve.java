package Entities;

public class Eleve {
    private String nom;
    private String prenom;
    private String mdp;
    private String sexe;
    private String dateDeNaissance;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;
    private String email;
    private User user;

    public Eleve(String nom, String prenom, User user) {
        this.nom = nom;
        this.prenom = prenom;
        this.user = user;
    }

    public Eleve(String unNom, String unPrenom, String uneAddresse, String unPostal, String uneVille, String unTelephone, String unMdp, String uneDateDeNaissance, String unEmail, String unSexe) {
        nom = unNom;
        prenom = unPrenom;
        adresse = uneAddresse;
        codePostal = unPostal;
        ville = uneVille;
        telephone = unTelephone;
        mdp = unMdp;
        dateDeNaissance = uneDateDeNaissance;
        email = unEmail;
        sexe = unSexe;
    }

    public Eleve(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public User getUser() {
        return user;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

}
