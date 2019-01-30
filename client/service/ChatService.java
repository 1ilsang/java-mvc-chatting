package service;

import dto.CommandDTO;
import thread.AcceptThread;

/**
 * Need Thread-safe logic
 */
public class ChatService implements IChatService {
    private static ChatService chatService = new ChatService();
    AcceptThread acceptThread = null;

    private ChatService() {
    }

    public static ChatService getInstance() {
        return chatService;
    }

    public void init(CommandDTO commandDTO) {
        try {
            acceptThread = new AcceptThread(commandDTO);
            acceptThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(CommandDTO commandDTO) {
        acceptThread.disconnect(commandDTO);
    }

    public void sendBroadCast(CommandDTO commandDTO) {
        acceptThread.sendBroadCast(commandDTO);
    }
}
