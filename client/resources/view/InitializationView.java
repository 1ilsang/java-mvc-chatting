package resources.view;

import controller.DispatcherController;
import dto.ModelAndView;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InitializationView implements IView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();

    private static InitializationView main = new InitializationView();

    public static Frame frame;

    public static InitializationView getInstance() {
        return main;
    }

    private InitializationView() {
        show(null);
    }

    @Override
    public void show(ModelAndView modelAndView) {
        frame = new Frame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setLayout(new GridLayout(5, 0));
        frame.setSize(300, 300);
        frame.setResizable(false);
    }
}
