import java.util.ArrayList;

public class NQueensMain {
    public static void main(String [] args) {
        if (args.length != 0) {
            runSingleSolve(args);
        } else {
            runTests();
        }
    }

    public static void runSingleSolve(String[] args) {
        // Parse n from cli args
        int n = Integer.parseInt(args[0]);
        if (n < 4)
            System.out.println("No possible solution for n=" + n);

        // Run solvers
        System.out.print(new NQueensIterativeRepair(n));
        System.out.print(new NQueensExhaustiveSearch(n));
    }

    public static void runTests() {
        int[] testCases = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        ArrayList<NQueensIterativeRepair> repairTests = new ArrayList<>();
        ArrayList<NQueensExhaustiveSearch> searchTests = new ArrayList<>();

        // Run each test
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("NQueens Solutions for n=" + testCases[i]);

            repairTests.add(new NQueensIterativeRepair(testCases[i]));
            System.out.print(repairTests.get(i));

            searchTests.add(new NQueensExhaustiveSearch(testCases[i]));
            System.out.print(searchTests.get(i));
            System.out.println();
        }

        // Print out formatted results
        System.out.println("Time results for each test:");
        for (int i = 0; i < testCases.length; i++) {
            StringBuilder result = new StringBuilder();
            result.append("n=")
                    .append(testCases[i])
                    .append(" | Search: ")
                    .append(searchTests.get(i).runTime)
                    .append("ns | Repair: ")
                    .append(repairTests.get(i).runTime)
                    .append("ns  |\n");

            result.append("n=")
                    .append(testCases[i])
                    .append(" | Search: ")
                    .append(searchTests.get(i).basicOpCount)
                    .append("ops | Repair: ")
                    .append(repairTests.get(i).basicOpCount)
                    .append("ops  |");
            System.out.println(result);
        }
    }
}
