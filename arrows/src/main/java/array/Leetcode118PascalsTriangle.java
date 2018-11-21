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

package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** @see <a href="https://leetcode.com/problems/pascals-triangle/">leetcode </a> */
public class Leetcode118PascalsTriangle {
  public List<List<Integer>> generate(int numRows) {
    Integer[] last = new Integer[numRows];
    List<List<Integer>> r = new ArrayList();
    if (numRows == 0) {
      return r;
    }
    r.add(Arrays.asList(1));
    last[0] = 1;
    for (int size = 2; size <= numRows; size++) {
      last[size - 1] = 1;
      for (int j = size - 2; j > 0; j--) {
        last[j] = last[j] + last[j - 1];
      }
      Integer[] cur = new Integer[size];
      System.arraycopy(last, 0, cur, 0, size);
      r.add(Arrays.asList(cur));
    }
    return r;
  }
}
