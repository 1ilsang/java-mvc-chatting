package resources.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IndexView {
    private String id;
    private HomeView home = HomeView.getInstance();
    private RoomListView roomList = RoomListView.getInstance();
    private RegisterView register = RegisterView.getInstance();

    private static IndexView main = new IndexView();

    public static Frame frame;

    public static IndexView getInstance() {
        return main;
    }

    private IndexView() {
        frame = new Frame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setLayout(new GridLayout(5, 0));
        frame.setSize(300, 300);
        frame.setResizable(false);
        // FIXME Controller
        show("home");
    }

    void show(String s) {
        // 컨트롤러로 분리
        if(s.equals("home")) home.show();
        else if(s.equals("roomList")) roomList.show();
        else if(s.equals("register")) register.show();
    }
}
