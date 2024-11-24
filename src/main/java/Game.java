import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class Game {
    private final Board board;
    private final GameRenderer renderer;
    private final Score score;
    private final PieceSelector pieceSelector;
    private boolean running;
    private final Screen screen;

    /**
     * Constructor for Game.
     * @param screen the screen to render the game on
     */
    public Game(Screen screen) {
        this.screen = screen;
        this.board = new Board(10, 20);
        this.renderer = new GameRenderer(screen);
        this.score = new Score();
        this.pieceSelector = new PieceSelector();
        this.running = true;

        board.addObserver(score);
    }

    /**
     * Start the game loop.
     */
    public void run() {
        PositionedPiece currentPiece = generateNewPiece();

        while (running) {
            renderer.render(board, currentPiece, score.getPoints());
            processInput(currentPiece);

            // Check if the piece can move down; otherwise, lock it in place
            if (!board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
                board.addPositionedPiece(currentPiece);
                board.deleteFullLines();

                // Generate a new piece and check for Game Over
                currentPiece = generateNewPiece();
                if (!board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY())) {
                    running = false; // Stop the loop
                    renderer.renderGameOver(); // Show "Game Over" screen
                    break;
                }
            } else {
                currentPiece.moveDown();
            }

            sleep(); // Slow down the game loop
        }
    }


    /**
     * Generate a new piece for the game.
     * @return the new piece
     */
    private PositionedPiece generateNewPiece() {
        return new PositionedPiece(pieceSelector.randomPiece(), 4, 0);
    }

    /**
     * Process user input.
     * @param currentPiece the current piece being moved
     */
    private void processInput(PositionedPiece currentPiece) {
        try {
            KeyStroke keyStroke;
            while ((keyStroke = screen.pollInput()) != null) {
                switch (keyStroke.getKeyType()) {
                    case ArrowLeft:
                        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() - 1, currentPiece.getY())) {
                            currentPiece.moveLeft();
                        }
                        break;

                    case ArrowRight:
                        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() + 1, currentPiece.getY())) {
                            currentPiece.moveRight();
                        }
                        break;

                    case ArrowDown:
                        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
                            currentPiece.moveDown();
                        }
                        break;

                    case Character:
                        char key = keyStroke.getCharacter();
                        if (key == 'd') {
                            currentPiece.rotate(board, new RotateRightStrategy());
                        } else if (key == 'a'){
                            currentPiece.rotate(board, new RotateLeftStrategy());
                        } else if (key == ' ') {
                            // Instant drop
                            while (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
                                currentPiece.moveDown();
                            }
                        } else if (key == 'p') {
                            pauseGame();
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pause the game.
     * @throws IOException if an I/O exception occurs
     */
    private void pauseGame() throws IOException {
        System.out.println("Game paused. Press 'p' again to resume.");
        while (true) {
            KeyStroke keyStroke = screen.readInput(); // Wait for user input
            if (keyStroke != null && keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'p') {
                System.out.println("Game resumed.");
                break;
            }
        }
    }

    /**
     * Sleep for a short duration.
     */
    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}