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

package java_time;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PatentCase {
  /*
   all patents expire exactly in 20 years after their filling date.
   Useful APIs provided by https://ped.uspto.gov/peds/#!/
   Example
   For patents = ["60447158"], the output should be
   solution(patents) = "2023-02-12", since the patent's filling date is 2003-02-12.

   String[] patents: representing either
     the patent's application number <number> or
     the patent number US <number>.
     Note, that the numbers might have leading zeros which are still part of the number.
     1 ≤ patents.length ≤ 5.

   Return
    The date the last patent from patents expires in the
    yyyy-mm-dd format and be in UTC time zone with zero offset.
  */
  public String theDateOfLastExpiresOf(String[] patents) {
    return null;
  }

  /*
   string to LocalDatatime
   compare and find the last one
   then convert the last one to yyyy-mm-dd format and be in UTC time zone with zero offset.
  */
  public static void main(String[] args) {
    System.out.println("now: " + LocalDateTime.now().toString());

    // 1 convert time from string to LocalDateTime
    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US);
    LocalDateTime a = LocalDateTime.parse("2021-11-21T20:33:29.000420Z", f);
    LocalDateTime b = LocalDateTime.parse("2021-11-22T22:30:34.332375Z", f);
    // 2 compare
    if (a.isBefore(b)) {
      System.out.println(Duration.between(a, b).toHours() + " hours");
    }

    // 3   LocalDateTime.atZone() ->  ZonedDateTime
    System.out.println("here: " + ZoneId.systemDefault());
    ZonedDateTime z = b.atZone(ZoneId.systemDefault());
    System.out.println("default: " + b.toString());
    System.out.println("with zone: " + z.toString());
    // 4  ZonedDateTime.withZoneSameInstant()
    ZonedDateTime zd = z.withZoneSameInstant(ZoneId.of("Europe/Kaliningrad"));
    System.out.println(zd);
    System.out.println(zd.toLocalDate());

    System.out.println("now the time of UTC " + z.withZoneSameInstant(ZoneId.of("UTC")));

    // 5 LocalDateTime.atOffset(). take it as in the zone specified by the ZoneOffSET
    System.out.println(b.atOffset(ZoneOffset.of("+08:00")));
    System.out.println(b.atOffset(ZoneOffset.of("-08:00")));
  }
}
