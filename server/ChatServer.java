import thread.LogThread;
import thread.ChatSocketThread;
import thread.LoginSocketThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private static final String PORT = "7777";
    private static LogThread logThread;
    private static ArrayList[] roomList = new ArrayList[5];

    public static void main(String[] args) throws IOException {

        LoginSocketThread loginSocketThread = new LoginSocketThread();
        loginSocketThread.start();

        for (int i = 0; i < 5; i++) roomList[i] = new ArrayList<ChatSocketThread>();

        logThread = LogThread.getInstance();
        ServerSocket ss = null;

        try {
            logThread.start();
            logThread.log("mainThread: " + Thread.currentThread().getName());
            ss = new ServerSocket(Integer.parseInt(PORT));
            logThread.log(">> Server is ready... <<");

            // FIXME how to break ?
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
            ss.close();
            logThread.stopThis();
        }
    }
}
