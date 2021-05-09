public class NQueensExhaustiveSearch {
    int n;
    long runTime;
    boolean[] columnRef; // Reference to quickly check if the current column has a queen in any row
    boolean[] positiveRef; // Reference to quickly check if there is a queen in the current positive diagonal
    boolean[] negativeRef; // Reference to quickly check if there is a queen in the current negative diagonal
    int[] queens; // The locations of queens where the index is the row and the value is the column

    public NQueensExhaustiveSearch(int n) {
        // Initialize the board
        this.n = n;
        columnRef = new boolean[n];
        positiveRef = new boolean[2 * n - 1];
        negativeRef = new boolean[2 * n - 1];
        queens = new int[n];

        long start = System.nanoTime();
        solve(0, 0);
        runTime = System.nanoTime() - start;
    }

    public boolean canPlaceQueen(int row, int col) {
        // Check columns and diagonals
        return !columnRef[col] && !negativeRef[row - col + n - 1] && !positiveRef[row + col];
    }

    // A depth-first search for finding a solution
    public boolean solve(int row, int col) {
        if (col == n) return false; // If we reach column = n, there's no solution
        if (row == n) return true; // If we reach row = n, we've found a solution
        // Place a queen in the current column if possible
        if (canPlaceQueen(row, col)) {
            // Place queen at first available space
            queens[row] = col;
            columnRef[col] = true;
            negativeRef[row - col + n - 1] = true;
            positiveRef[row + col] = true;

            // Validate this placement
            if (solve(row + 1, 0)) {
                // Current queen placement reaches a queen solution
                return true;
            }

            // Current queen placement is not solvable, reset placements
            columnRef[col] = false;
            negativeRef[row - col + n - 1] = false;
            positiveRef[row + col] = false;
        }

        // Find the next unused column
        int nextCol = col + 1;
        for (int i = nextCol; i <  n; i++) {
            if (!columnRef[i]) {
                nextCol = i;
                break;
            }
        }

        return solve(row, nextCol);
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
                if (j == queens[i]) {
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
