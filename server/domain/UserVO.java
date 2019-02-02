package domain;

import java.io.Serializable;

public class UserVO implements Serializable {
    // c10n COUNT ALL USERS
    private static int TUNO = 0;
    private String userName;
    private int uno;
    private String pw;

    public UserVO() {
        this.uno = ++TUNO;
        this.userName = "";
        this.pw = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
                "name='" + userName + '\'' +
                ", uno=" + uno +
                ", pw='" + pw + '\'' +
                '}';
    }
}
