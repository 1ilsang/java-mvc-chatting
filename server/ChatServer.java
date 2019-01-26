import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    static class ServerThread extends Thread {
        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;
        ArrayList<Socket> roomList = new ArrayList<>();

        public ServerThread(Socket s) throws IOException {
            this.socket = s;
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        }

        @Override
        public void run() {
            String line;
            try {
                while((line = in.readLine()) != null) {
                    if(line.equalsIgnoreCase("Q")) break;
                    System.out.println("client : " + line);
                    out.println("Server : " + line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(7777);
            System.out.println("Server is ready...");
            while(true) {
                Socket s = ss.accept();
                System.out.println("client " + s.getInetAddress());
                ServerThread t = new ServerThread(s);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
