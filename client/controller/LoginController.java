package controller;

import dto.CommandDTO;
import service.LoginService;
import util.GetUrlFirstPattern;

public class LoginController implements IController{
    private LoginService loginService = LoginService.getInstance();
    public static LoginController getInstance() {
        return loginController;
    }
    private static LoginController loginController = new LoginController();
    private LoginController() {}
    @Override
    public void in(CommandDTO commandDTO) {
        String pattern = GetUrlFirstPattern.getStringPattern(commandDTO);
        System.out.println("LoginController: " + pattern);

        if(pattern.equals("signIn")) {
            if(loginService.signIn(commandDTO)) {
                // 로그인 정상처리(/view/home)
            } else {
                // 로그인 비정상(dialog)
            }
        } else if(pattern.equals("signUp")) {
            loginService.signIn(commandDTO);
        }
    }
}
