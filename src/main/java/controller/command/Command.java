package controller.command;

    /**
     * This interface represents a command that can be executed by the controller.
     */
    public interface Command {
        /**
         * Executes the command.
         */
        void execute();
    }