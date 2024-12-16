



package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class BoardTest {

    private Board board;
    private BoardObserver observer;

    @BeforeEach
    void setUp() {
        board = new Board(10, 20); // Use standard board size
        observer = mock(BoardObserver.class);
        board.addObserver(observer);
    }

    @Test
    void testInitializeBoard() {
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                assertEquals(' ', board.getBoard()[row][col], "Board should initialize with empty spaces.");
                assertEquals(TextColor.ANSI.BLACK, board.getColors()[row][col], "Board should initialize with black color.");
            }
        }
    }

    @Test
    void testAddObserver() {
        BoardObserver anotherObserver = mock(BoardObserver.class);
        board.addObserver(anotherObserver);

        assertEquals(2, board.getObservers().size(), "There should be two observers after adding another.");
        assertTrue(board.getObservers().contains(observer), "Original observer should be present.");
        assertTrue(board.getObservers().contains(anotherObserver), "New observer should be present.");
    }


    @Test
    void testCanPlaceShape_ValidPlacement() {
        char[][] shape = {
                {'X', 'X'},
                {'X', 'X'}
        };
        assertTrue(board.canPlaceShape(shape, 0, 0), "Should allow placing shape within bounds.");
    }

    @Test
    void testCanPlaceShape_OutOfBounds() {
        char[][] shape = {
                {'X', 'X'},
                {'X', 'X'}
        };
        assertFalse(board.canPlaceShape(shape, -1, 0), "Should not allow placing shape out of left bound.");
        assertFalse(board.canPlaceShape(shape, 9, 0), "Should not allow placing shape out of right bound.");
        assertFalse(board.canPlaceShape(shape, 0, 19), "Should not allow placing shape out of bottom bound.");
    }

    @Test
    void testCanPlaceShape_Overlap() {
        // Place a piece at (0,0)
        char[][] shape = {
                {'X', 'X'},
                {'X', 'X'}
        };
        board.addPositionedPiece(new PositionedPiece(new Piece(shape, TextColor.ANSI.RED), 0, 0));

        // Attempt to place another piece overlapping at (0,0)
        char[][] overlappingShape = {
                {'O', 'O'},
                {'O', 'O'}
        };
        assertFalse(board.canPlaceShape(overlappingShape, 0, 0), "Should not allow overlapping shapes.");
    }

    @Test
    void testAddPositionedPiece() {
        char[][] shape = {
                {'X', 'X'},
                {'X', 'X'}
        };
        PositionedPiece piece = new PositionedPiece(new Piece(shape, TextColor.ANSI.RED), 1, 1);
        board.addPositionedPiece(piece);

        for (int row = 1; row < 3; row++) {
            for (int col = 1; col < 3; col++) {
                assertEquals('X', board.getBoard()[row][col], "Piece should be placed correctly on the board.");
                assertEquals(TextColor.ANSI.RED, board.getColors()[row][col], "Piece color should be set correctly.");
            }
        }
    }

    @Test
    void testDeleteFullLines_SingleLine() {
        // Fill row 0 completely
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            board.getBoard()[0][col] = 'X';
            board.getColors()[0][col] = TextColor.ANSI.RED;
        }

        // Invoke deleteFullLines
        board.deleteFullLines();

        // Verify that the observer is notified once for one line cleared
        verify(observer, times(1)).onLineCleared(1);

        // Verify that row 0 is cleared
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            assertEquals(' ', board.getBoard()[0][col], "Row 0 should be cleared after deletion.");
            assertEquals(TextColor.ANSI.BLACK, board.getColors()[0][col], "Row 0 color should be reset to black.");
        }
    }

    @Test
    void testDeleteFullLines_NoFullLines() {
        // Place two non-overlapping pieces
        char[][] shape1 = {
                {'X', 'X'},
                {'X', 'X'}
        };
        board.addPositionedPiece(new PositionedPiece(new Piece(shape1, TextColor.ANSI.RED), 0, 0));

        char[][] shape2 = {
                {'X', 'X'},
                {'X', 'X'}
        };
        board.addPositionedPiece(new PositionedPiece(new Piece(shape2, TextColor.ANSI.BLUE), 2, 2));

        // Before deletion, verify pieces are placed correctly
        for (int row = 0; row < 4; row++) { // Only rows 0 to 3 are affected
            for (int col = 0; col < 4; col++) { // Only columns 0 to 3 are affected
                if ((row < 2 && col < 2) || (row >= 2 && col >= 2)) {
                    assertEquals('X', board.getBoard()[row][col], "Piece should remain on the board.");
                } else {
                    assertEquals(' ', board.getBoard()[row][col], "Other cells should remain empty.");
                }
            }
        }

        // Invoke deleteFullLines
        board.deleteFullLines();

        // Verify that the observer is not notified
        verify(observer, never()).onLineCleared(anyInt());

        // Assert pieces still exist
        for (int row = 0; row < 4; row++) { // Only rows 0 to 3 are affected
            for (int col = 0; col < 4; col++) { // Only columns 0 to 3 are affected
                if ((row < 2 && col < 2) || (row >= 2 && col >= 2)) {
                    assertEquals('X', board.getBoard()[row][col], "Piece should remain on the board.");
                } else {
                    assertEquals(' ', board.getBoard()[row][col], "Other cells should remain empty.");
                }
            }
        }
    }

    @Test
    void testCheckGameOver_NotGameOver() {
        Board board = new Board(10, 20); // Standard board size
        char[][] shape = {
                {'A', 'A'},
                {'A', 'A'}
        };
        PositionedPiece piece = new PositionedPiece(new Piece(shape, TextColor.ANSI.BLACK), 4, 0); // Spawn near top center
        board.addPositionedPiece(piece);

        // Verify piece placement
        assertEquals('A', board.getBoard()[0][4], "Piece should be placed correctly on the board.");
        assertEquals('A', board.getBoard()[0][5], "Piece should be placed correctly on the board.");
        assertEquals('A', board.getBoard()[1][4], "Piece should be placed correctly on the board.");
        assertEquals('A', board.getBoard()[1][5], "Piece should be placed correctly on the board.");

        // Invoke checkGameOver
        board.checkGameOver(piece);

        // Verify that onGameOver was not called
        verify(observer, never()).onGameOver();
    }

    @Test
    void testCheckGameOver_GameOver() {
        // Place a piece at the top that cannot be placed
        char[][] shape = {
                {'X', 'X'},
                {'X', 'X'}
        };
        PositionedPiece piece = new PositionedPiece(new Piece(shape, TextColor.ANSI.RED), 0, 0);
        board.addPositionedPiece(piece);
        board.checkGameOver(piece);

        // Verify that the observer is notified for game over
        verify(observer, times(1)).onGameOver();
    }

    @Test
    void testShiftLinesDown() {
        // Fill row 0 completely
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            board.getBoard()[0][col] = 'A';
            board.getColors()[0][col] = TextColor.ANSI.RED;
        }

        // Fill row 1 partially
        for (int col = 0; col < board.getBoard()[1].length - 1; col++) {
            board.getBoard()[1][col] = 'B';
            board.getColors()[1][col] = TextColor.ANSI.BLUE;
        }
        // Leave the last cell empty to ensure row 1 is not full

        // Invoke deleteFullLines, which should only clear row 0
        board.deleteFullLines();

        // Verify that row 0 is cleared
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            assertEquals(' ', board.getBoard()[0][col], "Row 0 should be cleared.");
            assertEquals(TextColor.ANSI.BLACK, board.getColors()[0][col], "Row 0 color should be reset to black.");
        }

        // Verify that row 1 remains unchanged
        for (int col = 0; col < board.getBoard()[1].length; col++) {
            if (col < board.getBoard()[1].length - 1) {
                assertEquals('B', board.getBoard()[1][col], "Row 1 should remain unchanged.");
                assertEquals(TextColor.ANSI.BLUE, board.getColors()[1][col], "Row 1 color should remain blue.");
            } else {
                assertEquals(' ', board.getBoard()[1][col], "Row 1 last cell should remain empty.");
                assertEquals(TextColor.ANSI.BLACK, board.getColors()[1][col], "Row 1 last cell color should remain black.");
            }
        }
    }

    @Test
    void testClearBoard() {
        char[][] shape = {
                {'X', 'X'},
                {'X', 'X'}
        };
        board.addPositionedPiece(new PositionedPiece(new Piece(shape, TextColor.ANSI.RED), 0, 0));
        board.addPositionedPiece(new PositionedPiece(new Piece(shape, TextColor.ANSI.BLUE), 2, 2));

        board.resetBoard();

        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                assertEquals(' ', board.getBoard()[row][col], "Board should be cleared.");
                assertEquals(TextColor.ANSI.BLACK, board.getColors()[row][col], "Board colors should reset to black.");
            }
        }
    }
    @Test
    void testAddObserver_NullObserver() {
        assertThrows(NullPointerException.class, () -> board.addObserver(null), "Adding null observer should throw NullPointerException.");
    }
}
