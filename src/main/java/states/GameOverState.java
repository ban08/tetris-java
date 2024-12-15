package states;

import com.googlecode.lanterna.screen.Screen;
import model.menu.GameOverMenu;
import music.Music;
import view.GUI;

import java.io.IOException;

public class GameOverState extends State {
    private final GameOverMenu menu;
    private final Screen screen;
    private final Music music;

    public GameOverState(Screen screen, Music music) {
        this.screen = screen;
        this.music = music;
        this.menu = new GameOverMenu();
    }

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
