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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/count-of-smaller-numbers-after-self/?tab=Description">LeetCode</a>
 */
public class Leetcode315CountofSmallerNumbersAfterSelf {
    // ----------------------A Merge sort with tracking of those right-to-left jumps -----------------
    static class V_I {
        int v;
        int idx;

        V_I(int number, int index) {
            this.v = number;
            this.idx = index;
        }
    }

    static public List<Integer> countSmaller_sortValue(int[] nums) {
        V_I[] array = new V_I[nums.length];
        for (int i = 0; i < nums.length; i++) {
            array[i] = new V_I(nums[i], i);
        }
        int[] smallNumThanElementAt = new int[nums.length];
        mergeSortWithTrackingSmaller(array, smallNumThanElementAt);
        List<Integer> res = new ArrayList<>();
        for (int i : smallNumThanElementAt) {
            res.add(i);
        }
        return res;
    }

    // O(nlogn)
    static private V_I[] mergeSortWithTrackingSmaller(V_I[] sortingArray, int[] smallNumThanElementAt) {
        int half = sortingArray.length / 2;
        if (half > 0) {// divide until to 1 element

            V_I[] ofL = new V_I[half];
            for (int i = 0; i < ofL.length; i++) {
                ofL[i] = sortingArray[i];
            }

            V_I[] ofR = new V_I[sortingArray.length - half];
            for (int i = 0; i < ofR.length; i++) {
                ofR[i] = sortingArray[half + i];
            }

            ofL = mergeSortWithTrackingSmaller(ofL, smallNumThanElementAt);
            ofR = mergeSortWithTrackingSmaller(ofR, smallNumThanElementAt);

            int l = 0, ri = 0;
            while (l < ofL.length || ri < ofR.length) {
                if (ri == ofR.length || l < ofL.length && ofL[l].v <= ofR[ri].v) {
                    sortingArray[l + ri] = ofL[l];
                    smallNumThanElementAt[ofL[l].idx] += ri; //ri is just those right-to-left jumps.
                    l++;
                } else {
                    sortingArray[l + ri] = ofR[ri];
                    ri++;
                }
            }
        }
        return sortingArray;
    }

    // instead of sort the number in nums, sort the indexes of each number.
    static public List<Integer> countSmaller_sortIndex(int[] nums) {
        int[] idxes = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            idxes[i] = i;
        }
        int[] smallNumThanElementAt = new int[nums.length];
        mergesort(nums, idxes, smallNumThanElementAt);
        List<Integer> res = new ArrayList<>();
        for (int i : smallNumThanElementAt) {
            res.add(i);
        }
        System.out.println(Arrays.toString(smallNumThanElementAt));
        return res;
    }

    static private int[] mergesort(int[] nums, int[] idxes, int[] smallNumThanElementAt) {
        int half = idxes.length / 2;
        if (half > 0) {// divide until to 1 element

            int[] ofL = new int[half];
            for (int i = 0; i < ofL.length; i++) {
                ofL[i] = idxes[i];
            }

            int[] ofR = new int[idxes.length - half];
            for (int i = 0; i < ofR.length; i++) {
                ofR[i] = idxes[half + i];
            }

            ofL = mergesort(nums, ofL, smallNumThanElementAt);
            ofR = mergesort(nums, ofR, smallNumThanElementAt);

            int l = 0, ri = 0;
            while (l < ofL.length || ri < ofR.length) {
                if (ri == ofR.length || l < ofL.length && nums[ofL[l]] <= nums[ofR[ri]]) {
                    idxes[l + ri] = ofL[l];
                    smallNumThanElementAt[ofL[l]] += ri; //ri is just those right-to-left jumps.
                    l++;
                } else {
                    idxes[l + ri] = ofR[ri];
                    ri++;
                }
            }
        }
        return idxes;
    }

    //--------------------------------B:  BST----------------------------------------------------------
    static class BSTNode {
        private final int v;
        private int way1Num;   // left Sub Tree Node Num

        private BSTNode left;
        private BSTNode right;

        public BSTNode(int val) {
            this.v = val;
        }
    }

    //access and build BST with loop --------------------------------
    public List<Integer> _countSmaller(int[] nums) {
        Integer[] result = new Integer[nums.length];
        if (nums == null || nums.length == 0) {
            return Arrays.asList(result);
        }

        BSTNode root = new BSTNode(nums[nums.length - 1]);
        result[nums.length - 1] = 0; // Integer default is null

        for (int i = nums.length - 2; i >= 0; i--) {
            _insert(root, result, i, nums);
        }
        return Arrays.asList(result);
    }

    private void _insert(BSTNode parent, Integer[] smallNumThanElementAtIndexOf, int i, int[] array) {
        int cur = array[i];
        int way2 = 0;
        while (true) {
            if (cur <= parent.v) {
                parent.way1Num++;
                if (parent.left == null) {
                    parent.left = new BSTNode(cur);
                    smallNumThanElementAtIndexOf[i] = way2;
                    break;
                }
                parent = parent.left;
            } else {
                way2 += parent.way1Num + 1;
                if (parent.right == null) {
                    parent.right = new BSTNode(cur);
                    smallNumThanElementAtIndexOf[i] = way2;
                    break;
                }
                parent = parent.right;
            }
        }
    }

    //  access and build BST with recursion ------------------------------------
    static public List<Integer> countSmaller_(int[] nums) {
        Integer[] result = new Integer[nums.length];
        BSTNode root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            root = insert_(root, i, 0, result, nums);
        }
        System.out.println(Arrays.toString(result));
        return Arrays.asList(result);
    }

    static private BSTNode insert_(BSTNode parent, int index, int way2Num,
                                   Integer[] numOfNodeSmallerThanNodeAtIndexOf, int[] array) {
        if (parent == null) {
            parent = new BSTNode(array[index]);
            numOfNodeSmallerThanNodeAtIndexOf[index] = way2Num;
        } else if (array[index] <= parent.v) {
            parent.way1Num++;
            parent.left = insert_(parent.left, index, way2Num, numOfNodeSmallerThanNodeAtIndexOf, array);
        } else {
            parent.right = insert_(parent.right, index, way2Num + parent.way1Num + 1,
                    numOfNodeSmallerThanNodeAtIndexOf, array);
        }
        return parent;
    }

    // ------------------------C Binary index tree --------------------------------------------------------


    // ------------------------D  Segment tree--------------------------------------------------------
    public static void main(String[] args) {
        //                      0  1  2  3  4  5
        countSmaller_sortIndex(new int[]{5, 2, 3, 7, 6, 1});
        //                      3  1  1  2  1  0
    }
}
