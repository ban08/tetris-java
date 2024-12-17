package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import model.Board;
import model.PositionedPiece;
import model.Piece;

import java.io.IOException;
import java.util.List;
/**
 * A class responsible for rendering the game state to the screen.
 *
 */
public class GameRenderer {
    private final Screen screen;

    public GameRenderer(Screen screen) {
        this.screen = screen;
    }

    /**
     * Renders the game state to the screen.
     *
     * <p>This method takes the following parameters:
     * <ul>
     *     <li>board: the game board to render</li>
     *     <li>currentPiece: the active piece to render</li>
     *     <li>score: the score to render</li>
     *     <li>bonusCharge: the bonus charge to render</li>
     *     <li>bonusActive: whether the bonus is active or not</li>
     *     <li>choosingLines: whether the player is choosing lines or not</li>
     *     <li>selectedLine: the currently selected line</li>
     *     <li>chosenLines: the lines chosen by the player</li>
     *     <li>paused: whether the game is paused or not</li>
     *     <li>nextPiece: the next piece to render</li>
     *     <li>timer: the timer string to render</li>
     * </ul>
     *
     * <p>This method first clears the screen, then renders the game board, the active piece, the
     * next piece, the score, the bonus charge, the timer, and the instructions. It also handles the
     * bonus active and choosing lines states.
     *
     * <p>This method is called by the {@link model.GameModel} class when the game state changes.
     */
    public void render(Board board,
                       PositionedPiece currentPiece,
                       int score,
                       int bonusCharge,
                       boolean bonusActive,
                       boolean choosingLines,
                       int selectedLine,
                       List<Integer> chosenLines,
                       boolean paused,
                       Piece nextPiece,
                       String timer) {
        screen.clear();
        TextGraphics g = screen.newTextGraphics();
        g.setBackgroundColor(TextColor.ANSI.BLACK);
        g.setForegroundColor(TextColor.ANSI.WHITE);

        int cols = screen.getTerminalSize().getColumns();
        int rows = screen.getTerminalSize().getRows();

        int boardWidth = board.getBoard()[0].length;
        int boardHeight = board.getBoard().length;

        int boardX = (cols - boardWidth) / 2;
        int boardY = (rows - boardHeight) / 2;

        drawBox(g, boardX - 1, boardY - 1, boardWidth + 2, boardHeight + 2, TextColor.ANSI.WHITE);
        renderBoard(g, board, boardX, boardY);
        renderActivePiece(g, currentPiece, boardX, boardY);

        g.setBackgroundColor(TextColor.ANSI.BLACK);
        g.setForegroundColor(TextColor.ANSI.WHITE);

        g.putString(2, rows / 2, "Score: " + score);

        int filled = bonusCharge / 50;
        String bonusBar = "Bonus: [" + "=".repeat(filled) + " ".repeat(10 - filled) + "]";
        g.putString(2, (rows / 2) + 2, bonusBar);

        g.putString(cols - 15, 2, timer);

        renderNextPiece(g, nextPiece, cols - 15, 4);

        if (bonusActive && choosingLines) {
            g.putString(2, (rows / 2) + 4, "Select 3 lines with ENTER:");
            for (int y = 0; y < boardHeight; y++) {
                String marker = " ";
                if (y == selectedLine) marker = ">";
                if (chosenLines.contains(y)) marker = "*";
                g.setBackgroundColor(TextColor.ANSI.BLACK);
                g.setForegroundColor(TextColor.ANSI.WHITE);
                g.putString(boardX - 3, boardY + y, marker);
            }
        }

        String instructions = "[Arrows: Move | Space: Drop | A/D: Rotate | B: Bonus | P: Pause | E: Exit ]";
        g.putString((cols - instructions.length()) / 2, rows - 1, instructions);

        if (paused) {
            String pausedText = "PAUSED";
            g.putString((cols - pausedText.length()) / 2, rows / 2 - 2, pausedText);
        }

        refreshScreen();
    }

