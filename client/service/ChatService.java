package service;

import dto.MessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatService {
    private static final String LOCAL_HOST = "127.0.0.1";
    private static ChatService chatService = new ChatService();
    private Socket socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private ChatService() {
    }
    public static ChatService getInstance() {
        return chatService;
    }

    public void init() {
        try {
            socket = new Socket(LOCAL_HOST, 7777);
            in = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void end() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Socket
    // FILEIO
    public void sendBroadCast(MessageDTO messageDTO) {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(messageDTO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
