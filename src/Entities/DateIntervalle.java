package Entities;

public class DateIntervalle {
    String cas;
    String dateDebut;
    String dateFin;
    String login;
    String role;

    public DateIntervalle() {
    }

    public DateIntervalle(String cas, String dateDebut, String dateFin) {
        this.cas = cas;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getCas() {
        return cas;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
