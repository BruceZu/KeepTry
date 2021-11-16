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

package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Leetcode1253Reconstructa2RowBinaryMatrix {
  /*
    1253. Reconstruct a 2-Row Binary Matrix

    Given the following details of a matrix with n columns and 2 rows :

    The matrix is a binary matrix, which means each element in the matrix can be 0 or 1.
    The sum of elements of the 0-th(upper) row is given as upper.
    The sum of elements of the 1-st(lower) row is given as lower.
    The sum of elements in the i-th column(0-indexed) is colsum[i], where colsum is given as an integer array with length n.
    Your task is to reconstruct the matrix with upper, lower and colsum.

    Return it as a 2-D integer array.

    If there are more than one valid solution, any of them will be accepted.

    If no valid solution exists, return an empty 2-D array.


    Input: upper = 2, lower = 1, colsum = [1,1,1]
    Output: [[1,1,0],[0,0,1]]
    Explanation: [[1,0,1],[0,1,0]], and [[0,1,1],[1,0,0]] are also correct answers.


    Input: upper = 2, lower = 3, colsum = [2,2,1,1]
    Output: []


    Input: upper = 5, lower = 5, colsum = [2,1,2,0,1,0,1,2,0,1]
    Output: [[1,1,1,0,1,0,0,1,0,0],[1,0,1,0,0,0,1,1,0,1]]


    Constraints:

    1 <= colsum.length <= 10^5
    0 <= upper, lower <= colsum.length
    0 <= colsum[i] <= 2
  */
  public static List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
    List<List<Integer>> r = new ArrayList(2);
    int[] u = new int[colsum.length], d = new int[colsum.length];

    for (int i = 0; i < colsum.length; i++) {
      if (upper < 0 || lower < 0) return r;
      else {
        if (colsum[i] == 0) {
          u[i] = 0;
          d[i] = 0;
        } else if (colsum[i] == 2) {
          u[i] = 1;
          d[i] = 1;
          upper--;
          lower--;
        } else { // it is 1, assume colsum[i] is avalid
          if (upper >= lower) { // greedy
            u[i] = 1;
            d[i] = 0;
            upper--;
          } else {
            u[i] = 0;
            d[i] = 1;
            lower--;
          }
        }
      }
    }
    if (upper == 0 && lower == 0) {
      List<Integer> a = new ArrayList<>(), b = new ArrayList<>();
      for (int i : u) a.add(i);
      for (int i : d) b.add(i);
      r.add(a);
      r.add(b);
      return r;
    } else return r;
  }
}
