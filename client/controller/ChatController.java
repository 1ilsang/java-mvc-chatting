package controller;

import dto.CommandDTO;
import dto.MessageDTO;
import service.ChatService;
import util.GetUrlFirstPattern;

/**
 * ChatController :: Using Socket :: accept, connect, disconnect, send(1:N, 1:1)
 * <p>
 * param pattern = Method handler
 * rno = Target room(or user) number
 * *FIXME userName = id (Name and id are the same!)
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
    public void in(CommandDTO commandDTO) {
        String pattern = GetUrlFirstPattern.getStringPattern(commandDTO);
        System.out.println("ChatController: " + pattern);

        if (pattern.equals("/sendBroadCast")) {
            chatService.sendBroadCast(commandDTO);
        } else if (pattern.equals("sendRno")) {
            // TODO 1:1
        } else if (pattern.equals("/connect")) {
            commandDTO.setAction("connect");
            chatService.init(commandDTO);
        } else if (pattern.equals("/disconnect")) {
            commandDTO.setAction("disconnect");
            chatService.disconnect(commandDTO);
        }
    }
}
