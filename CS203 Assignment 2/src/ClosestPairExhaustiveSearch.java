import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairExhaustiveSearch {
    long runTime;
    PointPair closest = null;

    public ClosestPairExhaustiveSearch(Point[] points) {
        long start = System.nanoTime();
        Arrays.sort(points, Comparator.comparingInt(a -> a.x));
        closest = findClosestPair(points);
        runTime = System.nanoTime() - start;
    }

    private PointPair findClosestPair(Point[] p) {
        PointPair d = null;
        // Exhaustive search solution
        for (int i = 0; i < (p.length - 1); i++) {
            int k = i + 1;
            while (k <= (p.length - 1)) {
                PointPair testPair = new PointPair(p[k], p[i]);
                if (d == null) {
                    d = testPair;
                } else {
                    d = d.minDistance(testPair);
                }
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
