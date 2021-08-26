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

package sort;

import common_lib.Common;
import common_lib.Merger;

public class MergeSortNoRecursion {
  private static Merger merger = new Common();

  public MergeSortNoRecursion(Merger merger) {
    this.merger = merger;
  }

  /*
  Merge sort an array in Java use TimSort
  @see sort.Leetcode912SortanArray
  
  O(NlogN) runtime, O(N) space.
  */
  public static <T extends Comparable<T>> void mergeSortNoRecursion(T[] A) {
    if (A == null || A.length <= 1) return;
    Comparable[] tmp = new Comparable[A.length];
    int L = 1;
    while (L < A.length) {
      int s = 0;
      while (s + L <= A.length - 1) {
        merger.mergeInsort(A, s, s + L - 1, Math.min(s + 2 * L - 1, A.length - 1), tmp);
        s += 2 * L;
      }
      L = L << 1;
    }
  }
}
