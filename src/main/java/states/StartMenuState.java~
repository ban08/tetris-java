package states;

import com.googlecode.lanterna.screen.Screen;
import model.menu.StartMenu;
import music.Music;
import view.GUI;
import view.StartMenuViewer;

import java.io.IOException;

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
