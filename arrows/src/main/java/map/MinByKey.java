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

package map;

import java.util.*;

public class MinByKey {
  /* Step 1----------------------------------------------------------------------------

  Records that do not contain the specified key are considered to have value 0 for the key.
  Note that keys may map to negative values!

  You should handle an empty array of records in an idiomatic way in your language of choice.
  If several records share the same minimum value for the chosen key, you may return any of them.


  assert min_by_key("a", [{"a": 1, "b": 2}, {"a": 2}]) == {"a": 1, "b": 2}
  assert min_by_key("b", [{"a": 1, "b": 2}, {"a": 2}]) == {"a": 2}
  assert min_by_key("a", [{"a": 2}, {"a": 1, "b": 2}]) == {"a": 1, "b": 2}

  assert min_by_key("a", [{}]) == {}
  assert min_by_key("b", [{"a": -1}, {"b": -1}]) == {"b": -1}
  */

  public static Map<String, Integer> minByKey(String key, List<Map<String, Integer>> records) {
    if (records == null || records.size() == 0) return new HashMap<>();
    Map<String, Integer> rMap = new HashMap<>();
    int minv = Integer.MAX_VALUE;
    for (Map<String, Integer> m : records) {
      int cv = m.containsKey(key) ? m.get(key) : 0;
      if (cv < minv) {
        rMap = m;
        minv = cv;
      }
    }
    return rMap;
  }

  /*  Step 2----------------------------------------------------------------------------

     Our next step in database development is to add a new function.
     We'll call this function first_by_key. takes three arguments:

      a string key
      a string sort direction (which must be either "asc" or "desc")
      an array of records, just as in min_by_key.

    If the sort direction is "asc", then we should return the minimum record,
    otherwise we should return the maximum record.
    As before, records without a value for the key should be treated as having value 0.
    Once you have a working solution, you should re-implement min_by_key in terms of first_by_key .

    Java function signature:

     public static Map<String, Integer> firstByKey(String key, String direction, List<Map<String, Integer>> records);


  assert first_by_key("a", "asc", [{"a": 1}]) == {"a": 1}
  assert first_by_key("a", "asc",  [{"b": 1}, {"b": -2}, {"a": 10}]) in [{"b": 1}, {"b": -2}]
  assert first_by_key("a", "desc", [{"b": 1}, {"b": -2}, {"a": 10}]) == {"a": 10}
  assert first_by_key("b", "asc",  [{"b": 1}, {"b": -2}, {"a": 10}]) == {"b": -2}
  assert first_by_key("b", "desc", [{"b": 1}, {"b": -2}, {"a": 10}]) == {"b": 1}
  assert first_by_key("a", "desc", [{}, {"a": 10, "b": -10}, {}, {"a": 3, "c": 3}]) == {"a": 10, "b": -10}

   */

  public static Map<String, Integer> firstByKey(
      String key, String direction, List<Map<String, Integer>> records) {
    // validate(direction)
    if (records == null || records.size() == 0) return new HashMap<>();
    Map<String, Integer> rMap = new HashMap<>();
    int v = direction.equals("asc") ? Integer.MAX_VALUE : Integer.MIN_VALUE;
    for (Map<String, Integer> m : records) {
      int cv = m.containsKey(key) ? m.get(key) : 0;
      if (direction.equals("asc") && cv < v || direction.equals("desc") && cv > v) {
        rMap = m;
        v = cv;
      }
    }
    return rMap;
  }
  /* ----------------------------------------------------------------------------
  Step 3
  Examples (in Python):

  cmp = RecordComparator("a", "asc")
  assert cmp.compare({"a": 1}, {"a": 2}) == -1
  assert cmp.compare({"a": 2}, {"a": 1}) == 1
  assert cmp.compare({"a": 1}, {"a": 1}) == 0
  */
  Map<String, Integer> firstByComparator(
      String key, List<Map<String, Integer>> records, String direction) {
    if (records == null || records.size() == 0) return new HashMap();
    RecordComparator c = new RecordComparator(key, direction);

    Map<String, Integer> r = null;
    for (Map<String, Integer> m : records) {
      if (r == null) r = m;
      else if (c.compare(r, m) > 0) {
        r = m;
      }
    }
    return r == null ? new HashMap<>() : r;
  }

