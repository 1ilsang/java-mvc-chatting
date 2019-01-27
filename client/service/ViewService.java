package service;

import dto.MessageDTO;
import resources.view.RoomListView;

public class ViewService {
    private RoomListView roomListView = RoomListView.getInstance();
    private static ViewService viewService = new ViewService();
    public static ViewService getInstance() {
        return viewService;
    }
    private ViewService() {

    }
    public void printChat(MessageDTO messageDTO) {
        roomListView.printChat(messageDTO);
    }
}
