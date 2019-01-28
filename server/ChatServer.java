import dto.MessageDTO;
import thread.LogThread;
import thread.SocketThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private static LogThread logThread;
    private static ArrayList<SocketThread> roomList;

    public static void main(String[] args) throws IOException {
        logThread = LogThread.getInstance();
        roomList = new ArrayList<>();
        ServerSocket ss = null;

        try {
            logThread.start();
            logThread.log("mainThread: " + Thread.currentThread().getName());
            ss = new ServerSocket(7777);
            logThread.log(">> Server is ready... <<");

            // FIXME how to break ?
            while (true) {
                Socket s = ss.accept();
                SocketThread t = new SocketThread(s, roomList);
                roomList.add(t);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ss.close();
            logThread.stopThis();
        }
    }
}
