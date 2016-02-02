//  Copyright 2016 The Minorminor Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package charter5;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Charter5 {
    // Tail recursion
    private static boolean my_binarySearch(int target, int[] array, int startIndex, int endIndex) {
        if (startIndex == 0 && endIndex == array.length - 1) {
            if (target > array[array.length - 1] || target < array[0]) {
                return false;
            }

            if (target == array[0]) {
                return true;
            }
            if (target == array[array.length - 1]) {
                return true;
            }

            if (array.length <= 2) {
                return false;
            }
        }
        int mid = (startIndex + endIndex) / 2;
        if (mid == startIndex) { //
            return false;
        }

        if (array[mid] == target) {
            return true;
        }

        if (array[mid] < target) {
            return my_binarySearch(target, array, mid, endIndex);
        }

        return my_binarySearch(target, array, startIndex, mid);
    }

    public static boolean my_binarySearch(int target, int[] array) {
        return my_binarySearch(target, array, 0, array.length - 1);
    }

    private static boolean my_whileSearch(int target, int[] array, int startIndex, int endIndex) {
        if (startIndex == 0 && endIndex == array.length - 1) {
            if (target > array[array.length - 1] || target < array[0]) {
                return false;
            }

            if (target == array[0]) {
                return true;
            }
            if (target == array[array.length - 1]) {
                return true;
            }

            if (array.length <= 2) {
                return false;
            }
        }

        int mid = (startIndex + endIndex) / 2;
        while (mid != startIndex) { //
            if (array[mid] == target) {
                return true;
            }
            if (array[mid] < target) {
                startIndex = mid;
            } else {
                endIndex = mid;
            }
            mid = (startIndex + endIndex) / 2;
        }
        return false;
    }

    public static boolean my_whileSearch(int target, int[] array) {
        return my_whileSearch(target, array, 0, array.length - 1);
    }

    // Linear Recursion
    public static int my_linearSum(int[] data, int n) {
        if (n == data.length) {
            n = n - 1;
        }
        if (n == 0) {
            return data[0];
        }
        return data[n] + my_linearSum(data, n - 1);
    }


    // Binary recursion case:
    // O(logn) amount of additional space,
    // Running time is still O(n)
    public static int my_binarySum(int[] data, int startIndex, int endIndex) {
        if (startIndex + 1 == endIndex) {
            return data[startIndex] + data[endIndex];
        }
        if (startIndex == endIndex) {
            return data[startIndex];
        }

        int mid = (startIndex + endIndex) / 2;
        return my_binarySum(data, startIndex, mid)
                + my_binarySum(data, mid + 1, endIndex);
    }

    // multiple recursion
    public static long my_diskUsage(File root) {
        if (root.isDirectory()) {
            long total = 0;
            for (String childname : root.list()) {
                File child = new File(root, childname);
                total += my_diskUsage(child);
                System.out.println(total + "\t" + child);
                return total;
            }
        }
        return root.length();
    }

    // list all k or <=k size permutation of n unique elements.
    // n can be bigger than 64.
    // O(K^N) running time.
    //  @param only  If true, only show permutations of permutationSize,
    //               else also show permutations which size is less than permutationSize.
    public static void my_permutationOf(List<Integer> uniqueList, int permutationSize, List<Integer> permutation, boolean only) {
        if (permutation == null) {
            assert 0 < permutationSize && permutationSize <= uniqueList.size();
            permutation = new ArrayList<>(permutationSize);
            if (!only) {
                System.out.println(Arrays.toString(permutation.toArray()));
            }
        }
        if (permutationSize <= 64) {
            // Todo using bitwise trick
        }
        for (int elment : uniqueList) {
            if (permutation.contains(elment)) {
                continue;
            }
            permutation.add(elment);
            if (!only) {
                System.out.println(Arrays.toString(permutation.toArray()));
            } else if (permutation.size() == permutationSize) {
                System.out.println(Arrays.toString(permutation.toArray()));
            }
            if (permutation.size() < permutationSize) {
                my_permutationOf(uniqueList, permutationSize, permutation, only);
            }
            permutation.remove(permutation.size() - 1);
        }
    }

    // List all k size subset of n unique elements.
    // n can be bigger than 64.
    // O(K^N) running time.
    private static void my_subSetOf(List<Integer> list, int subSetSize, Set<Integer> subSet, int indexFrom, int indexEnd) {
        if (subSet == null) {
            assert 0 < subSetSize && subSetSize <= list.size();
            subSet = new HashSet(subSetSize);
        }
        if (subSetSize <= 64) {
            // Todo using bitwise trick
        }
        for (int index = indexFrom; index <= indexEnd; index++) {
            subSet.add(list.get(index));
            if (subSet.size() == subSetSize) {
                System.out.println(Arrays.toString(subSet.toArray()));
            }
            if (subSet.size() < subSetSize) {
                my_subSetOf(list, subSetSize, subSet,
                        index + 1,
                        list.size() - (subSetSize - subSet.size()));
            }
            subSet.remove(list.get(index));
        }
    }

    public static void my_subSetOf(List<Integer> list,
                                   int subSetSize,
                                   Set<Integer> subSet) {
        my_subSetOf(list, subSetSize, subSet, 0, list.size() - subSetSize);
    }

    // element uniqueness problem
    // O(N^2) running time
    private static boolean my_isUnique(int[] a, int checked, int from) {
        if (a[checked] == a[from]) {
            return false;
        }
        if (from - 1 != checked) {
            return my_isUnique(a, checked, from - 1);
        }
        if (checked + 1 <= a.length - 2) {
            return my_isUnique(a, checked + 1, a.length - 1);
        }
        return true;
    }

    public static boolean isUnique(int[] a) {
        if (a.length == 1) {
            return true;
        }
        return my_isUnique(a, 0, a.length - 1);
    }

    // element uniqueness problem
    // O(NlogN) running time
    public static boolean isUnique2(int[] data) {
        int n = data.length;
        int[] clone = Arrays.copyOf(data, n);
        Arrays.sort(clone);
        for (int j = 0; j < n - 1; j++) {
            if (clone[j] == clone[j + 1]) {
                return false;
            }
        }
        return true;
    }

    // Requires an exponential number of calls to the method. runtime >2^(N/2),  Ω(2^N))
    public static long my_bad_fibonacci(int n) {
        assert n >= 0;
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return my_bad_fibonacci(n - 1) + my_bad_fibonacci(n - 2);
    }

    // O(N)
    private static long my_fibonacci(int lastLast, int last, int indexOfLast, int indexTarget) {
        if (indexOfLast < indexTarget) {
            return my_fibonacci(last, lastLast + last, indexOfLast + 1, indexTarget);
        }
        return last;
    }

    public static long my_fibonacci(int indexTarget) {
        return my_fibonacci(0, 1, 1, indexTarget);
    }
}
