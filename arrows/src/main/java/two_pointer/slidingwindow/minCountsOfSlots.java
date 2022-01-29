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

package two_pointer.slidingwindow;

public class minCountsOfSlots {

  /*
    Clarify:
    given:
         - row of lots's acres
         - target acres of lot(s)


     if there are more solution: return any one's counts of lot(s)
        e.g. find
             6,2 = 8
             1,7 = 8
     if ther is no solution  return 0
             find
             2 3 4 : no 8


      Idea:
      sliding window  [l, r]
      counts:  current window slots number
      sum: current window lots' acres
      ans: tracking the answer

      Note:
       - each loop one only step
       - keep right side of window in valid
         when i=length-1;
  */

  public static int minSlotsWith_(int target, int[] lots) {
    if (lots == null || lots.length == 0) return 0;
    int l = 0, r = 0, sum = lots[0], count = 1;
    int ans = lots.length;
    boolean find = false;
    while (r < lots.length) {
      if (sum < target) {
        r++;
        if (r == lots.length) break; // kill me here;
        sum += lots[r];
        count++;
        continue;
      }
      if (sum == target) {
        find = true;
        if (count < ans) ans = count;
      }
      if (l < lots.length && sum >= target) {
        // kill me here. not while only 1 step; when sum==target also need move left
        sum -= lots[l];
        l++;
        count--;
      }
    }
    return find ? ans : 0;
  }

  /*
  sliding window idea:
    move only one step each loop and then check status
    decide move right or left, anyway must move one step.

  2 scenarios:
   move right or left:
       right can move and sum<target: move right one step
       the left scenario: move left one step
         - sum >=target:
           -- if sum == target: tracking the ans
         - right reach length-1 and can not move anymore. now it is still sum < target
   This also is:
          sum < target:  right has priority to move than left. move one step in total
          sum >=target:  move only left one step

  So the while condition use only l

  initiate r = -1, l=0 , sum=0, count=0
  */
  public static int minSlotsWith(int target, int[] lots) {
    if (lots == null || lots.length == 0) return 0;
    int l = 0, r = -1, sum = 0, count = 0;
    int ans = lots.length;
    boolean find = false;
    while (l < lots.length) {
      if (sum < target && r + 1 < lots.length) {
        r++;
        sum += lots[r];
        count++;
        continue; // only one step
      }

      if (sum == target) {
        find = true;
        if (count < ans) ans = count;
      }
      sum -= lots[l];
      l++;
      count--;
    }
    return find ? ans : 0;
  }

  public static void main(String[] ps) {
    System.out.println(minSlotsWith(8, new int[] {1, 3, 2, 1, 4, 1, 3, 2, 1, 1, 2}) == 3);
    System.out.println(minSlotsWith(8, new int[] {4, 1, 3, 2, 1, 1, 2}) == 3);
    System.out.println(minSlotsWith(8, new int[] {4, 1, 3, 2, 1, 1, 8}) == 1);
    System.out.println(minSlotsWith(8, new int[] {4, 2, 2, 1, 1, 1, 1, 1, 1}) == 3);
    System.out.println(minSlotsWith(8, new int[] {4, 4, 9, 4, 2, 2}) == 2);
    System.out.println(minSlotsWith(8, new int[] {8, 1, 1, 6, 2}) == 1);
    System.out.println(minSlotsWith(8, new int[] {4, 5, 1}) == 0);
    System.out.println(minSlotsWith(8, new int[] {8}) == 1);

    System.out.println(minSlotsWith_(8, new int[] {1, 3, 2, 1, 4, 1, 3, 2, 1, 1, 2}) == 3);
    System.out.println(minSlotsWith_(8, new int[] {4, 1, 3, 2, 1, 1, 2}) == 3);
    System.out.println(minSlotsWith_(8, new int[] {4, 1, 3, 2, 1, 1, 8}) == 1);
    System.out.println(minSlotsWith_(8, new int[] {4, 2, 2, 1, 1, 1, 1, 1, 1}) == 3);
    System.out.println(minSlotsWith_(8, new int[] {4, 4, 9, 4, 2, 2}) == 2);
    System.out.println(minSlotsWith_(8, new int[] {8, 1, 1, 6, 2}) == 1);
    System.out.println(minSlotsWith_(8, new int[] {4, 5, 1}) == 0);
    System.out.println(minSlotsWith_(8, new int[] {8}) == 1);
  }
}
