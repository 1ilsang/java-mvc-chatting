package service;

import domain.UserVO;
import dto.CommandDTO;
import dto.LoginDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginService {
//        private final static String REMOTE_HOST = "127.0.0.1";
    private static final String REMOTE_HOST = "35.243.106.143"; // GCP
    private final static String PORT = "6666";

    private LoginService() {
    }

    private static LoginService loginService = new LoginService();

    public static LoginService getInstance() {
        return loginService;
    }

    public LoginDTO signIn(CommandDTO commandDTO) {
        commandDTO.setUrl("/view/home");
        return getPermission(commandDTO, "/signIn");
    }

    public LoginDTO signUp(CommandDTO commandDTO) {
        commandDTO.setUrl("/view/register");
        return getPermission(commandDTO, "/signUp");
    }

    private LoginDTO getPermission(CommandDTO commandDTO, String action) {
        LoginDTO sendDTO = new LoginDTO();
        sendDTO.setMessage("서버가 연결되어 있지 않습니다!");
        sendDTO.setAccess(false);
        sendDTO.setUrl("/view/home");

        try {
            Socket socket = new Socket(REMOTE_HOST, Integer.parseInt(PORT));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            UserVO userVO = new UserVO();
            userVO.setUserName(commandDTO.getUserName());
            userVO.setPw(commandDTO.getPw());

            sendDTO.setAction(action);
            sendDTO.setUserVO(userVO);

            out.writeObject(sendDTO);
            out.flush();

            LoginDTO getDTO = (LoginDTO) in.readObject();

            getDTO.setUrl(commandDTO.getUrl());
            System.out.println("Login " + action + ": " + getDTO.isAccess() + ", " + getDTO.getMessage());

            return getDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendDTO;
    }
}
