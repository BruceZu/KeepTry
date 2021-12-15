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

package queue;

import java.nio.charset.Charset;
import java.util.Deque;
import java.util.LinkedList;

public class ValidCallTimes {
  /*
  Given a double type Current Time In Second
  Check whether in the past 10 second, including current time, the call counts number less than 5 times
  Assume `cur` time value is always > previous `cur`
  */
  Deque<Double> q;

  public ValidCallTimes() {
    q = new LinkedList<>();
  }
  /*
  O(1) space, at most keep 10 element in the deque
  O(1) average time
  */
  boolean isValidCallTimes(double cur) {
    // valid range is (cur-10, cur]
    q.addLast(cur);
    while (!q.isEmpty() && q.peekFirst() <= cur - 10) q.pollFirst(); // use while
    // check size
    if (q.size() < 5) return true;
    return false;
  }

  public static void main(String[] args) {
    ValidCallTimes v = new ValidCallTimes();
    System.out.println(v.isValidCallTimes(1d));
    System.out.println(v.isValidCallTimes(2d));
    System.out.println(v.isValidCallTimes(3d));
    System.out.println(v.isValidCallTimes(4d));
    System.out.println(v.isValidCallTimes(5d));
    System.out.println(v.isValidCallTimes(6d));

    System.out.println("----");
    v = new ValidCallTimes();
    System.out.println(v.isValidCallTimes(1d));
    System.out.println(v.isValidCallTimes(2d));
    System.out.println(v.isValidCallTimes(3d));
    System.out.println(v.isValidCallTimes(4d));
    System.out.println(v.isValidCallTimes(20d));
    System.out.println(v.isValidCallTimes(21d));
  }
  // 1 2 3 4 5 6
  // T T T T F F
  // 1 2 3 4 20 21
  // T T T T T  T
}
