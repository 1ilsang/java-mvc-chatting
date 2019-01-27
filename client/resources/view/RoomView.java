package resources.view;

import controller.DispatcherController;
import dto.CommandDTO;
import dto.MessageDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 각 방의 실질적인 유저임.
public class RoomView implements IView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();
    private IndexView idx = IndexView.getInstance();
    private Button send, leave;
    private TextField inputArea;
    private JLabel chatArea;
    private int rno;

    @Override
    public void show() {
        init();
        addEventListener();
        dispatcherController.in("chat", "connect");
    }

    public RoomView(int rno) {
        this.rno = rno;
    }

    private void init() {
        idx.frame.removeAll();
        idx.frame.setTitle("RoomView" + rno);

        send = new Button("send");
        leave = new Button("leave");
        inputArea = new TextField();
        chatArea = new JLabel();

        idx.frame.setLayout(new GridLayout(4, 0));
        idx.frame.add(chatArea);
        idx.frame.add(inputArea);
        idx.frame.add(send);
        idx.frame.add(leave);

        idx.frame.setVisible(true);
    }

    private void addEventListener() {
        send.addActionListener(e -> {
            if(inputArea.getText().equals("")) return;
            // FIXME DTO 만드는거 함수화
            CommandDTO commandDTO = new CommandDTO();
            commandDTO.setRno(rno);
            commandDTO.setText(inputArea.getText());
            commandDTO.setUserName(dispatcherController.getUserName());
            dispatcherController.in("chat", "sendBroadCast", commandDTO);
            inputArea.setText("");
        });
        leave.addActionListener(e -> {
            // TODO Front Controller
            dispatcherController.in("chat", "disconnect");
            IndexView.getInstance().show("roomList");
        });
    }

    public void printChat(MessageDTO messageDTO) {
        String prev = chatArea.getText();
        chatArea.setText("<html>" +prev + "<br/>" + messageDTO.getName() + ": " + messageDTO.getContents());
    }
}
