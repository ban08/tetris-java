import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextColor;

import java.io.IOException;

/**
 * Responsible for rendering the Tetris game board and score.
 */
public class GameRenderer {
    private final Screen screen;

    public GameRenderer(Screen screen) {
        this.screen = screen;
    }

    /**
     * Main render method to render the board and score.
     * @param board        The game board.
     * @param currentPiece The currently active piece.
     * @param score        The current score.
     */
    public void render(Board board, PositionedPiece currentPiece, int score) {
        TextGraphics graphics = screen.newTextGraphics();
        renderBoard(graphics, board); // Pass the board explicitly
        renderActivePiece(graphics, currentPiece);
        renderScore(graphics, board, score); // Pass the board explicitly
        refreshScreen();
    }

    /**
     * Render the Tetris board.
     * @param graphics The graphics context.
     * @param board     The game board.
     */
    private void renderBoard(TextGraphics graphics, Board board) {
        char[][] gameBoard = board.getBoard();
        TextColor[][] colors = board.getColors();

        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                graphics.setBackgroundColor(colors[row][col]);
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                graphics.putString(col, row, String.valueOf(gameBoard[row][col]));
            }
        }
    }

    /**
     * Render the score at the bottom of the screen.
     * @param graphics The graphics context.
     * @param board     The game board.
     * @param score     The current score.
     */
    private void renderScore(TextGraphics graphics, Board board, int score) {
        int scoreRow = board.getBoard().length + 1; // Render below the board
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(0, scoreRow, "Score: " + score);
    }

    /**
     * Refresh the screen to apply the updates.
     */
    private void refreshScreen() {
        try {
            screen.refresh();
        } catch (IOException e) {
            System.err.println("Failed to refresh the screen: " + e.getMessage());
        }
    }

    /**
     * Render the currently active piece.
     * @param graphics    The graphics context.
     * @param currentPiece The currently active piece.
     */
    private void renderActivePiece(TextGraphics graphics, PositionedPiece currentPiece) {
        char[][] shape = currentPiece.getPiece().getShape();
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        TextColor color = currentPiece.getPiece().getColor();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != ' ') {
                    graphics.setBackgroundColor(color);
                    graphics.setForegroundColor(TextColor.ANSI.WHITE);
                    graphics.putString(x + col, y + row, String.valueOf(shape[row][col]));
                }
            }
        }
    }

    /**
     * Display a "Game Over" message in the middle of the screen.
     */
    public void renderGameOver() {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.ANSI.RED);
        graphics.setForegroundColor(TextColor.ANSI.WHITE);

        int centerX = screen.getTerminalSize().getColumns() / 2 - 5; // Center horizontally
        int centerY = screen.getTerminalSize().getRows() / 2; // Center vertically

        graphics.putString(centerX, centerY, "GAME OVER");
        refreshScreen();
    }
}