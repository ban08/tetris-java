package controller.command;

import controller.ControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MoveRightCommandTest {

    private ControllerInterface mockController;
    private MoveRightCommand moveRightCommand;

    @BeforeEach
    void setUp() {
        mockController = mock(ControllerInterface.class);
        moveRightCommand = new MoveRightCommand(mockController);
    }

    @Test
    void execute_ShouldCallMoveRightOnController() {
        // Act
        moveRightCommand.execute();

        // Assert
        verify(mockController, times(1)).moveRight();
    }
}
