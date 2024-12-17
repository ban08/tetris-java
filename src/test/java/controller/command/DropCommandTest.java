package controller.command;

import controller.ControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DropCommandTest {

    private ControllerInterface mockController;
    private DropCommand dropCommand;

    @BeforeEach
    void setUp() {
        mockController = mock(ControllerInterface.class);
        dropCommand = new DropCommand(mockController);
    }

    @Test
    void execute_ShouldCallDropOnController() {
        // Act
        dropCommand.execute();

        // Assert
        verify(mockController, times(1)).drop();
    }
}
