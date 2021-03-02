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

package stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Leetcode1776CarFleetII {

  // O(N）time and space. each car is visited once, and is pushed in and poped out from the stack one
  // time.
  public static double[] getCollisionTimes(int[][] cars) {
    /*
     1 <= cars.length <= 105
     1 <= positioni, speedi <= 106
     positioni < positioni+1
    */

    int N = cars.length;
    double r[] = new double[N];
    r[N - 1] = -1d;
    // Stack keep index of car whose speed is slower but whose collision and merge time happen
    // firstly that current car.
    // In Stack one entry related car's collision target is just the next one entry related car.
    Stack<Integer> s = new Stack<Integer>();
    // Right car's collision time once is calculated, it will not be changed by the left ones.
    // So start from the right end.
    s.push(N - 1);
    for (int i = N - 2; i >= 0; i--) {
      // Current car never catch any right car which has a speed >= current one's and may
      // collide/merge into
      // a slower one. So skip all of these kind of faster cars
      // 'cars[i] = [positioni, speedi]'
      while (s.size() > 0 && cars[s.peek()][1] >= cars[i][1]) s.pop();

      // Cars with a slower speed that current car are left now. From them still skip those
      // with collision time happening and merging with a slower one firstly than current one.
      // Note: the last one is special with -1 collision time. So handle this special case with
      // - checking stack size >= 1, or
      // - checking r[s.peek()] > 0, or
      // - checking r[s.peek()] == -1 ? Integer.MAX_VALUE : r[s.peek()]
      while (s.size() > 1
          && (cars[s.peek()][0] - cars[i][0]) * 1.0d / (cars[i][1] - cars[s.peek()][1])
              > r[s.peek()]) s.pop();
      // Now left 0 or more cars with slower speed and the collision happen later than current car.
      if (s.size() > 0) {
        r[i] = (cars[s.peek()][0] - cars[i][0]) * 1.0d / (cars[i][1] - cars[s.peek()][1]);
      } else {
        r[i] = -1;
      }
      s.push(i);
      // So in Stack one entry related car's collision target is just the next one entry
      // related car.
    }
    return r;
  }

  // no comments version.
  public static double[] getCollisionTimes2(int[][] cars) {
    int N = cars.length;
    double r[] = new double[N];
    r[N - 1] = -1d;
    Stack<Integer> s = new Stack<Integer>();
    s.push(N - 1);
    for (int i = N - 2; i >= 0; i--) {
      while (s.size() > 0 && cars[s.peek()][1] >= cars[i][1]) s.pop();
      while (s.size() > 1
          && (cars[s.peek()][0] - cars[i][0]) * 1.0d / (cars[i][1] - cars[s.peek()][1])
              > r[s.peek()]) s.pop();
      if (s.size() > 0) {
        r[i] = (cars[s.peek()][0] - cars[i][0]) * 1.0d / (cars[i][1] - cars[s.peek()][1]);
      } else {
        r[i] = -1;
      }
      s.push(i);
    }
    return r;
  }
}
