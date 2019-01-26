package resources.view;

import controller.ChatController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// 각 방의 실질적인 유저임.
public class Room implements IView {
    private Index idx = Index.getInstance();
    private Button send, leave;
    private TextField inputArea;
    private Label chatArea;
    private int rno;

    @Override
    public void show() {
        init();
        addEventListener();
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
            }
        });
        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO socket 끊기.
                // FIXME Controller
                Index.getInstance().show("roomList");
            }
        });
    }
}
