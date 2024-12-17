package states;

import view.GUI;
import java.io.IOException;
/**
 * An abstract class representing a state in the game's state machine.
 *
 * <p>A state is responsible for handling user input and updating the game model
 * accordingly. A state can also transition to another state when certain
 * conditions are met.
 *
 *
 */
public abstract class State {
    /**
     * Handles user input and updates the game model accordingly.
     *
     * @param gui the GUI interface to use for rendering and input handling
     * @param time the current time in milliseconds
     * @return the next state to transition to, or {@code null} if the game should
     *         exit
     * @throws IOException if an I/O error occurs
     */
    public abstract State step(GUI gui, long time) throws IOException;
}