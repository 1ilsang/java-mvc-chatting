package service;

import dto.MessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Need Thread-safe logic
 */
public class ChatService {
    private ViewService viewService = ViewService.getInstance();
    // FIXME Using patternString.yaml
    private MessageDTO messageDTO = null;
    private static final String LOCAL_HOST = "127.0.0.1";
    private static ChatService chatService = new ChatService();
    private Socket socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private AcceptThread acceptThread = null;
    private ChatService() {
    }
    public static ChatService getInstance() {
        return chatService;
    }

    private class AcceptThread extends Thread {
        private boolean flag;
        AcceptThread() {
            this.flag = true;
        }
        public void stopAcceptThread() {
            this.flag = false;
        }
        // FIXME Socket closed Exception !
        @Override
        public void run() {
            try {
                in = new ObjectInputStream(socket.getInputStream());
                while(flag) {
                    // FIXME it's right?
                    messageDTO = (MessageDTO) in.readObject();
                    printView(messageDTO);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void printView(MessageDTO messageDTO) {
        viewService.printChat(messageDTO);
    }

    public void init() {
        try {
            socket = new Socket(LOCAL_HOST, 7777);
            out = new ObjectOutputStream(socket.getOutputStream());
            acceptThread = new AcceptThread();
            acceptThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void disconnect() {
        try {
            // TODO Send to FIN-ACK MessageDTO
            acceptThread.stopAcceptThread();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendBroadCast(MessageDTO messageDTO) {
        try {
            out.writeObject(messageDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
