//  Copyright 2017 The keepTry Open Source Project
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

package binarysearch;

import java.io.InputStream;
import java.util.*;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/count-of-smaller-numbers-after-self/?tab=Description">LeetCode</a>
 */
public class Leetcode315CountofSmallerNumbersAfterSelf {

    // O(N^2) It is slow but the code is easy to keep
    static public List<Integer> countSmaller2(int[] nums) {
        Integer[] result = new Integer[nums.length];

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = nums.length - 1; i >= 0; --i) {
            result[i] = ~Collections.binarySearch(list, nums[i],
                    (a, toBeInsert) -> a >= toBeInsert ? 1 : -1);// when a == b, b is insert before a, a is moved back.
            list.add(result[i], nums[i]);
        }
        System.out.println(Arrays.toString(result));
        return Arrays.asList(result);
    }

    // traverse the array backwards meanwhile build a BST (AVL, RB-tree..    (a, toBeInsert) -> (a >= toBeInsert) ? 1 : -1.).
    // The key is how to get how many nodes in tree smaller than current node.
    // O(n^2)
    // Time Limit Exceeded
    // Treeset there is not keep the size of 'tree.headSet(nums[i]).size()'
    static public List<Integer> countSmaller(int[] nums) {
        Integer[] result = new Integer[nums.length];
        TreeSet<Integer> tree = new TreeSet<>(
                (a, toBeInsert) -> a <= toBeInsert ? -1 : 1); // when a == b, b will not replace a in set, b is 'bigger'
        for (int i = nums.length - 1; i >= 0; i--) {
            tree.add(nums[i]);
            result[i] = tree.headSet(nums[i]).size();// o(n)
            // headSet(x): if in set there 'x,x' with the specific comparator the fist x will be 'bigger' than x.
        }
        System.out.println(Arrays.toString(result));
        return Arrays.asList(result);
    }

    //--------------------------------------------------------
    public static void main(String[] args) {
        countSmaller(new int[]{1, 3, 2, 3, 1});
        countSmaller(new int[]{2, 0, 1, 0});
        countSmaller(new int[]{5, 2, 2, 6, 1});

        //                      0  1  2  3  4  5
        countSmaller(new int[]{5, 2, 3, 7, 6, 1});
        //                      3  1  1  2  1  0

        countSmaller(new int[]{1, 7, 9});
        countSmaller(new int[]{1, 1});
        System.out.println(Integer.MAX_VALUE - Integer.MIN_VALUE);
        System.out.println(Integer.MIN_VALUE - Integer.MAX_VALUE);
    }
}
