package view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
/**
 * A GUI implementation using Lanterna library.
 *
 * <p>This class provides an implementation of the {@link GUI} interface using the Lanterna library.
 * It provides methods to retrieve the next user action, clear the screen, and refresh the screen.
 *
 *
 */
public class LanternaGUI implements GUI {
    private final Screen screen;

    /**
     * Creates a new instance of the LanternaGUI class.
     *
     * @param screen the Lanterna screen to use
     */
    public LanternaGUI(Screen screen) {
        this.screen = screen;
    }

    /**
     * Retrieves the next user action from the GUI.
     *
     * @return the next user action
     * @throws IOException if an I/O error occurs
     */
    @Override
    public ACTION getNextAction() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke == null) return ACTION.NONE;
        if (keyStroke.getKeyType() == KeyType.EOF) return ACTION.NONE;

        if (keyStroke.getKeyType() == KeyType.ArrowUp) return ACTION.ARROW_UP;
        if (keyStroke.getKeyType() == KeyType.ArrowDown) return ACTION.ARROW_DOWN;
        if (keyStroke.getKeyType() == KeyType.ArrowLeft) return ACTION.ARROW_LEFT;
        if (keyStroke.getKeyType() == KeyType.ArrowRight) return ACTION.ARROW_RIGHT;
        if (keyStroke.getKeyType() == KeyType.Escape) return ACTION.ESCAPE;
        if (keyStroke.getKeyType() == KeyType.Enter) return ACTION.SELECT;

        if (keyStroke.getKeyType() == KeyType.Character) {
            char c = Character.toLowerCase(keyStroke.getCharacter());
            return switch (c) {
                case ' ' -> ACTION.SPACE;
                case 'a' -> ACTION.A;
                case 'd' -> ACTION.D;
                case 'p' -> ACTION.P;
                case 's' -> ACTION.S_KEY;
                case 'e' -> ACTION.E_KEY;
                case 'r' -> ACTION.R_KEY;
                case 'q' -> ACTION.Q_KEY;
                case 'b' -> ACTION.B_KEY;
                default -> ACTION.NONE;
            };
        }

        return ACTION.NONE;
    }

    /**
     * Clears the screen.
     */
    @Override
    public void clear() {
        screen.clear();
    }

    /**
     * Refreshes the screen.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    /**
     * Closes the GUI.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        screen.close();
    }

    /**
     * Returns the underlying Lanterna screen.
     *
     * @return the Lanterna screen
     */
    public Screen getScreen() {
        return screen;
    }
}