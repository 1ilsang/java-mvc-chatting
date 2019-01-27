package controller;

import dto.CommandDTO;

/**
 * Branching controller :: All requests go through this.
 */
public class DispatcherController implements IController {
    private String userName;
    private static DispatcherController dispatcherController = new DispatcherController();
    private ChatController chatController = ChatController.getInstance();

    private DispatcherController() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static DispatcherController getInstance() {
        return dispatcherController;
    }

    public void in(String controller) {

    }

    public void in(String controller, String pattern) {
        if (controller.equals("chat")) {
            chatController.in(pattern);
        }
    }

    public void in(String controller, String pattern, CommandDTO cmdDTO) {
        if (controller.equals("chat")) {
            chatController.in(pattern, cmdDTO);
        }
    }
}







