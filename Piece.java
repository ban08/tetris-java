public class Piece {
    private final int[][] shape; //forma da peça
    private int x;
    private int y;


    public Piece(int[][] shape) {
        this.shape = shape;
        this.x = 0; // Inicializa na posição padrão
        this.y = 0;

    }
    public int[][] getShape() {
        return shape;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int posX, int posY){
        this.x +=posX;
        this.y += posY;
    }

    public int getWith(){
        return shape[0].length; //numero de colunas ocupadas pela peça na linha
    }

    public int getHeight(){
        return shape.length; //numero de linhas ocupadas pela peça
    }

    public static Piece createPiece(int[][] shape){
        return new Piece(shape);
    }
    public static Piece pieceT(){
        int[][] tShape = {
                {0, 1, 0},
                {1, 1, 1}
        };
        return createPiece(tShape);
    }

    public static Piece pieceL(){
        int[][] lShape = {
                {1, 0},
                {1, 0},
                {1, 1}
        };
        return createPiece(lShape);
    }

    public static Piece pieceSquare(){
        int[][] squareShape = {
                {1, 1},
                {1, 1}
        };
        return createPiece(squareShape);
    }

    public static Piece pieceZ(){
        int[][] zShape = {
                {1, 1, 0},
                {0, 1, 1}
        };
        return createPiece(zShape);
    }

    public static Piece pieceS(){
        int[][] sShape = {
                {0, 1, 1},
                {1, 1, 0}
        };
        return createPiece(sShape);
    }

    public static Piece pieceI(){
        int[][] iShape = {
                {1, 1, 1, 1},
        };
        return createPiece(iShape);
    }

}
