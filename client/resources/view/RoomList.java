package resources.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RoomList implements IView {
    private static RoomList roomList = new RoomList();
    private Index idx = Index.getInstance();
    private Room[] rooms = new Room[5];
    private Button btn1, btn2, goHomeBtn;

    public static RoomList getInstance() {
        return roomList;
    }
    private RoomList() {
        for(int i = 0 ; i < 5; i++) {
            rooms[i] = new Room(i);
        }
    }

    @Override
    public void show() {
        init();
        addEventListener();
    }

    private void init() {
        // TODO 서버에서 데이터 가져와야함.현재 존재하는 채팅방.

        idx.frame.removeAll();
        idx.frame.setTitle("RoomList");
        idx.frame.setLayout(new GridLayout(5, 0));

        btn1 = new Button("join1");
        btn2 = new Button("join2");
        goHomeBtn = new Button("Back");

        idx.frame.setLayout(new FlowLayout());
        idx.frame.add(btn1);
        idx.frame.add(btn2);
        idx.frame.add(goHomeBtn);

        idx.frame.setVisible(true);
    }
    private void addEventListener() {
        goHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Index.getInstance().show("home");
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rooms[1].show();
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rooms[2].show();
            }
        });
    }
}
