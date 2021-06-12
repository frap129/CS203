import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairDivideAndConquer {
    long runTime;
    PointPair closest = null;

    public ClosestPairDivideAndConquer(Point[] points) {
        long start = System.nanoTime();
        Arrays.sort(points, Comparator.comparingInt(a -> a.x));
        closest = findClosestPair(points);
        runTime = System.nanoTime() - start;
    }

    private PointPair findClosestPair(Point[] p) {
        PointPair d = null;
        if (p.length <= 3) {
            // Exhaustive search solution
            for (int i = 0; i < (p.length - 1); i++) {
                int k = i + 1;
                while (k <= (p.length - 1)) {
                    PointPair testPair = new PointPair(p[k], p[i]);
                    if (d == null) {
                        // First pair is the "Closest", until we find one that's closer
                        d = testPair;
                    } else {
                        // Compare the current closest with the current test pair
                        d = d.minDistance(testPair);
                    }
                    k++;
                }
            }
        } else {
            // Split points into pLeft, qLeft, pRight, qRight
            Pair<Pair<Point[]>> arrays = splitArrays(p);
            Point[] pLeft = arrays.first.first;
            Point[] qLeft = arrays.second.first;
            Point[] pRight = arrays.first.second;
            Point[] qRight = arrays.second.second;

            // Calculate d,  and m
            PointPair resultLeft = findClosestPair(pLeft);
            PointPair resultRight = findClosestPair(pRight);
            d = resultLeft.minDistance(resultRight);
            int m = p[pLeft.length - 1].x;

            // Create array S
            ArrayList<Point> sList = new ArrayList<>();
            for (int i = 0; i < p.length; i++) {
                if (i < qLeft.length) {
                    if ((qLeft[i].x - m) < d.distance) {
                        sList.add(qLeft[i]);
                    }
                } else {
                    if ((qRight[i - qLeft.length].x - m) < d.distance) {
                        sList.add(qRight[i - qLeft.length]);
                    }
                }
            }
            Point[] s = new  Point[sList.size()];
            sList.toArray(s);

            for (int i = 0; i < (s.length - 1); i++) {
                int k = i + 1;
                while (k <= (s.length - 1) && ((s[k].y - s[i].y)^2) < d.distanceSquared) {
                    // Compare the current closest with the current test pair
                    PointPair testPair = new PointPair(s[k], s[i]);
                    d = d.minDistance(testPair);
                    k++;
                }
            }
        }
        return d;
    }

    private Pair<Pair<Point[]>> splitArrays(Point[] p) {
        int newLength = p.length / 2;
        Point[] pLeft = new Point[newLength];
        Point[] pRight = new Point[p.length - newLength];
        for (int i = 0; i < p.length; i++) {
            if (i < newLength) {
                pLeft[i] = p[i];
            } else {
                pRight[i - newLength] = p[i];
            }
        }

        Point[] qLeft = pLeft.clone();
        Arrays.sort(qLeft, Comparator.comparingInt(a -> a.y));
        Point[] qRight = pRight.clone();
        Arrays.sort(qRight, Comparator.comparingInt(a -> a.y));

        return new Pair<>(new Pair<>(pLeft, pRight), new Pair<>(qLeft, qRight));
    }

    public static class Pair<T> {
        T first;
        T second;

        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }
    }

    public static class PointPair {
        Point first;
        Point second;
        double distance;
        double distanceSquared;

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
            distance = Math.sqrt((diffX*diffX) + (diffY*diffY));
            distanceSquared = distance * distance;
        }
    }
}
