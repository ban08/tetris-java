package states;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TerminalSize;
import controller.GameController;
import model.Board;
import model.GameModel;
import model.Piece;
import model.PositionedPiece;
import music.Music;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GUI;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests line selection logic in GameState when the bonus is activated.
 */
class GameStateLineSelectionTest {

    private GameModel mockModel;
    private GameController mockController;
    private Music mockMusic;
    private Screen mockScreen;
    private GameState gameState;
    private GUI mockGui;
    private TextGraphics mockTextGraphics;

    @BeforeEach
    void setUp() {
        mockModel = mock(GameModel.class);
        mockController = mock(GameController.class);
        mockMusic = mock(Music.class);
        mockScreen = mock(Screen.class);
        mockGui = mock(GUI.class);

        gameState = new GameState(mockScreen, mockMusic, mockModel, mockController);

        mockTextGraphics = mock(TextGraphics.class);
        when(mockScreen.newTextGraphics()).thenReturn(mockTextGraphics);
        when(mockScreen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        Piece dummyPiece = new Piece(new char[][]{{'X'}}, TextColor.ANSI.YELLOW);
        PositionedPiece dummyPosPiece = new PositionedPiece(dummyPiece, 0, 0);
        when(mockModel.getNextPiece()).thenReturn(dummyPosPiece);

        Board boardStub = mock(Board.class);
        when(boardStub.getBoard()).thenReturn(new char[20][10]);
        when(boardStub.getColors()).thenReturn(new TextColor[20][10]);
        when(mockModel.getBoard()).thenReturn(boardStub);

        when(mockModel.isRunning()).thenReturn(true);
        when(mockModel.isBonusActive()).thenReturn(false);
        when(mockModel.isPaused()).thenReturn(false);
    }

    @Test
    void testSelect3Lines_ExecuteBonus() throws IOException {
        when(mockModel.getBonusCharge()).thenReturn(500);
        when(mockModel.isBonusActive()).thenReturn(false);
        when(mockModel.isPaused()).thenReturn(false);
        when(mockModel.isRunning()).thenReturn(true);

        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.B_KEY);
        gameState.step(mockGui, System.currentTimeMillis());

        when(mockGui.getNextAction()).thenReturn(
                GUI.ACTION.ARROW_DOWN, GUI.ACTION.ARROW_DOWN, GUI.ACTION.SELECT,  // 1st line
                GUI.ACTION.ARROW_DOWN, GUI.ACTION.ARROW_DOWN, GUI.ACTION.SELECT,  // 2nd line
                GUI.ACTION.ARROW_DOWN, GUI.ACTION.ARROW_DOWN, GUI.ACTION.SELECT   // 3rd line
        );

        for (int i = 0; i < 9; i++) {
            gameState.step(mockGui, System.currentTimeMillis());
        }

        verify(mockModel, times(1)).executeBonus(anyList());
    }
}
