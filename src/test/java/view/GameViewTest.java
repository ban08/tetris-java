package view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import controller.ControllerInterface;
import model.Board;
import model.GameModel;
import model.Piece;
import model.PositionedPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class GameViewTest {
    @Mock
    Screen screen;
    @Mock
    ControllerInterface controller;
    @Mock
    GameModel model;

    GameView view;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(model.getBoard()).thenReturn(new Board(10,20));
        when(model.getCurrentPiece()).thenReturn(new PositionedPiece(new Piece(new char[][]{{'O'}}, null),4,0));
        when(model.getScorePoints()).thenReturn(0);

        view = new GameView(screen, controller, model);
    }

    @Test
    void testProcessInput_MoveLeft() throws Exception {
        when(screen.pollInput())
                .thenReturn(new KeyStroke(KeyType.ArrowLeft, false, false))
                .thenReturn(null);

        view.processInput();
        verify(controller).moveLeft();
    }
    @Test
    void testProcessInput_CharSpaceDrop() throws Exception {
        when(screen.pollInput())
                .thenReturn(new KeyStroke(' ', false, false, false))
                .thenReturn(null); // Return null on second call to end input reading

        view.processInput();
        verify(controller).drop();
    }

    @Test
    void testProcessInput_NoInput() throws Exception {
        when(screen.pollInput()).thenReturn(null);
        view.processInput();
        verifyNoInteractions(controller);
    }
}
