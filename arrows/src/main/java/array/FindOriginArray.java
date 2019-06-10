//  Copyright 2019 The KeepTry Open Source Project
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

public class FindOriginArray {
  /**
   * <pre>
   *  scenario:
   *  original array b: b[i] value range is 1~n. length is n
   *  given array a: a[i] = the number of b[j], j < i, and b[j]> b[i]
   *  require the original array b.
   *
   *  e.g.
   *   b = {4, 2, 5, 3, 1};
   *   a = {0, 1, 0, 2, 4};
   */
  // Runtime O(N^2). how to improve it?
  public static int[] getOrigin(int[] a) {
    int n = a.length;
    if (a == null || n == 0) {
      return new int[0];
    }

    List<Integer> list = new ArrayList<>(n);
    for (int i = 1; i <= n; i++) {
      list.add(i);
    }
    int[] b = new int[n];
    for (int i = n - 1; i >= 0; i--) {
      // index i is current size -1 of list
      // parameter of LinkedHashMap.remove(Object) is Object, not key.
      b[i] = list.remove(i - a[i]); // O(N)
    }
    return b;
  }

  // ---- test
  public static void main(String[] args) {
    int[] a = {4, 2, 5, 3, 1};
    int[] b = {0, 1, 0, 2, 4};
    System.out.println(Arrays.equals(getOrigin(b), a));
    a = new int[] {4, 3, 5, 2, 1};
    b = new int[] {0, 1, 0, 3, 4};
    System.out.println(Arrays.equals(getOrigin(b), a));
  }
}
