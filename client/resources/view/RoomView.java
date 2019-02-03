package resources.view;

import controller.DispatcherController;
import dto.ModelAndView;
import dto.MessageDTO;

import javax.swing.*;
import java.awt.*;

/**
 * This can be multiple objects.(2019/01/27 - 2 Rooms)
 * Each object will be chosen by RoomList
 */
public class RoomView implements IView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();
    private InitializationView idx = InitializationView.getInstance();
    private Button send, leave;
    private TextField inputArea;
    private JLabel chatArea;
    private int rno;
    private int chatCnt;

    @Override
    public void show(ModelAndView modelAndView) {
        if(modelAndView.getAction().equals("/print")) {
            printChat(modelAndView);
        } else {
            init();
            addEventListener();

            modelAndView.setRno(this.rno);
            modelAndView.setUrl("/chat/connect");

            dispatcherController.in(modelAndView);
        }
    }

    public RoomView(int rno) {
        this.rno = rno;
    }

    private void init() {
        dispatcherController = DispatcherController.getInstance();
        chatCnt = 0;
        idx.frame.removeAll();
        idx.frame.setTitle("RoomView " + rno);

        send = new Button("send");
        leave = new Button("leave");
        inputArea = new TextField();
        chatArea = new JLabel();

        idx.frame.setLayout(new GridLayout(4, 0));
        idx.frame.add(chatArea);
        idx.frame.add(inputArea);
        idx.frame.add(send);
        idx.frame.add(leave);

        idx.frame.setVisible(true);
    }
    private void sendMessage(ModelAndView modelAndView) {
        // TODO 35 이상일때 다이얼로그
        if (inputArea.getText().equals("") || inputArea.getText().length() > 35) return;

        modelAndView.setUrl("/chat/sendBroadCast");
        modelAndView.setText(dispatcherController.getUserName() + ": " + inputArea.getText());

        dispatcherController.in(modelAndView);

        inputArea.setText("");
    }

    private void addEventListener() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRno(rno);
        modelAndView.setUserName(dispatcherController.getUserName());

        inputArea.addActionListener(e -> sendMessage(modelAndView));
        send.addActionListener(e -> sendMessage(modelAndView));
        leave.addActionListener(e -> {
            modelAndView.setUrl("/chat/disconnect");

            dispatcherController.in(modelAndView);
            modelAndView.setUrl("/view/roomList");
            dispatcherController.in(modelAndView);
        });
    }

    private void printChat(ModelAndView modelAndView) {
        String prev = chatArea.getText()
                .replace("<html>", "")
                .replace("</html>", "");
        String post = modelAndView.getText()
                .replace("<", " &lt ")
                .replace(">", " &gt ");

        if (chatCnt == 4) prev = prev.substring(prev.indexOf("<br/>") + 5);
        else chatCnt++;

        chatArea.setText("<html>" + prev + post + "<br/></html>");
    }
}
