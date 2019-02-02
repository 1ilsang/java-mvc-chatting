package controller;

import dto.CommandDTO;
import dto.LoginDTO;
import service.LoginService;
import util.GetUrlFirstPattern;

public class LoginController implements IController{
    private DispatcherController dispatcherController;
    private LoginService loginService = LoginService.getInstance();
    public static LoginController getInstance() {
        return loginController;
    }
    private static LoginController loginController = new LoginController();
    private LoginController() {}
    @Override
    public void in(CommandDTO commandDTO) {
        dispatcherController = DispatcherController.getInstance();
        String pattern = GetUrlFirstPattern.getStringPattern(commandDTO);
        System.out.println("LoginController: " + pattern);

        if(pattern.equals("/signIn")) {
            if(loginService.signIn(commandDTO)) {
                // 로그인 정상처리(/view/home)
//                dispatcherController.setUserName(name);

            } else {
                // 로그인 비정상(dialog)
            }

            // c10n 관점 분리. 컨트롤러에 이 로직이 있어도 되겠지? 서비스 로직은 아니니까
        } else if(pattern.equals("/signUp")) {
            LoginDTO loginDTO = loginService.signUp(commandDTO);
            String name = commandDTO.getUserName();
            commandDTO.clear();

            if(loginDTO.isAccess()) {
                dispatcherController.setUserName(name);
                commandDTO.setUserName(name);
                commandDTO.setUrl("/view/roomList");
                dispatcherController.in(commandDTO);
            } else {
                commandDTO.setFlag(true);
                commandDTO.setText(loginDTO.getMessage());
                commandDTO.setUrl("/view/register");
                dispatcherController.in(commandDTO);
            }
        }
    }
}
