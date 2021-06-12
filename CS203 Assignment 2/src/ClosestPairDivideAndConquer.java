import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairDivideAndConquer {
    long runTime;
    PointPair closest = null;

    public ClosestPairDivideAndConquer(Point[] points) {
        long start = System.nanoTime();
        quicksortByX(points, 0, (points.length - 1));
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
        mergesortByY(qLeft);
        Point[] qRight = pRight.clone();
        mergesortByY(qRight);

        return new Pair<>(new Pair<>(pLeft, pRight), new Pair<>(qLeft, qRight));
    }

    public static void quicksortByX(Point[] A, int left, int right) {
        // Check if any sorting is really necessary.
        if (left < right) {
            // Array partitioning (any partitioning algorithm can be used here).
            int pivot = hoarePartitioning(A, left, right);
            // Recursive application of Quicksort to left-part and right-part.
            quicksortByX(A, left, pivot - 1);
            quicksortByX(A, pivot + 1, right);
        }
    }

    // Taken from the decrease and conquer lecture slides
    public static int hoarePartitioning(Point[] A, int left, int right) {
        int p = A[left].x;
        // Pivot selected as the first element in the array list.
        // Init internal indices for double-scans.
        int i = left;
        // First item to be considered is at i+1 (see do-while).
        int j = right + 1;
        // First item to be considered is at j-1 (see do-while).
        // Loop to perform multiple double-scans.
        do {
            // Left-to-right scan.
            while ((i <= (A.length - 1)) && (A[i].x < p)) {
                i++;
            }
            // Right-to-left scan.
            do {
                j--;
            } while ((j >= 0) && (A[j].x > p));
            // Swap A[i] with A[j].
            Point temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        } while (i < j);
        // Stop when double-scans indices met.
        // Undo the last swap because i was >= than j (re-swap A[i] with A[j]).
        Point temp = A[i];
        A[i] = A[j];
        A[j] = temp;
        // Put pivot in correct position (swap pivot at A[left] with A[j]).
        temp = A[left];
        A[left] = A[j];
        A[j] = temp;
        // Return index of pivot in final partitioning.
        return j;
    }

    public static void mergeSortedArrays(Point[] B, Point[] C, Point[] A) {
        int i = 0, j = 0, k = 0;
        // Init temp variables.
        // Scanning sorted arrays B and C, while inserting in A.
        while ((i < B.length) && (j < C.length)) {
            if (B[i].y <= C[j].y) {
                A[k] = B[i];
                i++;
            } else {
                A[k] = C[j];
                j++;
            }
            k++;
        }
        // One scan has terminated, transfer remaining sorted data in A.
        if (i == B.length) {
            System.arraycopy(C, j, A, k, C.length - j);
        } else {
            System.arraycopy(B, i, A, k, B.length - i);
        }
    }

    public static void mergesortByY(Point[] A) {
        if (A.length > 1) {
            // Check if sorting is really necessary.
            int h = (int) Math.floor(A.length / 2);
            // Determine halves size.
            // Init half 1 and 2.

            Point[] B = new Point[h];
            System.arraycopy(A, 0, B, 0, h);
            Point[] C = new Point[A.length - h];
            System.arraycopy(A, h, C, 0, A.length - h);
            // Sort (recursively) halves 1 and 2.
            mergesortByY(B);
            mergesortByY(C);
            // Merge sorted halves (arrays B and C) into final sorted array (A).
            mergeSortedArrays(B, C, A);
        }
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
