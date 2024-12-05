package view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import controller.ControllerInterface;
import controller.Game;

import java.io.IOException;

public class GameView {
    private final Screen screen;
    private final GameRenderer renderer;
    private final ControllerInterface controller;
    private final Game game;

    public GameView(Screen screen, ControllerInterface controller, Game game) {
        this.screen = screen;
        this.renderer = new GameRenderer(screen);
        this.controller = controller;
        this.game = game;
    }


    public void render() {
        renderer.render(game.getBoard(), game.getCurrentPiece(), game.getScore());
    }

    public void processInput() {
        try {
            KeyStroke keyStroke;
            while ((keyStroke = screen.pollInput()) != null) {
                switch (keyStroke.getKeyType()) {
                    case ArrowLeft:
                        controller.moveLeft();
                        break;
                    case ArrowRight:
                        controller.moveRight();
                        break;
                    case ArrowDown:
                        controller.moveDown();
                        break;
                    case Character:
                        char key = keyStroke.getCharacter();
                        if (key == 'a') {
                            controller.rotateLeft();
                        } else if (key == 'd') {
                            controller.rotateRight();
                        } else if (key == ' ') {
                            controller.drop();
                        } else if (key == 'p') {
                            controller.pause();
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderGameOver() {
        renderer.renderGameOver();
    }
}
