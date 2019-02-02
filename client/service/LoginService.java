package service;

import domain.UserVO;
import dto.CommandDTO;
import dto.LoginDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginService {
    private final static String LOCAL_HOST = "127.0.0.1";
    private final static String PORT = "6666";

    private LoginService() {
    }

    private static LoginService loginService = new LoginService();

    public static LoginService getInstance() {
        return loginService;
    }

    public boolean signIn(CommandDTO commandDTO) {
        return true;
    }

    public LoginDTO signUp(CommandDTO commandDTO) {
        try {
            Socket socket = new Socket(LOCAL_HOST, Integer.parseInt(PORT));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            UserVO userVO = new UserVO();
            userVO.setUserName(commandDTO.getUserName());
            userVO.setPw(commandDTO.getPw());

            LoginDTO sendDTO = new LoginDTO();
            sendDTO.setAction("/signUp");
            sendDTO.setUserVO(userVO);

            out.writeObject(sendDTO);
            out.flush();

            LoginDTO getDTO = (LoginDTO) in.readObject();
            System.out.println("Login " + getDTO.isAccess() + ", " + getDTO.getMessage());

            return getDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
