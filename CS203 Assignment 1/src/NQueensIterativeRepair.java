import java.util.Random;

public class NQueensIterativeRepair {
    int n;
    int[] queens;
    long runTime;

    // Count References
    int[] rowCount; // Number of queens in each row
    int[] columnCount; // Number of queens in each column
    int[] positiveDiagCount; // Number of queens in each positive diagonal
    int[] negativeDiagCount; // Number of queens in each negative diagonal

    // Swapped queens and counts
    // These are stored so that they don't have to be recreated if shouldSwap(i,j) returns true
    int[] swappedQueens;
    int[] swappedPositiveDiagCount;
    int[] swappedNegativeDiagCount;

    public NQueensIterativeRepair(int n) {
        this.n = n;

        // Find a solution and calculate run time
        long start = System.nanoTime();
        queenSearch();
        runTime = System.nanoTime() - start;
    }

    public void initBoard() {
        // Create queen location storage and fill with -1
        queens = new int[n];
        for (int i = 0; i < n; i++) queens[i] = -1;

        // Initialize count references
        rowCount = new int[n];
        columnCount = new int[n];
        positiveDiagCount = new int[2 * n - 1];
        negativeDiagCount = new int[2 * n - 1];
    }

    public boolean isAttacked(int row, int column) {
        // Check columns and diagonals
        return columnCount[column] != 0 ||
                negativeDiagCount[row - column + n - 1] != 0 ||
                positiveDiagCount[row + column] != 0;
    }

    public int numCollisions() {
        int collisions = 0;
        for(int i = 0; i < (2 * n - 1); i++) {
            // Check each count reference, increment collisions if count > 1
            if (i < n) {
                collisions += (rowCount[i] > 1) ? 1 : 0;
                collisions += (columnCount[i] > 1) ? 1 : 0;
            }
            collisions += (positiveDiagCount[i] > 1) ? 1 : 0;
            collisions += (negativeDiagCount[i] > 1) ? 1 : 0;
        }
        return collisions;
    }

    public void populateBoard() {
        initBoard();
        Random rand = new Random();

        // Iterate over each row
        for (int i = 0; i < n; i++) {
            // Randomly select a column that hasn't been chosen yet
            int col;
            do {
                col = rand.nextInt(n);
            } while (columnChosen(col, i));

            // Store column and update counts
            queens[i] = col;
            rowCount[i]++;
            columnCount[col]++;
            positiveDiagCount[i + col]++;
            negativeDiagCount[i - col + n - 1]++;
        }
    }
    public boolean columnChosen(int val, int length) {
        for (int i = 0; i <= length; i++) {
            if (queens[i] == val) return true;
        }
        return false;
    }

    public void queenSearch() {
        do {
            int swaps_performed;
            populateBoard();

            do {
                swaps_performed = 0;
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        if (isAttacked(i, queens[i]) || isAttacked(j, queens[j])) {
                            if(shouldSwap(i, j)) {
                                swapQueens();
                                swaps_performed++;
                            }
                        }
                    }
                }
            } while (swaps_performed != 0);
        } while (numCollisions() > 0);
    }

    public boolean shouldSwap(int index1, int index2) {
        // Get Collisions before swap
        int currCollisions = numCollisions();

        // Swap queens
        swappedQueens = queens.clone();
        int queenI = queens[index1];
        int queenJ = queens[index2];
        swappedQueens[index1] = queenJ;
        swappedQueens[index2] = queenI;

        // Update positive diagonal counts
        swappedPositiveDiagCount = positiveDiagCount.clone();
        swappedPositiveDiagCount[index1 + queenI]--;
        swappedPositiveDiagCount[index2 + queenI]++;
        swappedPositiveDiagCount[index2 + queenJ]--;
        swappedPositiveDiagCount[index1 + queenJ]++;

        // Update negative diagonal counts
        swappedNegativeDiagCount = negativeDiagCount.clone();
        swappedNegativeDiagCount[index1 - queenI + n - 1]--;
        swappedNegativeDiagCount[index2 - queenI + n - 1]++;
        swappedNegativeDiagCount[index2 - queenJ + n - 1]--;
        swappedNegativeDiagCount[index1 - queenJ + n - 1]++;


        // Count new collisions
        int collisions = 0;
        for(int i = 0; i < (2 * n - 1); i++) {
            // Check each count reference, increment collisions if count > 1
            if (i < n) {
                collisions += (rowCount[i] > 1) ? 1 : 0;
                collisions += (columnCount[i] > 1) ? 1 : 0;
            }
            collisions += (swappedPositiveDiagCount[i] > 1) ? 1 : 0;
            collisions += (swappedNegativeDiagCount[i] > 1) ? 1 : 0;
        }

        return collisions < currCollisions;
    }

    public void swapQueens() {
        queens = swappedQueens;
        positiveDiagCount = swappedPositiveDiagCount;
        negativeDiagCount = swappedNegativeDiagCount;
    }

    @Override
    public String toString() {
        StringBuilder solution = new StringBuilder();
        solution.append("Iterative Repair on n=")
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
