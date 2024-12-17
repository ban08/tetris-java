/**
 * A command that moves the active tetromino to the right.
 */
package controller.command;

import controller.ControllerInterface;

/**
 * A command that moves the current piece to the right.
 */
public class MoveRightCommand implements Command {
    private final ControllerInterface controller;

    /**
     * Creates a new MoveRightCommand.
     *
     * @param controller the controller to use
     */
    public MoveRightCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    /**
     * Executes the command.
     *
     */
    @Override
    public void execute() {
        controller.moveRight();
    }
}