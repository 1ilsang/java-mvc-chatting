package util;

import dto.ModelAndView;
import resources.view.*;

public class ViewResolver {
    private static HomeView home = HomeView.getInstance();
    private static RoomListView roomList = RoomListView.getInstance();
    private static RegisterView register = RegisterView.getInstance();
    private static RoomView[] room = RoomListView.getInstance().rooms;
    private static InitializationView index;

    private ViewResolver() {}

    public static IView getView(ModelAndView modelAndView) {
        String s = GetUrlFirstPattern.getStringPattern(modelAndView);
        System.out.println("ViewResolver get: " + s);

        if(s == null) return null;
        else if(s.equals("/home")) return home;
        else if(s.equals("/index")) {
             index = InitializationView.getInstance();
             return home;
        }
        else if(s.equals("/roomList")) return roomList;
        else if(s.equals("/register")) return register;
        else if(s.equals("/room")) return room[modelAndView.getRno()];
        else return null;
    }
}
