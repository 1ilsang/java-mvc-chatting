import dto.MessageDTO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    static ArrayList<Socket> roomList;

    static class ServerThread extends Thread {
        MessageDTO messageDTO = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        Socket socket = null;

        public ServerThread(Socket s) throws IOException {
            this.socket = s;
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());
        }

        @Override
        public void run() {
            try {
                while(true) {
                    messageDTO = (MessageDTO) in.readObject();
                    System.out.println(messageDTO.getName() + ", " + messageDTO.getContents());
//                    if(messageDTO.getFIN() == 1) break;
//                for (int i = 0; i < roomList.size(); i++) {
//                    out = new ObjectOutputStream(roomList.get(i).getOutputStream());
                    out.writeObject(messageDTO);
//                }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = null;
        roomList = new ArrayList<>();
        try {
            ss = new ServerSocket(7777);
            System.out.println("Server is ready...");
            while (true) {
                Socket s = ss.accept();
                roomList.add(s);
                System.out.println("client " + s.getInetAddress());
                ServerThread t = new ServerThread(s);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ss.close();
        }
    }
}
