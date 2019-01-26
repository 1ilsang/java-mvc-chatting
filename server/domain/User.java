package domain;

public class User {
    private static int TUNO = 0;
    private String name;
    private String id;
    private int uno;
    private String pw;

    public User(String id, String pw, String name) {
        this.uno = ++TUNO;
        this.name = name;
        this.id = id;
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
