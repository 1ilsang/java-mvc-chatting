package thread;

import dto.CommandDTO;
import dto.MessageDTO;
import service.ViewService;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class AcceptThread extends Thread {
    private ViewService viewService = ViewService.getInstance();

    // FIXME Using patternString.yaml
    private static final String REMOTE_HOST = "127.0.0.1";
//    private static final String REMOTE_HOST = "35.243.106.143"; // GCP
//    private static final String REMOTE_HOST = "70.12.245.35";   // Wifi
    private boolean flag;
    private MessageDTO messageDTO = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Socket socket = null;
    private CommandDTO commandDTO = null;

    public AcceptThread(CommandDTO commandDTO) {
        this.flag = true;
        this.commandDTO = commandDTO;
    }

    private void stopAcceptThread() {
        // c10n 이거 대체 왜 안되는것 ?
        // FIXME flag 로 run while 멈추는게 안된다. 그냥 에러뜨면서 탈출됨.
        // HACK
//            System.out.println("break!");
        this.flag = false;
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(REMOTE_HOST, 7777);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            this.sendBroadCast(commandDTO);
            while (flag) {
                // FIXME
                messageDTO = (MessageDTO) in.readObject();
                printView(messageDTO);
                sleep(100);
                if (!flag) System.out.println("flag! break!!");
            }
            // TODO 예외 처리 코드 더 잘해보고싶다.
        } catch (EOFException e) {
//                e.printStackTrace();
        } catch (SocketException e) {
//                e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void printView(MessageDTO messageDTO) {
        viewService.printChat(messageDTO);
    }

    public void disconnect(CommandDTO commandDTO) {
        sendBroadCast(commandDTO);
        stopAcceptThread();
    }

    public void sendBroadCast(CommandDTO commandDTO) {
        MessageDTO messageDTO = commandToMessage(commandDTO);
        try {
            out.writeObject(messageDTO);
        } catch (Exception e) {
//                e.printStackTrace();
        }
    }

    private MessageDTO commandToMessage(CommandDTO commandDTO) {
        int flag = 0;
        if (commandDTO.getAction().equals("disconnect")) {
            commandDTO.setText(":: System :: " + commandDTO.getUserName() + " 님이 나갔습니다!");
            flag = 1 << 2;
        } else if (commandDTO.getAction().equals("connect")) {
            commandDTO.setText(":: System :: " + commandDTO.getUserName() + " 님이 접속하셨습니다!");
            flag = 1 << 5;
        }

        MessageDTO messageDTO = new MessageDTO.Builder(commandDTO.getRno(), commandDTO.getUserName())
                .contents(commandDTO.getText())
                .flag(flag)
                .build();

        return messageDTO;
    }
}
