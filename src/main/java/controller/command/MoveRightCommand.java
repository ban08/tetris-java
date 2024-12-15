package controller.command;

import controller.ControllerInterface;

public class MoveRightCommand implements Command {
    private final ControllerInterface controller;
    public MoveRightCommand(ControllerInterface controller) { this.controller = controller; }
    @Override public void execute() { controller.moveRight(); }
}
