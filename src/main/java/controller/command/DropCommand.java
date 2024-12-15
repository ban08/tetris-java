package controller.command;

import controller.ControllerInterface;

public class DropCommand implements Command {
    private final ControllerInterface controller;

    public DropCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.drop();
    }
}
