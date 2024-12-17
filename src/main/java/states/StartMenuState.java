package states;

import com.googlecode.lanterna.screen.Screen;
import model.menu.StartMenu;
import music.Music;
import view.GUI;
import view.StartMenuViewer;

import java.io.IOException;
/**
 * The start menu state of the game.
 * This state is responsible for rendering the start menu and handling user input.
 *
 */
public class StartMenuState extends State {
    private final StartMenu menu;
    private final StartMenuViewer viewer;
    private final Screen screen;
    private final Music music;

    public StartMenuState(Screen screen, Music music) {
        this.screen = screen;
        this.menu = new StartMenu();
        this.music = music;
        this.viewer = new StartMenuViewer(menu);
    }

    /**
     * Advances the state of the start menu based on user input.
     *
     * This method draws the current state of the start menu using the provided GUI,
     * retrieves the next user action, and transitions to the appropriate state.
     * If the 'S' key is pressed, the game transitions to the GameState.
     * If the 'E' key is pressed, the game exits. For any other key, the state remains
     * in the StartMenuState.
     *
     * @param gui the GUI used to interact with the user
     * @param time the current time in milliseconds
     * @return the next state of the game, or null if the game is exiting
     * @throws IOException if an I/O error occurs during the state transition
     */
    @Override
    public State step(GUI gui, long time) throws IOException {
        viewer.draw(gui);
        GUI.ACTION action = gui.getNextAction();
        switch(action) {
            case S_KEY -> {return new GameState(screen,music);}
            case E_KEY -> {return null;}
            default -> {return this;}
        }
    }
}