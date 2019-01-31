package resources.view;

import controller.DispatcherController;
import dto.CommandDTO;
import dto.MessageDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomListView implements IView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();
    private static RoomListView roomList = new RoomListView();
    private IndexView idx = IndexView.getInstance();
    private RoomView[] rooms = new RoomView[5];
    private Button btn1, btn2, btn3, goHomeBtn;
    private Label emptyLabel;

    public static RoomListView getInstance() {
        return roomList;
    }

    private RoomListView() {
    }

    @Override
    public void show() {
        init();
        // TODO Create dynamic size
        for (int i = 0; i < 4; i++) rooms[i] = new RoomView(i);
        addEventListener();
    }

    private void init() {
        dispatcherController = DispatcherController.getInstance();
        // TODO 서버에서 데이터 가져와야함.현재 존재하는 채팅방.

        idx.frame.removeAll();
        idx.frame.setTitle("RoomListView");
        idx.frame.setLayout(new GridLayout(6, 0));

        emptyLabel = new Label();
        btn1 = new Button("join1");
        btn2 = new Button("join2");
        btn3 = new Button("join3");
        goHomeBtn = new Button("Back");

        idx.frame.add(emptyLabel);
        idx.frame.add(btn1);
        idx.frame.add(btn2);
        idx.frame.add(btn3);
        idx.frame.add(goHomeBtn);

        idx.frame.setVisible(true);
    }

    private void addEventListener() {
        goHomeBtn.addActionListener(e -> {
            CommandDTO commandDTO = new CommandDTO();
            commandDTO.setUrl("/view/home");
            dispatcherController.in(commandDTO);
        });
        btn1.addActionListener(e -> rooms[1].show());
        btn2.addActionListener(e -> rooms[2].show());
        btn3.addActionListener(e -> rooms[3].show());
    }

    public void printChat(MessageDTO messageDTO) {
        rooms[messageDTO.getRoomNumber()].printChat(messageDTO);
    }
}
