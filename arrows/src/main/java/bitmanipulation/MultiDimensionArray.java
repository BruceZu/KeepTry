package bitmanipulation;

import java.util.Arrays;

// Given a multi-dimensional arrays, compute the sum of all values.
// Given API getValue(dn, dn-1.... d0) dn = index at dimension.
class MultiDimensionArray {
    // provided function
    public static int getValue(int... indexOfDimension) {
        int value = 1; // e.g.
        System.out.println(Arrays.toString(indexOfDimension));
        return value;
    }

    // lengthOfDeminsion: each dimension's length, assume it is valid: lengthOfDeminsion[i]>0.
    public static Integer sum(MultiDimensionArray mArray, int[] lengthOfDeminsion) {
        if (lengthOfDeminsion == null || lengthOfDeminsion.length == 0) {
            return null;
        }
        // O(N)solution. only iterator, no recursion, no extra space
        final int dims = lengthOfDeminsion.length;
        int[] cur = new int[dims] ;

        int sum = 0;
        int rightIdx;
        out:
        while (true) {
            sum += mArray.getValue(cur);

            rightIdx = dims - 1;
            cur[rightIdx]++;

            while (cur[rightIdx] > lengthOfDeminsion[rightIdx]-1) {
                cur[rightIdx] = 0;
                rightIdx--;
                if (rightIdx < 0) {
                    break out;
                }
                cur[rightIdx]++;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(sum(new MultiDimensionArray(), new int[] {3, 4, 2}));
    }
}
