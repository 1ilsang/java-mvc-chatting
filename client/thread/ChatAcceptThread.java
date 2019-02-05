package thread;

import controller.DispatcherController;
import dto.ModelAndView;
import dto.MessageDTO;
import service.ViewService;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ChatAcceptThread extends Thread {
    private ViewService viewService = ViewService.getInstance();
    private DispatcherController dispatcherController;

    // FIXME Using patternString.yaml
    private static final String REMOTE_HOST = "127.0.0.1";      // Local host
//    private static final String REMOTE_HOST = "35.243.106.143"; // GCP
//    private static final String REMOTE_HOST = "70.12.245.35";   // Wifi
    private boolean flag;
    private MessageDTO messageDTO = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Socket socket = null;
    private ModelAndView modelAndView = null;

    public ChatAcceptThread(ModelAndView modelAndView) {
        this.flag = true;
        this.modelAndView = modelAndView;
    }

    @Override
    public void run() {
        dispatcherController = DispatcherController.getInstance();
        try {
            this.socket = new Socket(REMOTE_HOST, 7777);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            this.sendBroadCast(modelAndView);
            while (flag) {
                // FIXME
                messageDTO = (MessageDTO) in.readObject();
                printView(messageDTO);
                sleep(100);
                if (!flag) System.out.println("flag! break!!");
            }
            // TODO 예외 처리 코드 더 잘해보고싶다.
        } catch (EOFException | SocketException e) {
//                e.printStackTrace();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setUrl("/view/room");
        modelAndView.setRno(messageDTO.getRoomNumber());
        modelAndView.setAction("/print");
        modelAndView.setUserName(messageDTO.getName());
        modelAndView.setText(messageDTO.getContents());

        dispatcherController.in(modelAndView);
    }

    public void disconnect(ModelAndView modelAndView) {
        sendBroadCast(modelAndView);
    }

    public void sendBroadCast(ModelAndView modelAndView) {
        MessageDTO messageDTO = commandToMessage(modelAndView);
        try {
            out.writeObject(messageDTO);
        } catch (Exception e) {
//                e.printStackTrace();
        }
    }

    private MessageDTO commandToMessage(ModelAndView modelAndView) {
        int flag = 0;
        if (modelAndView.getAction().equals("disconnect")) {
            modelAndView.setText(":: System :: " + modelAndView.getUserName() + " 님이 나갔습니다!");
            flag = 1 << 2;
        } else if (modelAndView.getAction().equals("connect")) {
            modelAndView.setText(":: System :: " + modelAndView.getUserName() + " 님이 접속하셨습니다!");
            flag = 1 << 5;
        }

        MessageDTO messageDTO = new MessageDTO.Builder(modelAndView.getRno(), modelAndView.getUserName())
                .contents(modelAndView.getText())
                .flag(flag)
                .build();

        return messageDTO;
    }
}
