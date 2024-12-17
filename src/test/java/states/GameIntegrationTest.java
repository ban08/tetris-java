// src/test/java/states/GameIntegrationTest.java
package states;

import com.googlecode.lanterna.TextColor;
import controller.GameController;
import model.Board;
import model.GameModel;
import model.Piece;
import model.PositionedPiece;
import model.Score;
import model.RandomPieceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the GameController, GameModel, and Board classes.
 */
public class GameIntegrationTest {

    private GameModel model;
    private GameController controller;
    private Board board;
    private Score score;
    private RandomPieceFactory pieceFactory;

    @BeforeEach
    public void setUp() {
        // Initialize the Board with standard Tetris dimensions (e.g., 10 columns x 20 rows)
        board = new Board(10, 20);

        // Initialize the Score
        score = new Score();

        // Initialize the PieceFactory
        pieceFactory = new RandomPieceFactory();

        // Initialize the GameModel with dependencies
        model = new GameModel(board, score, pieceFactory);

        // Initialize the GameController with the GameModel
        controller = new GameController(model);
    }



    /**
     * Integration Test: Simulate Pause and Resume functionality.
     */
    @Test
    void testPauseAndResume() {
        // Ensure the game starts running and not paused
        assertTrue(model.isRunning(), "Game should start in running state.");
        assertFalse(model.isPaused(), "Game should not be paused initially.");

        // Pause the game
        controller.pause();
        assertTrue(model.isPaused(), "Game should be paused after pausing.");

        // Resume the game
        controller.pause();
        assertFalse(model.isPaused(), "Game should resume after pausing again.");
    }

    /**
     * Integration Test: Simulate Clearing Lines.
     */
    @Test
    void testClearingLines() {
        // Fill the bottom row except the last cell
        int bottomRow = board.getBoard().length - 1;
        for (int col = 0; col < board.getBoard()[0].length - 1; col++) {
            PositionedPiece positionedPiece = new PositionedPiece(
                    new Piece(new char[][]{{'X'}}, TextColor.ANSI.BLUE),
                    col,
                    bottomRow
            );
            board.addPositionedPiece(positionedPiece);
        }

        // Create a current piece that will fill the last cell in the bottom row
        Piece singleBlock = new Piece(new char[][]{{'X'}}, TextColor.ANSI.RED);
        PositionedPiece newPiece = new PositionedPiece(singleBlock, board.getBoard()[0].length - 1, bottomRow - 1);
        model.setCurrentPiece(newPiece);

        // Drop the piece, which should lock it into the board and clear the line
        controller.drop();

        // Verify the row is cleared
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            assertEquals(' ', board.getBoard()[bottomRow][col], "Bottom row should be cleared after line completion.");
        }

        // Verify score increment (assuming 1 line cleared adds 100 points)
        assertEquals(100, model.getScorePoints(), "Score should increase by 100 after clearing one line.");
    }
}
