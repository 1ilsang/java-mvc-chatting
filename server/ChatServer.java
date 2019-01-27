import dto.MessageDTO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatServer {
    private static ArrayList<ServerThread> roomList;
    private static BufferedWriter bw;
    private static Calendar date;

    private static class ServerThread extends Thread {
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
                    bw.write("[" + date.getTime() + "] " + messageDTO.getName() + ": " + messageDTO.getContents() + "\n");
                    bw.flush();

                    for(ServerThread e: roomList) e.out.writeObject(messageDTO);
                }
            }catch (EOFException e) {

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
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

    public static void main(String[] args) throws IOException {
        date = Calendar.getInstance();
        ServerSocket ss = null;
        roomList = new ArrayList<>();
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        try {
            ss = new ServerSocket(7777);
            System.out.println(">> Server is ready... << " + date.getTime());
            while (true) {
                Socket s = ss.accept();
                bw.write("["+ date.getTime()+ "] Client join: " + s.getInetAddress() + "\n");
                bw.flush();
                ServerThread t = new ServerThread(s);
                roomList.add(t);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ss.close();
            bw.close();
        }
    }
}
