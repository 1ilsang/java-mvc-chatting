package thread;

import dto.MessageDTO;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketThread extends Thread {
    private LogThread logThread = LogThread.getInstance();
    private ArrayList<SocketThread> roomList;
    private String threadName;

    MessageDTO messageDTO = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    Socket socket = null;

    public SocketThread(Socket s, ArrayList<SocketThread> list) throws IOException {
        threadName = Thread.currentThread().getName();
        roomList = list;
        socket = s;
        in = new ObjectInputStream(s.getInputStream());
        out = new ObjectOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        try {
            logThread.log(threadName+ " :: Socket Open " + socket.getInetAddress());
            while (true) {
                messageDTO = (MessageDTO) in.readObject();
                logThread.log(threadName + " :: " + messageDTO.getName() + ": " + messageDTO.getContents());

                for (SocketThread e : roomList) e.out.writeObject(messageDTO);
            }
        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logThread.log(threadName + " :: Socket Close - Leave - " + this.messageDTO.getName());
            roomList.remove(this);
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
