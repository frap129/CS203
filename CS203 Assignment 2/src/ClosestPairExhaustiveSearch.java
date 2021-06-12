import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairExhaustiveSearch {
    long runTime;
    long basicOpCount = 0;
    PointPair closest = null;

    public ClosestPairExhaustiveSearch(Point[] points) {
        long start = System.nanoTime();
        Arrays.sort(points, Comparator.comparingInt(a -> a.x));
        closest = findClosestPair(points);
        runTime = System.nanoTime() - start;
    }

    /*
     * Exhaustive search solution by brute force.
     * For each element, we iterate over all other elements and calculate the distance between
     * them. A pair of points "d" with the shortest distance is recorded and updated whenever a
     * new pair with a shorter distance is found.
     */
    private PointPair findClosestPair(Point[] p) {
        PointPair d = null;
        for (int i = 0; i < (p.length - 1); i++) {
            int k = i + 1;
            while (k <= (p.length - 1)) {
                PointPair testPair = new PointPair(p[k], p[i]);
                if (d == null) {
                    d = testPair;
                } else {
                    d = d.minDistance(testPair);
                }
                basicOpCount++;
                k++;
            }
        }
        return d;
    }

    public static class PointPair {
        Point first;
        Point second;
        double distance;

        public PointPair(Point first, Point second) {
            this.first = first;
            this.second = second;
            euclideanDistance();
        }

        public PointPair minDistance(PointPair pair) {
            if (this.distance < pair.distance) {
                return this;
            } else {
                return pair;
            }
        }

        private void euclideanDistance() {
            int diffX = second.x - first.x;
            int diffY = second.y - first.y;
            distance = Math.sqrt((diffX * diffX) + (diffY * diffY));
        }
    }
}
