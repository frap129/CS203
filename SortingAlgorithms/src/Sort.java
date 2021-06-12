import java.util.Arrays;
import java.util.Random;

public class Sort {

    public static void main(String[] args) {
        int[] array = generateArray(50);
        int[] array2 = new int[50];
        System.arraycopy(array, 0, array2, 0, 50);
        System.out.println("Unsorted array:");
        System.out.println(Arrays.toString(array));

        // Test quicksort
        long start = System.nanoTime();
        quicksort(array, 0, array.length - 1);
        long runTime = System.nanoTime() - start;
        System.out.println("Sorted with Quicksort in " + runTime + " ns:");
        System.out.println(Arrays.toString(array));

        // Test mergesort
        start = System.nanoTime();
        mergesort(array2);
        runTime = System.nanoTime() - start;
        System.out.println("Sorted with Mergesort in " + runTime + " ns:");
        System.out.println(Arrays.toString(array2));
    }

    private static int[] generateArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(100);
        }
        return arr;
    }

    public static void quicksort(int[] A, int left, int right) {
        // Check if any sorting is really necessary.
        if (left < right) {
            // Array partitioning (any partitioning algorithm can be used here).
            int pivot = hoarePartitioning(A, left, right);
            // Recursive application of Quicksort to left-part and right-part.
            quicksort(A, left, pivot - 1);
            quicksort(A, pivot + 1, right);
        }
    }

    // Taken from the decrease and conquer lecture slides
    public static int hoarePartitioning(int[] A, int left, int right) {
        int p = A[left];
        // Pivot selected as the first element in the array list.
        // Init internal indices for double-scans.
        int i = left;
        // First item to be considered is at i+1 (see do-while).
        int j = right + 1;
        // First item to be considered is at j-1 (see do-while).
        // Loop to perform multiple double-scans.
        do {
            // Left-to-right scan.
            do {
                i++;
            } while ((i <= A.length - 1) && (A[i] < p));
            // Right-to-left scan.
            do {
                j--;
            } while ((j >= 0) && (A[j] > p));
            // Swap A[i] with A[j].
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        } while (i < j);
        // Stop when double-scans indices met.
        // Undo the last swap because i was >= than j (re-swap A[i] with A[j]).
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
        // Put pivot in correct position (swap pivot at A[left] with A[j]).
        temp = A[left];
        A[left] = A[j];
        A[j] = temp;
        // Return index of pivot in final partitioning.
        return j;
    }

    public static void mergeSortedArrays(int[] B, int[] C, int[] A) {
        int i = 0, j = 0, k = 0;
        // Init temp variables.
        // Scanning sorted arrays B and C, while inserting in A.
        while ((i < B.length) && (j < C.length)) {
            if (B[i] <= C[j]) {
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

    public static void mergesort(int[] A) {
        if (A.length > 1) {
            // Check if sorting is really necessary.
            int h = (int) Math.floor(A.length / 2);
            // Determine halves size.
            // Init half 1 and 2.
            int B[] = new int[h];
            System.arraycopy(A, 0, B, 0, h);
            int C[] = new int[A.length - h];
            System.arraycopy(A, h, C, 0, A.length - h);
            // Sort (recursively) halves 1 and 2.
            mergesort(B);
            mergesort(C);
            // Merge sorted halves (arrays B and C) into final sorted array (A).
            mergeSortedArrays(B, C, A);
        }
    }
}
