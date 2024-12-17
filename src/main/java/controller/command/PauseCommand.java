package controller.command;

import controller.ControllerInterface;

/**
 * Command to pause the game.
 */
public class PauseCommand implements Command {
    private final ControllerInterface controller;

    /**
     * Constructs a PauseCommand with the given controller.
     *
     * @param controller the controller to be paused
     */
    public PauseCommand(ControllerInterface controller) {
        this.controller = controller;
    }

    /**
     * Executes the pause command, pausing the game.
     */
    @Override
    public void execute() {
        controller.pause();
    }
}