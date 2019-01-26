package resources.view;

import controller.DispatcherController;
import dto.CommandDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 각 방의 실질적인 유저임.
public class Room implements IView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();
    private Index idx = Index.getInstance();
    private Button send, leave;
    private TextField inputArea;
    private Label chatArea;
    private int rno;

    @Override
    public void show() {
        init();
        addEventListener();
        dispatcherController.in("chat","connect");
    }

    public Room(int rno) {
        this.rno = rno;
    }

    private void init() {
        idx.frame.removeAll();
        idx.frame.setTitle("Room" + rno);

        send = new Button("send");
        leave = new Button("leave");
        inputArea = new TextField();
        chatArea = new Label();

        idx.frame.setLayout(new GridLayout(4, 0));
        idx.frame.add(chatArea);
        idx.frame.add(inputArea);
        idx.frame.add(send);
        idx.frame.add(leave);

        idx.frame.setVisible(true);
    }

    private void addEventListener() {
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 1. Controller 를 통해 데이터 센드
                // TODO 2. 받은 데이터 chatArea 에 덧붙이기
                CommandDTO commandDTO = new CommandDTO();
                commandDTO.setRno(rno);
                commandDTO.setText(inputArea.getText());
                dispatcherController.in("chat", "sendBroadCast", commandDTO);
            }
        });
        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO socket 끊기.
                // FIXME Controller
                dispatcherController.in("chat", "disconnect");
                Index.getInstance().show("roomList");
            }
        });
    }
}
