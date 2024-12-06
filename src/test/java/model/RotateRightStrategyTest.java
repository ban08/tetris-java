package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RotateRightStrategyTest {
    @Test
    void testRotateRight() {
        RotateRightStrategy strategy = new RotateRightStrategy();
        char[][] input = { {'A','B'}, {'C','D'} };
        char[][] result = strategy.rotate(input);
        char[][] expected = {
                {'C','A'},
                {'D','B'}
        };
        assertArrayEquals(expected, result);
    }

    @Test
    void testRotateEmptyThrows() {
        RotateRightStrategy strategy = new RotateRightStrategy();
        assertThrows(IllegalArgumentException.class, () -> strategy.rotate(new char[][]{}));
    }
}
