package resources.view;

import controller.DispatcherController;
import dto.CommandDTO;

import java.awt.*;

public class RegisterView implements IView {
    private static RegisterView register = new RegisterView();
    private IndexView idx = IndexView.getInstance();
    private DispatcherController dispatcherController = DispatcherController.getInstance();

    private Button registerBtn, back;
    private TextField id, pw;
    private Label cmdLabel, stateLabel;

    public static RegisterView getInstance() {
        return register;
    }

    private RegisterView() {

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
        idx.frame.setTitle("Register");
        idx.frame.setLayout(new GridLayout(6, 0));

        id = new TextField();
        pw = new TextField();
        stateLabel = new Label();
        stateLabel.setAlignment(Label.CENTER);
        pw.setEchoChar('*');
        registerBtn = new Button("Sign up");
        back = new Button("back");
        cmdLabel = new Label("1: ID, 2: PW");
        cmdLabel.setAlignment(Label.CENTER);

        idx.frame.add(cmdLabel);
        idx.frame.add(id);
        idx.frame.add(pw);
        idx.frame.add(registerBtn);
        idx.frame.add(back);
        idx.frame.add(stateLabel);

        idx.frame.setVisible(true);
    }

    private void addEventListener() {
        CommandDTO commandDTO = new CommandDTO();
        registerBtn.addActionListener(e -> {
            commandDTO.setUrl("/login/signUp");
            commandDTO.setUserName(id.getText());
            commandDTO.setPw(pw.getText());
            dispatcherController.in(commandDTO);
        });
        back.addActionListener(e -> {
            commandDTO.setUrl("/view/home");
            dispatcherController.in(commandDTO);
        });
    }
}
