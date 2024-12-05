package model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;

class PieceTest {
    @Test
    void testGettersAndSetters() {
        // Arrange
        TextColor.Indexed color = TextColor.Indexed.fromRGB(1, 1, 1);
        char[][] shape = {"AZAZ".toCharArray()};

        // Act
        Piece piece = new Piece(shape, color);
        piece.setShape(shape);

        // Assert
        assertEquals(color, piece.getColor());
        assertSame(shape, piece.getShape());
        assertArrayEquals(shape[0], piece.getShape()[0]);
    }
}
