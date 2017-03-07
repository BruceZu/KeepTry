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

package tree.tree_map;

import java.util.*;

/**
 * <pre>
 * <a href="http://infotechgems.blogspot.com/2011/11/java-collections-performance-time.html">java-collections-performance-time</a>
 */
public class Leetcode480SlidingWindowMedian {
    // -----------------------------------using sorted list-------------------------------------------------
    public double[] medianSlidingWindow2(int[] nums, int k) {
        List<Integer> sortedList = new ArrayList(k);
        for (int i = 0; i < k; i++) sortedList.add(insertIndexOf(sortedList, nums[i]), nums[i]);

        double[] result = new double[nums.length - k + 1];
        result[0] = getMedian(sortedList);
        // O(nk)
        for (int i = k; i < nums.length; i++) {
            sortedList.add(insertIndexOf(sortedList, nums[i]), nums[i]); // O(k) with ArrayList
            sortedList.remove(insertIndexOf(sortedList, nums[i - k]));   // O(k) with ArrayList
            result[i - k + 1] = getMedian(sortedList);
        }
        return result;
    }

    public double getMedian(List<Integer> sortedList) {
        int size = sortedList.size();
        if (size % 2 == 1) return sortedList.get(size / 2);
        return (sortedList.get(size / 2) * 1L + sortedList.get(size / 2 - 1)) * 0.5;
    }

    // binarySearch on sorted list O(logk) with ArrayList
    public int insertIndexOf(List<Integer> sortedList, int v) {
        int l = 0, r = sortedList.size() - 1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (sortedList.get(mid) > v) {
                r = mid - 1;
            } else if (sortedList.get(mid) == v) {
                return mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    // -------O(nk) use 2 heap -------------------------
    static public double[] medianSlidingWindow3(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        int size = 0;
        PriorityQueue<Integer> top = new PriorityQueue<>((a, b) -> (int) ((double) b - a)); // use double then back to int
        PriorityQueue<Integer> bottom = new PriorityQueue<>();
        int i = 0;
        // Initialize the heaps
        while (i < k) bottom.offer(nums[i++]);
        for (int j = 1; j <= (k + 1) / 2; j++) top.offer(bottom.poll());

        for (; ; i++) {
            // median
            if (k % 2 == 0) result[size++] = ((double) top.peek() + bottom.peek()) / 2.0;
            else result[size++] = (double) top.peek();
            // stop
            if (i == nums.length) {
                break; // else the last median will be lost
            }
            // move out
            Integer moveout = new Integer(nums[i - k]);
            if (moveout <= top.peek()) top.remove(moveout);// o(k)
            else bottom.remove(moveout);

            //always keep small.size() == big.size() or small.size() == big.size()+1
            if (top.size() <= bottom.size()) top.add(nums[i]);
            else bottom.add(nums[i]);
            // make sure the order
            if (bottom.size() > 0) {
                while (top.peek() > bottom.peek()) {
                    bottom.add(top.poll());
                    top.add(bottom.poll());
                }
            }
        }
        return result;
    }

    // -------O(nlgk) use 2 treeMap-------------------------
    static private int removeOneGetIt(Map<Integer, Integer> map, int num) {
        map.put(num, map.get(num) - 1); // O(lgk)
        if (map.get(num) == 0) map.remove(num);
        return num;
    }

    static private void addOne(Map<Integer, Integer> map, int num) {
        if (!map.containsKey(num)) map.put(num, 1);
        else map.put(num, map.get(num) + 1);
    }

    static public double[] medianSlidingWindow(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        int size = 0;
        // TreeMap not Map
        // Collections.reverseOrder();
        // use double then back to int
        TreeMap<Integer, Integer> top = new TreeMap<>((a, b) -> -a.compareTo(b));
        TreeMap<Integer, Integer> bottom = new TreeMap();
        int i = 0;
        // Initialize the heaps
        int bottomSize = 0, topSize = 0;
        while (i < k) {
            addOne(bottom, nums[i++]);
            bottomSize++;
        }
        for (int j = 1; j <= (k + 1) / 2; j++) {
            addOne(top, removeOneGetIt(bottom, bottom.firstKey()));
            bottomSize--;
            topSize++;
        }

        for (; ; i++) {
            // median
            if (k % 2 == 0) result[size++] = ((double) top.firstKey() + bottom.firstKey()) / 2.0;
            else result[size++] = (double) top.firstKey();
            // stop
            if (i == nums.length) {
                break; // else the last median will be lost
            }
            // move out
            Integer moveout = new Integer(nums[i - k]);
            if (moveout <= top.firstKey()) {
                removeOneGetIt(top, moveout);
                topSize--; // o(logk)
            } else {
                removeOneGetIt(bottom, moveout);
                bottomSize--;
            }

            //always keep small.size() == big.size() or small.size() == big.size()+1
            if (topSize <= bottomSize) {
                addOne(top, nums[i]);
                topSize++;
            } else {
                addOne(bottom, nums[i]);
                bottomSize++;
            }
            // make sure the order
            if (bottom.size() > 0) {
                while (top.firstKey() > bottom.firstKey()) {
                    addOne(bottom, removeOneGetIt(top, top.firstKey()));
                    addOne(top, removeOneGetIt(bottom, bottom.firstKey()));
                }
            }
        }
        return result;
    }

    // ------------------------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println(Arrays.toString(medianSlidingWindow(new int[]{1, 2}, 1)));
        System.out.println(Arrays.toString(medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
        System.out.println(Arrays.toString(medianSlidingWindow(new int[]{
                        -2147483648, -2147483648, 2147483647, -2147483648, -2147483648, -2147483648, 2147483647,
                        2147483647, 2147483647, 2147483647, -2147483648, 2147483647, -2147483648},
                2)));
    }
}
