package controller.command;

import controller.ControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MoveLeftCommandTest {

    private ControllerInterface mockController;
    private MoveLeftCommand moveLeftCommand;

    @BeforeEach
    void setUp() {
        mockController = mock(ControllerInterface.class);
        moveLeftCommand = new MoveLeftCommand(mockController);
    }

    @Test
    void execute_ShouldCallMoveLeftOnController() {
        // Act
        moveLeftCommand.execute();

        // Assert
        verify(mockController, times(1)).moveLeft();
    }
}
