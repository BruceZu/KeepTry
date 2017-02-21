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

/**
 * <pre>
 *  https://www.youtube.com/watch?v=v_wj_mOAlig
 *  https://en.wikipedia.org/wiki/Fenwick_tree
 *
 *  1 what is the BIT for?
 *    It is tree in concept. but it is a array.
 *    It is implemented using a flat array analogous to
 *    implementations of a binary heap.
 *    its values are based on a flat array of n numbers.
 *
 *                                      sum()   add()
 *     In a flat array of n numbers     o(N)    o(1)
 *     In prefix sum array              o(1)    o(N)
 *     Fenwick trees                  O(log n)  O(log n)
 *
 *  2 relation of BIT array and related flat array?  what is the value for a given index in BIT array?
 *
 *     Binary represent the index (start from 1) using 2^x
 *                13 = 0b1101
 *                13 = 0b1000      +     0b0100     +     0b0001
 *
 *     so:    sum can be calculated by 'binary ranges':
 *            sum(13)= sum(1, 0b1000 ) + sum(9, 12) + sum(13, 13 )
 *     BIT's value is 'binary ranges' sum:
 *            sum(1, 0b1000 ),  the sum of 2^3 elements (0b1000) and the last one is 0b1000, is the value of BIT( 0b1000 )
 *            sum(9, 0b1100 ),  the sum of 2^2 elements (0b100) and the last one is 0b1100, is the value of BIT( 0b1100 )
 *            sum(13,0b1101 ),  the sum of 2^0 elmentts (0b1) and the last one is 0b1101, is the value of BIT( 0b1101 )
 *                                           |
 *                                        number of   of index of BIT
 *            int elements = i & (-i)
 *            int from = i - (elements - 1)
 *            BIT[i] = sum(from , i) of array
 *
 *     sum(13)= BIT( 0b1000 ) + BIT( 0b1100) + BIT( 0b1101)
 *     sum tree looks like:
 *     <img src="../../resources/BIT_sum13.png" height="400" width="600">
 *     <img src="../../resources/BIT_sum7.png" height="400" width="600">
 *
 *   3 how to calculate sum using BIT array?
 *     extract last set bit: x & (-x)
 *     remove last set bit:  x - ( x & (-x))  or using x & (x-1)
 *
 *       -13 = 0b11110011
 *        13 = 0b00001101
 *        12 = 0b00001100
 *
 *     int getSum (int i) {
 *        int sum = 0;
 *        while (i > 0) {
 *          sum += tree[i];
 *          i -= (i & -i);
 *       }
 *       return sum;
 *     }
 *
 *  4  when there is an updating on the origin array, how to upate the BIT array.
 *  update tree looks like
 *   <img src="../../resources/BIT_add.png" height="400" width="600">
 *
 *     extract last set bit: x & (-x)
 *     add last set bit:  x + ( x & (-x))
 *
 *   void add (int i,int delta)  {
 *        while (i <=BIT.length){
 *          BIT[i] += delta;
 *          i += (i & -i);
 *        }
 *    }
 *
 *    update operation can be used to build BIT array
 *
 *     void build () {
 *         int [] BIT = new BIT[N];
 *         for (int i = 1; i <=N; ++i){
 *            add (i,a[i]);
 *         }
 *      }
 *
 *  5 why the operation is o(log n)
 *
 *
 *  Notes:
 *    use array of length + 1
 *    range sum(from, to) = sum(to) - sum(from-1)
 *    also useful for other operation like product, xor
 *    but not for min/max (using segment tree)
 */
package tree.binary_indexed_tree;