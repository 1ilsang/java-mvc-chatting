package controller;

import dto.ModelAndView;
import service.ChatService;
import util.GetUrlFirstPattern;

/**
 * ChatController :: Using Socket :: accept, connect, disconnect, send(1:N, 1:1)
 * <p>
 * param
 * pattern = Method handler
 * rno = Target room(or user) number
 * userName = id
 * message = Chatting text
 */
public class ChatController implements IController {
    private static ChatController chatController = new ChatController();
    private ChatService chatService = ChatService.getInstance();

    private ChatController() {
    }

    public static ChatController getInstance() {
        return chatController;
    }

    @Override
    public ModelAndView in(ModelAndView modelAndView) {
        String pattern = GetUrlFirstPattern.getStringPattern(modelAndView);
        System.out.println("ChatController: " + pattern);

        if (pattern.equals("/sendBroadCast")) {
            chatService.sendBroadCast(modelAndView);
        } else if (pattern.equals("/sendRno")) {
            // TODO 1:1
        } else if (pattern.equals("/connect")) {
            modelAndView.setAction("connect");
            chatService.init(modelAndView);
        } else if (pattern.equals("/disconnect")) {
            modelAndView.setAction("disconnect");
            chatService.disconnect(modelAndView);
        }
        return null;
    }
}
