package controller.command;

import controller.ControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RotateLeftCommandTest {

    private ControllerInterface mockController;
    private RotateLeftCommand rotateLeftCommand;

    @BeforeEach
    void setUp() {
        mockController = mock(ControllerInterface.class);
        rotateLeftCommand = new RotateLeftCommand(mockController);
    }

    @Test
    void execute_ShouldCallRotateLeftOnController() {
        // Act
        rotateLeftCommand.execute();

        // Assert
        verify(mockController, times(1)).rotateLeft();
    }
}
