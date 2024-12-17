package controller.command;

import controller.ControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RotateRightCommandTest {

    private ControllerInterface mockController;
    private RotateRightCommand rotateRightCommand;

    @BeforeEach
    void setUp() {
        mockController = mock(ControllerInterface.class);
        rotateRightCommand = new RotateRightCommand(mockController);
    }

    @Test
    void execute_ShouldCallRotateRightOnController() {
        // Act
        rotateRightCommand.execute();

        // Assert
        verify(mockController, times(1)).rotateRight();
    }
}
