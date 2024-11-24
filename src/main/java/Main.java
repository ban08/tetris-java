import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

/**
 * The main class for the game. This class sets up the screen and runs the game.
 */
public class Main {
    /**
     * The main method which starts the game.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            // Set up the screen
            TerminalSize terminalSize = new TerminalSize(10, 22); // Board size plus space for the score
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();

            // Run the game
            Game game = new Game(screen);
            game.run();

            // Close the screen
            screen.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}