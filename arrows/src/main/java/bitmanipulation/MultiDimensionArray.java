package bitmanipulation;

import java.util.Arrays;

// Given a multi-dimensional arrays, compute the sum of all values.
// Given API getValueByIndex(dn, dn-1.... d0) dn = index at dimension.
// other version
//    You have a class named NDimensionArray, and a static method which take index of its element
//    and return value in this index. Now you have a reference of such object, and 1D int array
//    where each entry represents each dimension's length.
//    Please write a function to return sum of all elements in this NDimensionArray
//        - Static method: public static Integer getValueByIndex(int ... index) (can use)
//        - Input you have: NDimensiionArray input, int[] sizeOfEachDemin

class MultiDimensionArray {
    // provided function
    public static int getValueByIndex(int... indexOfDimension) {
        int value = 1; // e.g.
        System.out.println(Arrays.toString(indexOfDimension));
        return value;
    }

    // lengthOfDeminsion: each dimension's length, assume it is valid: lengthOfDeminsion[i]>0.
    public static Integer sum(MultiDimensionArray mArray, int[] sizeOfEachDemin) {
        if (sizeOfEachDemin == null || sizeOfEachDemin.length == 0) {
            return null;
        }
        // O(N)solution. only iterator, no recursion, no extra space
        final int dims = sizeOfEachDemin.length;
        int[] cur = new int[dims] ;

        int sum = 0;
        int rightIdx;
        out:
        while (true) {
            sum += mArray.getValueByIndex(cur);

            rightIdx = dims - 1;
            cur[rightIdx]++;

            while (cur[rightIdx] > sizeOfEachDemin[rightIdx]-1) {
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
