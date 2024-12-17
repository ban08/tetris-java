package states;

import com.googlecode.lanterna.screen.Screen;
import controller.GameController;
import model.GameModel;
import music.Music;
import view.GUI;
import view.GameRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameState extends State {
    private final GameModel model;
    private final GameController controller;
    private final GameRenderer renderer;
    private final Screen screen;
    private final Music music;

    private int frameCounter;
    private int fallThreshold = 10;
    private boolean choosingLines = false;
    private final List<Integer> chosenLines = new ArrayList<>();
    private int selectedLine = 0;

    // Timer fields
    private long lastUpdateTime;
    private long elapsedTime; // in milliseconds

    public GameState(Screen screen, Music music) {
        this.screen = screen;
        this.music = music;
        this.model = new GameModel();
        this.controller = new GameController(model);
        this.renderer = new GameRenderer(screen);
        this.frameCounter = 0;

        // Initialize timer
        this.elapsedTime = 0;
        this.lastUpdateTime = System.currentTimeMillis();
    }
    /**
     * Overloaded constructor for testing purposes.
     * Allows injection of mocked GameModel and GameController.
     *
     * @param screen     the Lanterna Screen
     * @param music      the Music instance
     * @param model      the GameModel instance (can be mocked)
     * @param controller the GameController instance (can be mocked)
     */
    public GameState(Screen screen, Music music, GameModel model, GameController controller) {
        this.screen = screen;
        this.music = music;
        this.model = model;
        this.controller = controller;
        this.renderer = new GameRenderer(screen);

        // Initialize timer
        this.elapsedTime = 0;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public State step(GUI gui, long time) throws IOException {
        // Calculate delta time
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;

        // Update elapsedTime only if not paused
        if (!model.isPaused()) {
            elapsedTime += deltaTime;
        }

        String formattedTime = formatElapsedTime(elapsedTime);

        renderer.render(
                model.getBoard(),
                model.getCurrentPiece(),
                model.getScorePoints(),
                model.getBonusCharge(),
                model.isBonusActive(),
                choosingLines,
                selectedLine,
                chosenLines,
                model.isPaused(),
                model.getNextPiece().getPiece(),
                formattedTime // Pass the timer string
        );

        if (!model.isRunning()) {
            return new GameOverState(screen, music);
        }

        GUI.ACTION action = gui.getNextAction();
        handleInput(action);
        flushInput();

        int score = model.getScorePoints();
        fallThreshold = Math.max(3, 10 - (score / 1000));

        if (!model.isPaused() && !model.isBonusActive() && !choosingLines) {
            frameCounter++;
            if (frameCounter >= fallThreshold) {
                controller.updateGame();
                frameCounter = 0;
            }
        }

        return this;
    }

    private String formatElapsedTime(long elapsedMillis) {
        long totalSeconds = elapsedMillis / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format("Time: %02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("Time: %02d:%02d", minutes, seconds);
        }
    }

    private void handleInput(GUI.ACTION action) {
        boolean inBonusOrChoosing = model.isBonusActive() || choosingLines;
        switch (action) {
            case ARROW_LEFT -> {
                if (!inBonusOrChoosing && !model.isPaused()) controller.moveLeft();
            }
            case ARROW_RIGHT -> {
                if (!inBonusOrChoosing && !model.isPaused()) controller.moveRight();
            }
            case ARROW_DOWN -> {
                if (choosingLines && !model.isPaused()) {
                    int boardHeight = model.getBoard().getBoard().length;
                    selectedLine = Math.min(boardHeight - 1, selectedLine + 1);
                } else if (!inBonusOrChoosing && !model.isPaused()) {
                    controller.moveDown();
                }
            }
            case ARROW_UP -> {
                if (choosingLines && !model.isPaused()) {
                    selectedLine = Math.max(0, selectedLine - 1);
                }
            }
            case A -> {
                if (!inBonusOrChoosing && !model.isPaused()) controller.rotateLeft();
            }
            case D -> {
                if (!inBonusOrChoosing && !model.isPaused()) controller.rotateRight();
            }
            case SPACE -> {
                if (!inBonusOrChoosing && !model.isPaused()) controller.drop();
            }
            case P -> {
                controller.pause();
            }
            case E_KEY -> {
                System.exit(0);
            }
            case B_KEY -> {
                if (!model.isPaused() && model.getBonusCharge() >= 500 && !model.isBonusActive() && !choosingLines) {
                    model.activateBonus();
                    choosingLines = true;
                    chosenLines.clear();
                    selectedLine = 0;
                }
            }
            case SELECT -> {
                if (choosingLines && !model.isPaused()) {
                    if (!chosenLines.contains(selectedLine)) {
                        chosenLines.add(selectedLine);
                        if (chosenLines.size() == 3) {
                            model.executeBonus(chosenLines);
                            choosingLines = false;
                        }
                    }
                }
            }
            case R_KEY -> {
                if (model.isPaused()) {
                    model.resetGame();
                    elapsedTime = 0;
                    lastUpdateTime = System.currentTimeMillis();
                }
            }
            default -> {}
        }
    }

    private void flushInput() throws IOException {
        while (screen.pollInput() != null) {}
    }
}
