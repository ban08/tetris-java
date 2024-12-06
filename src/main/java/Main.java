import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import model.GameModel;
import controller.GameController;
import view.GameView;

public class Main {
    public static void main(String[] args) {
        try {
            TerminalSize terminalSize = new TerminalSize(10, 22);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();

            GameModel model = new GameModel();
            GameController controller = new GameController(model);
            GameView view = new GameView(screen, controller, model);

            while (model.isRunning()) {
                view.render();
                view.processInput();
                controller.updateGame();
                Thread.sleep(500);
            }

            view.renderGameOver();
            screen.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
