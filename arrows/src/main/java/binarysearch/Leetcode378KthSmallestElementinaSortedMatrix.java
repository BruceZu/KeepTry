//  Copyright 2021 The KeepTry Open Source Project
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

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Leetcode378KthSmallestElementinaSortedMatrix {
  /*
    Leetcode  378. Kth Smallest Element in a Sorted Matrix
    Given an n x n matrix where each of the rows and columns
    are sorted in ascending order, return the kth smallest element in the matrix.

    Note that it is the kth smallest element in the sorted order, not the kth distinct element.


    Input: matrix = [[1,  5, 9],
                     [10,11,13],
                     [12,13,15]], k = 8
    Output: 13
    Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13


    Input: matrix = [[-5]], k = 1
    Output: -5


    Constraints:

    n == matrix.length
    n == matrix[i].length
    1 <= n <= 300
    -10^9 <= matrix[i][j] <= 10^9
    All the rows and columns of matrix are guaranteed to be sorted in non-decreasing order.
    1 <= k <= n^2

  */

  /* --------------------------------------------------------------------------
  finding the Kth smallest elements from amongst N row sorted lists
  Skill: min{K,N} size min heap
  O(K*logmin{K,N}) time, N is row number,
  O(K) space

  but the sorted column property is not used here
  */
  public int kthSmallest_(int[][] matrix, int k) {
    int N = matrix.length;
    Queue<MyHeapNode> q = new PriorityQueue<>(Math.min(N, k), Comparator.comparingInt(a -> a.v));
    for (int r = 0; r < Math.min(N, k); r++) {
      q.offer(new MyHeapNode(matrix[r][0], r, 0));
    }
    MyHeapNode cur = q.peek();
    while (k-- > 0) {
      cur = q.poll();
      int r = cur.r, c = cur.c;
      if (c < N - 1) {
        q.offer(new MyHeapNode(matrix[r][c + 1], r, c + 1));
      }
    }
    return cur.v;
  }

  class MyHeapNode {
    public int r, c, v;

    public MyHeapNode(int v, int r, int c) {
      this.v = v;
      this.r = r;
      this.c = c;
    }
  }
  /*---------------------------------------------------------------------------
  apply the Binary Search on the number range instead of the index range.
  use the middle value split the matrix into 2 parts
  [min, <=mv] [>mv, max]
  during the process of dividing
     -track the max element in the left part <=mv
     -track the min element in the right part >mv
     put in array lmr, initialized with min and max of matrix
     use them to calculate next mv or find the answer if the left part counts is just same as k

  How about duplicated matix no-descending
     3  3  3
     3  3  3
     3  3  3

     l=3 r=3 mv=3
  when [l, r] are all duplicated value or l==v then the kth element value is l,
  need not divide anymore

  Each time to divide, the way to account the left part number is same

  rows and colums number is N
  Value range diff is V
  O((logV)*N) time
  O(1) space
   */
  public int kthSmallest(int[][] matrix, int k) {
    int N = matrix.length;
    int l = matrix[0][0], r = matrix[N - 1][N - 1];
    while (l < r) { // when duplicated element find
      int mv = l + (r - l) / 2;
      int[] lmr = {matrix[0][0], matrix[N - 1][N - 1]}; // initial value
      int n = split(matrix, mv, lmr);
      if (n == k) return lmr[0];
      if (n < k) l = lmr[1];
      else r = lmr[0];
    }
    return l;
  }

  // return element number of the left part [min, mv]
  // kee tracking the max one <=mv and the min one >mv
  private int split(int[][] matrix, int mv, int[] lmr) {
    int n = 0;
    int N = matrix.length;
    int row = N - 1, col = 0;
    while (row >= 0 && col < N) {
      int v = matrix[row][col];
      if (matrix[row][col] > mv) {
        lmr[1] = Math.min(lmr[1], v);
        row--;
      } else { // <=mv
        lmr[0] = Math.max(lmr[0], v);
        n += row + 1;
        col++;
      }
    }
    return n;
  }
}
