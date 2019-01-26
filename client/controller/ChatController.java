package controller;

import dto.CommandDTO;
import dto.MessageDTO;
import service.ChatService;
/**
 * ChatController :: Using Socket :: listen, connect, disconnect, send(1:N, 1:1)
 *          The actual operation is 'Service'
 *
 * param pattern = Method handler
 *       rno = Target room(or user) number
 *       *FIXME userName = id (Name and id are the same!)
 *       message = Chatting text
 */
public class ChatController implements IController{
    private static ChatController chatController = new ChatController();
    private ChatService chatService = ChatService.getInstance();
    private ChatController() {}
    public static ChatController getInstance() {
        return chatController;
    }

    public void in(String pattern) {
        if(pattern.equals("disconnect")) {
            this.disconnect();
        } else if(pattern.equals("connect")) {
            this.chatInit();
        }
    }
    public void in(String pattern, CommandDTO commandDTO) {
        MessageDTO messageDTO = new MessageDTO(commandDTO.getRno(), commandDTO.getUserName(), commandDTO.getText());
        if(pattern.equals("sendBroadCast")) {
            this.sendBroadCast(messageDTO);
        } else if(pattern.equals("sendRno")) {

        }
    }

    private void chatInit() {
        chatService.init();
    }
    private void sendBroadCast(MessageDTO messageDTO) {
        chatService.sendBroadCast(messageDTO);
    }
    private void disconnect(){
        chatService.end();
    }
}
