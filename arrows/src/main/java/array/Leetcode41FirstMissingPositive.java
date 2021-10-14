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

public class Leetcode41FirstMissingPositive {
  /*
      41. First Missing Positive

      Given an unsorted integer array nums, return the smallest missing positive integer.
      You must implement an algorithm that runs in O(n) time and uses constant extra space.


      Input: nums = [1,2,0]
      Output: 3

      Input: nums = [3,4,-1,1]
      Output: 2


      Input: nums = [7,8,9,11,12]
      Output: 1

      Input: nums = [1,2,2,2]
      Output: 3

      Constraints:
      1 <= nums.length <= 5 * 10^5
      -2^31 <= nums[i] <= 2^31 - 1

   Usage
        for each server name keep an allocated number array
       allocate ServerName number
          return the smaller available number, and added it to the array.
       deallocate ServerName number
         find and remove the number from the number
  */
  /*
  Idea:
     filter out not related number in [1,N]
     the existence of v is marked by make the value in v's location: index v-1  as negative.
     o no positive and negative, so use N+1 as no related number.
     also note the duplicated elments
  */
  public int firstMissingPositive1(int[] A) {
    int N = A.length;
    for (int i = 0; i < N; i++) {
      if (A[i] < 1 || A[i] > N) A[i] = N + 1;
    }
    for (int i = 0; i < N; i++) {
      int v = Math.abs(A[i]);
      if (v != N + 1 && A[v - 1] > 0) A[v - 1] *= -1;
    }
    for (int i = 0; i < N; i++) {
      if (A[i] > 0) return i + 1;
    }
    return N + 1;
  }

  /* --------------------------------------------------------------
  O(N) time, O(1) extra space
  the while loop:
    at most N time
    A[A[i] - 1] != A[i] avoid duplicated number

  */
  public int firstMissingPositive(int[] A) {
    int N = A.length;
    for (int i = 0; i < N; i++) {
      while (A[i] >= 1 && A[i] <= N && A[A[i] - 1] != A[i]) {
        swap(A, i, A[i] - 1);
      }
    }

    for (int i = 0; i < N; i++) {
      if (A[i] != i + 1) return i + 1;
    }
    return N + 1;
  }

  private void swap(int[] A, int l, int r) {
    if (l != r) {
      int x = A[l] ^ A[r];
      A[l] = x ^ A[l];
      A[r] = x ^ A[r];
    }
  }
}
