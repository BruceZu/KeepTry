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

public class Leetcode132PalindromePartitioningII {
  /*
    132. Palindrome Partitioning II
    Given a string s, partition s such that every substring of the partition is a palindrome.
    Return the minimum cuts needed for a palindrome partitioning of s.


    Input: s = "aab"
    Output: 1
    The palindrome partitioning ["aa","b"] could be produced using 1 cut.

    Input: s = "a"
    Output: 0

    Input: s = "ab"
    Output: 1

    Constraints:
    1 <= s.length <= 2000
    s consists of lower-case English letters only.
  */
  /*---------------------------------------------------------------------------
       Idea
         Idea comes from 131 backtracking. watch and find there is overlap which can be cached in memory.
         if a recursive problem computes and solves the same subproblem multiple times,
         it has an Overlapping Subproblem property. Such problems can be optimized using a DP technique called Memoization.
         E.g.: "abcd"
         backtracking:
         a|b|c|d
         a|b|c d
         a|b c|d  here the min of `d` has been calculated in above.
         a|b c d
         a b|c|d
         a b|c d  here the min of `cd` has been calculated in above.
         a b c|d
         a b c d: here when checking whether `abcd` is palindrome, need to know `bc` is palindrome or not
         - watch and find the overlap is calculating d,c,cd,bc is palindrome. basic insight of
           dynamic programming is to compute the result of a problem, we must first compute the
           results of its subproblems. E.g. to find the minimum number of cuts for the string aab,
           we must know the result for all the substrings a, aa, ab, and so on.
         - Moreover, we can observe that the same optimization can be also applied to Find Minimum Cut
   O(N^2) time
   calculating the results for any substring only once.
   a string size N  has N^2 possible substrings.
   within each recursive call, we are also checking if a substring is palindrome or not: O(1) time

   O(N^2) space

   The dfs/backtracking is top down + cache. finding the result for the original string aab and
   recursively moved towards computing the result of smaller subproblems like a, aa, ab.

   This can be done `right to left`/`bottom up`. compute the results of all the subproblems
   before moving towards the larger problem. As the name suggests, the results of subproblems
   are stored in a table which can be referred to in the future for computing the result of
   a larger problem. This can be implemented using a nested loop.

   for(l=length-1;i>=0;i--){
     for(r=l;r<=length-1;r++){
      //
     }
   }
   or
   for(r=0;i<=length-1 ;i++){
     for(l=r;r>=0;r--){
      //
     }
   }
  */
  // Use Integer and Boolean, thus use  null to represent no value yet.
  private Integer min[];
  // need 1d array is enough, min[i] is the min cuts of
  // suffix sub-array [i, s.length() - 1]
  private Boolean is[][];

  public int minCut___(String s) {
    int N = s.length();
    min = new Integer[N];
    is = new Boolean[N][N];
    return findMinimumCut(s, 0);
  }

  // v is minimum value
  private int findMinimumCut(String s, int l) {
    if (min[l] != null) return min[l];
    int v = s.length() - l + 1;
    for (int r = l; r <= s.length() - 1; r++) {
      if (isPalindrome(s, l, r)) {
        v = r == s.length() - 1 ? 0 : Math.min(v, 1 + findMinimumCut(s, r + 1));
      }
    }
    return min[l] = v;
  }

  private boolean isPalindrome(String s, int l, int r) {
    if (is[l][r] != null) return is[l][r];
    return is[l][r] = s.charAt(l) == s.charAt(r) && (r - l <= 2 || is[l + 1][r - 1]);
  }

  /*
  Idea
   1> calculate any sub-array is palindrome or not
   2> "aabb":
       aab|b : 2
       aa|bb : 1
       a|abb : x
       |aabb : x
  O(N^2) time and space
  */
  public int minCut__(String s) {
    int N = s.length();
    boolean is[][] = new boolean[N][N]; // default false
    for (int r = 0; r < N; r++)
      for (int l = r; 0 <= l; l--)
        if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || is[l + 1][r - 1])) {
          is[l][r] = true;
        }

    int min[] = new int[N]; // min[i] keep min value for prefix [0,i] init value is length -1
    for (int r = 0; r < N; r++) min[r] = r;

    for (int r = 0; r < N; r++)
      for (int l = r; 0 <= l; l--)
        if (is[l][r]) {
          min[r] = l == 0 ? 0 : Math.min(min[r], min[l - 1] + 1);
        }
    return min[N - 1];
  }

  /*---------------------------------------------------------------------------
  Idea
   merge together
   */
  public int minCut_(String s) {
    int N = s.length();
    int min[] = new int[N];
    boolean is[][] = new boolean[N][N];

    for (int r = 0; r < N; r++) {
      int v = r; // init value is length -1
      for (int l = r; 0 <= l; l--) { // it is okay use   (int l = 0; l <= r; l++)
        if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || is[l + 1][r - 1])) {
          is[l][r] = true;
          v = l == 0 ? 0 : Math.min(v, min[l - 1] + 1);
        }
      }
      min[r] = v;
    }
    return min[N - 1];
  }

  /*---------------------------------------------------------------------------
  Idea
   palindromic string mirrors around the center.
   even length:  `aabbaa`
   odd  length:  `aabcbaa`

   where is the center and how to expand?
   If only care how to expand, then need not to use special/virtual char within chars in original string.
   instead, just expand twice:
   - expand both `l` and `r` index from current `i`  till char at index l is not char at index r
   - expand both `l` and `r` index from current `i-1`, `i` when `i>0` till ...
    min[r] = l == 0 ? 0 : Math.min(min[r], min[l - 1] + 1);
   `aab`, `efe`

   O(N^2)time. inner loop at most N/2 iterator
   O(N) space
  */
  public int minCut(String s) {
    int N = s.length();
    int min[] = new int[N];
    for (int i = 0; i < s.length(); i++) min[i] = i;
    //  min[0]=0;
    for (int i = 1; i < s.length(); i++) {
      int l = i, r = i;
      expand(l, r, s, min);
      l = i - 1;
      r = i;
      expand(l, r, s, min);
    }
    return min[s.length() - 1];
  }

  private void expand(int l, int r, String s, int[] min) {
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
      min[r] = l == 0 ? 0 : Math.min(min[r], min[l - 1] + 1);
      l--;
      r++;
    }
  }
}
