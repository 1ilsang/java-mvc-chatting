package thread;

import dto.MessageDTO;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
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
        boolean isFInFlag;
        String threadName = Thread.currentThread().getName();
        try {
            logThread.log(threadName + " :: Socket Open " + socket.getInetAddress());
            while (true) {
                messageDTO = (MessageDTO) in.readObject();
                ArrayList curRoom = roomList[messageDTO.getRoomNumber()];

                if (!curRoom.contains(this)) curRoom.add(this);
                isFInFlag = setMsgDTO(threadName, messageDTO);

                for (Object e : curRoom) ((SocketThread) e).out.writeObject(messageDTO);
                if (isFInFlag) break;
            }
        } catch (EOFException e) {
        } catch (SocketException e) {
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

    private boolean setMsgDTO(String threadName, MessageDTO messageDTO) {
        boolean isFin = false;
        if (messageDTO.getFLAG() == SYK) {
            logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + " 유저가 방에 접속");
            messageDTO.setContents(":: System :: " + messageDTO.getName() + " 님이 접속하셨습니다!");
            messageDTO.setFLAG(1 >> 5);
        } else if (messageDTO.getFLAG() == FIN) {
            logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + " 유저가 방에서 떠남.");
            messageDTO.setFLAG(1 >> 2);
            roomList[this.messageDTO.getRoomNumber()].remove(this);
            isFin = true;
        } else {
            logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + ": " + messageDTO.getContents());
            messageDTO.setContents(messageDTO.getName() + ": " + messageDTO.getContents());
        }
        return isFin;
    }
}
