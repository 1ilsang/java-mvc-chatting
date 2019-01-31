package thread;

import java.net.ServerSocket;

public class LoginSocketThread extends Thread {
    private final static String PORT = "6666";
    private static LogThread logThread;

    @Override
    public void run() {
        logThread = LogThread.getInstance();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(PORT));

        } catch (Exception e) {

        }
    }
}
