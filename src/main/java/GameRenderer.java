import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;


import java.io.IOException;

public class GameRenderer {
    //classe que desenha o tabuleiro

    private final Screen screen;

    public GameRenderer(Screen screen){
        this.screen = screen;
    }

    public void render(Board board, int score){
        TextGraphics graphic  = screen.newTextGraphics();
        char[][] newBoard = board.getBoard();
        TextColor[][] colors = board.getColors();

        //Exibir tabuleiro
        for (int i=0; i < newBoard.length; i++){
            for (int j = 0; j < newBoard[i].length; j++){
                graphic.setBackgroundColor(colors[i][j]);
                graphic.setForegroundColor(TextColor.ANSI.WHITE);
                graphic.putString(i, j, String.valueOf(newBoard[i][j]));
            }
        }

        //Exibir pontuação
        graphic.setBackgroundColor(TextColor.ANSI.BLACK);
        graphic.setForegroundColor(TextColor.ANSI.WHITE);
        graphic.putString(0, newBoard.length, "Score: " + score);

        try {
            screen.refresh();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
