package resources.view;

import controller.DispatcherController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register implements IView {
    private static Register register = new Register();
    private Index idx = Index.getInstance();
    private DispatcherController dispatcherController = DispatcherController.getInstance();

    private Button registerBtn, back;
    private TextField id, pw;
    private Label cmdLabel;

    public static Register getInstance() {
        return register;
    }

    private Register() {

    }

    @Override
    public void show() {
        init();
        addEventListener();
    }

    private void init() {
        idx.frame.removeAll();
        idx.frame.setTitle("Register");
        idx.frame.setLayout(new GridLayout(6, 0));

        id = new TextField();
        pw = new TextField();
        registerBtn = new Button("Sign up");
        back = new Button("back");
        cmdLabel = new Label("1: ID, 2: PW");
        cmdLabel.setAlignment(Label.CENTER);

        idx.frame.add(cmdLabel);
        idx.frame.add(id);
        idx.frame.add(pw);
        idx.frame.add(registerBtn);
        idx.frame.add(back);

        idx.frame.setVisible(true);
    }

    private void addEventListener() {
        // Before Lambda expression
//        registerBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dispatcherController.setUserName(id.getText());
//                Index.getInstance().show("roomList");
//            }
//        });
        registerBtn.addActionListener(e -> {
            dispatcherController.setUserName(id.getText());
            Index.getInstance().show("roomList");
        });
        back.addActionListener(e -> {
            Index.getInstance().show("home");
        });
    }
}
