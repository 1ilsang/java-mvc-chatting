package controller;

import dto.CommandDTO;
import dto.LoginDTO;
import service.LoginService;
import util.GetUrlFirstPattern;

public class LoginController implements IController{
    private DispatcherController dispatcherController;
    private LoginService loginService = LoginService.getInstance();
    public static LoginController getInstance() {
        return loginController;
    }
    private static LoginController loginController = new LoginController();
    private LoginController() {}
    @Override
    public void in(CommandDTO commandDTO) {
        dispatcherController = DispatcherController.getInstance();
        String pattern = GetUrlFirstPattern.getStringPattern(commandDTO);
        System.out.println("LoginController: " + pattern);
        LoginDTO loginDTO = null;

        if(pattern.equals("/signIn")) {
            loginDTO = loginService.signIn(commandDTO);
        } else if(pattern.equals("/signUp")) {
            loginDTO = loginService.signUp(commandDTO);
        }

        String name = commandDTO.getUserName();
        commandDTO.clear();

        if(loginDTO.isAccess()) {
            dispatcherController.setUserName(name);
            commandDTO.setUserName(name);
            commandDTO.setUrl("/view/roomList");
            dispatcherController.in(commandDTO);
        } else {
            commandDTO.setText(loginDTO.getMessage());
            commandDTO.setUrl(loginDTO.getUrl());
            dispatcherController.in(commandDTO);
        }
    }
}
