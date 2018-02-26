//  Copyright 2018 The KeepTry Open Source Project
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

package binarysearch.utility;

public class BS {

  /**
   * @param A sorted distinct Array
   * @param key
   */
  public static int BSIndexWithLessComparisons(int[] A, int key) {
    if (A == null || A.length == 0) return -1;
    int m;
    int l = 0, r = A.length - 1;

    while (r - l > 1) { // At least 3 members
      m = l + (r - l) / 2;

      if (A[m] <= key) l = m;
      else r = m;
    }

    // only 2 members are left
    if (A[l] == key) return l;
    if (A[r] == key) return r; // maybe only 2 members in total
    else return -1;
  }

  /**
   * Find index of input 'key'. If it is not in the given array return the floor value of the input
   * ‘key’ if the floor value exists. Say, A = {-1, 2, 3, 5, 6, 8, 9, 10} and key = 7, we should
   * return 6 as outcome.
   *
   * @param A sorted distinct Array
   * @param key
   */
  public static int indexOrFloorIndex(int[] A, int key) {
    if (A == null || A.length == 0) return -1;
    if (key < A[0]) return -1; // added these 2 lines
    if (key > A[A.length - 1]) return A.length - 1;
    int m;
    int l = 0, r = A.length - 1;

    while (r - l > 1) {
      m = l + (r - l) / 2;

      if (A[m] <= key) l = m;
      else r = m;
    }

    if (A[l] == key) return l;
    if (A[r] == key) return r;
    else return l; // changed this line
  }

  /**
   * Return the floor value of the input ‘key’ if the floor value exists. Say, A = {-1, 2, 3, 5, 6,
   * 8, 9, 10} and key = 7, we should return 6 as outcome.
   *
   * @param A sorted distinct Array
   * @param key
   */
  public static int floorIndex(int[] A, int key) {
    if (A == null || A.length == 0) return -1;
    if (key <= A[0]) return -1; // Note '<='
    if (key > A[A.length - 1]) return A.length - 1;
    int m;
    int l = 0, r = A.length - 1;

    while (r - l > 1) {
      m = l + (r - l) / 2;

      if (A[m] >= key) r = m;
      else l = m;
    }
    return l;
  }

  /**
   * Return the ceil value of the input ‘key’ if the floor value exists. Say, A = {-1, 2, 3, 5, 6,
   * 8, 9, 10} and key = 7, we should return 8 as outcome.
   *
   * @param A sorted distinct Array
   * @param key
   */
  public static int ceilIndex(int[] A, int key) {
    if (A == null || A.length == 0) return -1;
    if (key >= A[A.length - 1]) return -1;
    if (key < A[0]) return 0;
    // between cases: exit key or not; need loop to cut half each time, or not(only 2 members)
    int m;
    int l = 0, r = A.length - 1;

    while (r - l > 1) {
      m = l + (r - l) / 2;

      if (A[m] <= key) l = m;
      else r = m;
    }
    return r;
  }

  /**
   * Return the left position value of the input ‘key’ . Say, A = {-1, 2, 3, 5, 5, 5, 6, 8, 9, 10}
   * and key = 5, we should return index 3 as outcome.
   *
   * @param A sorted Array with duplicated number
   * @param key
   */
  public static int leftMostIndex(int[] A, int key) {
    if (A == null || A.length == 0) return -1;
    if (A[0] > key || A[A.length - 1] < key) return -1;
    if (A[0] == key) return 0;

    int l = 0, r = A.length - 1;
    int m;
    while (r - l > 1) {
      m = (r + l) / 2;
      if (A[m] >= key) r = m; // let r move toward the left position
      else l = m;
    }
    // the r will be the key
    return r;
  }

  /**
   * Return the left position value of the input ‘key’ . Say, A = {-1, 2, 3, 5, 5, 5, 6, 8, 9, 10}
   * and key = 5, we should return index 5 as outcome.
   *
   * @param A sorted Array with duplicated number
   * @param key
   */
  public static int rightMostIndex(int[] A, int key) {
    if (A == null || A.length == 0) return -1;
    if (A[0] > key || A[A.length - 1] < key) return -1;
    if (A[A.length - 1] == key) return A.length - 1;

    int l = 0, r = A.length - 1;
    int m;
    while (r - l > 1) {
      m = (r + l) / 2;
      if (A[m] <= key) l = m; // let l move toward the right position
      else r = m;
    }
    // the l will be the key
    return l;
  }

