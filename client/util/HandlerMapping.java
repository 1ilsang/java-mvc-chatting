package util;

import controller.ChatController;
import controller.IController;
import controller.LoginController;
import controller.ViewController;
import dto.ModelAndView;

public class HandlerMapping {
    private HandlerMapping() {}
    public static IController getController(ModelAndView modelAndView) {
        ViewController viewController = ViewController.getInstance();
        LoginController loginController = LoginController.getInstance();
        ChatController chatController = ChatController.getInstance();
        String controller = GetUrlFirstPattern.getStringPattern(modelAndView);

        if(controller.equals("/index")) return viewController;
        else if(controller.equals("/view")) return viewController;
        else if(controller.equals("/chat")) return chatController;
        else if(controller.equals("/login")) return loginController;
        else return null;
    }
}
