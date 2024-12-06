package controller.command;

import controller.ControllerInterface;

public class RotateLeftCommand implements Command {
    private final ControllerInterface controller;

    public RotateLeftCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.rotateLeft();
    }
}
