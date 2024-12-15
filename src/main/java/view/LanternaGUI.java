package view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class LanternaGUI implements GUI {
    private final Screen screen;

    public LanternaGUI(Screen screen) {
        this.screen = screen;
    }

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
            return switch(c) {
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

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public void close() throws IOException {
        screen.close();
    }

    public Screen getScreen() {
        return screen;
    }
}
