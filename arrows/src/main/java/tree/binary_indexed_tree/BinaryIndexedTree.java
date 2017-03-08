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
 * Note: idx: index of binary indexed tree, start from 1;
 * <pre>
 *  https://www.youtube.com/watch?v=v_wj_mOAlig
 *  https://en.wikipedia.org/wiki/Fenwick_tree
 *
 *  1 BIT array 每个数存的是什么.
 *     binary tree array[0]从来不用。 或者说BIT array 的index 值是 1 based的
 *     其他数存的是原来数组的一个range sum：
 *     如binary tree array[12]
 *                12 = 0b1100
 *                最后一个bit‘1’对应的十进制数是 2 =  x-(x&(x-1))
 *                从原数组中数出2个数： 从第12个数既array【11】开始，含，向左数2个，求和存入 binary tree array[12]
 *
 *  2  原数组从第一个数开始到index i的数的和，用 binary tree array 怎么算？ 算法复杂度是多少
 *     如求 原数组index 0 到 index 12 这13个数的和
 *     分析13： 0b1101
 *      13 个数= 0b1000 个数     +     0b0100  个数   +     0b0001   个数
 *      他们已经存储在对应的BIT array 中的index为
 *              0b1000                0b1100              0b1101
 *
 *      各个index 求法： 13： 0b1101自己算一个，用  x&(x-1) 求前边一个知道为0停止。
 *      算法复杂度 是 o(1ogn)
 *
 *     <img src="../../../resources/BIT_sum13.png" height="400" width="600">
 *     <img src="../../../resources/BIT_sum7.png" height="400" width="600">
 *
 * 3   原数组index 为12 发生变化，add（）对 binary tree array的影响：
 *    可用add（） build binary tree array
 *
 *   <img src="../../../resources/BIT_add.png" height="400" width="600">
 *
 *       binary tree array 中 index 13 自己需要更新
 *       后续需要更新的index是：
 *       分析13： 0b1101
 *       下一个是 0b1110
 *       下一个是0b10000
 *       直到出界为止
 *       算法复杂度 是 o(1ogn)
 *       x + ( x & (-x))
 *
 *  Notes:
 *     BIT array 长度是原数组的length + 1
 *     基本BIT array 可以求原数组任意区间的sum sum(i, j】 = sum(j) - sum(i)
 *     还可用于求 product, xor。 but not for min/max (using segment tree)
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
        int idx = i_1_based;
        while (idx < bitree.length) {
            bitree[idx] += delta;
            idx += idx & -idx;
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
    @Override
    public String toString() {
        return Arrays.toString(bitree);
    }

    public static void main(String[] args) {
        //                     0  1  2  3  4  5  6  7  8  9
        int[] test = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
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
}
