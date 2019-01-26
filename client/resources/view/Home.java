package resources.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Home implements IView {
    private static Home home = new Home();
    private Index idx = Index.getInstance();
    private Button loginButton, registerButton;
    private Label titleLabel, textLabel;
    private TextField id, pw;

    public static Home getInstance(){
        return home;
    }

    private Home() {
    }

    @Override
    public void show() {
        init();
        addEventListener();
    }

    private void init() {
        idx.frame.removeAll();
        idx.frame.setTitle("Login");
        idx.frame.setLayout(new GridLayout(6, 0));

        loginButton = new Button("Login");
        registerButton = new Button("Register");
        id = new TextField();
        pw = new TextField();
        textLabel = new Label();
        titleLabel = new Label("로그인이 필요한 서비스 입니다.");
        titleLabel.setAlignment(Label.CENTER);

        idx.frame.add(titleLabel);
        idx.frame.add(id);
        idx.frame.add(pw);
        idx.frame.add(loginButton);
        idx.frame.add(registerButton);
        idx.frame.add(textLabel);

        idx.frame.setVisible(true);
    }
    private void addEventListener() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Index.getInstance().show("roomList");
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Index.getInstance().show("register");
            }
        });
    }
}
