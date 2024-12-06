package controller.command;

import controller.ControllerInterface;

public class MoveDownCommand implements Command {
    private final ControllerInterface controller;

    public MoveDownCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.moveDown();
    }
}
