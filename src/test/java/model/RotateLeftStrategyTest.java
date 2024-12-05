package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotateLeftStrategyTest {

    @Test
    void testRotate_EmptyArray() {
        // Arrange
        RotateLeftStrategy rotateLeftStrategy = new RotateLeftStrategy();
        char[][] input = {};

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> rotateLeftStrategy.rotate(input));
    }

    @Test
    void testRotateSymmetry() {
        char[][] original = {
                {'A', 'B'},
                {'C', 'D'}
        };


        RotateRightStrategy rightStrategy = new RotateRightStrategy();
        RotateLeftStrategy leftStrategy = new RotateLeftStrategy();

        char[][] rotatedRight = rightStrategy.rotate(original);
        char[][] rotatedLeft = leftStrategy.rotate(rotatedRight);


        assertArrayEquals(original, rotatedLeft); // Should pass now
    }
    @Test
    void testRotateRectangularMatrix() {
        char[][] original = {
                {'1', '2', '3'},
                {'4', '5', '6'}
        };


        RotateRightStrategy rightStrategy = new RotateRightStrategy();
        RotateLeftStrategy leftStrategy = new RotateLeftStrategy();

        char[][] rotatedRight = rightStrategy.rotate(original);
        char[][] rotatedLeft = leftStrategy.rotate(rotatedRight);


        assertArrayEquals(original, rotatedLeft);
    }



}