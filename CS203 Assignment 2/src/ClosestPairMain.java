import java.util.Random;

public class ClosestPairMain {
    private final static int MAX_COORDINATE = 100;

    public static void main(String [] args) {
        int n = 0;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
            runSingleTest(n);
        } else {
            runTests();
        }
    }

    private static void runSingleTest(int n) {
        System.out.println("Generating " + n + " random points...");
        Point[] points = generatePoints(n);

        System.out.println("Running Divide and Conquer algorithm...");
        ClosestPairDivideAndConquer dAndC = new ClosestPairDivideAndConquer(points);
        StringBuilder dAndCResult = new StringBuilder();
        dAndCResult.append("Completed in ")
                .append(dAndC.runTime)
                .append("ns. Closest pair is points (")
                .append(dAndC.closest.first.x)
                .append(", ")
                .append(dAndC.closest.first.y)
                .append(") and (")
                .append(dAndC.closest.second.x)
                .append(", ")
                .append(dAndC.closest.second.y)
                .append(") with a distance of ")
                .append(dAndC.closest.distance);

        System.out.println(dAndCResult);

        System.out.println();
        System.out.println("Running Exhaustive algorithm...");
        ClosestPairExhaustiveSearch search = new ClosestPairExhaustiveSearch(points);
        StringBuilder searchResult = new StringBuilder();
        searchResult.append("Completed in ")
                .append(search.runTime)
                .append("ns. Closest pair is points (")
                .append(search.closest.first.x)
                .append(", ")
                .append(search.closest.first.y)
                .append(") and (")
                .append(search.closest.second.x)
                .append(", ")
                .append(search.closest.second.y)
                .append(") with a distance of ")
                .append(search.closest.distance);

        System.out.println(searchResult);
    }

    private static void runTests() {
        int n = 2;
        int k = 50;
        for (int i = 0; i <= k; i++) {
            n *= 2;
            runSingleTest(n);
            System.out.println("------------------------------------------------------------------");
        }
    }

    private static Point[] generatePoints(int n) {
        Random rand = new Random();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(rand.nextInt(MAX_COORDINATE), rand.nextInt(MAX_COORDINATE));
        }
        return points;
    }
}
