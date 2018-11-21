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

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @see <a href
 *     ="https://www.hackerrank.com/contests/citrix-code-master/challenges/failing-tests/submissions/code/6963408">hacker
 *     rank</a>
 */
public class HackerrankFailingTest {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    calculate(s);
  }

  private static void calculate(Scanner s) {
    // O(Nlog5) ?
    PriorityQueue<Float> minums =
        new PriorityQueue<Float>(
            (Float o1, Float o2) -> {
              return -(o1).compareTo(o2); // descending
            }) {
          @Override
          public boolean offer(Float o) {
            if (this.size() < 5) {
              return super.offer(o);
            } else { // ==5
              if (o < this.peek()) {
                this.poll();
                return super.offer(o);
              } else {
                return true;
              }
            }
          }
        };
    // parsing input
    float cur;
    while (true) {
      try {
        cur = s.nextFloat();
        if (cur == -1) { // this is for debug in IDEA
          break;
        }
        minums.offer(cur);
      } catch (Exception e) {
        break;
      }
    }
    // print
    String[] rs = new String[5];
    for (int i = 0;
        i <= 4;
        i++) { // It is said inputs contain at least five values, and no inputs extend beyond 6 decimals.
      float f = minums.poll().floatValue();
      int digit = (int) f;
      int decimal = (int) (f * 1000000 - digit * 1000000);
      StringBuilder r = new StringBuilder();
      r.append(digit).append(".");

      int decimalLength = String.valueOf(decimal).length();
      if (decimalLength < 6) { //care . e.g.  34.000990
        for (int j = 1; j <= 6 - decimalLength; j++) {
          r.append("0");
        }
      }
      rs[i] = r.append(decimal).toString();
    }
    for (int i = 4; i >= 0; i--) {
      System.out.println(rs[i]);
    }
  }

  // numberOfCategory =0: append
  // numberOfCategory = 1,2,3,4
  //     cur >= last one: append
  //     cur <last one: insert sort
  // numberOfCategory == 5
  //     cur >= last one: ignore
  //     cur < last one : insert sort
  private static void calculate2(Scanner s) {
    Float[] minimum = new Float[5]; // ascending
    int size = 0;
    while (true) {
      float cur;
      try {
        cur = s.nextFloat();
      } catch (Exception e) {
        break;
      }
      if (size == 0 || cur >= minimum[size - 1] && size < 5) { // care
        minimum[size++] = cur;
      } else if (cur < minimum[size - 1]) { // insert sort O(n*5)
        int insertTo = -1;
        for (int i = 0; i < size; i++) {
          if (cur < minimum[i]) {
            insertTo = i;
            break; //care
          }
        }
        // move
        if (size < 5) {
          for (int j = size - 1; j >= insertTo; j--) {
            minimum[j + 1] = minimum[j];
          }
          minimum[insertTo] = cur;
          size++;
        } else { // numberOfCategory =5
          for (int j = size - 1; j >= insertTo + 1; j--) {
            minimum[j] = minimum[j - 1];
          }
          minimum[insertTo] = cur;
        }
      }
    }
    print(minimum);
  }

  private static void print(Float[] minimum) {
    for (int i = 0; i < 5; i++) {
      int pre = (int) minimum[i].floatValue();
      int decimal = (int) (minimum[i] * 1000000 - pre * 1000000);
      String l = String.valueOf(decimal);
      System.out.print(pre + ".");
      if (l.length() < 6) { //care . e.g.  34.000990
        for (int j = 1; j <= 6 - l.length(); j++) {
          System.out.print("0");
        }
      }
      System.out.println(decimal);
    }
  }
}
