package controller.command;

import controller.ControllerInterface;

    /**
     * A command to rotate the current piece left.
     *
     */

    public class RotateLeftCommand implements Command {
        private final ControllerInterface controller;

        /**
         * Constructs a RotateLeftCommand with the specified controller.
         *
         * @param controller the controller to execute the command
         */
        public RotateLeftCommand(ControllerInterface controller) {
            this.controller = controller;
        }

        /**
         * Executes the command to rotate the current piece left.
         */
        @Override public void execute() { controller.rotateLeft(); }
    }