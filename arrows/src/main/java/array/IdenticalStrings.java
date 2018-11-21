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

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * Please submit solutions to the following Java programming questions.
 * Feel free to use standard Java APIs in your solutions and also to compile/test
 * your code. However, the solutions should represent your own original work.
 *
 * We will be examining your solutions for correctness, efficiency, and
 * good programming practice.
 *
 * QUESTION #1:
 *
 * Write a Java method that takes an array of "sets" of String objects,
 * and determines whether _all_ sets in the array are equivalent.
 *
 * Each "set" in the input array is represented as an array of String objects, in
 * no particular order, and possibly containing duplicates. Nevertheless, when
 * determining whether two of these "sets" are equivalent, you should disregard
 * order and duplicates. For example, the sets represented by these arrays are
 * all equivalent:
 *
 *      {"a", "b"}
 *      {"b", "a"}
 *      {"a", "b", "a"}
 *
 * The signature for your method should be:
 *
 *       public static boolean allStringSetsIdentical(String[ ][ ] sets)
 *
 * Examples of the method in operation:
 *
 *      allStringSetsIdentical(new String[][] {{"a","b"},{"b","b","a"},{"b","a"}})
 *      returns true
 *
 *      allStringSetsIdentical(new String[][] {{"a","b"},{"a"},{"b"}})
 *      returns false
 */
public class IdenticalStrings {
  // As need a read only set, replace 'new HashSet<>(Arrays.asList(sets[i]))' with this method
  // to improve a bit performance .
  private static Set toSet(String[] arr) {
    Set<String> r = new HashSet<>(arr.length);
    for (String s : arr) {
      r.add(s);
    }
    return r;
  }

  /**
   * <pre>
   * Convert the first element, a String array, to a set. If there is a set represented by any other element
   * that is not same as the one converted by the first one, then all elements are not identical.
   *
   * Note: Element of the input array maybe null.
   *       This method is not thread-safe.
   *
   * @return true if the sets represented by arrays, elements of the input array,
   *         are all equivalent, else false.
   *
   *         Note: If the input array is null or the size of the input array is less than 2,
   *              this method will return true.
   */
  public static boolean allStringSetsIdentical(String[][] arrays) {
    if (null == arrays || arrays.length <= 1) {
      return true;
    }
    if (null == arrays[0]) {
      for (int i = 1; i < arrays.length; i++) {
        if (null != arrays[i]) {
          return false;
        }
      }
      return true;
    }
    Set<String> set = toSet(arrays[0]);
    for (int i = 1; i < arrays.length; i++) {
      if (null == arrays[i] || !set.equals(toSet(arrays[i]))) {
        return false;
      }
    }
    return true;
  }
}
