package thread;

import dto.MessageDTO;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ChatSocketThread extends Thread {
    private int SYK = 1 << 5;
    private int FIN = 1 << 2;
    private LogThread logThread = LogThread.getInstance();
    private ArrayList[] roomList;
    private MessageDTO messageDTO = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Socket socket = null;
    private ArrayList curRoom;
    private String curUserName;
    private int curRoomNumber;

    public ChatSocketThread(Socket s, ArrayList[] roomList) throws IOException {
        this.socket = s;
        this.roomList = roomList;
    }

    @Override
    public void run() {
        boolean isFInFlag;
        String threadName = Thread.currentThread().getName();
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            logThread.log(threadName + " :: Socket Open " + socket.getInetAddress());
            while (true) {
                sleep(100);
                messageDTO = (MessageDTO) in.readObject();
                curRoom = roomList[messageDTO.getRoomNumber()];
                curUserName = messageDTO.getName();
                curRoomNumber = messageDTO.getRoomNumber();

                if (!curRoom.contains(this)) curRoom.add(this);
                isFInFlag = manufactureMessageDTO(threadName, messageDTO);

                for (Object e : curRoom) ((ChatSocketThread) e).out.writeObject(messageDTO);
                if (isFInFlag) break;
            }
        } catch (SocketException e) {
        } catch (EOFException e) {
            socketCloseExceptionMessage(threadName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logThread.log(threadName + " :: Socket Close - Leave - " + this.messageDTO.getName());
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean manufactureMessageDTO(String threadName, MessageDTO messageDTO) {
        boolean isFin = false;
        if (messageDTO.getFLAG() == SYK) {
            logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + " 유저가 방에 접속");
        } else if (messageDTO.getFLAG() == FIN) {
            curRoom.remove(this);
            logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + " 유저가 방에서 떠남.");
            isFin = true;
        } else {
            logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getContents());
        }
        return isFin;
    }

    private void socketCloseExceptionMessage(String threadName) {
        curRoom.remove(this);
        logThread.log(threadName + " :: Room[" + messageDTO.getRoomNumber() + "] " + messageDTO.getName() + " 유저가 예외 강제종료");
        MessageDTO messageDTO2 = new MessageDTO.Builder(curRoomNumber, curUserName)
                .contents(" :: System :: " + curUserName + " 님이 나갔습니다!")
                .build();
        for (Object e : curRoom) {
            try {
                ((ChatSocketThread) e).out.writeObject(messageDTO2);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}