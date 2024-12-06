package view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyType;

import controller.ControllerInterface;
import controller.command.Command;
import controller.command.MoveLeftCommand;
import controller.command.MoveRightCommand;
import controller.command.MoveDownCommand;
import controller.command.RotateLeftCommand;
import controller.command.RotateRightCommand;
import controller.command.DropCommand;
import controller.command.PauseCommand;

import model.GameModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameView {
    private final Screen screen;
    private final GameRenderer renderer;
    private final ControllerInterface controller;
    private final GameModel model;
    private final Map<Character, Command> commandMap;
    private final Map<KeyType, Command> specialKeyCommandMap;

    public GameView(Screen screen, ControllerInterface controller, GameModel model) {
        this.screen = screen;
        this.renderer = new GameRenderer(screen);
        this.controller = controller;
        this.model = model;
        this.commandMap = new HashMap<>();
        this.specialKeyCommandMap = new HashMap<>();

        initializeCommands();
    }

    private void initializeCommands() {
        // Character-based commands
        commandMap.put('a', new RotateLeftCommand(controller));
        commandMap.put('d', new RotateRightCommand(controller));
        commandMap.put(' ', new DropCommand(controller));
        commandMap.put('p', new PauseCommand(controller));

        specialKeyCommandMap.put(KeyType.ArrowLeft, new MoveLeftCommand(controller));
        specialKeyCommandMap.put(KeyType.ArrowRight, new MoveRightCommand(controller));
        specialKeyCommandMap.put(KeyType.ArrowDown, new MoveDownCommand(controller));
    }

    public void render() {
        renderer.render(model.getBoard(), model.getCurrentPiece(), model.getScorePoints());
    }

    public void processInput() {
        try {
            KeyStroke keyStroke;
            while ((keyStroke = screen.pollInput()) != null) {
                if (keyStroke.getKeyType() == KeyType.Character) {
                    char c = keyStroke.getCharacter();
                    Command cmd = commandMap.get(c);
                    if (cmd != null) cmd.execute();
                } else {
                    Command cmd = specialKeyCommandMap.get(keyStroke.getKeyType());
                    if (cmd != null) cmd.execute();
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
