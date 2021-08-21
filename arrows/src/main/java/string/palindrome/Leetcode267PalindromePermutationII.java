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

package string.palindrome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
267. Palindrome Permutation II

  Given a string s, return all the palindromic permutations (without duplicates) of it.
  return the answer in any order. If s has no palindromic permutation, return an empty list.

    1 <= s.length <= 16
    s consists of only lowercase English letters.

   Given s = "aabb", return ["abba", "baab"].
   Given s = "abc", return [].

 Hint:
 If a palindromic permutation exists, we just need to generate the first half of the string.
 To generate all distinct permutations of a (half of) string, use a similar approach
 from: `Permutations II` or `Next Permutation`.
*/
/*
 Idea:
 "a b a b a c a b b c 3 3 4 "

  1.  sort and get the left side   "3  a  a  b  b  c "
     if find pair, keep one.
     if length is odd and find single char, allocate it to middle.
     (if string length is odd and there is more one single char it is invalid)

             0  1  2  3  4  5  6  7  8  9 10 11 12
            "3  a  a  b  b  c  4  ................"
                               |
                             13/2

     kept the middle character till the loop is over, then allocate it to the middle index
     avoiding break the pairs in the loop

 2.  check duplicated:
     now the arr left is sorted.
     need a set to check if current choice is used, see  Leetcode47PermutationsII2
     because this is the left half, so at least assume  the arr length is 2
 3. once got a permutation of left, mirror -> right side.

 O((N/2)!) time. Used for permutation
 O(N) space
 */
public class Leetcode267PalindromePermutationII {
  public List<String> generatePalindromes(String str) {
    List r = new ArrayList();
    if (str.length() == 1) {
      r.add(str);
      return r;
    }

    char[] a = str.toCharArray();
    int size = 0;
    Character middle = null;
    boolean oddArrMiddleFound = false;

    Arrays.sort(a); // "ababacabbc334" sort -> "334aaaabbbbcc"
    for (int i = 0; i <= a.length - 1; i++) {
      char ci = a[i];
      if (i < a.length - 1 && ci == a[i + 1]) {
        a[size++] = ci;
        i++;
        continue;
      }
      if ((a.length & 1) == 1 && !oddArrMiddleFound) {
        middle = ci;
        oddArrMiddleFound = true;
        continue;
      }
      return r; // can not form palindrome
    }
    // "334aaaabbbbcc" -> "3aabbc......", middle is `4`
    if ((a.length & 1) == 1) {
      a[a.length / 2] = middle;
    }
    // "3aabbc4......"
    dfs(0, a, r);
    return r;
  }

  private void dfs(int curIndex, char[] a, List r) {
    if (curIndex == a.length / 2 - 1) {
      mirror(a);
      r.add(new String(a));
      return;
    }

    //  "3aabbc4......"
    // used chars/choices for current position of permutation
    // select choices from [current index, a.length / 2 - 1]
    Set<Character> used = new HashSet(a.length / 2 - 1 - curIndex + 1);
    for (int i = curIndex; i <= a.length / 2 - 1; i++) {
      char ci = a[i];
      if (!used.contains(ci)) {
        used.add(ci);
        swap(a, curIndex, i);
        dfs(curIndex + 1, a, r);
        swap(a, curIndex, i);
      }
    }
  }

  private void mirror(char[] arr) {
    int i = 0, j = arr.length - 1;
    while (i < j) {
      arr[j] = arr[i];
      i++;
      j--;
    }
  }

  private void swap(char[] arr, int i, int j) {
    if (i != j && arr[i] != arr[j]) {
      int t = arr[i] ^ arr[j];
      arr[j] ^= t;
      arr[i] ^= t;
    }
  }

  /*---------------------------------------------------------------------------
  Idea:
    use a set to kee result  to remove duplicated
    use an independent canPermutePalindrome() to check the possibility to form palindrome
   */

  Set<String> r = new HashSet<>();

  public List<String> generatePalindromes_(String s) {
    int[] f = new int[128];
    char[] half = new char[s.length() / 2];
    if (!canPermutePalindrome(s, f)) return new ArrayList<>();
    char middleChar = 0;
    int k = 0;
    for (int i = 0; i < f.length; i++) {
      if (f[i] % 2 == 1) middleChar = (char) i;
      for (int j = 0; j < f[i] / 2; j++) half[k++] = (char) i;
    }
    dfs(half, 0, middleChar);
    return new ArrayList<>(r);
  }

  public boolean canPermutePalindrome(String s, int[] f) {
    int oddCount = 0;
    for (int i = 0; i < s.length(); i++) {
      f[s.charAt(i)]++;
      if (f[s.charAt(i)] % 2 == 0) oddCount--;
      else oddCount++;
    }
    return oddCount <= 1;
  }

  void dfs(char[] half, int p, char mchar) {
    if (p == half.length) {
      r.add(
          new String(half)
              + (mchar == 0 ? "" : mchar)
              + new StringBuffer(new String(half)).reverse());
    } else {
      for (int i = p; i < half.length; i++) {
        if (half[i] != half[p] || i == p) {
          swap(half, p, i);
          dfs(half, p + 1, mchar);
          swap(half, p, i);
        }
      }
    }
  }
}
