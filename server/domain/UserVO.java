package domain;

import java.io.Serializable;

public class UserVO implements Serializable {
    // c10n COUNT ALL USERS
    private static int TUNO = 0;
    private String name;
    private int uno;
    private String pw;

    public UserVO() {
        this.uno = ++TUNO;
        this.name = "";
        this.pw = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "UserVO{" +
                "name='" + name + '\'' +
                ", uno=" + uno +
                ", pw='" + pw + '\'' +
                '}';
    }
}
