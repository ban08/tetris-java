package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import model.menu.StartMenu;

import java.io.IOException;

public class StartMenuViewer {
    private final StartMenu menu;

    public StartMenuViewer(StartMenu menu) {
        this.menu = menu;
    }

    public void draw(GUI gui) throws IOException {
        if (!(gui instanceof LanternaGUI)) {
            throw new IllegalStateException("GUI must be a LanternaGUI instance");
        }
        LanternaGUI lg = (LanternaGUI) gui;

        Screen screen = lg.getScreen();
        screen.clear();
        TextGraphics tg = screen.newTextGraphics();
        tg.setBackgroundColor(TextColor.ANSI.BLACK);
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        int cols = screen.getTerminalSize().getColumns();
        int rows = screen.getTerminalSize().getRows();

        String[] title = {
                "  ████████ ███████ ████████ ██████  ██ ███████ ",
                "     ██    ██         ██    ██   ██ ██ ██      ",
                "     ██    █████      ██    ██████  ██ ███████ ",
                "     ██    ██         ██    ██   ██ ██      ██ ",
                "     ██    ███████    ██    ██   ██ ██ ███████ "

        };
        int startRow = (rows / 2) - (title.length / 2) - 2;
        for (int i = 0; i < title.length; i++) {
            tg.putString((cols - title[i].length()) / 2, startRow + i, title[i]);
        }

        String startText = "Press S to Start Game";
        String exitText = "Press E to Exit";
        tg.putString((cols - startText.length()) / 2, startRow + title.length + 2, startText);
        tg.putString((cols - exitText.length()) / 2, startRow + title.length + 4, exitText);

        screen.refresh();
    }
}
