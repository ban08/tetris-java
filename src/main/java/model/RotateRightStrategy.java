package model;

/**
 * Implementation of {@link RotationStrategy} that rotates a matrix to the right.
 */
public class RotateRightStrategy implements RotationStrategy {
    /**
     * Rotates the given matrix to the right.
     *
     * @param matrix the matrix to be rotated.
     * @return the rotated matrix.
     * @throws IllegalArgumentException if the given matrix is null or empty.
     */
    @Override
    public char[][] rotate(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] rotated = new char[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = matrix[i][j]; // Rotates clockwise (right).
            }
        }
        return rotated;
    }

}