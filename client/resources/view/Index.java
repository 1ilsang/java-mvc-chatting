package resources.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Index {
    private String id;
    private Home home = Home.getInstance();
    private RoomList roomList = RoomList.getInstance();
    private Register register = Register.getInstance();

    private static Index main = new Index();

    public static Frame frame;

    public static Index getInstance() {
        return main;
    }

    private Index() {
        frame = new Frame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setLayout(new GridLayout(5, 0));
        frame.setSize(300, 300);

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