    /**
     * Draws a rectangular box on the screen with specified dimensions and color.
     *
     * <p>The box is drawn using ASCII characters with the corners represented by:
     * <ul>
     *     <li> '┌' for the top-left corner</li>
     *     <li> '┐' for the top-right corner</li>
     *     <li> '└' for the bottom-left corner</li>
     *     <li> '┘' for the bottom-right corner</li>
     * </ul>
     * The sides of the box are drawn using '─' for horizontal lines and '│' for vertical lines.
     *
     * @param g the TextGraphics object used to draw on the screen
     * @param x the x-coordinate of the top-left corner of the box
     * @param y the y-coordinate of the top-left corner of the box
     * @param width the width of the box
     * @param height the height of the box
     * @param color the color of the box's outline
     */
    private void drawBox(TextGraphics g, int x, int y, int width, int height, TextColor color) {
        g.setForegroundColor(color);
        g.setBackgroundColor(TextColor.ANSI.BLACK);
        char topLeft = '┌', topRight = '┐', bottomLeft = '└', bottomRight = '┘', horizontal = '─', vertical = '│';

        g.putString(x, y, String.valueOf(topLeft));
        for (int i = 1; i < width - 1; i++) g.putString(x + i, y, String.valueOf(horizontal));
        g.putString(x + width - 1, y, String.valueOf(topRight));

        for (int row = 1; row < height - 1; row++) {
            g.putString(x, y + row, String.valueOf(vertical));
            g.putString(x + width - 1, y + row, String.valueOf(vertical));
        }

        g.putString(x, y + height - 1, String.valueOf(bottomLeft));
        for (int i = 1; i < width -1; i++) g.putString(x + i, y + height - 1, String.valueOf(horizontal));
        g.putString(x + width - 1, y + height - 1, String.valueOf(bottomRight));
    }

    /**
     * Renders the game board on the screen.
     *
     * <p>This method iterates over each cell of the board, setting the background color
     * according to the color of the cell and placing the character representing the cell
     * at the appropriate position on the screen using the provided TextGraphics object.
     *
     * <p>The board is rendered at the specified offset coordinates, effectively positioning
     * the top-left corner of the board at (offsetX, offsetY) on the screen.
     *
     * @param g the TextGraphics object used to draw on the screen
     * @param board the game board containing the characters and colors to render
     * @param offsetX the x-coordinate offset for rendering the board
     * @param offsetY the y-coordinate offset for rendering the board
     */
    private void renderBoard(TextGraphics g, Board board, int offsetX, int offsetY) {
        char[][] gameBoard = board.getBoard();
        TextColor[][] colors = board.getColors();
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                g.setBackgroundColor(colors[row][col]);
                g.setForegroundColor(TextColor.ANSI.WHITE);
                g.putString(offsetX + col, offsetY + row, String.valueOf(gameBoard[row][col]));
            }
        }
    }

    /**
     * Renders the active piece at the specified offset coordinates.
     *
     * <p>This method renders the active piece at the specified offset coordinates
     * by iterating over each cell of the piece's shape and rendering it on the
     * screen using the provided TextGraphics object. The background color of each
     * cell is set to the color of the piece, and the foreground color is set to
     * white. The piece is rendered at the position (offsetX + x, offsetY + y) on
     * the screen, where (x, y) is the position of the piece on the board.
     *
     * @param g the TextGraphics object used to draw on the screen
     * @param currentPiece the active piece containing the shape and color to render
     * @param offsetX the x-coordinate offset for rendering the piece
     * @param offsetY the y-coordinate offset for rendering the piece
     */
    private void renderActivePiece(TextGraphics g, PositionedPiece currentPiece, int offsetX, int offsetY) {
        char[][] shape = currentPiece.getPiece().getShape();
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        TextColor color = currentPiece.getPiece().getColor();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != ' ') {
                    g.setBackgroundColor(color);
                    g.setForegroundColor(TextColor.ANSI.WHITE);
                    g.putString(offsetX + x + col, offsetY + y + row, "█");
                }
            }
        }
    }

    private void renderNextPiece(TextGraphics g, Piece nextPiece, int startX, int startY) {
        int boxWidth = 8;
        int boxHeight = 6;
        drawBox(g, startX, startY, boxWidth, boxHeight, TextColor.ANSI.WHITE);

        char[][] shape = nextPiece.getShape();
        TextColor color = nextPiece.getColor();
        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        int offsetX = startX + (boxWidth - shapeWidth) / 2;
        int offsetY = startY + (boxHeight - shapeHeight) / 2;

        for (int r = 0; r < shapeHeight; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != ' ') {
                    g.setBackgroundColor(color);
                    g.setForegroundColor(TextColor.ANSI.WHITE);
                    g.putString(offsetX + c, offsetY + r, "█");
                }
            }
        }

        g.setBackgroundColor(TextColor.ANSI.BLACK);
        g.setForegroundColor(TextColor.ANSI.WHITE);
        g.putString(startX + 1, startY + boxHeight - 1, "Next");
    }

    private void refreshScreen() {
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}