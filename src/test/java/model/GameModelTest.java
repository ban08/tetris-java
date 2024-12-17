package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the GameModel class.
 */
public class GameModelTest {

    private GameModel gameModel;
    private Board mockBoard;
    private Score mockScore;
    private PieceFactory mockPieceFactory;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        mockBoard = mock(Board.class);
        mockScore = mock(Score.class);
        mockPieceFactory = mock(PieceFactory.class);

        // Initialize mock Pieces
        Piece mockPiece1 = mock(Piece.class);
        Piece mockPiece2 = mock(Piece.class);
        when(mockPieceFactory.createPiece()).thenReturn(mockPiece1, mockPiece2);

        // Stub board methods used in initGameState()
        char[][] mockBoardArray = new char[10][20]; // Adjust dimensions as per your Board
        TextColor[][] mockColorsArray = new TextColor[10][20];
        when(mockBoard.getBoard()).thenReturn(mockBoardArray);
        when(mockBoard.getColors()).thenReturn(mockColorsArray);

        // Stub additional board methods
        when(mockBoard.canPlaceShape(any(), anyInt(), anyInt())).thenReturn(true);
        doNothing().when(mockBoard).addPositionedPiece(any(PositionedPiece.class));
        doNothing().when(mockBoard).deleteFullLines();
        doNothing().when(mockBoard).resetBoard();

