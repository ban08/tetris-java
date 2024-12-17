package states;

import com.googlecode.lanterna.screen.Screen;
import model.menu.GameOverMenu;
import music.Music;
import view.GUI;

import java.io.IOException;
/**
 * The GameOverState class represents the game over state of the game.
 * It is responsible for rendering the game over screen and handling user input to restart the game or quit.
 *
 *
 */
public class GameOverState extends State {
    private final GameOverMenu menu;
    private final Screen screen;
    private final Music music;

    public GameOverState(Screen screen, Music music) {
        this.screen = screen;
        this.music = music;
        this.menu = new GameOverMenu();
    }

    /**
     * Renders the game over screen and handles user input actions.
     * Clears the screen, displays the "GAME OVER" message, and presents options to
     * either return to the start menu or quit the game. Listens for the user's key
     * input and transitions to the corresponding state based on the action.
     *
     * @param gui the GUI instance used to fetch the next action
     * @param time the current time in milliseconds
     * @return the new State based on user input or the current GameOverState if no relevant action is taken
     * @throws IOException if an error occurs during screen rendering or input fetching
     */
    @Override
    public State step(GUI gui, long time) throws IOException {
        screen.clear();
        var tg = screen.newTextGraphics();
        tg.setBackgroundColor(com.googlecode.lanterna.TextColor.ANSI.BLACK);
        tg.setForegroundColor(com.googlecode.lanterna.TextColor.ANSI.WHITE);
        int cols = screen.getTerminalSize().getColumns();
        int rows = screen.getTerminalSize().getRows();
        String gameOver = "GAME OVER";
        tg.putString((cols - gameOver.length())/2, rows/2, gameOver);
        String retryText = "Press R to Return to Menu or Q to Quit";
        tg.putString((cols - retryText.length())/2, (rows/2)+2, retryText);
        screen.refresh();

        GUI.ACTION action = gui.getNextAction();
        switch(action) {
            case R_KEY -> {return new StartMenuState(screen, music);}
            case Q_KEY -> {return null;}
            default -> {return this;}
        }
    }
}

