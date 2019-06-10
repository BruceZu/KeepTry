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

package math;

import java.util.Arrays;

public class ReRrangeArrayWithoutExtraSpace {
  /**
   * scenario:
   *  array A, length n, value is 0 ~ n-1, the index value
   *  require get another array B. B[i]=A[A[i]]
   *  require with O(1) extra space.
   *
   * <a href='https://www.careercup.com/question?id=4909367207919616'> see career up <a/>
   * <pre>
   * basic idea:
   * x<n, y<n;
   * then
   * ( x + y * n ) / n = y;
   * ( x + y % n * n) / n = y;
   *
   * during the process need the middle status,
   * because other element after current i need the original value which can be get by idea:
   * middle status ms= (x + y % n * n)
   *
   *   ms % n
   *   = (x + y % n * n) % n
   *   = x % n + y % n * n % n
   *   = x % n + 0
   *   = x % n
   *   = x
   *
   *   so if z is other element:
   *   z + (x + y % n * n) % n * n
   *   = z + x % n * n + y % n * n % n * n
   *   = z + x * n + 0
   *   = z + x * n
   */
  static void rearrange(int A[]) {
    int n = A.length;
    for (int i = 0; i < n; i++) {
      A[i] += A[A[i]] % n * n;
    }


    for (int i = 0; i < n; i++) {
      A[i] /= n;
    }
  }

  // ---- test
  static void printArr(int arr[], int n) {
    for (int i = 0; i < n; i++) System.out.print(arr[i] + " ");
    System.out.println("");
  }

  public static void main(String[] args) {
    int arr[] = {3, 2, 0, 1};
    rearrange(arr);
    System.out.println(Arrays.equals(arr, new int[] {1, 0, 3, 2}));
  }
}
