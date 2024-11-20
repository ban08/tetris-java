import java.util.ArrayList;
import java.util.List;

public class PieceSelector {
    private final List<Piece> pieces;

    public PieceSelector(){
        pieces = new ArrayList<>();
        for (int i = 0; i<5; i++){
            pieces.add(Piece.randomShape());
        }
    }

    public Piece selectPiece(int index) {
        if (index >= 0 && index < pieces.size()) {
            Piece selected = pieces.get(index);
            pieces.set(index, Piece.randomShape()); // Substituir peça escolhida
            return selected;
        }
        return null;
    }
}
