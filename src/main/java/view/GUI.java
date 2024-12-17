package view;

import java.io.IOException;

/**
 * The GUI interface defines the structure for graphical user interface
 * interactions in the game, including actions, screen management, and
 * resource cleanup.
 */
public interface GUI {
    /**
     * Enum representing possible user actions captured by the GUI.
     */
    enum ACTION {
        ARROW_UP, ARROW_DOWN, ARROW_LEFT, ARROW_RIGHT,
        SPACE, A, D, P, S_KEY, E_KEY, R_KEY, Q_KEY, B_KEY,
        NONE, SELECT, ESCAPE
    }

    /**
     * Retrieves the next user action from the GUI input.
     *
     * @return the next ACTION captured by the GUI
     * @throws IOException if an I/O error occurs during input retrieval
     */
    ACTION getNextAction() throws IOException;

    /**
     * Clears the current display on the GUI.
     */
    void clear();

    /**
     * Refreshes the GUI display to reflect any changes.
     *
     * @throws IOException if an I/O error occurs during the refresh
     */
    void refresh() throws IOException;

    /**
     * Closes the GUI and releases any resources associated with it.
     *
     * @throws IOException if an I/O error occurs during closing
     */
    void close() throws IOException;
}