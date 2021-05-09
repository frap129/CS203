import java.util.Arrays;

public class NQueensExhaustiveSearch {
    int n;
    long runTime;
    boolean[] columnRef; // Reference to quickly check if the current column has a queen in any row
    boolean[] positiveRef; // Reference to quickly check if there is a queen in the current positive diagonal
    boolean[] negativeRef; // Reference to quickly check if there is a queen in the current negative diagonal
    boolean[][] boardRows; // The board; 2D array where the first dimension is row

    public NQueensExhaustiveSearch(int n) {
        // Initialize the board
        this.n = n;
        columnRef = new boolean[n];
        positiveRef = new boolean[2 * n - 1];
        negativeRef = new boolean[2 * n - 1];
        boardRows = new boolean[n][n];

        long start = System.nanoTime();
        solve(0);
        runTime = System.nanoTime() - start;
    }

    public boolean canPlaceQueen(int row, int column) {
        // Check columns and diagonals
        return !columnRef[column] && !negativeRef[row - column + n - 1] && !positiveRef[row + column];
    }

    // A depth-first search for finding a solution
    public boolean solve(int row) {
        if (row == n) return true; // If we reach row = n, we've found a solution
        // Iterate each column in the current row
        for (int i = 0; i < n; i++) {
            if (canPlaceQueen(row, i)) {
                // Place queen at first available space
                boardRows[row][i] = true;
                columnRef[i] = true;
                negativeRef[row - i + n - 1] = true;
                positiveRef[row + i] = true;

                // Validate this placement
                if (solve(row + 1)) {
                    // Current queen placement reaches a queen solution
                    return true;
                } else {
                    // Current queen placement is not solvable, go to next column
                    boardRows[row][i] = false;
                    columnRef[i] = false;
                    negativeRef[row - i + n - 1] = false;
                    positiveRef[row + i] = false;
                }
            }
        }

        // No solution is possible with the placements in the previous rows
        return false;
    }

    @Override
    public String toString() {
        StringBuilder solution = new StringBuilder();
        solution.append("Exhaustive Search on n=")
                .append(n)
                .append(" run time: ")
                .append(runTime)
                .append("ns\n");

        solution.append("Found solution:\n");
        for (int i = 0; i < n; i++) {
            solution.append("----".repeat(n))
                    .append("-\n");
            for (int j = 0; j < n; j++) {
                if (boardRows[i][j]) {
                    solution.append("| Q ");
                } else {
                    solution.append("|   ");
                }
            }
            solution.append("|\n");
        }
        solution.append("----".repeat(n))
                .append("-\n");

        return solution.toString();
    }
}
