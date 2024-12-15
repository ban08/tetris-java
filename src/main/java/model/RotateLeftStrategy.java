package model;

public class RotateLeftStrategy implements RotationStrategy {
    @Override
    public char[][] rotate(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[cols - 1 - j][i] = matrix[i][j];
            }
        }
        return rotated;
    }
}
