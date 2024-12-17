package controller.command;

import controller.ControllerInterface;

/**
 * Command to move the current piece down in the game.
 */
public class MoveDownCommand implements Command {
    private final ControllerInterface controller;

    /**
     * Constructs a MoveDownCommand with the specified controller.
     *
     * @param controller the controller to execute the move down command on
     */
    public MoveDownCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    /**
     * Executes the command to move the current piece down.
     */
    @Override
    public void execute() {
        controller.moveDown();
    }
}