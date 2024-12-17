package controller.command;

import controller.ControllerInterface;

/**
 * A command to rotate the current piece to the right.
 */
public class RotateRightCommand implements Command {
    private final ControllerInterface controller;

    /**
     * Constructs a RotateRightCommand with the given controller.
     *
     * @param controller the controller to execute the command on
     */
    public RotateRightCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    /**
     * Executes the command to rotate the current piece to the right.
     */
    @Override
    public void execute() {
        controller.rotateRight();
    }
}