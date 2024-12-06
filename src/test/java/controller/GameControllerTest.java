package controller;

import model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class GameControllerTest {
    @Mock
    GameModel model;

    private GameController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new GameController(model);
    }

    @Test
    void testMoveLeft() {
        controller.moveLeft();
        verify(model).moveLeft();
    }

    @Test
    void testMoveRight() {
        controller.moveRight();
        verify(model).moveRight();
    }

    @Test
    void testDrop() {
        controller.drop();
        verify(model).drop();
    }

    @Test
    void testRotateLeft() {
        controller.rotateLeft();
        verify(model).rotateLeft();
    }

    @Test
    void testRotateRight() {
        controller.rotateRight();
        verify(model).rotateRight();
    }

    @Test
    void testPause() {
        controller.pause();
        verify(model).pause();
    }

    @Test
    void testUpdateGame() {
        controller.updateGame();
        verify(model).update();
    }

    @Test
    void testIsGameRunning() {
        when(model.isRunning()).thenReturn(true);
        assert(controller.isGameRunning());
    }
}
