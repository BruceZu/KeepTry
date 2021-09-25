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

package double_pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PairElementsFromTwoArrayLessOrEqualToTarget {
  /*
     Given 2 lists a and b.

     Each element is a pair of integers where the first integer represents the unique id
     and the second integer represents a value.

     Your task is to find an element from a and an element form b such that the sum of
     their values is less or equal, <=,  to target and as close to target as possible.

     Return a list of ids of selected elements.
     If no pair is possible, return an empty list.


   Input:
   a = [[1, 2], [2, 4], [3, 6]]
   b = [[1, 2]]
   target = 7
   Output: [[2, 1]]
   Explanation:
   There are only three combinations [1, 1], [2, 1], and [3, 1], which have a total sum of 4, 6 and 8, respectively.
   Since 6 is the largest sum that does not exceed 7, [2, 1] is the optimal pair.


   Input:
   a = [[1, 3], [2, 5], [3, 7], [4, 10]]
   b = [[1, 2], [2, 3], [3, 4], [4, 5]]
   from     b       s       b         s
   stop     s       b       s         b
   target = 10
   Output: [[2, 4], [3, 2]]
   Explanation:
   There are two pairs possible. Element with id = 2 from the list `a` has a value 5, and element with id = 4 from the list `b` also has a value 5.
   Combined, they add up to 10. Similarily, element with id = 3 from `a` has a value 7, and element with id = 2 from `b` has a value 3.
   These also add up to 10. Therefore, the optimal pairs are [2, 4] and [3, 2].

   Input:
   a = [[1, 8], [2,  7], [3, 14]]
   b = [[1, 5], [2, 10], [3, 14]]
   from     b       s       b
   stop     s       b       s
   target = 20
   Output: [[3, 1]]
   Example 4:

   Input:
   a = [[1, 8], [2, 15], [3, 9]]
   b = [[1, 8], [2, 11], [3, 12]]
   target = 20
   Output: [[1, 3], [3, 2]]
  */

  /*
    A [i   ]
    B [   j]

    i keep moving right,
    j keep moving left
    both i and j will not move back.
    each loop move one step from i or j


    for the start or any step:
       if  sum > target
        - need not tracing max sum with current pair
        - move options:
             -- move i left: no, i-1 does not exist or i-1 have been used with j,because
                                when sum<=target always move i to right, not moves j to left
             -- move j left
       else sum <= target
         - need tracing max sum with current pair
               if current max> maxsum
               need a new list to keep the result
               for the duplicated element with value as B[j], j always at the right most index of
               these duplicated elements if they exist, because when need move current j left,
               all duplicated elements need be skipped.
         - move options:
             -- move i right:
             -- move j right:no, j+1 does not exist or j+1 have been used with i,because
                                when sum>target always move j to left, not moves i to left


   O(M+N) time not taking in account the duplicated element in B[]
   O(1) extra space
  */


  static List<int[]> getPairs(int[][] A, int[][] B, int t) {
    Arrays.sort(A, Comparator.comparingInt(i -> i[1]));
    Arrays.sort(B, Comparator.comparingInt(i -> i[1]));
    List<int[]> r = new ArrayList<>();
    int maxSum = Integer.MIN_VALUE; // closer to target,  <= target

    int i = 0, j = B.length - 1;
    while (i < A.length && j >= 0) {
      int s = A[i][1] + B[j][1];
      if (s > t) j--;
      else {
        if (s >= maxSum) {
          if (s > maxSum) {
            maxSum = s;
            r = new ArrayList<>();
          }
          r.add(new int[] {A[i][0], B[j][0]});

          int d = j - 1; // duplicated in A[]
          while (d >= 0 && B[d][1] == B[d + 1][1]) r.add(new int[] {A[i][0], B[d--][0]});
        }
        i++;
      }
    }
    return r;
  }


  public static void main(String[] args) {
    int[][] A, B;
    A =
        new int[][] {
          {1, 2},
          {2, 4},
          {3, 6}
        };
    B = new int[][] {{1, 2}};
    print(A, B, 7, "[[2,1]]");

    A =
        new int[][] {
          {1, 3},
          {2, 5},
          {3, 7},
          {4, 10}
        };
    B =
        new int[][] {
          {1, 2},
          {2, 3},
          {3, 4},
          {4, 5}
        };
    print(A, B, 10, "[[2,4][3,2]]");

    A =
        new int[][] {
          {1, 8},
          {2, 7},
          {3, 14},
        };
    B =
        new int[][] {
          {1, 5},
          {2, 10},
          {2, 10},
        };
    print(A, B, 20, "[[3,1]]");

    A =
        new int[][] {
          {1, 8},
          {2, 15},
          {3, 9},
        };
    B =
        new int[][] {
          {1, 8},
          {2, 11},
          {3, 12},
        };
    print(A, B, 20, "[[1,3][3,2]]");

    A =
        new int[][] {
          {1, 8},
          {2, 8},
          {3, 9},
          {4, 9},
          {5, 15},
          {6, 15},
        };
    B =
        new int[][] {
          {1, 8},
          {2, 8},
          {3, 11},
          {4, 11},
          {5, 12},
          {6, 12},
        };
    print(A, B, 20, "[[1,6][1,5][2,6][2,5][3,4][3,3][4,4][4,3]]");
  }
  private static void print(int[][] A, int[][] B, int t, String expected) {
    List<int[]> r;
    StringBuilder pairs;

    r = getPairs(A, B, t);
    pairs = new StringBuilder("[");
    for (int[] a : r) {
      pairs.append("[" + a[0] + "," + a[1] + "]");
    }
    pairs.append("]");
    System.out.println(pairs.toString().equals(expected));
    System.out.println(expected);
  }

}
