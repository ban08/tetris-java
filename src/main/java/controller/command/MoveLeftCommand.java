package controller.command;

import controller.ControllerInterface;

public class MoveLeftCommand implements Command {
    private final ControllerInterface controller;
    public MoveLeftCommand(ControllerInterface controller) { this.controller = controller; }
    @Override public void execute() { controller.moveLeft(); }
}
