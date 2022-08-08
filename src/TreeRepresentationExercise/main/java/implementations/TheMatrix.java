package TreeRepresentationExercise.main.java.implementations;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];

    }

    public void solve() {
        fillMatrix(this.startRow, this.startCol);
    }

    private void fillMatrix(int startRow, int startCol) {

        if (isInBounds(startRow, startCol) || this.matrix[startRow][startCol] != this.toBeReplaced) {
            return;
        }
        this.matrix[startRow][startCol] = this.fillChar;

        this.fillMatrix(startRow + 1, startCol);
        this.fillMatrix(startRow, startCol + 1);
        this.fillMatrix(startRow - 1, startCol);
        this.fillMatrix(startRow, startCol - 1);
    }

    private boolean isInBounds(int r, int c) {
        return r < 0 || r >= this.matrix.length || c < 0 || c >= this.matrix[r].length;
    }

    public String toOutputString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                sb.append(this.matrix[i][j]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
