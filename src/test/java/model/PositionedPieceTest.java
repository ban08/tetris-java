package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PositionedPieceTest {

    @Test
    void testMoves() {
        Piece p = new Piece(new char[][]{{'A'}}, TextColor.ANSI.BLUE);
        PositionedPiece pos = new PositionedPiece(p, 2, 3);

        pos.moveLeft();
        assertEquals(1, pos.getX());
        pos.moveRight();
        assertEquals(2, pos.getX());
        pos.moveDown();
        assertEquals(4, pos.getY());
    }

    @Test
    void testRotate_Valid() {
        Board board = mock(Board.class);
        when(board.getBoard()).thenReturn(new char[5][5]);

        char[][] shape = {{'A','B'},{'C','D'}};
        Piece p = new Piece(shape, TextColor.ANSI.RED);
        PositionedPiece pos = new PositionedPiece(p,0,0);

        RotationStrategy strategy = mock(RotationStrategy.class);
        char[][] rotated = {{'B','D'},{'A','C'}};
        when(strategy.rotate(shape)).thenReturn(rotated);

        when(board.canPlaceShape(rotated,0,0)).thenReturn(true);
        pos.rotate(board, strategy);

        assertArrayEquals(rotated, pos.getPiece().getShape());
    }

    @Test
    void testRotate_InvalidPlacement() {
        Board board = mock(Board.class);
        when(board.getBoard()).thenReturn(new char[5][5]);

        char[][] shape = {{'X','X'}};
        Piece p = new Piece(shape, TextColor.ANSI.GREEN);
        PositionedPiece pos = new PositionedPiece(p,0,0);

        RotationStrategy strategy = mock(RotationStrategy.class);
        char[][] rotated = {{'X','X'}};
        when(strategy.rotate(shape)).thenReturn(rotated);

        when(board.canPlaceShape(rotated,0,0)).thenReturn(false);
        pos.rotate(board, strategy);

        // Should revert
        assertArrayEquals(shape, pos.getPiece().getShape());
    }
}
