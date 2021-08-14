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

package array;

import java.util.HashMap;
import java.util.Map;

public class PokerScore {
  /*
  Return score of cards
  Assume each card value is valid:
      cards[i] is one of '2','3','4','5','6','7','8','9','10','J','Q','K','A'
      duplicated card number <=4
      cards.length <=42
  Rules used to calculate score of cards
   -A any continuous cards score with number >=5 is greater than that of any 3 cards with same value
   -B any 3 cards score with same value is greater than that of any 2 cards with same value
   -C any 2 cards score with same value is greater than that of any card value.
   -D card A score is greater than that of card 'K'
  O(N) time, space
  */
  private int score(String[] cards) {
    int[] v = new int[15];
    for (String c : cards) {
      switch (c) {
        case "A":
          v[14]++;
          break;
        case "K":
          v[13]++;
          break;
        case "Q":
          v[12]++;
          break;
        case "J":
          v[11]++;
          break;
        default:
          v[Integer.valueOf(c)]++;
      }
    }
    // Rule A
    int startOfContinues = -1, i = 0, len = 0;
    while (i < v.length) {
      while (i < v.length && v[i] == 0) i++;
      int start = i;
      while (i < v.length && v[i] != 0) i++;
      int curLen = i - start;
      if (curLen >= 5 && curLen > len) {
        len = curLen;
        startOfContinues = start;
      }
    }
    if (len != 0) return 14 * (len - 1) + startOfContinues;
    // Rule B,C,D
    int maxScore = 0;
    for (int j = 0; j < v.length; j++) maxScore = Math.max(maxScore, 14 * (v[i] - 1) + i);
    return maxScore;
  }

  /*
  Step 1: min_by_key

  assert min_by_key("a", [{"a": 1, "b": 2}, {"a": 2}]) == {"a": 1, "b": 2}
  assert min_by_key("a", [{"a": 2}, {"a": 1, "b": 2}]) == {"a": 1, "b": 2}
  assert min_by_key("b", [{"a": 1, "b": 2}, {"a": 2}]) == {"a": 2}
  assert min_by_key("a", [{}]) == {}
  assert min_by_key("b", [{"a": -1}, {"b": -1}]) == {"b": -1}


  Step 2: first_by_key
  assert first_by_key("a", "asc", [{"a": 1}]) == {"a": 1}
  assert first_by_key("a", "asc", [{"b": 1}, {"b": -2}, {"a": 10}]) in [{"b": 1}, {"b": -2}]
  assert first_by_key("a", "desc", [{"b": 1}, {"b": -2}, {"a": 10}]) == {"a": 10}
  assert first_by_key("b", "asc", [{"b": 1}, {"b": -2}, {"a": 10}]) == {"b": -2}
  assert first_by_key("b", "desc", [{"b": 1}, {"b": -2}, {"a": 10}]) == {"b": 1}
  assert first_by_key("a", "desc", [{}, {"a": 10, "b": -10}, {}, {"a": 3, "c": 3}]) == {"a": 10, "b": -10}

  Step 3
  Examples (in Python):

  cmp = RecordComparator("a", "asc")
  assert cmp.compare({"a": 1}, {"a": 2}) == -1
  assert cmp.compare({"a": 2}, {"a": 1}) == 1
  assert cmp.compare({"a": 1}, {"a": 1}) == 0
  */
  static class C {
    String k;
    boolean isAscending;

    public C(String k, boolean isAscending) {
      this.k = k;
      this.isAscending = isAscending;
    }

    private int v(Map<String, Integer> map) {
      if (!map.containsKey(k)) return 0;
      int r = isAscending ? Integer.MAX_VALUE : Integer.MIN_VALUE;
      for (Map.Entry<String, Integer> e : map.entrySet())
        if (e.getKey().equals(k))
          r = isAscending ? Math.min(r, e.getValue()) : Math.max(r, e.getValue());
      return r;
    }

    public int compare(Map<String, Integer> a, Map<String, Integer> b) {
      int va = v(a), vb = v(b);
      if (va < vb && isAscending || va > vb && !isAscending) return -1;
      if (va == vb) return 0;
      return 1;
    }
  }

  Map<String, Integer> first_by_key(String key, Map<String, Integer>[] arr, boolean isAscending) {
    if (arr == null || arr.length == 0) return new HashMap();
    int r = 0;
    C c = new C(key, isAscending);
    for (int i = 1; i < arr.length; i++) {
      int cr = c.compare(arr[r], arr[i]);
      if (cr < 0) r = i;
    }
    return arr[r];
  }
}