  static class RecordComparator {
    String k;
    boolean isAscending;

    public RecordComparator(String k, String direction) {
      // validate(direction)
      this.k = k;
      this.isAscending = direction.equals("asc");
    }

    private int v(Map<String, Integer> map) {
      return map.containsKey(k) ? map.get(k) : 0;
    }

    public int compare(Map<String, Integer> a, Map<String, Integer> b) {
      int va = v(a), vb = v(b);
      if (va < vb && isAscending || va > vb && !isAscending) return -1;
      if (va == vb) return 0;
      return 1;
    }
  }
  /*
  Step 4

  Time to take this"firstby" functionality further, to sort by more than one key at a time.

  Consider an array of records like this one:

  [{“a”: 1, “b”: 1}, {“a”: 1, “b”: 2}, {“a”: 2, “b”: 1}, {“a”: 2, “b”: 2}]

  Using first_by_key with this array of records with key “a” might not give us as much control
  as we’d like over the result, since more than one record has the same value for
  "a" (similarly for “b”).

  More generally, we might say "order by the first key, in the first direction.
  Break ties according to the second key, in the second direction.
  Break remaining ties by the third key,in the third direction, and so on."

  Note that the sort direction for different keys need not be the same.
  We might represent this ordering with a list of pairs like
  [
   ("a", "asc"),
   ("b", "asc"),
   ("c", "desc"),
  ]

  We’ll call this type of representation a sort_order.
  You’ll need to write a function first_by_sort_order. It takes two arguments:
  a sort_order, an array of records

  It returns the first record according to the given sort_order.
  As before, we’ll ask that you reimplement your previous functionality (first_by_key)
  in terms of first_by_sort_order.

  Hint: can you construct a “sortorder” comparator using your comparator from the previous step?
  How might constructing a sort order comparator be helpful?

  Java function signature:

  public static Map<String, Integer> firstBySortOrder(LinkedHashMap<String, String> sortOrder, List<Map<String, Integer>> records);

  Examples (in Python):

  assert(
   first_by_sort_order(
    [("a", "desc")],
    [{"a": 5.0}, {"a": 6.0}],  ) == {"a": 6.0}
  )
  assert(
     first_by_sort_order(
      [("b", "asc"), ("a", "asc")],
      [{"a": 5,  "b": 10}, {"a": 4,  "b": 9}], ) == {"a": 4, "b": 9}
     )
  assert(
    first_by_sort_order(
     [("b", "asc"), ("a", "asc")],
     [{"a": -5, "b": 10}, {"a": -4, "b": 10}],  ) == {"a": -5, "b": 10}
    )
    )
   */

  public static Map<String, Double> firstBySortOrder(
      LinkedHashMap<String, String> sortOrder, List<Map<String, Double>> records) {

    if (records == null || records.size() == 0) return new HashMap();
    RecordComparator2 c = new RecordComparator2(sortOrder);

    Map<String, Double> r = null;
    for (Map<String, Double> m : records) {
      if (r == null) r = m;
      else if (c.compare(r, m) > 0) { // expected order is -1 or 0
        r = m;
      }
    }
    return r == null ? new HashMap<>() : r;
  }

  static class RecordComparator2 {
    LinkedHashMap<String, String> sortOrder;

    public RecordComparator2(LinkedHashMap<String, String> sortOrder) {
      this.sortOrder = sortOrder;
    }

    public int compare(Map<String, Double> a, Map<String, Double> b) {
      for (Map.Entry<String, String> e : sortOrder.entrySet()) {
        String k = e.getKey(), direction = e.getValue();
        validate(direction);
        boolean isAscending = direction.equals("asc");
        double va = a.containsKey(k) ? a.get(k) : 0, vb = b.containsKey(k) ? b.get(k) : 0;
        if (va != vb) {
          if (va < vb && isAscending || va > vb && !isAscending) return -1;
          return 1;
        }
      }
      return 0;
    }

