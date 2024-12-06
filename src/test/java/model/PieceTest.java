package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void testGettersAndSetters() {

        TextColor color = TextColor.ANSI.CYAN;
        char[][] shape = { {'A','Z'} };
        Piece piece = new Piece(shape, color);
        assertArrayEquals(shape, piece.getShape());
        assertEquals(color, piece.getColor());

        char[][] newShape = { {'X','X'} };
        piece.setShape(newShape);
        assertArrayEquals(newShape, piece.getShape());
    }
}
