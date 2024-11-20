import com.googlecode.lanterna.TextColor;

public class Piece {
    private final char[][] shape; //forma da peça
    private TextColor color;


    public Piece(char[][] shape, TextColor color) {
        this.shape = shape;
        this.color = color;

    }
    public char[][] getShape() {
        return shape;
    }

    public TextColor getColor() {
        return color;
    }

    public static Piece randomShape(){
        char[][] shape;
        TextColor color;

        switch ((int) (Math.random() * 5)) {
            case 0:
                shape = new char[][]{{'O', 'O'}, {'O', 'O'}};
                color = TextColor.ANSI.YELLOW;
                break;
            case 1:
                shape = new char[][]{{'I', 'I', 'I', 'I'}};
                color = TextColor.ANSI.CYAN;
                break;
            case 2:
                shape = new char[][]{{' ', 'T', ' '}, {'T', 'T', 'T'}};
                color = TextColor.ANSI.MAGENTA;
                break;
            case 3:
                shape = new char[][]{{'J', ' '}, {'J', ' '}, {'J', 'J'}};
                color = TextColor.ANSI.BLUE;
                break;
            case 4:
                shape = new char[][]{{'L', ' '}, {'L', ' '}, {'L', 'L'}};
                color = TextColor.ANSI.RED;
                break;
            default:
                shape = new char[][]{{' ', 'I', ' '}, {' ', 'I', ' '}, {' ', 'I', ' '}}; // Forma do "I" (vertical)
                color = TextColor.ANSI.GREEN;
        }
        return new Piece(shape, color); // Retorna a peça aleatória criada
    }

}



