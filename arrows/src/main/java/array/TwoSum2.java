//  Copyright 2016 The Sawdust Open Source Project
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

package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 C523 Describe a recursive algorithm that will check if an array A of integers contains
 an integer A[i] that is the sum of two integers that appear earlier in A, that is,
 such that A[i] = A[ j] + A[k] for j,k < i.

 change it question to return the first pair of A[i],A[j] and A[k] if it is found.
 */

public class TwoSum2 {
    // running time of the worst case is O(N^2)
    private static void findSum(Object[] nums, long k, int[] ids/* indexes*/) {
        // walk from both sides towards center
        int l = ids[0];
        int r = ids[1];
        if (l == r) {
            ids[0] = -1;
            ids[1] = -1;
            return;
        }
        int sum = (Integer) nums[l] + (Integer) nums[r];
        if (sum == k) {
            return;
        }
        if (sum < k) {
            ids[0]++;
        } else {
            ids[1]--;
        }
        findSum(nums, k, ids);
    }

    private static void check(List<Integer> data, List<Integer> shadow, int i, int[] ids) {
        if (i == data.size()) {
            ids[0] = -1;
            ids[1] = -1;
            return;
        }
        // sort it in
        int indexAfterSort = Arrays.binarySearch(data.toArray(), 0, i, data.get(i));
        if (indexAfterSort < 0) {
            indexAfterSort = -(indexAfterSort + 1);
        } else if (indexAfterSort == i - 1) {
            indexAfterSort = i; //without this, for the case of [0, 8, 8], result would be not found.
        }
        if (indexAfterSort != i) {
            data.add(indexAfterSort, data.remove(i));
            shadow.add(indexAfterSort, shadow.remove(i));
        }
        // find sum
        if (indexAfterSort >= 2) {
            List<Integer> next = data.subList(0, indexAfterSort); //[)
            ids[0] = 0;
            ids[1] = next.size() - 1;
            findSum(next.toArray(), data.get(indexAfterSort), ids);
        }
        // recursion
        if (ids[0] == -1 && ids[1] == -1) {
            check(data, shadow, i + 1, ids);
            return;
        }
        ids[0] = shadow.get(ids[0]);
        ids[1] = shadow.get(ids[1]);
        ids[2] = i;
    }

    // running time:
    // for i>=2 : binary search O(logi) + find sum O(i/2)
    // (n^2 + n - 6)/4 + log3+log4+…logn
    // O(n^2)
    public static int[] check(final int[] array) {
        List shadow = new LinkedList() {{
            for (int i = 0; i < array.length; i++) {
                add(i);
            }
        }};

        if (array[0] > array[1]) {
            array[0] ^= array[1];
            array[1] ^= array[0];
            array[0] ^= array[1];

            shadow.add(0, shadow.remove(1));
        }
        int[] resultIndex = new int[3];
        resultIndex[0] = -1;
        resultIndex[1] = -1;
        check(new LinkedList<Integer>() {{
            for (int i = 0; i < array.length; i++) {
                add(array[i]);
            }
        }}, shadow, 2, resultIndex);
        return resultIndex;
    }

    // running time O(n^2) with map
    public static int[] check2(final int[] array) {
        int[] r = new int[3];
        r[0] = -1;
        r[1] = -1;

        Map<Integer, List<Integer>> map = new HashMap(array.length);
        for (int i = 0; i < 2; i++) {
            int v = array[i];
            List<Integer> ids = map.get(v);
            if (ids == null) {
                ids = new LinkedList();
            }
            ids.add(i);
            map.put(v, ids);
        }

        for (int k = 2; k < array.length; k++) {
            int v = array[k];
            for (int j = 0; j < k; j++) {
                int I = v - array[j];
                if (map.keySet().contains(I)) {
                    List<Integer> ids = map.get(I);
                    for (int i : ids) {
                        if (i != j && i != k) {
                            r[0] = j;
                            r[1] = i;
                            r[2] = k;
                            return r;
                        }
                    }
                }
            }
            List<Integer> ids = map.get(v);
            if (ids == null) {
                ids = new LinkedList();
            }
            ids.add(k);
            map.put(v, ids);
        }
        return r;
    }

    // running time O(n^2) with int[]
    public static int[] check3(final int[] array) {
        int[] r = new int[3];
        r[0] = -1;
        r[1] = -1;

        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        int delta = -Math.min(min - max, min);

        int maxIndex = max - min + 1;
        maxIndex = Math.max(maxIndex, array.length);
        maxIndex += delta;

        int[] vIndex = new int[maxIndex + 1];
        int v = array[0];
        vIndex[v + delta] = 0 + 1;
        v = array[1];
        vIndex[v + delta] = 1 + 1;

        for (int i = 2; i < array.length; i++) {
            int vi = array[i];
            for (int j = 0; j < i; j++) {
                int vj = array[j];
                int vk = vi - vj;
                int kPlusOne = vIndex[vk + delta];
                if (kPlusOne != 0 && kPlusOne - 1 != j) {
                    r[0] = j;
                    r[1] = kPlusOne - 1;
                    r[2] = i;
                    return r;
                }
                vIndex[vi + delta] = i + 1;
            }
        }
        return r;
    }
}
