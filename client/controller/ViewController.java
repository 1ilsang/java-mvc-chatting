package controller;

import dto.CommandDTO;

public class ViewController implements IController {
    private static ViewController viewController = new ViewController();

    public static ViewController getInstance() {
        return viewController;
    }

    private ViewController() {
    }

    @Override
    public void in(CommandDTO commandDTO) {

    }
}
