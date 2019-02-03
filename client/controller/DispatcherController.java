package controller;

import dto.ModelAndView;
import resources.view.IView;
import util.HandlerMapping;
import util.View;
import util.ViewResolver;

/**
 * Branching controller :: All requests go through this.
 */
public class DispatcherController {
    private String userName;
    private static DispatcherController dispatcherController = new DispatcherController();

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

    public void in(ModelAndView modelAndView) {
        IController controller = HandlerMapping.getController(modelAndView);
        System.out.println("DispatcherController: " + controller);
        modelAndView = controller.in(modelAndView);

        IView view = ViewResolver.getView(modelAndView);
        if(view != null) View.showFrame(view, modelAndView);
    }
}







