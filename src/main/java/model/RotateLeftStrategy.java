package model;
/**
 * A strategy for rotating a matrix to the left by 90 degrees.
 *
 * <p>
 * This class implements the {@link RotationStrategy} interface and provides
 * a concrete implementation of the {@link RotationStrategy#rotate(char[][])}
 * method. The method takes a matrix as an argument and returns a new matrix
 * with the same elements, but rotated 90 degrees to the left.
 *
 * <p>
 * The rotation is performed in-place, meaning that the original matrix is not
 * modified. The returned matrix is a new instance, created by this class.
 *
 *
 */
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