    private void validate(String direction) {
      if (!direction.equals("asc") && !direction.equals("desc"))
        throw new RuntimeException("Direction value is invalid");
    }
  }

  public static void main(String[] args) {
    // testMinByKey();
    // testFirstByKey();
    // testFirstByComparator();
    // testFirstBySortOrder();
  }

  private static void testFirstBySortOrder() {

    LinkedHashMap<String, String> sortOrder = new LinkedHashMap<>();
    sortOrder.put("a", "desc");

    List<Map<String, Double>> records = new ArrayList<>();
    Map<String, Double> m = new HashMap<>();
    m.put("a", 5.0);
    records.add(m);

    m = new HashMap<>();
    m.put("a", 6.0);
    records.add(m);
    firstBySortOrder(sortOrder, records);

    sortOrder = new LinkedHashMap<>();
    sortOrder.put("b", "asc");
    sortOrder.put("a", "asc");

    records = new ArrayList<>();
    m = new HashMap<>();
    m.put("a", 5d);
    m.put("b", 10d);
    records.add(m);

    m = new HashMap<>();
    m.put("a", 4d);
    m.put("b", 9d);
    records.add(m);

    firstBySortOrder(sortOrder, records);

    sortOrder = new LinkedHashMap<>();
    sortOrder.put("b", "asc");
    sortOrder.put("a", "asc");
    records = new ArrayList<>();
    m = new HashMap<>();
    m.put("a", -5d);
    m.put("b", 10d);
    records.add(m);

    m = new HashMap<>();
    m.put("a", -4d);
    m.put("b", 10d);
    records.add(m);
    firstBySortOrder(sortOrder, records);
  }

  private static void testFirstByComparator() {
    RecordComparator cmp = new RecordComparator("a", "asc");
    Map<String, Integer> m = new HashMap<>(), m2 = new HashMap<>();
    m.put("a", 1);
    m2.put("a", 2);
    System.out.println(cmp.compare(m, m2) == -1);
    System.out.println(cmp.compare(m2, m) == 1);
    System.out.println(cmp.compare(m, m) == 0);
  }

  private static void testFirstByKey() {
    List<Map<String, Integer>> records = new ArrayList<>();
    Map<String, Integer> m = new HashMap<>();
    m.put("a", 1);
    records.add(m);
    firstByKey("a", "asc", records);

    records = new ArrayList<>();
    m = new HashMap<>();
    m.put("b", 1);
    records.add(m);

    m = new HashMap<>();
    m.put("b", -2);
    records.add(m);

    m = new HashMap<>();
    m.put("a", 10);
    records.add(m);
    firstByKey("a", "asc", records);
    firstByKey("a", "desc", records);
    firstByKey("b", "asc", records);
    firstByKey("b", "desc", records);

    records = new ArrayList<>();
    m = new HashMap<>();
    records.add(m);

    m = new HashMap<>();
    m.put("a", 10);
    m.put("b", -10);
    records.add(m);

    m = new HashMap<>();
    records.add(m);

    m = new HashMap<>();
    m.put("a", 3);
    m.put("b", 3);
    records.add(m);
    firstByKey("a", "desc", records);
  }

  private static void testMinByKey() {
    List<Map<String, Integer>> records = new ArrayList<>();
    Map<String, Integer> m = new HashMap<>();
    m.put("a", 1);
    m.put("b", 2);
    records.add(m);
    m = new HashMap<>();
    m.put("a", 2);
    records.add(m);
    minByKey("b", records);
    minByKey("a", records);

    records = new ArrayList<>();
    m = new HashMap<>();
    m.put("a", 2);
    records.add(m);
    m = new HashMap<>();
    m.put("a", 1);
    m.put("b", 2);
    records.add(m);
    minByKey("a", records);

    records = new ArrayList<>();
    records.add(new HashMap<>());
    minByKey("a", records);

    records = new ArrayList<>();
    m = new HashMap<>();
    m.put("a", -1);
    records.add(m);
    m = new HashMap<>();
    m.put("b", -1);
    records.add(m);
    minByKey("b", records);
  }
}
