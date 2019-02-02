package thread;

import domain.UserVO;
import dto.LoginDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LoginSocketThread extends Thread {
    private static Map<String, UserVO> uMap;
    private final static String PORT = "6666";
    private final static String ACCOUNT_ALREADY_EXIST = "이미 존재하는 사용자 입니다.";
    private final static String LOGIN_SUCCESS = "로그인 성공!";
    private final static String SIGN_UP = "회원 가입 성공!";
    private final static String LOGIN_FAIL = "로그인 실패!";
    private static LogThread logThread;

    public static Map<String, UserVO> getuMap() {
        return uMap;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        logThread = LogThread.getInstance();
        uMap = new HashMap<>();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(PORT));
            logThread.log(threadName + " Login Socket Open");

            while (true) {
                Socket socket = serverSocket.accept();
                logThread.log("Login Request :: " + socket.getInetAddress());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                LoginDTO getDTO = (LoginDTO) in.readObject();
                LoginDTO sendDTO = null;

                String name = getDTO.getUserVO().getUserName();

                // Noti LoginService
                if (getDTO.getAction().equals("/signUp")) {
                    if (uMap.containsKey(name)) {
                        sendDTO = new LoginDTO(false, ACCOUNT_ALREADY_EXIST);
                    } else {
                        uMap.put(name, getDTO.getUserVO());
                        sendDTO = new LoginDTO(true, SIGN_UP);
                    }
                } else if (getDTO.getAction().equals("/signIn")) {
                    if (!uMap.containsKey(name)) {
                        sendDTO = new LoginDTO(false, LOGIN_FAIL);
                    } else if (uMap.get(name).getPw().equals(getDTO.getUserVO().getPw())) {
                        sendDTO = new LoginDTO(true, LOGIN_SUCCESS);
                    } else sendDTO = new LoginDTO(false, LOGIN_FAIL);
                }
                logThread.log("Login " + sendDTO.isAccess() + " " + sendDTO.getMessage());

                out.writeObject(sendDTO);
                out.flush();
                in.close();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
