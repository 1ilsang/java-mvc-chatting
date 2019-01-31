package resources.view;

import controller.DispatcherController;
import controller.ViewController;
import dto.CommandDTO;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IndexView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();

    private static IndexView main = new IndexView();

    public static Frame frame;

    public static IndexView getInstance() {
        return main;
    }

    private IndexView() {
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

        CommandDTO commandDTO = new CommandDTO("/view/home");
        dispatcherController.in(commandDTO);
    }
}
