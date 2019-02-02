package resources.view;

import controller.DispatcherController;
import dto.CommandDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView implements IView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();
    private static HomeView home = new HomeView();
    private IndexView idx = IndexView.getInstance();
    private Button loginButton, registerButton;
    private Label titleLabel, stateLabel;
    private TextField id, pw;

    public static HomeView getInstance() {
        return home;
    }

    private HomeView() {
    }

    @Override
    public void show(CommandDTO commandDTO) {
        init();
        stateLabel.setText(commandDTO.getText());
        addEventListener();
    }

    private void init() {
        dispatcherController = DispatcherController.getInstance();

        idx.frame.removeAll();
        idx.frame.setTitle("Login");
        idx.frame.setLayout(new GridLayout(6, 0));

        loginButton = new Button("Login");
        registerButton = new Button("RegisterView");
        id = new TextField();
        pw = new TextField();
        pw.setEchoChar('*');
        stateLabel = new Label();
        stateLabel.setAlignment(Label.CENTER);
        titleLabel = new Label("로그인이 필요한 서비스 입니다.");
        titleLabel.setAlignment(Label.CENTER);

        idx.frame.add(titleLabel);
        idx.frame.add(id);
        idx.frame.add(pw);
        idx.frame.add(loginButton);
        idx.frame.add(registerButton);
        idx.frame.add(stateLabel);

        idx.frame.setVisible(true);
    }

    private void addEventListener() {
        CommandDTO commandDTO = new CommandDTO();
        loginButton.addActionListener(e -> {
            commandDTO.setUserName(id.getText());
            commandDTO.setPw(pw.getText());
            commandDTO.setUrl("/login/signIn");
            dispatcherController.in(commandDTO);
        });
        registerButton.addActionListener(e -> {
            commandDTO.setUrl("/view/register");
            dispatcherController.in(commandDTO);
        });
    }
}
