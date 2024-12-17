package view;

import model.GameModel;
import java.util.List;
/**
 * The GameView class is responsible for rendering the game state using a
 * {@link GameRenderer}. It provides two methods for rendering the game state:
 * one for rendering the normal game state, and one for rendering the game state
 * while the user is choosing lines to clear during the bonus round.
 *
 */
public class GameView {
    private final GameRenderer renderer;
    private final GameModel model;

    /**
     * Creates a new GameView instance.
     *
     * @param renderer the {@link GameRenderer} to use for rendering
     * @param model the {@link GameModel} to render
     */
    public GameView(GameRenderer renderer, GameModel model) {
        this.renderer = renderer;
        this.model = model;
    }

    /**
     * Renders the game state using the given timer string.
     *
     * @param timer the timer string to display
     */
    public void render(String timer) {
        renderer.render(
                model.getBoard(),
                model.getCurrentPiece(),
                model.getScorePoints(),
                model.getBonusCharge(),
                model.isBonusActive(),
                false,        
                0,            
                List.of(),    
                model.isPaused(),
                model.getNextPiece().getPiece(),
                timer
        );
    }

    /**
     * Renders the game state while the user is choosing lines to clear during
     * the bonus round.
     *
     * @param choosingLines whether the user is currently choosing lines
     * @param selectedLine the currently selected line
     * @param chosenLines the lines that have already been chosen
     * @param timer the timer string to display
     */
    public void render(boolean choosingLines, int selectedLine, List<Integer> chosenLines, String timer) {
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
                timer
        );
    }
}