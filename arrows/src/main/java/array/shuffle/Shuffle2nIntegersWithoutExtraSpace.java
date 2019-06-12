//  Copyright 2019 The KeepTry Open Source Project
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

package array.shuffle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shuffle2nIntegersWithoutExtraSpace {
    /**
     * <pre>
     * Given an array of 2n elements in the following format
     *  { a1, a2, a3, a4, …, an, b1, b2, b3, b4, …, bn }.
     * The task is shuffle the array to
     *  {a1, b1, a2, b2, a3, b3, …, an, bn }
     * without using extra space.
     *
     *Idea:
     * - the first and the last one will not in any circle
     * - there is more than one circle. how to avoid repeat any one?
     *   -- linear times 2 * n - 2
     *   -- start from n-1, once current circle is done and back to
     *      the start point, move one step toward left to start next
     *      circle.
     *   -- Is there is one circle whose has 2 adjacent parts? and why)
     * When array length =24. this solution does not work
     * need know if a element has been moved to avoid move a circle twice
     */
    static void shuffleArrayWrong(Integer[] a) {
        if (a.length % 2 != 0) return;

        int n = a.length / 2;
        int done = 0;
        int start = n, i = n; // targetIndex
        while (done++ < 2 * n - 2) {
            if (start == i) {
                start--;
                i--;
            }

            i = i > n - 1 ? 2 * (i - n) + 1 : 2 * i;
            swap(a, start, i);
        }
    }

    public static void swap(Integer[] a, int from, int to) {
        if (from != to) {
            a[from] ^= a[to];
            a[to] ^= a[from];
            a[from] ^= a[to];
        }
    }

    /**
     * <pre>
     * only support array length is power of 2
     * divide and conquer
     */
    static void shuffleArray(Integer a[], int from, int last) {
        // Only 2 element, return
        if (last - from == 1) return;
        int mid = (from + last) / 2;

        // for swapping first half of second array
        int temp = mid + 1;
        for (int i = (from + mid) / 2 + 1; i <= mid; i++) {
            swap(a, i, temp++);
        }

        shuffleArray(a, from, mid);
        shuffleArray(a, mid + 1, last);
    }

    static boolean isPowerOfTwo(int n) {
        return n == 0
                ? false
                : Math.ceil((Math.log(n) / Math.log(2)))
                        == Math.floor(((Math.log(n) / Math.log(2))));
    }

    public static void main(String[] args) {
        List<Integer> odd = new ArrayList<>();
        List<Integer> even = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        int N = 1000;
        for (int i = 1; i <= N; i = i + 2) {
            odd.add(i);
            even.add(i + 1);
            expected.add(i);
            expected.add(i + 1);
            int n = i + 1;

            Integer[] input = new Integer[n];
            odd.toArray(input);
            System.arraycopy(even.toArray(), 0, input, n / 2, n / 2);

            Integer[] expect = new Integer[n];
            expected.toArray(expect);
            if (isPowerOfTwo(n)) {
                shuffleArray(input, 0, input.length - 1);
                System.out.println("Length =" + n + ";" + Arrays.equals(input, expect));
            } else {
                // System.out.println("Do not support Length =" + n + ";");
            }
        }
    }
}
