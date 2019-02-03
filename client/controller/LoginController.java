package controller;

import dto.ModelAndView;
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
    public ModelAndView in(ModelAndView modelAndView) {
        dispatcherController = DispatcherController.getInstance();

        String pattern = GetUrlFirstPattern.getStringPattern(modelAndView);
        System.out.println("LoginController: " + pattern);
        LoginDTO loginDTO = null;

        if(pattern.equals("/signIn")) {
            loginDTO = loginService.signIn(modelAndView);
        } else if(pattern.equals("/signUp")) {
            loginDTO = loginService.signUp(modelAndView);
        }

        String name = modelAndView.getUserName();
        modelAndView.clear();

        if(loginDTO.isAccess()) {
            dispatcherController.setUserName(name);
            modelAndView.setUserName(name);
            modelAndView.setUrl("/roomList");
        } else {
            modelAndView.setText(loginDTO.getMessage());
            modelAndView.setUrl(loginDTO.getUrl());
        }

        return modelAndView;
    }
}
