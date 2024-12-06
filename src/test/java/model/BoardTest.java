package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setup() {
        board = new Board(3, 3);
    }

    @Test
    void testInitializeBoard() {
        assertEquals(3, board.getBoard().length);
        assertEquals(3, board.getBoard()[0].length);
        assertEquals(' ', board.getBoard()[0][0]);
        assertEquals(TextColor.ANSI.BLACK, board.getColors()[0][0]);
    }

    @Test
    void testCanPlaceShape_Valid() {
        char[][] shape = {{'X'}};
        assertTrue(board.canPlaceShape(shape, 1, 1));
    }

    @Test
    void testCanPlaceShape_InvalidOutOfBound() {
        char[][] shape = {{'X','X'}};
        assertFalse(board.canPlaceShape(shape, 2, 2));
    }

    @Test
    void testAddPositionedPiece() {
        char[][] shape = {{'A','A'}};
        PositionedPiece piece = new PositionedPiece(new Piece(shape, TextColor.ANSI.RED), 0, 0);
        board.addPositionedPiece(piece);
        assertEquals('A', board.getBoard()[0][0]);
        assertEquals(TextColor.ANSI.RED, board.getColors()[0][0]);
    }

    @Test
    void testDeleteFullLines() {
        // Fill a line
        char[][] line = {{'X','X','X'}};
        board.addPositionedPiece(new PositionedPiece(new Piece(line, TextColor.ANSI.BLUE),0,0));
        board.deleteFullLines();
        assertEquals(' ', board.getBoard()[0][0]);
    }

    @Test
    void testNotifyGameOver() {
        BoardObserver observer = mock(BoardObserver.class);
        board.addObserver(observer);

        char[][] shape = {{'X','X'}};
        PositionedPiece piece = new PositionedPiece(new Piece(shape, TextColor.ANSI.YELLOW), 2,2);
        board.checkGameOver(piece); // Out of bounds => game over
        verify(observer).onGameOver();
    }

    @Test
    void testNotifyLineCleared() {
        BoardObserver observer = mock(BoardObserver.class);
        board.addObserver(observer);

        char[][] line = {{'X','X','X'}};
        board.addPositionedPiece(new PositionedPiece(new Piece(line, TextColor.ANSI.GREEN),0,0));
        board.deleteFullLines();
        verify(observer).onLineCleared(1);
    }
}
