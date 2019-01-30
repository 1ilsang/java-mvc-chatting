import dto.MessageDTO;
import thread.LogThread;
import thread.SocketThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private static final String PORT = "7777";
    private static LogThread logThread;
    private static ArrayList[] roomList = new ArrayList[5];

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 5; i++) roomList[i] = new ArrayList<SocketThread>();

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
                    SocketThread t = new SocketThread(s, roomList);
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
