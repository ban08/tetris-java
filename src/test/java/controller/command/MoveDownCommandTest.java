package controller.command;

import controller.ControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MoveDownCommandTest {

    private ControllerInterface mockController;
    private MoveDownCommand moveDownCommand;

    @BeforeEach
    void setUp() {
        mockController = mock(ControllerInterface.class);
        moveDownCommand = new MoveDownCommand(mockController);
    }

    @Test
    void execute_ShouldCallMoveDownOnController() {
        // Act
        moveDownCommand.execute();

        // Assert
        verify(mockController, times(1)).moveDown();
    }
}
