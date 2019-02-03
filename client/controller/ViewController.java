package controller;

import dto.ModelAndView;
import service.ViewService;
import util.GetUrlFirstPattern;

public class ViewController implements IController {
    private ViewService viewService = ViewService.getInstance();

    public static ViewController getInstance() {
        return viewController;
    }
    private static ViewController viewController = new ViewController();

    private ViewController() {
    }

    @Override
    public ModelAndView in(ModelAndView modelAndView) {
        String pattern = GetUrlFirstPattern.getStringPattern(modelAndView);
        modelAndView.setUrl(pattern);
        System.out.println("ViewController: " + pattern);
        return modelAndView;
    }
}
