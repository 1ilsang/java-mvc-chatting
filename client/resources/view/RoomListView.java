package resources.view;

import dto.MessageDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomListView implements IView {
    private static RoomListView roomList = new RoomListView();
    private IndexView idx = IndexView.getInstance();
    private RoomView[] rooms = new RoomView[5];
    private Button btn1, btn2, goHomeBtn;

    public static RoomListView getInstance() {
        return roomList;
    }

    private RoomListView() {
    }

    @Override
    public void show() {
        init();
        for (int i = 0; i < 5; i++) rooms[i] = new RoomView(i);
        addEventListener();
    }

    private void init() {
        // TODO 서버에서 데이터 가져와야함.현재 존재하는 채팅방.

        idx.frame.removeAll();
        idx.frame.setTitle("RoomListView");
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
                IndexView.getInstance().show("home");
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
    public void printChat(MessageDTO messageDTO) {
        rooms[messageDTO.getRoomNumber()].printChat(messageDTO);
    }
}
