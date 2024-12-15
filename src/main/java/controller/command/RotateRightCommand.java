package controller.command;

import controller.ControllerInterface;

public class RotateRightCommand implements Command {
    private final ControllerInterface controller;
    public RotateRightCommand(ControllerInterface controller) { this.controller = controller; }
    @Override public void execute() { controller.rotateRight(); }
}
