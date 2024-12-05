package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    @Test
    void testInitialScore() {
        Score score= new Score();
        int initpoints= score.getPoints();
        assertEquals(0,initpoints);

    }
    @Test
    void testOnlineCleared() {
        Score score= new Score();
        score.onLineCleared(2);
        score.onLineCleared(1);
        score.onLineCleared(3);
        assertEquals(600,score.getPoints());
    }
    @Test
    void testOnGameOver() {
        Score score= new Score();
        score.onGameOver();
    }
}
