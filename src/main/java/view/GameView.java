package view;

import model.GameModel;
import java.util.List;

public class GameView {
    private final GameRenderer renderer;
    private final GameModel model;

    public GameView(GameRenderer renderer, GameModel model) {
        this.renderer = renderer;
        this.model = model;
    }

    public void render(String timer) {
        renderer.render(
                model.getBoard(),
                model.getCurrentPiece(),
                model.getScorePoints(),
                model.getBonusCharge(),
                model.isBonusActive(),
                false,        // choosingLines
                0,            // selectedLine
                List.of(),    // chosenLines (empty)
                model.isPaused(),
                model.getNextPiece().getPiece(),
                timer
        );
    }

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
