import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionedPieceTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Piece piece = new Piece(new char[][]{"AZAZ".toCharArray()}, TextColor.Indexed.fromRGB(1, 1, 1));

        // Act
        PositionedPiece positionedPiece = new PositionedPiece(piece, 2, 3);
        positionedPiece.moveDown();
        positionedPiece.moveLeft();
        positionedPiece.moveRight();

        // Assert
        assertEquals(2, positionedPiece.getX());
        assertEquals(4, positionedPiece.getY());
        assertSame(piece, positionedPiece.getPiece());
    }

    @Test
    void testRotate_givenEmptyArrayOfChar() {
        // Arrange
        PositionedPiece positionedPiece = new PositionedPiece(
                new Piece(new char[][]{new char[]{}}, TextColor.Indexed.fromRGB(1, 1, 1)), 2, 3);
        Board board = new Board(1, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> positionedPiece.rotate(board, new RotateLeftStrategy()));
    }

    @Test
    void testRotate_givenPositionedPieceWithPieceIsNull() {
        // Arrange
        PositionedPiece positionedPiece = new PositionedPiece(null, 2, 3);
        Board board = new Board(1, 1);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> positionedPiece.rotate(board, new RotateLeftStrategy()));
    }

    @Test
    void testRotate_givenPositionedPieceWithPieceIsPiece() {
        // Arrange
        PositionedPiece positionedPiece = new PositionedPiece(
                new Piece(new char[][]{"AZAZ".toCharArray()}, TextColor.Indexed.fromRGB(1, 1, 1)), -1, 3);
        Board board = new Board(1, 1);

        // Act
        positionedPiece.rotate(board, new RotateLeftStrategy());

        // Assert
        char[][] shape = positionedPiece.getPiece().getShape();
        assertEquals(1, shape.length);
        assertArrayEquals("AZAZ".toCharArray(), shape[0]);
    }

    @Test
    void testRotate_givenPositionedPieceWithPieceIsPieceAndXIsZeroAndYIsMinusOne() {
        // Arrange
        PositionedPiece positionedPiece = new PositionedPiece(
                new Piece(new char[][]{"AZAZ".toCharArray()}, TextColor.Indexed.fromRGB(1, 1, 1)), 0, -1);
        Board board = new Board(1, 1);

        // Act
        positionedPiece.rotate(board, new RotateLeftStrategy());

        // Assert
        char[][] shape = positionedPiece.getPiece().getShape();
        assertEquals(1, shape.length);
        assertArrayEquals("AZAZ".toCharArray(), shape[0]);
    }

}