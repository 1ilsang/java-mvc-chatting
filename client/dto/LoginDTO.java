package dto;

import domain.UserVO;

import java.io.Serializable;

public class LoginDTO implements Serializable {
    private String action;
    private UserVO userVO;
    private boolean access;
    private String message;

    public LoginDTO() {
        this.action = "";
        this.userVO = null;
        this.access = false;
        this.message = "";
    }

    public LoginDTO(boolean access, String message) {
        this();
        this.access = access;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "action='" + action + '\'' +
                ", userVO=" + userVO +
                ", access=" + access +
                ", message='" + message + '\'' +
                '}';
    }
}
