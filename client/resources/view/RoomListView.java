package resources.view;

import controller.DispatcherController;
import dto.ModelAndView;

import java.awt.*;

public class RoomListView implements IView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();
    private static RoomListView roomList = new RoomListView();
    private InitializationView idx = InitializationView.getInstance();
    public RoomView[] rooms;
    private Button btn1, btn2, btn3, goHomeBtn;
    private Label emptyLabel;

    public static RoomListView getInstance() {
        return roomList;
    }

    private RoomListView() {
        rooms = new RoomView[5];
        for (int i = 0; i < 4; i++) rooms[i] = new RoomView(i);
    }

    @Override
    public void show(ModelAndView modelAndView) {
        roomList = getInstance();
        init();
        // TODO Create dynamic size
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setUserName(dispatcherController.getUserName());

        goHomeBtn.addActionListener(e -> {
            modelAndView.setUrl("/view/home");
            dispatcherController.in(modelAndView);
        });
        btn1.addActionListener(e -> rooms[1].show(modelAndView));
        btn2.addActionListener(e -> rooms[2].show(modelAndView));
        btn3.addActionListener(e -> rooms[3].show(modelAndView));
    }
}
