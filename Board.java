public class Board {

    private final int[][] board; //tabuleiro onde se armazena as peças
    private final int[][] colorBoard;
    private final int columns=10;
    private final int rows=20;

    public Board(){
        board = new int[rows][columns];
        colorBoard = new int[rows][columns];
    }

    //verificar se a peça pode ser adicionada numa certa posiçao (x, y)
    public boolean canMove(Piece piece, int x, int y){
        for (int i=0; i<piece.getHeight(); i++){ //iterar sobre as linhas
            for (int j = 0; j < piece.getWith(); j++){ //iterar sobre as colunas
                //verificar se ha colisao com a borda ou com outra peça
                if (piece.getShape()[i][j]!=0 && ((x + i)>=rows || (y+j) < 0
                        || (y + j) >= columns
                        || board[x + i][y + j] != 0)){
                    return false;
                }
            }
        }
        return true;
    }
    //adicionar piece ao board na posição (x, y)
    public void addPiece(Piece piece, int x, int y) {
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWith(); j++) {
                if (piece.getShape()[i][j] != 0) {
                    board[x + i][y + j] = piece.getShape()[i][j]; //preenche a posição da peça
                    colorBoard[x + i][y + j] = piece.getColor();  // Preenche a cor da peça
                }
            }
        }
    }

    //remover linhas completas do board
    public void deleteFullLines () {
        for (int i = 0; i < rows; i++) {
            boolean fullLine = true;
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == 0) {
                    fullLine = false;  // A linha não está completamente preenchida
                    break;
                }
            }
            if (fullLine) {
                deleteLine(i); //remove a linha totalmente preenchida
            }
        }
    }


    private void deleteLine ( int line){
        // remove uma linha e move todas as linhas acima dessa para baixo
        for (int i = line; i > 0; i--) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = board[i - 1][j];
                colorBoard[i][j] = colorBoard[i - 1][j];
            }
        }
        // a primeira linha fica vazia
        for (int j = 0; j < columns; j++) {
            board[0][j] = 0;
            colorBoard[0][j] = 0;//a primeira linha fica com "cor vazia"
        }
    }

    //cálculo da pontuação conforme o número de linhas completamente preenchidas encontradas
    public int getScored(){
        int score = 0;
        for (int i = 0; i < rows; i++){
            boolean fullLine = true;
            for (int j = 0; j < columns; j++){
                if (board[i][j] == 0){
                    fullLine = false;
                    break;
                }
            }
            if (fullLine){
                score += 100; //cada linha completa dá 100 score
            }
        }
        return score;
    }

    //estado do board
    public int[][] getBoard(){
        return board;
    }

    //cores das peças no tabuleiro
    public int[][] getColorBoard() {
        return colorBoard;
    }


}