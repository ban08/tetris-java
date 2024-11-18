public class Piece {
    private final int[][] shape; //forma da peça
    private int x;
    private int y;
    private final int color;


    public Piece(int[][] shape, int color) {
        this.shape = shape;
        this.x = 0; // Inicializa na posição padrão
        this.y = 0;
        this.color = color;

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

    public int getColor() {
        return color;
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

    public static Piece createPiece(int[][] shape, int color){
        return new Piece(shape, color);
    }
    public static Piece pieceT(int color){
        int[][] tShape = {
                {0, 1, 0},
                {1, 1, 1}
        };
        return createPiece(tShape, color);
    }

    public static Piece pieceL(int color){
        int[][] lShape = {
                {1, 0},
                {1, 0},
                {1, 1}
        };
        return createPiece(lShape, color);
    }

    public static Piece pieceSquare(int color){
        int[][] squareShape = {
                {1, 1},
                {1, 1}
        };
        return createPiece(squareShape, color);
    }

    public static Piece pieceZ(int color){
        int[][] zShape = {
                {1, 1, 0},
                {0, 1, 1}
        };
        return createPiece(zShape, color);
    }

    public static Piece pieceS(int color){
        int[][] sShape = {
                {0, 1, 1},
                {1, 1, 0}
        };
        return createPiece(sShape, color);
    }

    public static Piece pieceI(int color){
        int[][] iShape = {
                {1, 1, 1, 1},
        };
        return createPiece(iShape, color);
    }

}
