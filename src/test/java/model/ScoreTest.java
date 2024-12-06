package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void testInitialScore() {
        Score score = new Score();
        assertEquals(0, score.getPoints());
    }

    @Test
    void testOnLineCleared() {
        Score score = new Score();
        score.onLineCleared(2);
        score.onLineCleared(1);
        assertEquals(300, score.getPoints()); // (2*100 + 1*100 = 300)
    }

    @Test
    void testOnGameOver() {
        Score score = new Score();
        // Just ensure no exceptions (you could redirect System.out to test actual output)
        score.onGameOver();
    }
}
