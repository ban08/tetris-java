import com.googlecode.lanterna.TextColor;

public class Board {

    private final char[][] board; //tabuleiro onde se armazena as peças
    private final int width;
    private final int height;
    private final TextColor[][] colors;
    private final Score score;

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        this.board = new char[height][width];
        this.colors = new TextColor[height][width];
        this.score = new Score(500); //exemplo de 500 pontos para bonus

        //inicializar um tabuleiro
        for (int i = 0; i<height; i++){
            for (int j = 0; j < width; j++){
                board[i][j] = ' ';
                colors[i][j] = TextColor.ANSI.BLACK;
            }
        }
    }

    public char[][] getBoard(){
        return board;
    }

    //cores das peças no tabuleiro
    public TextColor[][] getColors() {
        return colors;
    }

    public Score getScore(){
        return score;
    }



    //adicionar piece ao board na posição (x, y)
    public void addPiece(Piece piece, int x, int y) {
        char[][] shape = piece.getShape();
        TextColor color = piece.getColor();

        for (int row = 0; row < shape.length; row++) { //percorre as linhas da peça
            for (int column = 0; column < shape[row].length; column++) { //percorre todas as colunas de cada linha da peça
                if (shape[row][column] != ' ') { //se a célula não for vazia, faz parte da peça
                    int newX = x + column;
                    int newY = y + row;

                    if (newX >= 0 && newY < width && newY>=0 && newY < height){ //verificar se a posição está dentro das margens do tabuleiro
                        //colocar o caractere de cada célula da peça na posição correspondente do tabuleiro
                        board[newY][newX] = shape[row][column];
                        //aplicar a cor da célula da peça à célula correspondente no tabuleiro
                        colors[newY][newX] = color;
                    }
                }
            }
        }
    }

    //eliminar linhas completas e mover todas as linhas superiores a uma certa linha completa para baixo
    private void findAndDeleteLine ( int line){
        if (line >= 0 && line < board.length){
            for (int i = 0; i < board[0].length; i++){
                board[line][i] = ' ';
                colors[line][i] = TextColor.ANSI.BLACK;
            }
        }

        for (int j = line; j > 0; j--) {
            System.arraycopy(board[j - 1], 0, board[j], 0, board[j].length);
            System.arraycopy(colors[j - 1], 0, colors[j], 0, colors[j].length);
        }
    }

        public void deleteFullLine(){
            for (int row = 0; row <height; row++){
                boolean isFullLine = true;

                for (int column = 0; column < width; column++){
                    if (board[row][column] == ' '){
                        isFullLine = false;
                        break;
                    }
                }

                if (isFullLine){
                    findAndDeleteLine(row);
                    score.addPoints(100);
                }
            }
        }

}