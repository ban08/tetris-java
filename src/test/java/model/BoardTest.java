package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoardTest {

    @Test
    public void testInitializeBoard() {
        Board board = new Board(3, 3);
        assertEquals(3, board.getBoard().length);
        assertEquals(3, board.getBoard()[0].length);
        assertEquals(' ', board.getBoard()[0][0]);
        assertEquals(TextColor.ANSI.BLACK, board.getColors()[0][0]);
    }

    @Test
    public void testAddObserver() {
        Board board = new Board(3, 3);
        BoardObserver observer = mock(BoardObserver.class);
        board.addObserver(observer);

        assertEquals(1, board.getObservers().size());
        assertEquals(observer, board.getObservers().get(0));  // Verifica se o observador correto foi adicionado
    }

    @Test
    public void testCanPlaceShape() {
        Board board = new Board(3, 3);
        char[][] shape = {{'A', 'A'}, {'A', 'A'}};
        assertTrue(board.canPlaceShape(shape, 0, 0));
        assertFalse(board.canPlaceShape(shape, 2, 3));
    }
    @Test
    public void testAddPositionedPiece() {
        Board board = new Board(3, 3);  // Instanciando o Board real
        Piece piece = new Piece(new char[][]{{'A', 'A'}, {'A', 'A'}}, TextColor.ANSI.BLACK);  // Criando a peça real
        PositionedPiece positionedPiece = new PositionedPiece(piece, 0, 0);  // Criando a peça posicionada

        // Verificando que a posição começa vazia (espaco)
        assertEquals(' ', board.getBoard()[0][0]);

        // Adicionando a peça posicionada ao Board
        board.addPositionedPiece(positionedPiece);

        // Verificando se a peça foi colocada corretamente no tabuleiro
        assertEquals('A', board.getBoard()[0][0]);  // Verifica se a posição foi alterada
        assertEquals(TextColor.ANSI.BLACK, board.getColors()[0][0]);  // Verifica a cor associada
    }

    @Test
    public void testDeleteFullLines() {
        // Mock do observer
        BoardObserver observer = mock(BoardObserver.class);

        // Criando um Board 3x3 e configurando uma linha cheia
        Board board = new Board(3, 3);
        board.getBoard()[0] = new char[]{'A', 'A', 'A'};
        board.getColors()[0] = new TextColor[]{TextColor.ANSI.RED, TextColor.ANSI.RED, TextColor.ANSI.RED};

        // Adicionando o observer
        board.addObserver(observer);

        // Deletando linhas completas
        board.deleteFullLines();

        // Verificando se o observer foi notificado
        verify(observer).onLineCleared(1);

        // Verificando que a linha foi limpa
        assertArrayEquals(new char[]{' ', ' ', ' '}, board.getBoard()[0]);
        assertArrayEquals(new TextColor[]{TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK}, board.getColors()[0]);
    }

    @Test
    public void testCheckGameOver() {
        Board board = new Board(3, 3);
        PositionedPiece piece = new PositionedPiece(new Piece(new char[][]{{'A', 'A'}, {'A', 'A'}}, TextColor.ANSI.BLACK), 1, 1);
        board.checkGameOver(piece);
        // Verificar se o jogo acabou
    }

    @Test
    void testNotifyLineCleared() {
        Board board = new Board(3, 3);
        BoardObserver mockObserver = mock(BoardObserver.class);

        board.addObserver(mockObserver);

        // Add and clear a full line
        board.addPositionedPiece(new PositionedPiece(new Piece(new char[][]{{'A', 'A', 'A'}}, TextColor.ANSI.GREEN), 0, 0));
        board.deleteFullLines();

        verify(mockObserver, times(1)).onLineCleared(1);
    }
}