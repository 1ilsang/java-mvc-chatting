package util;

import dto.ModelAndView;
import resources.view.IView;

public class View {
    public static void showFrame(IView view, ModelAndView modelAndView) {
        System.out.println("ShowFrame: " + view);
        view.show(modelAndView);
    }
}
