package service;

import dto.CommandDTO;
import dto.MessageDTO;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Need Thread-safe logic
 */
public class ChatService implements IChatService {
    private ViewService viewService = ViewService.getInstance();
    // FIXME Using patternString.yaml
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
        private MessageDTO messageDTO = null;

        AcceptThread() {
            this.flag = true;
        }

        public void stopAcceptThread() {
            this.flag = false;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(socket.getInputStream());
                while (flag) {
                    // FIXME
                    sleep(100);
                    messageDTO = (MessageDTO) in.readObject();
                    printView(messageDTO);
                }
            } catch (EOFException e) {
                // TODO EOFException
            } catch (SocketException e) {
                // TODO SocketException
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printView(MessageDTO messageDTO) {
        viewService.printChat(messageDTO);
    }

    public void init(CommandDTO commandDTO) {
        try {
            socket = new Socket(LOCAL_HOST, 7777);
            out = new ObjectOutputStream(socket.getOutputStream());
            acceptThread = new AcceptThread();
            acceptThread.start();

            sendBroadCast(commandDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(CommandDTO commandDTO) {
        try {
            sendBroadCast(commandDTO);

            acceptThread.stopAcceptThread();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBroadCast(CommandDTO commandDTO) {
        MessageDTO messageDTO = commandToMessage(commandDTO);
        try {
            out.writeObject(messageDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MessageDTO commandToMessage(CommandDTO commandDTO) {
        int flag = 0;
        if(commandDTO.getAction().equals("disconnect")) {
            commandDTO.setText(":: System :: " + commandDTO.getUserName() + " 님이 나갔습니다!");
            flag = 1 << 2;
        }
        else if(commandDTO.getAction().equals("connect")) {
            commandDTO.setText(":: System :: " + commandDTO.getUserName() + " 님이 접속하셨습니다!");
            flag = 1 << 5;
        }

        MessageDTO messageDTO = new MessageDTO.Builder(commandDTO.getRno(), commandDTO.getUserName())
                .contents(commandDTO.getText())
                .flag(flag)
                .build();

        return messageDTO;
    }
}