        // Initialize GameModel with mocks
        gameModel = new GameModel(mockBoard, mockScore, mockPieceFactory);
    }

    @Test
    public void testLockCurrentPieceAddsPieceToBoardAndClearsLines() {
        // Arrange
        Piece mockPiece = mock(Piece.class);
        PositionedPiece currentPiece = new PositionedPiece(mockPiece, 0, 0);
        gameModel.setCurrentPiece(currentPiece);

        // Mock behavior
        when(mockBoard.canPlaceShape(any(), anyInt(), anyInt())).thenReturn(true);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        verify(mockBoard, times(1)).addPositionedPiece(currentPiece);
        verify(mockBoard, times(1)).deleteFullLines();
    }

    @Test
    public void testFallSpeedDecreasesAfterScoreThreshold() {
        // Arrange
        gameModel.setFallSpeed(500);
        when(mockScore.getPoints()).thenReturn(1000);
        gameModel.setLastScore(900);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertEquals(450, gameModel.getFallSpeed(), "Fall speed should decrease by 50 after reaching 1000 points");
    }

    @Test
    public void testFallSpeedDoesNotDecreaseBelowMinimum() {
        // Arrange
        gameModel.setFallSpeed(100);
        when(mockScore.getPoints()).thenReturn(2000);
        gameModel.setLastScore(1900);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertEquals(100, gameModel.getFallSpeed(), "Fall speed should not decrease below 100");
    }

    @Test
    public void testBonusChargeUpdatesCorrectlyBasedOnScoreIncrements() {
        // Arrange
        gameModel.setBonusCharge(200);
        when(mockScore.getPoints()).thenReturn(300); // Gained 100 points since lastScore
        gameModel.setLastScore(200);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertEquals(250, gameModel.getBonusCharge(), "Bonus charge should increase by half the gained score");
    }

    @Test
    public void testBonusChargeCappedAtMaximum() {
        // Arrange
        gameModel.setBonusCharge(450);
        when(mockScore.getPoints()).thenReturn(600); // Gained 150 points since lastScore
        gameModel.setLastScore(450);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertEquals(500, gameModel.getBonusCharge(), "Bonus charge should not exceed 500");
    }

    @Test
    public void testLockCurrentPieceStopsGameWhenNoSpaceForNewPiece() {
        // Arrange
        Piece mockPiece = mock(Piece.class);
        PositionedPiece newPiece = new PositionedPiece(mockPiece, 0, 0);
        gameModel.setCurrentPiece(newPiece);

        // Mock behavior to simulate no space for new piece
        when(mockBoard.canPlaceShape(any(), anyInt(), anyInt())).thenReturn(false);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertFalse(gameModel.isRunning(), "Game should stop when new piece cannot be placed");
    }

    @Test
    public void testLockCurrentPieceDoesNotStopGameWhenSpaceIsAvailable() {
        // Arrange
        Piece mockPiece = mock(Piece.class);
        PositionedPiece newPiece = new PositionedPiece(mockPiece, 0, 0);
        gameModel.setCurrentPiece(newPiece);

        // Mock behavior to simulate space available for new piece
        when(mockBoard.canPlaceShape(any(), anyInt(), anyInt())).thenReturn(true);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertTrue(gameModel.isRunning(), "Game should continue when new piece can be placed");
    }

    @Test
    public void testLockCurrentPieceDoesNotDecreaseFallSpeedWhenThresholdNotMet() {
        // Arrange
        gameModel.setFallSpeed(500);
        when(mockScore.getPoints()).thenReturn(950); // Not a multiple of 1000
        gameModel.setLastScore(900);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertEquals(500, gameModel.getFallSpeed(), "Fall speed should not decrease when score threshold not met");
    }

    @Test
    public void testLockCurrentPieceHandlesNegativeScoreIncrement() {
        // Arrange
        gameModel.setBonusCharge(100);
        when(mockScore.getPoints()).thenReturn(800); // Current score less than lastScore
        gameModel.setLastScore(900);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertEquals(100, gameModel.getBonusCharge(), "Bonus charge should not decrease when gained score is negative");
        assertEquals(800, gameModel.getLastScore(), "Last score should be updated to current score even if negative increment");
    }

    @Test
    public void testLockCurrentPieceSpawnsNewPiece() {
        // Arrange
        Piece mockCurrentPiece = mock(Piece.class);
        PositionedPiece currentPiece = new PositionedPiece(mockCurrentPiece, 5, 0);
        gameModel.setCurrentPiece(currentPiece);

        Piece mockNextPiece = mock(Piece.class);
        PositionedPiece nextPiece = new PositionedPiece(mockNextPiece, 0, 0);
        gameModel.setNextPiece(nextPiece);

        // Mock behavior
        when(mockBoard.canPlaceShape(any(), anyInt(), anyInt())).thenReturn(true);

        // Act
        gameModel.lockCurrentPiece();

        // Assert
        assertEquals(mockNextPiece, gameModel.getCurrentPiece().getPiece(), "Current piece should be set to next piece after locking");
        verify(mockBoard, times(1)).addPositionedPiece(currentPiece);
        verify(mockBoard, times(1)).deleteFullLines();
    }

    // 1.1.2. Test Fall Speed Decrease and Bonus Charge
    // (Already covered by the above tests)

    // 1.2. resetGame Method

    @Test
    public void testResetGameResetsGameState() {
        // Arrange
        // Simulate some game state
        gameModel.setFallSpeed(300);
        gameModel.setBonusCharge(250);
        gameModel.setLastScore(200);

        // Mock score behavior
        when(mockScore.getPoints()).thenReturn(0);

        // Act
        gameModel.resetGame();

        // Assert
        verify(mockBoard, times(1)).resetBoard();
        assertEquals(0, gameModel.getScorePoints(), "Score should be reset to 0");
        assertEquals(0, gameModel.getBonusCharge(), "Bonus charge should be reset");
        assertTrue(gameModel.isRunning(), "Game should be running after reset");
        assertEquals(0, gameModel.getLastScore(), "Last score should be reset to 0");
    }

    // 1.3. activateBonus and executeBonus Methods
    // 1.3.1. Test Activating Bonuses and Clearing Selected Lines

    @Test
    public void testActivateBonusWithSufficientCharge() {
        // Arrange
        gameModel.setBonusCharge(500);

        // Act
        gameModel.activateBonus();

        // Assert
        assertTrue(gameModel.isBonusActive(), "Bonus should be active after activation");
    }

    @Test
    public void testExecuteBonusClearsSelectedLines() {
        // Arrange
        // Activate bonus first
        gameModel.setBonusCharge(500);
        gameModel.activateBonus();

        List<Integer> linesToClear = new ArrayList<>(List.of(5, 8, 9)); // Mutable list
        when(mockScore.getPoints()).thenReturn(300); // Example value

        // Act
        gameModel.executeBonus(linesToClear);

        // Assert
        // Verify that Score.onLineCleared is called with the correct number of lines
        verify(mockScore, times(1)).onLineCleared(linesToClear.size());

        // Verify bonus is inactive and charge reset
        assertFalse(gameModel.isBonusActive(), "Bonus should be inactive after execution");
        assertEquals(0, gameModel.getBonusCharge(), "Bonus charge should be reset after execution");
        assertEquals(gameModel.getScorePoints(), gameModel.getLastScore(), "Last score should be updated to current score after executing bonus");
    }

    // 1.3.2. Edge Case: Attempting to Activate a Bonus with Insufficient Charge

    @Test
    public void testActivateBonusWithInsufficientCharge() {
        // Arrange
        gameModel.setBonusCharge(400);

        // Act
        gameModel.activateBonus();

        // Assert
        assertFalse(gameModel.isBonusActive(), "Bonus should not activate with insufficient charge");
    }
}
