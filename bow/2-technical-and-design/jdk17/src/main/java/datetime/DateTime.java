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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/*
Question comes from https://www.1point3acres.com/bbs/thread-796230-1-1.html
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


 Date d=new Date(long_timestamp)
 Instant inst = Instant.ofEpochMilli(long_timestamp);
 LocalDateTime t = LocalDateTime.ofInstant(inst, TimeZone.getDefault().toZoneId());
*/

public class DateTime {
  Set<String> bolts = new HashSet<>();
  Map<String, Long> ave;

  // user and req(same second)  counts
  Map<String, Map<Date, Integer>> detect = new HashMap<>();
  // user and  time of status 1,  2 ,  3 ]
  Map<String, LocalDateTime[]> status = new HashMap<>();

  public void process_invites(int N, String csvfilepath) throws IOException {
    try (BufferedReader fb = Files.newBufferedReader(Paths.get(csvfilepath))) {
      String line;
      while ((line = fb.readLine()) != null && N-- > 0) {
        parseLog(line);
      }
      System.out.println(handle());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public String handle() throws ParseException {
    Long sum = 0l, count = 0l;
    for (Map.Entry<String, Long> e : ave.entrySet()) {
      if (bolts.contains(e.getKey())) continue;
      sum += e.getValue();
      count++;
    }
    return bolts.size() + " " + sum / count;
  }

  /*
  log line format: timestamp,event_type,user_email
             E.g.: 1623834502,invite_requested,john@gmail.com
  */
  private void parseLog(String log) throws ParseException {
    String[] l = log.split(",");
    long timestamp = Long.valueOf(l[0]);
    String eventType = l[1], userId = l[2];

    if (bolts.contains(userId)) return;
    Instant inst = Instant.ofEpochMilli(timestamp);
    LocalDateTime t = LocalDateTime.ofInstant(inst, TimeZone.getDefault().toZoneId());

    if (eventType == "invite_requested") {
      Date d = new Date(timestamp);
      d.setSeconds(0);
      if (!detect.containsKey(userId)) {
        detect.put(userId, new HashMap<>());
        detect.get(userId).put(d, 1);
      } else {
        Map<Date, Integer> count = detect.get(userId);
        count.put(d, count.getOrDefault(d, 0) + 1);
        if (count.get(d) >= 5) { // in same second >= 5 req
          bolts.add(userId);
          detect.remove(userId);
          status.remove(userId);
        }
      }

      // calculate the ave and verify the req<invite<activted time
      status.putIfAbsent(userId, new LocalDateTime[] {null, null, null});
      // use the fist timestamp for "invite_requested" will be used to calculate the average time
      if (status.get(userId)[0] != null) status.get(userId)[0] = t;
      // no duplicated timestamp for  "invite_send" and "invite_actived"
    } else if (eventType == "invite_send") {
      status.putIfAbsent(userId, new LocalDateTime[] {null, null, null});
      status.get(userId)[1] = t;
    } else if (eventType == "invite_actived") {
      status.putIfAbsent(userId, new LocalDateTime[] {null, null, null});
      status.get(userId)[2] = t;
      LocalDateTime a = status.get(userId)[0];
      LocalDateTime b = status.get(userId)[1];
      if (a.isBefore(b) && b.isBefore(t)) {
        ave.put(userId, Duration.between(a, t).toSeconds());
      }
    } else {
      // ignore wrong event type
    }
  }
}
