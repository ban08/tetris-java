package controller.command;

import controller.ControllerInterface;

/**
 * Command to move the current piece left in the game.
 */

public class MoveLeftCommand implements Command {
    private final ControllerInterface controller;

    /**
     * Creates a new MoveLeftCommand.
     *
     * @param controller the controller to use
     */
    public MoveLeftCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    /**
     * Executes the command.
     */
    @Override
    public void execute() {
        controller.moveLeft();
    }
}