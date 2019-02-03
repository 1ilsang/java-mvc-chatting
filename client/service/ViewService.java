package service;

import dto.MessageDTO;
import resources.view.RoomListView;

public class ViewService {
    private static ViewService viewService = new ViewService();
    public static ViewService getInstance() {
        return viewService;
    }
    private ViewService() {

    }
}
