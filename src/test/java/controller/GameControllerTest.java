package controller;

import model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the GameController class.
 */
public class GameControllerTest {

    @Mock
    private GameModel mockModel;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and inject them into GameController
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMoveLeft() {
        // Act
        gameController.moveLeft();

        // Assert
        verify(mockModel, times(1)).moveLeft();
    }

    @Test
    public void testMoveRight() {
        // Act
        gameController.moveRight();

        // Assert
        verify(mockModel, times(1)).moveRight();
    }

    @Test
    public void testMoveDown() {
        // Act
        gameController.moveDown();

        // Assert
        verify(mockModel, times(1)).moveDown();
    }

    @Test
    public void testRotateLeft() {
        // Act
        gameController.rotateLeft();

        // Assert
        verify(mockModel, times(1)).rotateLeft();
    }

    @Test
    public void testRotateRight() {
        // Act
        gameController.rotateRight();

        // Assert
        verify(mockModel, times(1)).rotateRight();
    }

    @Test
    public void testDrop() {
        // Act
        gameController.drop();

        // Assert
        verify(mockModel, times(1)).drop();
    }

    @Test
    public void testPause() {
        // Act
        gameController.pause();

        // Assert
        verify(mockModel, times(1)).togglePause();
    }

    @Test
    public void testUpdateGame() {
        // Act
        gameController.updateGame();

        // Assert
        verify(mockModel, times(1)).update();
    }

    @Test
    public void testIsGameRunning() {
        // Arrange
        when(mockModel.isRunning()).thenReturn(true);

        // Act
        boolean isRunning = gameController.isGameRunning();

        // Assert
        verify(mockModel, times(1)).isRunning();
        assertTrue(isRunning, "Game should be running");
    }
}
