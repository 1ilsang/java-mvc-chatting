import thread.ChatRoomSocketThread;
import thread.LogThread;
import thread.LoginSocketThread;

public class ChatServer {

    public static void main(String[] args) {
        LogThread logThread = LogThread.getInstance();

        logThread.log("mainThread: " + Thread.currentThread().getName());
        logThread.log(">> Server is ready... <<");

        LoginSocketThread loginSocketThread = new LoginSocketThread();
        loginSocketThread.start();

        ChatRoomSocketThread chatRoomSocketThread = new ChatRoomSocketThread();
        chatRoomSocketThread.start();

    }
}
