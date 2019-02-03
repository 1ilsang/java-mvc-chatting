import controller.DispatcherController;
import dto.ModelAndView;

public class ChatTest {
    public static void main(String[] args) {
        DispatcherController dispatcherController = DispatcherController.getInstance();
        ModelAndView modelAndView = new ModelAndView("/view/index");
        dispatcherController.in(modelAndView);
    }
}