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

package subset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Given a set of distinct integers, nums, return all possible subsets.

Note:
Elements in a subset must be in non-descending order.
The solution set must not contain duplicate subsets.
For example,
If nums = [3,2,1], a solution is:

[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
 */
public class Leetcode78Subset {
    /**
     * Idea A  Back-tracing
     * <pre>
     * List all subset of n unique elements.
     * running time O(?)
     *
     * Idea 1:
     * E.g. sorted array {1,2,3,4,5}
     * A If it is to calculate subsets with a given size.
     * For all subsets with size = 3, 0<= 3 <= array.length.
     *  1 First number  has 3 choices.
     *    Second number's choices depends on the result of first number
     *    Third number's choices depends on the result of above 2 numbers.
     *    [1, 2, 3]
     *    [1, 2, 4]
     *    [1, 2, 5]
     *    [1, 3, 4]
     *    [1, 3, 5]
     *    [1, 4, 5]
     *
     *    [2, 3, 4]
     *    [2, 3, 5]
     *    [2, 4, 5]
     *
     *    [3, 4, 5]
     *
     *  2 It is set, this means it has nothing to do with the order of elements,
     *    {1,2,3} is same as {1 3 2}, that means when deciding the loop scope of the choices for a left number,
     *    never look back, left, instead just go forward towards right
     *    e.g. if {1,3} is selected 2 numbers, now the left one will be selected from 4 and 5,
     *    do not need care 2 anymore, because {1,3,2} is same as {1,2,3} which has been done.
     *
     *  3  need to check the result length before add it to result
     *     also need valid index for current number:
     *      index <= array.length - (size of subset - selected elements' number)
     *      So
     *      current number choices:
     *      start index  <= array.length - size of subset ( above case is <= 5  -  3)
     *
     * B  If it is to calculate all subsets without size specified. As in this way each result is distinguish,
     *    that means all results are just the excepted results except the empty set
     *
     * Note
     *  1 each step back of back-tracing, need restore to original status.
     *  2 Not use LinkedList in this class, special scenario
     *
     * </pre>
     *
     * @param sorted   sorted array
     * @param result   keep all subsets.
     * @param selected selected elements for current subset
     * @param choice   index of choice to select current number to calculate current subset in order.
     * @see <a href ="https://www.mathsisfun.com/combinatorics/combinations-permutations.html">Combinations and Permutations</a>
     */
    private static void subsets(int[] sorted, List<List<Integer>> result, ArrayList<Integer> selected, int choice) {
        while (choice < sorted.length) {
            selected.add(sorted[choice]);
//            // Check subset with size
//            if(selected.size() == 3) {
//                System.out.println(Arrays.toString(selected.toArray()));
//            }
            result.add((ArrayList<Integer>) selected.clone());
            if (choice + 1 < sorted.length) {
                subsets(sorted, result, selected, choice + 1);
            }
            selected.remove(selected.size() - 1);
            choice++;
        }
    }


    public List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<Integer>());

        subsets(nums, result, new ArrayList<Integer>(), 0);
        return result;
    }

    /**
     * * Idea B
     * <pre>
     * if (nums.length <= 64) using bit manipulation with runtime O(N*2^N)
     *
     *           1 2 3 4 5
     *     index 0 1 2 3 4
     *
     *           0 0 0 0 1
     *           0 0 0 1 0
     *           0 0 0 1 1
     *            ......
     *           1 1 1 1 1
     */
    public List<List<Integer>> subsets64(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<Integer>());
        int n = nums.length;
        for (long i = 1; i < Math.pow(2, n); i++) {
            long selected = i;
            List<Integer> subset = new ArrayList();
            int index = 0;
            do {
                if ((selected & 1) == 1) {
                    subset.add(nums[index]);
                }

                index++;
                selected >>= 1;
            } while (selected != 0);
            result.add(subset);
        }
        return result;
    }

    /**
     * Idea C
     * running time O(?)
     */
    public List<List<Integer>> subsetsLoop(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(Arrays.asList(new Integer[]{}));

        for (int i = 0; i < nums.length; i++) {
            int currentSize = result.size();
            for (int j = 0; j < currentSize; j++) {
                List l = new ArrayList<>(result.get(j));
                l.add(nums[i]);
                result.add(l);
            }
        }
        return result;
    }

    private static void subsets2(int[] nums, List<List<Integer>> result, int from) {
        final int current = nums[from];
        int currentSize = result.size();
        for (int j = 0; j < currentSize; j++) {
            List l = new ArrayList<>(result.get(j));
            l.add(current);
            result.add(l);
        }
        if (from + 1 < nums.length) {
            subsets2(nums, result, from + 1);
        }
    }

    public List<List<Integer>> subsets2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<Integer>());
        subsets2(nums, result, 0);
        return result;
    }
}
