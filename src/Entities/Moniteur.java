package Entities;

public class Moniteur {
    private String nom;
    private String prenom;
    private String sexe;
    private String dateDeNaissance;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;
    private String mdp;
    private User user;
    private String email;
    private int nbLecon;
    private int idMoniteur;


    public Moniteur(String nom, String prenom, User user) {
        this.nom = nom;
        this.prenom = prenom;
        this.user = user;
    }
    public  Moniteur(int nbLecon,int id,String nom,String prenom){
        this.nbLecon = nbLecon;
        this.idMoniteur = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Moniteur(String nom, String prenom, String uneAddresse, String unPostal, String uneVille, String unTelephone, String unMdp, String uneDateDeNaissance, String unEmail, String unSexe) {
        this.nom = nom;
        this.prenom = prenom;
        adresse = uneAddresse;
        codePostal = unPostal;
        ville = uneVille;
        telephone = unTelephone;
        mdp = unMdp;
        dateDeNaissance = uneDateDeNaissance;
        email =unEmail;
        sexe =unSexe;
    }
    public  Moniteur(String unNom,String unPrenom){
        nom = unNom;
        prenom = unPrenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMdp() {
        return mdp;
    }

    public User getUser() {
        return user;
    }

    public String getNom() {
        return nom;
    }

    public int getNbLecon() {
        return nbLecon;
    }

    public int getIdMoniteur() {
        return idMoniteur;
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
