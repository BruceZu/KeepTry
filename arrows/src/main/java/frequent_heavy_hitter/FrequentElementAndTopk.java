//  Copyright 2022 The KeepTry Open Source Project
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

package frequent_heavy_hitter;

import java.util.*;

public class FrequentElementAndTopk {

  /*
   Boyer and Moore algorithm
   The majority number of a given array.

   Assume
   - the array at least 2 elements
   - the array value range does not include MIN_VALUE
   - return MIN_VALUE if there is not majority element
  */
  public static int BoyerMooreMajority(int[] A) {
    if (A == null || A.length <= 1) throw new RuntimeException("Invalid input");
    Integer v = null;
    int f = 0;
    for (int n : A) {
      if (v != null && Integer.compare(v, f) == 0) f++;
      else {
        if (v == null) {
          v = n;
          f = 1;
        } else {
          f--;
          if (f == 0) v = null;
        }
      }
    }
    f = 0;
    for (int n : A) {
      if (n == v) f++;
    }
    if (f > A.length / 2) return v;
    return Integer.MIN_VALUE; // No majority number
  }
  /*
   Misra–Gries algorithm
    calculate all number whose frequency > n/k

    After the first pass over the finite array, every item which occurs
    more than Math.floor(n/k) times is guaranteed to appear in the output map.
    But not all item in the output map is guaranteed to be occurs more than
    Math.floor(n/k) times

     This gives a stronger accuracy guarantee when the input
     distribution is skewed, for example, if the frequencies
     follow a Zipfian distribution.

    So need the second pass over the finite array to verify the exact frequencies
    for the k−1 items

   k is partition number. k>=2
   At least run 2 pass over the finite array or using
   Count min sketch provided estimated number

   O(N) time
   O(k) space
  */
  private static Set<Integer> kMajority(int[] A, int k) {
    if (k <= 1) return new HashSet<>();
    int i = 0;
    Map<Integer, Integer> map = new HashMap<>(); // candidate numbers

    while (i < A.length) {
      int v = A[i];
      if (map.containsKey(v)) map.put(v, map.get(v) + 1);
      else if (map.size() < k - 1) map.put(v, 1); // not full: take place
      else { // full: decrease
        // java.util.ConcurrentModificationException
        Set<Integer> remove = new HashSet<>();
        for (int n : map.keySet()) {
          map.put(n, map.get(n) - 1);
          if (map.get(n) == 0) remove.add(n);
        }
        for (int key : remove) {
          map.remove(key);
        }
        // delete but not take the place. k*N/k+N%k = N
        //  if (map.size() < k - 1) map.put(v, 1);
      }
      i++;
    }

    System.out.println("candidates:" + map.entrySet());
    // verify the candidates: each candidate number accurate frequency
    Map<Integer, Integer> cnt = new HashMap<>();
    for (int v : A) {
      if (map.containsKey(v)) cnt.put(v, cnt.getOrDefault(v, 0) + 1);
    }

    // filter out
    Set<Integer> ans = new HashSet<>();
    for (int key : cnt.keySet()) {
      if (cnt.get(key) >= A.length / k + 1) ans.add(key);
    }

    return ans;
  }
  /*
  SpaceSaving algorithm
  StreamSummary data structure: like LFU without the kv map.

  Here use a simple data structure:
   map< key: frequency> nf
   map<frequency: key set> fNset
                            If use TreeMap, the time is O(N*logK)
                            So use: Map + min frequency => time O(N). like LFU, but without the kv map
                            still the keySet not use LinkedHashSet=>keep insert order or
                            (fi-\epsilon_i) in descending order
   map<key: \epsilon> nMinf
   int minF maxF: tracing min and max frequency
  */
  public static List<Integer> spaceSavingAlgorithm(int[] stream, int moniterNumber) {
    if (moniterNumber == 0 || stream == null || stream.length == 0) return new LinkedList<>();

    Map<Integer, Integer> nf = new HashMap<>();

    Map<Integer, Set<Integer>> fNset = new HashMap<>();
    int minF = 0, maxF = 0;

    Map<Integer, Integer> nMinf = new HashMap<>(); // usage ??

    for (int n : stream) { // o(N) time, N is stream length
      if (nf.containsKey(n)) { // O(1) time
        int f = nf.get(n);
        nf.put(n, f + 1);

        fNset.computeIfAbsent(f + 1, x -> new LinkedHashSet<>()).add(n);
        maxF = Math.max(maxF, f + 1);
        fNset.get(f).remove(n);
        if (fNset.get(f).isEmpty()) {
          fNset.remove(f);
          if (minF == f) minF++;
        }
      } else {
        if (nf.size() < moniterNumber) { // o(1) time
          nf.put(n, 1);

          fNset.computeIfAbsent(1, X -> new LinkedHashSet<>()).add(n);
          minF = 1;

          nMinf.put(n, 0);
        } else { // o(1) time
          int removedN = fNset.get(minF).iterator().next(); // pick any one in the set O(1) time

          nf.remove(removedN);
          nf.put(n, minF + 1);

          nMinf.remove(removedN);
          nMinf.put(n, minF);

          fNset.computeIfAbsent(minF + 1, x -> new HashSet<>()).add(n);
          maxF = Math.max(maxF, minF + 1);
          fNset.get(minF).remove(removedN);

          if (fNset.get(minF).isEmpty()) {
            fNset.remove(minF); // use minF before change it.
            minF++;
          }
        }
      }
    }
    System.out.println("number and cnt: " + nf.entrySet());
    List<Integer> ans = new LinkedList<>();

    for (int f = maxF; f >= minF; f--) { // O(maxF-minF) time
      if (fNset.containsKey(f)) {
        System.out.println(fNset.get(f) + ":cnt " + f);
        ans.addAll(fNset.get(f));
      }
    }
    System.out.println("answer in descending:" + ans);
    return ans;
  }

  public static void main(String[] args) {
    System.out.println(kMajority(new int[] {9, 9, 9, 9, 1, 2, 3, 4, 5, 6, 7}, 2));
    System.out.println(kMajority(new int[] {9, 9, 9, 9, 1, 2, 3, 4, 5, 6, 7}, 3));
    System.out.println(kMajority(new int[] {9, 9, 9, 8, 8, 8, 7, 7, 1, 2, 3}, 5));
    System.out.println(BoyerMooreMajority(new int[] {9, 9, 9, 8, 8, 8, 7, 7, 1, 2, 3}));
    System.out.println(BoyerMooreMajority(new int[] {1, 2, 1}));
    System.out.println(BoyerMooreMajority(new int[] {1, 2}));
    /*
     This gives a stronger accuracy guarantee when the input
     distribution is skewed, for example, if the frequencies
     follow a Zipfian distribution.
    */
    System.out.println("==");
    System.out.println(
        kMajority(new int[] {1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 6, 7, 8, 9, 10}, 8));
    System.out.println(
        kMajority(new int[] {5, 6, 7, 8, 9, 10, 4, 4, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 1}, 8));
    System.out.println("=saving space algorithm=");
    spaceSavingAlgorithm(
        new int[] {1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}, 8);
    spaceSavingAlgorithm(
        new int[] {1, 3, 1, 4, 1, 2, 8, 2, 2, 1, 10, 3, 4, 1, 5, 6, 7, 2, 9, 3, 11, 12, 13, 14}, 8);
    spaceSavingAlgorithm(
        new int[] {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 4, 4, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 1}, 8);

    spaceSavingAlgorithm(new int[] {1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 3);
    spaceSavingAlgorithm(new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 1, 1, 1, 1}, 3);
  }
}
