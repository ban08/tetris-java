package controller.command;

import controller.ControllerInterface;

/**
 * A command to drop the current  piece to the bottom of the board.
 */
public class DropCommand implements Command {
    private final ControllerInterface controller;

    /**
     * Creates a new DropCommand.
     *
     * @param controller the controller to use when dropping the piece
     */
    public DropCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.drop();
    }
}