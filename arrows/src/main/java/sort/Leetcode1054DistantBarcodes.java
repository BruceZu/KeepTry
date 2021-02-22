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

package sort;

public class Leetcode1054DistantBarcodes {
  /*
  In a warehouse, there is a row of barcodes, where the ith barcode is barcodes[i].
  Rearrange the barcodes so that no two adjacent barcodes are equal.
  You may return any answer, and it is guaranteed an answer exists.
  */
  // O(N) time
  public int[] rearrangeBarcodes2(int[] barcodes) {
    /*
    1 <= barcodes.length <= 10000
    1 <= barcodes[i] <= 10000
     */
    // 1> Firstly, let the number with maximum number to take the even place, index 0, 2, 4, ...,N-1
    // is
    //    possible, it can at most reach index N-1 because 'it is guaranteed an answer exists.'
    // 2> Other numbers: if the even index place has not be used out by the first number,
    //    continue take even place, then from left end take the odd index place. This require
    //    the other number need not to be placed in order, but need be placed together.

    // 'int[] barcodes' means each number is integer and ' 1 <= barcodes[i] <= 10000' means
    // the sort can be done in O(N) not O(NlogN)
    int[] counts = new int[10000 + 1];
    int max = 0, num = 0;
    for (int n : barcodes) {
      counts[n]++;
      if (counts[n] > max) {
        max = counts[n];
        num = n;
      }
    }
    int index = 0;
    // 'it is guaranteed an answer exists.' so index will not be great than N-1
    while (max-- > 0) {
      barcodes[index] = num;
      index += 2;
    }
    counts[num] = 0;
    // locate other numbers
    for (int i = 0; i < counts.length; i++) {
      if (counts[i] == 0) continue;
      while (counts[i]-- > 0) {
        if (index >= barcodes.length) index = 1;
        barcodes[index] = i;
        index += 2;
      }
    }
    return barcodes;
  }
  // merge the locating numbers in one loop
  // by using the '1 <= barcodes[i] <= 10000'
  public int[] rearrangeBarcodes(int[] barcodes) {
    int[] counts = new int[10000 + 1];
    int max = 0, num = 0;
    for (int n : barcodes) {
      counts[n]++;
      if (counts[n] > max) {
        max = counts[n];
        num = n;
      }
    }
    // '1 <= barcodes[i] <= 10000' which
    // means the counts[0] is not used
    // and num need be precessed firstly
    // once it is processed counts[num] will be 0 and will be skipped
    // once the loop reach it again later.
    int index = 0;
    for (int i = 0; i < counts.length; i++) {
      int k = i == 0 ? num : i;
      while (counts[k]-- > 0) {
        if (index >= barcodes.length) index = 1;
        barcodes[index] = k;
        index += 2;
      }
    }
    return barcodes;
  }
}
