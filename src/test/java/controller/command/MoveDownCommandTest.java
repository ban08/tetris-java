package controller.command;

import controller.ControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class MoveDownCommandTest {
    @Mock
    ControllerInterface controller;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() {
        MoveDownCommand cmd = new MoveDownCommand(controller);
        cmd.execute();
        verify(controller).moveDown();
    }
}
