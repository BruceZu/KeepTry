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
import java.text.ParseException;
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

/*
logic
 -  input is in ascending order by timestamp, not 2 line have the same timestamp
 -  log line format: epoch timestamp(in second),event_type,user_email
              E.g.: 1623834502,invite_requested,john@gmail.com
 - bolt: in same minute >= 5 req
 - use the fist timestamp for "invite_requested" to calculate the average time
 - no duplicated timestamp for  "invite_send" and "invite_activated"
   calculate the average and verify the time of "invite_requested" <"invite_send" < "invite_activated"
     1m2s,send,john
     1m3s,req,john
     1m5s,activated,john
     1m6s,req,john
     1m6s,req,john
     1m6s,req,john
     1m6s,req,john
     1m6s,req,john

 - user can activate their beta invite once Stripe has sent it to them.
*/
public class BetaInvites {
  Set<String> bolts = new HashSet<>();
  Map<String, Long> ave;
  // User:  <Date without second, req counts>
  Map<String, Map<Date, Integer>> detect = new HashMap<>();
  Map<String, Long[]> status = new HashMap<>();

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

  private void parseLog(String log) throws ParseException {
    String[] l = log.split(",");
    long t = Long.valueOf(l[0]);
    String eventType = l[1], userId = l[2];

    if (bolts.contains(userId)) return;
    status.putIfAbsent(userId, new Long[] {null, null, null});

    if (eventType == "invite_requested") {
      Date d = new Date(t);
      d.setSeconds(0);
      if (!detect.containsKey(userId)) {
        detect.put(userId, new HashMap<>());
        detect.get(userId).put(d, 1);
      } else {
        Map<Date, Integer> count = detect.get(userId);
        count.put(d, count.getOrDefault(d, 0) + 1);
        if (count.get(d) >= 5) bolts.add(userId);
      }

      if (status.get(userId)[0] != null) status.get(userId)[0] = t;
    } else if (eventType == "invite_send") {
      status.get(userId)[1] = t;
    } else if (eventType == "invite_activated") {
      Long a = status.get(userId)[0], b = status.get(userId)[1];
      if (a != null && b != null && a < b && b < t) ave.put(userId, t - a);
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
}
