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

import java.util.*;

public class RemoveServe {

  /*
    Question comes from
     https://www.1point3acres.com/bbs/thread-780948-1-1.html
     https://1o24bbs.com/t/topic/5821/10
     https://www.1point3acres.com/bbs/thread-802334-1-1.html

    Part 1
            Given logs and removed_time calculate the total penalty score
            logs consist of 0 and 1 in one string
            ex) "0 0 0 1 0 0"
            0 means the server was up and running
            1 means the server was down and not functioning

            if the server is taken down too early you get a +1 penalty for reach the running server
            after removed_time
            if the server is taken down too late, you get a +1 penalty for reach a server that was
            down before removed_time

            Example 1

            "0 0 1 0" remove_time = 2 (0 based index)
            when the server is shut down, there is a penalty of 1 since during time 3rd and 4th
            log 4th indicated it was on when it should have been off.
            penalty = 1

            Example 2
            "0 0 1 0" remove_time = 0 index
            penalty = 3
            log 1st, 2nd, 4th was on when it should have been off

            Example 3
            "1 1 1 0" remove_time = 0 index
            penalty = 1
            only log 4th was on when it should have been off

    Part 2
            write another function to give logs find when is the best time to take the server down

            Example
            log "1 1 1 0"
            best_time = 0  0-based index
            because for time 1 2 3 4 penalties will be 2 3 4 3
            at time 1th penalty is 1 which is the lowest

            log   "1 0 0"
                   0 1 1
                   2 2 1
           penalty 2 3 2, there 2 time with the lowest penalty,
          how to select now: index 0 or 2 ??

  Part 3

            write another function which can take in logs
            this time with 'BEGIN' 'END' '0' '1' '\n'
            they can be out of order
            There are no nested loops

            ex) 'BEGIN END BEGIN BEGIN 1 0 0 END END 0 0 1'
            The only valid sequence is
                                 BEGIN 1 0 0 END
            It must start with BEGIN and ‍‌‍‌‌‍‌‌‍‍‌‍‍‌‍‍‌‌end with END
            There can be multiple valid sequences,
            return a list of the best times to take the server down

            Example 1
            "BEGIN BEGIN 1 0 0 END"
            return [2] 1-based index
            because the valid sequence is "1 0 0" now use
            the function we wrote in part 2 and return  it in a list.

            Example 2  1-based index
            "BEGIN BEGIN 1 0 0 END 0 0 0 1 BEGIN 1 1 1 0 END"
            return [2, 0]
            valid sequence is "1 0 0" and "1 1 1 0"*/

  public static int getPenalty(String line, int removed) {
    if (line == null || line.length() == 0) return 0;
    String[] digits = line.split("\\s");
    int r = 0;
    for (int i = 0; i < digits.length; i++) {
      if ((i < removed && digits[i].equals("1")) || (i >= removed && digits[i].equals("0"))) {
        r++;
      }
    }
    return r;
  }

  public static int bestTimeToShutDown(String line) {
    if (line == null || line.length() == 0) return -1;

    String[] digits = line.split("\\s");
    int N = digits.length;
    int[] nozero = new int[N];
    int nozSum = 0;
    for (int i = 1; i < N; i++) {
      if (digits[i - 1].equals("1")) nozSum++;
      nozero[i] = nozSum;
    }
    int minP = Integer.MAX_VALUE, r = -1;
    int no1 = 0;
    for (int i = N - 1; i >= 0; i--) {
      if (digits[i].equals("0")) no1++;
      if (no1 + nozero[i] < minP) {
        minP = no1 + nozero[i];
        r = i;
      }
    }
    return r;
  }

  public static List<Integer> parseLogs(String line) {
    String[] cuts = line.split("\\s");
    int start = -1, end = -1;
    int i = 0;
    List<String> logs = new ArrayList<>();
    while (i < cuts.length) {
      if (i > 0 && cuts[i].length() == 1 && Character.isDigit(cuts[i].charAt(0))) {
        start = i - 1;
        StringBuilder s = new StringBuilder();
        while (i < cuts.length && cuts[i].length() == 1 && Character.isDigit(cuts[i].charAt(0))) {
          s.append(cuts[i]).append(" ");
          i++;
        }
        if (i < cuts.length && cuts[i].equals("END") && cuts[start].equals("BEGIN")) {
          logs.add(s.toString());
        }
      } else {
        i++;
      }
    }
    List<Integer> r = new LinkedList<>();
    for (String log : logs) {
      r.add(bestTimeToShutDown(log));
    }
    return r;
  }

  public static void main(String[] args) {
    System.out.println(getPenalty("0 0 1 0", 2) == 1);
    System.out.println(getPenalty("0 0 1 0", 0) == 3);
    System.out.println(getPenalty("1 1 1 0", 0) == 1);

    System.out.println(bestTimeToShutDown("1 1 1 0") == 0);
    System.out.println(bestTimeToShutDown("0 0 1 0") == 2);
    System.out.println(bestTimeToShutDown("1 1 0") == 0);
    System.out.println(bestTimeToShutDown("1 0 0") == 2); // 0 or 2

    System.out.println(
        Arrays.toString(parseLogs("BEGIN END BEGIN BEGIN 1 0 0 END END 0 0 1").toArray())
            .equals("[2]"));
    System.out.println(Arrays.toString(parseLogs("BEGIN BEGIN 1 0 0 END").toArray()).equals("[2]"));
    System.out.println(
        Arrays.toString(parseLogs("BEGIN BEGIN 1 0 0 END 0 0 0 1 BEGIN 1 1 1 0 END").toArray())
            .equals("[2, 0]"));
  }
}
