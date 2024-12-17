package controller.command;

import controller.ControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PauseCommandTest {

    private ControllerInterface mockController;
    private PauseCommand pauseCommand;

    @BeforeEach
    void setUp() {
        mockController = mock(ControllerInterface.class);
        pauseCommand = new PauseCommand(mockController);
    }

    @Test
    void execute_ShouldCallPauseOnController() {
        // Act
        pauseCommand.execute();

        // Assert
        verify(mockController, times(1)).pause();
    }
}
