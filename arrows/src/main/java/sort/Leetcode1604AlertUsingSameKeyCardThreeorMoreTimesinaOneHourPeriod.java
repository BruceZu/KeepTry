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

package sort;

import java.util.*;

public class Leetcode1604AlertUsingSameKeyCardThreeorMoreTimesinaOneHourPeriod {
  /*
      Leetcode  1604. Alert Using Same Key-Card Three or More Times in a One Hour Period

    LeetCode company workers use key-cards to unlock office doors.
    Each time a worker uses their key-card, the security system saves the worker's name
    and the time when it was used.
    The system emits an alert if any worker uses the key-card three or more times in a one-hour period.

    You are given a list of strings keyName and keyTime where [keyName[i], keyTime[i]]
    corresponds to a person's name and the time when their key-card was used
    in a single day.

    Access times are given in the 24-hour time format "HH:MM", such as "23:51" and "09:49".
    Return a list of unique worker names who received an alert for frequent
    keycard use. Sort the names in ascending order alphabetically.

    Notice that "10:00" - "11:00" is considered to be within a one-hour period,
    while "22:51" - "23:52" is not considered to be within a one-hour period.



    Input: keyName = ["daniel","daniel","daniel","luis","luis","luis","luis"],
           keyTime = ["10:00","10:40","11:00","09:00","11:00","13:00","15:00"]
    Output: ["daniel"]
    Explanation: "daniel" used the keycard 3 times in a one-hour period ("10:00","10:40", "11:00").


    Input: keyName = ["alice","alice","alice","bob","bob","bob","bob"],
           keyTime = ["12:01","12:00","18:00","21:00","21:20","21:30","23:00"]
    Output: ["bob"]
    Explanation: "bob" used the keycard 3 times in a one-hour period ("21:00","21:20", "21:30").


    Input: keyName = ["john","john","john"]
           keyTime = ["23:58","23:59","00:01"]
    Output: []

    Input: keyName = ["a","a","a","a","a","b","b","b","b","b","b"]
           keyTime = ["04:48","23:53","06:36","07:45","12:16","00:52","10:59","17:16","00:36","01:26","22:42"]
    Output: [b]

    Constraints:

    1 <= keyName.length, keyTime.length <= 105
    keyName.length == keyTime.length
    keyTime[i] is in the format "HH:MM".
    [keyName[i], keyTime[i]] is unique.
    1 <= keyName[i].length <= 10
    keyName[i] contains only lowercase English letters.
  */

  /*
   input data is of "in a single day" can be not in order like above last 2 cases
   Assume the sigle day is [ 00:00 - 23:59]
  */
  public static List<String> alertNames(String[] Names, String[] Times) {
    Map<String, TreeSet<Integer>> map = new HashMap<>();
    int N = Names.length;

    // sort each employee one day records in the time order
    for (int i = 0; i < N; i++) {
      int t =
          Integer.parseInt(Times[i].substring(0, 2)) * 60 + Integer.parseInt(Times[i].substring(3));
      map.computeIfAbsent(Names[i], s -> new TreeSet<>()).add(t);
    }

    TreeSet<String> ans = new TreeSet<>(); // Sort the names in ascending order alphabetically.
    for (Map.Entry<String, TreeSet<Integer>> e : map.entrySet()) {
      List<Integer> list = new ArrayList<>(e.getValue());
      for (int i = 2; i < list.size(); ++i) {
        if (list.get(i) - list.get(i - 2) <= 60) {
          ans.add(e.getKey());
          break;
        }
      }
    }
    return new ArrayList<>(ans);
  }

  /*

  */
  public List<String> alertNames_(String[] Names, String[] Times) {
    Map<String, TreeSet<Integer>> map = new HashMap<>();
    for (int i = 0; i < Names.length; ++i) {
      int t =
          Integer.parseInt(Times[i].substring(0, 2)) * 60 + Integer.parseInt(Times[i].substring(3));
      map.computeIfAbsent(Names[i], s -> new TreeSet<>()).add(t);
    }
    TreeSet<String> ans = new TreeSet<>();
    for (Map.Entry<String, TreeSet<Integer>> e : map.entrySet()) {
      Deque<Integer> dq = new ArrayDeque<>();
      for (int t : new ArrayList<>(e.getValue())) {
        dq.offer(t);
        while (dq.peekLast() - dq.peek() > 60) dq.poll();

        if (dq.size() >= 3) {
          ans.add(e.getKey());
          break;
        }
      }
    }
    return new ArrayList<>(ans);
  }

  public static void main(String[] args) {
    System.out.println(
        alertNames(
            new String[] {"b", "b", "b", "b", "b", "b"},
            new String[] {"00:52", "10:59", "17:16", "00:36", "01:26", "22:42"}));
  }
}
