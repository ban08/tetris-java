package view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import model.Board;
import model.Piece;
import model.PositionedPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class GameRendererTest {
    @Mock
    Screen screen;
    @Mock
    TextGraphics graphics;

    GameRenderer renderer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        renderer = new GameRenderer(screen);
        when(screen.newTextGraphics()).thenReturn(graphics);

        // Mock terminal size for both tests
        TerminalSize terminalSize = new TerminalSize(20, 20);
        when(screen.getTerminalSize()).thenReturn(terminalSize);
    }

    @Test
    void testRender() throws IOException {
        Board board = new Board(3,3);
        Piece p = new Piece(new char[][]{{'X'}}, null);
        PositionedPiece currentPiece = new PositionedPiece(p,1,1);

        renderer.render(board, currentPiece, 100);
        verify(screen).refresh(); // Ensures no exception and refresh called
    }

    @Test
    void testRenderGameOver() throws IOException {
        renderer.renderGameOver();
        verify(screen).refresh();
    }
}
