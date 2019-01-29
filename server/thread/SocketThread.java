package thread;

import dto.MessageDTO;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketThread extends Thread {
    private int SYK = 1 << 5;
    private int FIN = 1 << 2;
    private LogThread logThread = LogThread.getInstance();
    private ArrayList[] roomList;
    private MessageDTO messageDTO = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Socket socket = null;

    public SocketThread(Socket s, ArrayList[] roomList) throws IOException {
        this.socket = s;
        this.roomList = roomList;
        this.in = new ObjectInputStream(s.getInputStream());
        this.out = new ObjectOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        boolean endF = false;
        String threadName = Thread.currentThread().getName();
        try {
            logThread.log(threadName + " :: Socket Open " + socket.getInetAddress());
            while (true) {
                messageDTO = (MessageDTO) in.readObject();
                if (!roomList[messageDTO.getRoomNumber()].contains(this)) {
                    roomList[messageDTO.getRoomNumber()].add(this);
                }
                if (messageDTO.getFLAG() == SYK) {
                    logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + " 유저가 방에 접속");
                    messageDTO.setContents(":: System :: " + messageDTO.getName() + " 님이 접속하셨습니다!");
                    messageDTO.setFLAG(1 >> 5);
                } else if (messageDTO.getFLAG() == FIN) {
                    logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + " 유저가 방에서 떠남.");
                    messageDTO.setFLAG(1 >> 2);
                    roomList[this.messageDTO.getRoomNumber()].remove(this);
                    endF = true;
                } else {
                    logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + ": " + messageDTO.getContents());
                    messageDTO.setContents(messageDTO.getName() + ": " + messageDTO.getContents());
                }
                for (Object e : roomList[messageDTO.getRoomNumber()]) {
                    ((SocketThread) e).out.writeObject(messageDTO);
                }
                if (endF) break;
            }
        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logThread.log(threadName + " :: Socket Close - Leave - " + this.messageDTO.getName());
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
