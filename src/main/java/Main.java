import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import controller.Game;
import view.GameView;

public class Main {
    public static void main(String[] args) {
        try {
            // Set up the screen
            TerminalSize terminalSize = new TerminalSize(10, 22);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();

            // Initialize MVC components
            Game game = new Game();
            GameView view = new GameView(screen, game, game);

            // controller.Game loop
            while (game.isRunning()) {
                view.render();
                view.processInput();
                game.update();
                Thread.sleep(500); // Control the game speed
            }

            // Render controller.Game Over screen
            view.renderGameOver();
            screen.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
