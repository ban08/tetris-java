import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class Game {
    private final Board board;
    private final GameRenderer renderer;
    private final PieceSelector pieceSelector;
    private final Score score;
    private final boolean running;

    public Game(){
        this.board = new Board(10, 20);//Tabuleiro 10x20
        Screen screen = drawScreen();
        this.renderer = new GameRenderer(screen);
        this.pieceSelector = new PieceSelector();
        this.score = new Score(500);
        this.running = true ;
    }

    private Screen drawScreen(){
        try {
            TerminalSize terminalSize = new TerminalSize(10, 22); //incluir espaço para a pontuação
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();
            return screen;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    public void run(){
        Piece currPiece = pieceSelector.selectPiece((int) (Math.random() * 5)); //seleciona uma das 5 peças disponíveis na lista
        int posX = 5, posY = 0; //posição inicial da peça, meio da primeira linha superior do tabuleiro

        while (running){
            renderer.render(board, score.getPoints());

            if (score.isBonusActive()){
                score.useBonus(board);
            }
            //peça caindo
            board.addPiece(currPiece, posX, posY);
            posY++;

            if (posY>=board.getBoard().length){//quando a peça atinge o fundo, posY volta a 0
                posY= 0;
                currPiece = pieceSelector.selectPiece((int) (Math.random() * 5));//volta a escolher uma peça aleatória
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
