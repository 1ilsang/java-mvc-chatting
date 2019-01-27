package resources.view;

import controller.DispatcherController;
import dto.CommandDTO;
import dto.MessageDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * This can be multiple objects.(2019/01/27 - 2 Rooms)
 * Each object will be chosen by RoomList
 */
public class RoomView implements IView {
    private DispatcherController dispatcherController = DispatcherController.getInstance();
    private IndexView idx = IndexView.getInstance();
    private Button send, leave;
    private TextField inputArea;
    private JLabel chatArea;
    private int rno;
    private int chatCnt;

    @Override
    public void show() {
        init();
        addEventListener();
        dispatcherController.in("chat", "connect");
    }

    public RoomView(int rno) {
        this.rno = rno;
    }

    private void init() {
        idx.frame.removeAll();
        idx.frame.setTitle("RoomView" + rno);

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
    private void sendMessage() {
        if (inputArea.getText().equals("")) return;
        // TODO Functionalization this.
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.setRno(rno);
        commandDTO.setText(inputArea.getText());
        commandDTO.setUserName(dispatcherController.getUserName());
        dispatcherController.in("chat", "sendBroadCast", commandDTO);
        inputArea.setText("");
    }

    private void addEventListener() {
        inputArea.addActionListener(e -> sendMessage());
        send.addActionListener(e -> sendMessage());
        leave.addActionListener(e -> {
            // TODO Front Controller
            dispatcherController.in("chat", "disconnect");
            IndexView.getInstance().show("roomList");
        });
    }

    public void printChat(MessageDTO messageDTO) {
        String prev = chatArea.getText().replace("<html>", "").replace("</html>", "");
        if (chatCnt == 4) prev = prev.substring(prev.indexOf("<br/>") + 5);
        else chatCnt++;
        // XXX Important :: XSS :: You must block XSS.
        chatArea.setText("<html>" + prev + messageDTO.getName() + ": " + messageDTO.getContents() + "<br/></html>");
    }
}