  /**
   * Return the number of value of the input ‘key’ . Say, A = {-1, 2, 3, 5, 5, 5, 6, 8, 9, 10} and
   * key = 5, we should return 3 as outcome.
   *
   * @param A sorted Array with duplicated number
   * @param key
   */
  public static int countOccurances(int[] A, int key) {
    int l;
    if ((l = leftMostIndex(A, key)) == -1) return 0;
    else return rightMostIndex(A, key) - l + 1;
  }

  /**
   * Given a sorted array of distinct elements, and the array is rotated at an unknown position.
   * Find minimum element in the array.
   */
  public static int indexOfMinimumRotatedArray(int A[]) {
    if (A == null || A.length == 0) return -1;
    if (A[0] <= A[A.length - 1]) return 0; // no rotation
    int l = 0, r = A.length - 1;
    int m;
    while (r - l > 1) {
      m = (l + r) / 2;
      if (A[m] > A[r]) l = m;
      else r = m; // They are distinct. NO == relation
    }
    // only right boundary and left boundary are left; [max, min]
    return r;
  }

  public static void main(String[] args) {

    System.out.println(BSIndexWithLessComparisons(null, 5) == -1);
    System.out.println(BSIndexWithLessComparisons(new int[] {}, 5) == -1);
    System.out.println(BSIndexWithLessComparisons(new int[] {1}, 5) == -1);
    System.out.println(BSIndexWithLessComparisons(new int[] {7}, 5) == -1);
    System.out.println(BSIndexWithLessComparisons(new int[] {5}, 5) == 0);
    System.out.println(BSIndexWithLessComparisons(new int[] {5, 7, 9}, 5) == 0);
    System.out.println(BSIndexWithLessComparisons(new int[] {2, 3, 5}, 5) == 2);
    System.out.println(BSIndexWithLessComparisons(new int[] {5, 7}, 5) == 0);
    System.out.println(BSIndexWithLessComparisons(new int[] {2, 5}, 5) == 1);
    // key in
    System.out.println(BSIndexWithLessComparisons(new int[] {1, 5, 7}, 5) == 1);
    System.out.println(BSIndexWithLessComparisons(new int[] {1, 2, 5, 7}, 5) == 2);
    System.out.println(BSIndexWithLessComparisons(new int[] {1, 5, 7, 9}, 5) == 1);
    // key not in
    System.out.println(BSIndexWithLessComparisons(new int[] {2, 3, 7}, 5) == -1);
    System.out.println(BSIndexWithLessComparisons(new int[] {3, 7, 9}, 5) == -1);
    System.out.println(BSIndexWithLessComparisons(new int[] {1, 7}, 5) == -1);

    // indexOrFloorIndex
    System.out.println("indexOrFloorIndex====");
    System.out.println(indexOrFloorIndex(null, 5) == -1);
    System.out.println(indexOrFloorIndex(new int[] {}, 5) == -1);
    System.out.println(indexOrFloorIndex(new int[] {1}, 5) == 0);
    System.out.println(indexOrFloorIndex(new int[] {7}, 5) == -1);
    System.out.println(indexOrFloorIndex(new int[] {5}, 5) == 0);
    System.out.println(indexOrFloorIndex(new int[] {5, 7, 9}, 5) == 0);
    System.out.println(indexOrFloorIndex(new int[] {2, 3, 5}, 5) == 2);
    System.out.println(indexOrFloorIndex(new int[] {5, 7}, 5) == 0);
    System.out.println(indexOrFloorIndex(new int[] {2, 5}, 5) == 1);
    // key in
    System.out.println(indexOrFloorIndex(new int[] {1, 5, 7}, 5) == 1);
    System.out.println(indexOrFloorIndex(new int[] {1, 2, 5, 7}, 5) == 2);
    System.out.println(indexOrFloorIndex(new int[] {1, 5, 7, 9}, 5) == 1);
    // key not in
    System.out.println(indexOrFloorIndex(new int[] {2, 3, 7}, 5) == 1);
    System.out.println(indexOrFloorIndex(new int[] {3, 7, 9}, 5) == 0);
    System.out.println(indexOrFloorIndex(new int[] {1, 7}, 5) == 0);
    // floorIndex
    System.out.println("floorIndex====");
    System.out.println(floorIndex(null, 5) == -1);
    System.out.println(floorIndex(new int[] {}, 5) == -1);
    System.out.println(floorIndex(new int[] {1}, 5) == 0);
    System.out.println(floorIndex(new int[] {7}, 5) == -1);
    System.out.println(floorIndex(new int[] {5}, 5) == -1);
    System.out.println(floorIndex(new int[] {5, 7, 9}, 5) == -1);
    System.out.println(floorIndex(new int[] {2, 3, 5}, 5) == 1);
    System.out.println(floorIndex(new int[] {5, 7}, 5) == -1);
    System.out.println(floorIndex(new int[] {2, 5}, 5) == 0);
    // key in
    System.out.println(floorIndex(new int[] {1, 5, 7}, 5) == 0);
    System.out.println(floorIndex(new int[] {1, 2, 5, 7}, 5) == 1);
    System.out.println(floorIndex(new int[] {1, 5, 7, 9}, 5) == 0);
    // key not in
    System.out.println(floorIndex(new int[] {2, 3, 7}, 5) == 1);
    System.out.println(floorIndex(new int[] {3, 7, 9}, 5) == 0);
    System.out.println(floorIndex(new int[] {1, 7}, 5) == 0);

    // ceilIndex
    System.out.println("ceilIndex====");
    System.out.println(ceilIndex(null, 5) == -1);
    System.out.println(ceilIndex(new int[] {}, 5) == -1);
    System.out.println(ceilIndex(new int[] {1}, 5) == -1);
    System.out.println(ceilIndex(new int[] {7}, 5) == 0);
    System.out.println(ceilIndex(new int[] {5}, 5) == -1);
    System.out.println(ceilIndex(new int[] {5, 7, 9}, 5) == 1);
    System.out.println(ceilIndex(new int[] {2, 3, 5}, 5) == -1);
    System.out.println(ceilIndex(new int[] {5, 7}, 5) == 1);
    System.out.println(ceilIndex(new int[] {2, 5}, 5) == -1);
    // key in
    System.out.println(ceilIndex(new int[] {1, 5, 7}, 5) == 2);
    System.out.println(ceilIndex(new int[] {1, 2, 5, 7}, 5) == 3);
    System.out.println(ceilIndex(new int[] {1, 5, 7, 9}, 5) == 2);
    // key not in
    System.out.println(ceilIndex(new int[] {2, 3, 7}, 5) == 2);
    System.out.println(ceilIndex(new int[] {3, 7, 9}, 5) == 1);
    System.out.println(ceilIndex(new int[] {1, 7}, 5) == 1);

    // leftMostIndex
    System.out.println("leftMostIndex ====");
    System.out.println(leftMostIndex(null, 5) == -1);
    System.out.println(leftMostIndex(new int[] {}, 5) == -1);
    // not in
    System.out.println(leftMostIndex(new int[] {1}, 5) == -1);
    System.out.println(leftMostIndex(new int[] {1, 2, 3}, 5) == -1);
    System.out.println(leftMostIndex(new int[] {6, 7, 8}, 5) == -1);
    // in
    System.out.println(leftMostIndex(new int[] {5}, 5) == 0);
    System.out.println(leftMostIndex(new int[] {1, 2, 5}, 5) == 2);
    System.out.println(leftMostIndex(new int[] {5, 6, 7}, 5) == 0);
    System.out.println(leftMostIndex(new int[] {3, 5, 7}, 5) == 1);
    // duplicated
    System.out.println(leftMostIndex(new int[] {5, 5, 9}, 5) == 0);
    System.out.println(leftMostIndex(new int[] {2, 5, 5}, 5) == 1);

    System.out.println(leftMostIndex(new int[] {1, 2, 5, 5, 5, 5, 5, 5}, 5) == 2);
    System.out.println(leftMostIndex(new int[] {5, 5, 5, 5, 5, 5, 7, 8}, 5) == 0);
    System.out.println(leftMostIndex(new int[] {1, 2, 5, 5, 5, 5, 5, 5, 7, 8}, 5) == 2);
    // no loop
    System.out.println(leftMostIndex(new int[] {5, 9}, 5) == 0);
    System.out.println(leftMostIndex(new int[] {2, 5}, 5) == 1);

    // rightMostIndex
    System.out.println("rightMostIndex ====");
    System.out.println(rightMostIndex(null, 5) == -1);
    System.out.println(rightMostIndex(new int[] {}, 5) == -1);
    // not in
    System.out.println(rightMostIndex(new int[] {1}, 5) == -1);
    System.out.println(rightMostIndex(new int[] {1, 2, 3}, 5) == -1);
    System.out.println(rightMostIndex(new int[] {6, 7, 8}, 5) == -1);
    // in
    System.out.println(rightMostIndex(new int[] {5}, 5) == 0);
    System.out.println(rightMostIndex(new int[] {1, 2, 5}, 5) == 2);
    System.out.println(rightMostIndex(new int[] {5, 6, 7}, 5) == 0);
    System.out.println(rightMostIndex(new int[] {3, 5, 7}, 5) == 1);
    // duplicated
    System.out.println(rightMostIndex(new int[] {5, 5, 9}, 5) == 1);
    System.out.println(rightMostIndex(new int[] {2, 5, 5}, 5) == 2);

    System.out.println(rightMostIndex(new int[] {1, 2, 5, 5, 5, 5, 5, 5}, 5) == 7);
    System.out.println(rightMostIndex(new int[] {5, 5, 5, 5, 5, 5, 7, 8}, 5) == 5);
    System.out.println(rightMostIndex(new int[] {1, 2, 5, 5, 5, 5, 5, 5, 7, 8}, 5) == 7);
    // no loop
    System.out.println(rightMostIndex(new int[] {5, 9}, 5) == 0);
    System.out.println(rightMostIndex(new int[] {2, 5}, 5) == 1);

    // countOccurances
    System.out.println("countOccurances ====");
    System.out.println(countOccurances(null, 5) == 0);
    System.out.println(countOccurances(new int[] {}, 5) == 0);
    // not in
    System.out.println(countOccurances(new int[] {1}, 5) == 0);
    System.out.println(countOccurances(new int[] {1, 2, 3}, 5) == 0);
    System.out.println(countOccurances(new int[] {6, 7, 8}, 5) == 0);
    // in
    System.out.println(countOccurances(new int[] {5}, 5) == 1);
    System.out.println(countOccurances(new int[] {1, 2, 5}, 5) == 1);
    System.out.println(countOccurances(new int[] {5, 6, 7}, 5) == 1);
    System.out.println(countOccurances(new int[] {3, 5, 7}, 5) == 1);
    // duplicated
    System.out.println(countOccurances(new int[] {5, 5, 9}, 5) == 2);
    System.out.println(countOccurances(new int[] {2, 5, 5}, 5) == 2);

    System.out.println(countOccurances(new int[] {1, 1, 2, 5, 5, 5, 5, 5, 5}, 5) == 6);
    System.out.println(countOccurances(new int[] {5, 5, 5, 5, 5, 5, 7, 8}, 5) == 6);
    System.out.println(countOccurances(new int[] {1, 2, 2, 5, 5, 5, 5, 5, 5, 7, 8}, 5) == 6);
    // no loop
    System.out.println(countOccurances(new int[] {5, 9, 9}, 5) == 1);
    System.out.println(countOccurances(new int[] {2, 2, 5}, 5) == 1);

    // indexOfMinimumRotatedArray
    System.out.println("indexOfMinimumRotatedArray ====");
    System.out.println(indexOfMinimumRotatedArray(null) == -1);
    System.out.println(indexOfMinimumRotatedArray(new int[] {}) == -1);
    System.out.println(indexOfMinimumRotatedArray(new int[] {1}) == 0);
    System.out.println(indexOfMinimumRotatedArray(new int[] {1, 2, 3}) == 0);

    System.out.println(indexOfMinimumRotatedArray(new int[] {7, 8, 6}) == 2);
    System.out.println(indexOfMinimumRotatedArray(new int[] {8, 6, 7}) == 1);
    System.out.println(indexOfMinimumRotatedArray(new int[] {2, 3, 5, 0, 1}) == 3);
    System.out.println(indexOfMinimumRotatedArray(new int[] {4, 5, 0, 1, 2}) == 2);
  }
}
