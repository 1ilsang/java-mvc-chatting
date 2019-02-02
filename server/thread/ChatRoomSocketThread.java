package thread;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoomSocketThread extends Thread {

    private static final String PORT = "7777";
    private static LogThread logThread;
    private static ArrayList[] roomList = new ArrayList[5];

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) roomList[i] = new ArrayList<ChatSocketThread>();

        logThread = LogThread.getInstance();
        ServerSocket ss = null;

        try {
            logThread.start();
            logThread.log("SocketRoomThread: " + Thread.currentThread().getName());
            ss = new ServerSocket(Integer.parseInt(PORT));
            logThread.log(">> Socket Room Thread is ready... <<");

            while (true) {
                try {
                    Socket s = ss.accept();
                    ChatSocketThread t = new ChatSocketThread(s, roomList);
                    t.start();
                } catch (EOFException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
