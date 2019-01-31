package controller;

import dto.CommandDTO;
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
    public void in(CommandDTO commandDTO) {
        String pattern = GetUrlFirstPattern.getStringPattern(commandDTO);
        commandDTO.setUrl(pattern);
        System.out.println("ViewController: " + pattern);
        viewService.showFrame(commandDTO);
    }
}
