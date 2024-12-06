package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomPieceFactoryTest {

    @Test
    void testCreatePiece() {
        RandomPieceFactory factory = new RandomPieceFactory();
        Piece p = factory.createPiece();
        assertNotNull(p.getShape());
        assertNotNull(p.getColor());
    }

    @Test
    void testMultipleCallsProduceDifferentInstances() {
        RandomPieceFactory factory = new RandomPieceFactory();
        Piece p1 = factory.createPiece();
        Piece p2 = factory.createPiece();
        assertNotSame(p1, p2);
    }
}
