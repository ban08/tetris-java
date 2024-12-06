package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RotateLeftStrategyTest {
    @Test
    void testRotateLeft() {
        RotateLeftStrategy strategy = new RotateLeftStrategy();
        char[][] input = { {'A','B'}, {'C','D'} };
        char[][] result = strategy.rotate(input);

        char[][] expected = {
                {'B','D'},
                {'A','C'}
        };
        assertArrayEquals(expected, result);
    }

    @Test
    void testRotateEmptyThrows() {
        RotateLeftStrategy strategy = new RotateLeftStrategy();
        assertThrows(IllegalArgumentException.class, () -> strategy.rotate(new char[][]{}));
    }
}
