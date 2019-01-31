package service;

import dto.CommandDTO;

public class LoginService {
    private LoginService() {}
    private static LoginService loginService = new LoginService();
    public static LoginService getInstance() {
        return loginService;
    }

    public boolean signIn(CommandDTO commandDTO) {
        return true;
    }
    public boolean signUp(CommandDTO commandDTO) {
        return true;
    }
}
