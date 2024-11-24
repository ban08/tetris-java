public interface RotationStrategy {
    /**
     * Rotates a given matrix in a clockwise or counter-clockwise direction.
     *
     * @param matrix the matrix to be rotated
     * @return the rotated matrix
     */
    char[][] rotate(char[][] matrix);
}