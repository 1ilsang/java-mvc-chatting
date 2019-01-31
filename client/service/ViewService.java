package service;

import dto.MessageDTO;
import resources.view.HomeView;
import resources.view.IndexView;
import resources.view.RegisterView;
import resources.view.RoomListView;

public class ViewService {
    private HomeView home = HomeView.getInstance();
    private RoomListView roomList = RoomListView.getInstance();
    private RegisterView register = RegisterView.getInstance();
    private RoomListView roomListView = RoomListView.getInstance();
    private IndexView indexView = IndexView.getInstance();
    private static ViewService viewService = new ViewService();
    public static ViewService getInstance() {
        return viewService;
    }
    private ViewService() {

    }
    public void printChat(MessageDTO messageDTO) {
        roomListView.printChat(messageDTO);
    }
    public void showFrame(String s) {
        System.out.println("showFrame: " + s);
        if(s.equals("/home")) home.show();
        else if(s.equals("/roomList")) roomList.show();
        else if(s.equals("/register")) register.show();
    }
}
