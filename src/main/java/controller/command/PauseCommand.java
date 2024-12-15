package controller.command;

import controller.ControllerInterface;

public class PauseCommand implements Command {
    private final ControllerInterface controller;
    public PauseCommand(ControllerInterface controller) { this.controller = controller; }
    @Override public void execute() { controller.pause(); }
}
