package Entities;

public class User {
    private int id;
    private String login;
    private String password;
    private int role;

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public User(int id, String login, int role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public int getRole() {
        return role;
    }
}
