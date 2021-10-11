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
package datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/*
Question comes from https://www.1point3acres.com/bbs/thread-801267-1-1.html
Exercise Date and Time operation with JDK 17

Date year month day
Time: hour minutes second, million second

`DateTimeFormatter.ofPattern(String)` // `f`
`new SimpleDateFormat( String)`  // `sf`

The String e.g.:  "yyyy-MM-dd+HH:mm:ss"

    y year
    M month
    m minute
    H hour 24
    h hour 12
    s second

Date d=sf.part(String);
     d.setSeconds(0); // can change value


LocalDateTime dtime = LocalDateTime.parse(string, f);
// used for compare and diff
 if (a.isBefore(b)) ...
 Duration.between(a, dtime).toSeconds());

*/
// which request time will be used as the one mapping to approved time
public class DateTime {
  Set<String> bolts = new HashSet<>();
  Map<String, Long> ave;

  // user and req(same second)  counts
  Map<String, Map<Date, Integer>> detect = new HashMap<>();
  // user and  time of status 1,  2 ,  3 ]
  Map<String, LocalDateTime[]> status = new HashMap<>();

  public String handle(List<String> logs) throws ParseException {
    for (String log : logs) {
      parseLog(log);
    }
    Long sum = 0l, count = 0l;
    for (Map.Entry<String, Long> e : ave.entrySet()) {
      if (bolts.contains(e.getKey())) continue;
      sum += e.getValue();
      count++;
    }
    return bolts.size() + " " + sum / count;
  }

  // yyyy-MM-dd+HH:mm:ss  userID  status[1,2,3]
  private void parseLog(String log) throws ParseException {
    String[] l = log.split(" ");
    String dt = l[0], userId = l[1], eventType = l[2];

    if (bolts.contains(userId)) return;
    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss");
    LocalDateTime logtime = LocalDateTime.parse(dt, f);

    if (eventType == "1") {
      // dt="2014-11-25+16:30:30";
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
      Date d = sf.parse(dt);
      d.setSeconds(0);
      if (!detect.containsKey(userId)) {
        detect.put(userId, new HashMap<>());
        detect.get(userId).put(d, 1);
      } else {
        detect.get(userId).put(d, detect.get(userId).getOrDefault(d, 0) + 1);
        if (detect.get(userId).get(d) >= 5) { // in same second >= 5 req
          bolts.add(userId);
          detect.remove(userId);
          status.remove(userId);
        }
      }
      // status: req time update, use the last req time as the one used to
      // calculate the ave and verify the req<invite<activted time
      status.putIfAbsent(userId, new LocalDateTime[] {null, null, null});
      status.get(userId)[0] = logtime;

    } else if (eventType == "2") {
      status.putIfAbsent(userId, new LocalDateTime[] {null, null, null});
      status.get(userId)[1] = logtime;
    } else if (eventType == "3") {
      status.putIfAbsent(userId, new LocalDateTime[] {null, null, null});
      status.get(userId)[2] = logtime;
      LocalDateTime a = status.get(userId)[1];
      LocalDateTime b = status.get(userId)[1];
      if (a.isBefore(b) && b.isBefore(logtime)) {
        ave.put(userId, Duration.between(a, logtime).toSeconds());
      }
    } else {
      // ignore wrong event type
    }
  }

  public static void main(String[] args) throws ParseException {
    // there is not million second
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
    String dt = "2014-11-25+16:30:20";
    Date d = sf.parse(dt);
    d.setSeconds(0);
    dt = "2014-11-25+16:30:30";
    Date d2 = sf.parse(dt);
    d2.setSeconds(0);
    System.out.println(d.equals(d2) == true);
  }
}
