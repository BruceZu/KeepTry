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

package tree.binary_indexed_tree;

import java.util.Arrays;

/**
 * Note: idx: index of binary indexed tree, start from 1; see the mapping rule.
 * <pre>
 *  https://www.youtube.com/watch?v=v_wj_mOAlig
 *  https://en.wikipedia.org/wiki/Fenwick_tree
 *  https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/
 *
 *  1 BIT array value.
 *     binary tree array[0] is not used, BIT array index is 1 based.
 *     content is decided by what it is for, one member or segment members' sum, production  or ... of original array
 *     value in BIT is sum/production/xor of segment of original array from array[?]~array[ith].
 *
 *     take sum as example:
 *     e.g. BST[12]
 *                index  12 = 0b1100
 *                last bit‘1’ is 4 = x & (-x)
 *
 *                get 4 member from original array： start from the 12th: array【11】 including.
 *                BIT[12]=array【11】+array【10】+array【9】+array【8】
 *                        12th　　　　 11th　　　  10th　　　 9th
 *
 *     How to get the sum of the original array first 12 members.
 *     mapping rule:
 *     sum of first 12 members = 0b 1100＝0b 1000 + 0b 0100
 *                                  8　　　　４
 *                  　 ４　＝　　　　 x & (-x)　saved in BST[0b 1100]
 *                 　  8  ＝　x - ( x & (-x))saved in BST[0b 1000]
 *     calculate indexes of BST
 *       12 = 0b 1100,                   BIT[0b 1100] => ４
 *       x  = x - ( x & (-x)) = 0b 1000; BIT[0b 1000] => 8
 *       x  = x - ( x & (-x)) = 0      ; stop
 *      sum of first 12 members = BIT[0b 1100]  + BIT[0b 1000]
 *                              = BIT[12]       + BIT[8]
 *
 *  2  How to calculate the sum of [index 0~index i] of original array via binary tree array
 *     e.g. sum of [index 0 ~index 12] the first 13 members' sum:
 *      13： 0b1101
 *           0b1101 = 0b1000 + 0b0100 + 0b0001
 *      mapping rule: saved in   BIT array's index:
 *                    0b1000   0b1100   0b1101
 *      0b1101 is the first; next x=x-x&(x-1); till x==0;
 *      runtime O(1ogn)
 *
 *     <img src="../../../resources/BIT_sum13.png" height="400" width="600">
 *     <img src="../../../resources/BIT_sum7.png" height="400" width="600">
 *
 * 3   Change happened in the original array will affect the BIT.
 *     add() will affect BIT
 *     add() also can be used to build binary tree array initially.
 *     e.g.:  Change happened on the original array array[12], the 13th member. It is added some value.
 *     <img src="../../../resources/BIT_add.png" height="400" width="600">
 *
 *       BIT[13] need updated accordingly and firstly.
 *       the following members in BIT are also need updated:
 *       calculated next index of BIT
 *         13：      0b    1101
 *         next 14   0b    1110
 *         next 16   0b   10000
 *         next 32   0b  100000
 *         next 64   0b 1000000
 *       till reach or out of the length of BIT
 *       runtime is O(1ogn)
 *       x is current index; next index: x + ( x & (-x))
 *
 *  Notes:
 *     BIT array length = array length + 1
 *    - sum sum(i, j】 = sum(j) - sum(i)
 *    - product
 *    - xor
 *    but not for min/max (using segment tree)
 */
public class BinaryIndexedTree {
    private final int[] bitree;

    BinaryIndexedTree(int[] flat) {
        bitree = new int[flat.length + 1];
        for (int i_0_based = 0; i_0_based < flat.length; i_0_based++) {
            add(i_0_based + 1, flat[i_0_based]);
        }
    }

    public void add(int i_1_based, int delta) {
        while (i_1_based < bitree.length) {
            bitree[i_1_based] += delta;
            i_1_based += i_1_based & -i_1_based;
        }
    }

    public int sum(int i_0_based) {
        int sum = 0;
        // Note
        int i_1_based = i_0_based + 1;
        while (i_1_based > 0) {
            sum += bitree[i_1_based];
            i_1_based -= i_1_based & -i_1_based;
        }
        return sum;
    }

    // [i,j]
    public int sum(int i_0_based, int j_0_based) {
        return sum(j_0_based) - sum(i_0_based - 1);
    }

    /*------------------------common founctions ----------------*/

    public static void main(String[] args) {
        //                     0  1  2  3  4  5  6  7  8  9
        int[] test = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        BinaryIndexedTree bit = new BinaryIndexedTree(test);
        System.out.println(bit.toString());
        System.out.println(bit.sum(0));
        System.out.println(bit.sum(1));
        System.out.println(bit.sum(2));
        System.out.println(bit.sum(3));
        System.out.println(bit.sum(4));
        System.out.println(bit.sum(5));
        System.out.println(bit.sum(6));
        System.out.println(bit.sum(7));
        System.out.println(bit.sum(8));
        System.out.println(bit.sum(9));
        System.out.println("----");
        System.out.println(bit.sum(0, 2));
        System.out.println(bit.sum(4, 5));
    }

    @Override
    public String toString() {
        return Arrays.toString(bitree);
    }
}
