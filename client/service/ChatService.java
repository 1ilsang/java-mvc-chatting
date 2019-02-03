package service;

import dto.ModelAndView;
import thread.ChatAcceptThread;

/**
 * Need Thread-safe logic
 */
public class ChatService {
    private static ChatService chatService = new ChatService();
    private ChatAcceptThread chatAcceptThread = null;

    private ChatService() {
    }

    public static ChatService getInstance() {
        return chatService;
    }

    public void init(ModelAndView modelAndView) {
        try {
            chatAcceptThread = new ChatAcceptThread(modelAndView);
            chatAcceptThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(ModelAndView modelAndView) {
        chatAcceptThread.disconnect(modelAndView);
    }

    public void sendBroadCast(ModelAndView modelAndView) {
        chatAcceptThread.sendBroadCast(modelAndView);
    }
}
