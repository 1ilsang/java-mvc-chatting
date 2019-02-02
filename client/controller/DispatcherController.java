package controller;

import dto.CommandDTO;
import util.GetUrlFirstPattern;

import javax.swing.text.View;

/**
 * Branching controller :: All requests go through this.
 */
public class DispatcherController implements IController {
    private String userName;
    private ViewController viewController = ViewController.getInstance();
    private LoginController loginController = LoginController.getInstance();
    private static DispatcherController dispatcherController = new DispatcherController();
    private ChatController chatController = ChatController.getInstance();

    private DispatcherController() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        System.out.println("User name: " + userName);
        this.userName = userName;
    }

    public static DispatcherController getInstance() {
        return dispatcherController;
    }

    @Override
    public void in(CommandDTO commandDTO) {
        String controller = GetUrlFirstPattern.getStringPattern(commandDTO);
        System.out.println("DispatcherController: " + controller);

        if(controller.equals("/chat")) chatController.in(commandDTO);
        else if(controller.equals("/view")) viewController.in(commandDTO);
        else if(controller.equals("/login")) loginController.in(commandDTO);
    }
}







