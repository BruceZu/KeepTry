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

package java_time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.temporal.ChronoUnit.HOURS;

public class Java_Time {

  /*
        see {@link Date}  {@link Calendar}  {@link DateFormat}
        The new Date and Time APIs are thread-safe, immutable, cacheable,
        and represent a point in time measured to the nanosecond
        and have the option for backward compatibility.
        It borrows the ideas from Joda-Time
        and allows the programmers to capitalize on
        the features which were not available in java.util.Date and Calendar.

    java.util.Date has no specific time zone,
    the value within a java.util.Date is the number of milliseconds since the Unix epoch,
    which occurred at midnight January 1st 1970, UTC.
    The same epoch could also be described in other time zones,
    but the traditional description is in terms of UTC.
    As it's a number of milliseconds since a fixed epoch,
    the value within java.util.Date is the same around the world at any particular instant,
    regardless of local time zone.


    Calendar uses the local timezone,
    Date.toString() also uses the local timezone,
    SimpleDateFormat instance by default also uses local timezone.
  */
  public static void main(String[] args) throws ParseException {
    Date d = new Date();
    String s = d.toString();
    System.out.println("Date.toString() use local timezone :\n" + s);

    System.out.println("DateFormat.getDateInstance()  only has date, no time");
    DateFormat df = DateFormat.getDateInstance();
    System.out.println(df.format(d));
    System.out.println(df.parse(df.format(d)));
    System.out.println("DateFormat.getTimeInstance()  only has time, no data");
    DateFormat tf = DateFormat.getTimeInstance();
    System.out.println(tf.format(d));
    System.out.println(tf.parse(tf.format(d)).toString());
    System.out.println("DateFormat.getDateTimeInstance()  only has time, no data");
    DateFormat dtf = DateFormat.getDateTimeInstance();
    System.out.println(dtf.format(d));
    System.out.println(dtf.parse(dtf.format(d)));

    System.out.println("SimpleDateFormat default foramt");
    SimpleDateFormat sf = new SimpleDateFormat(); // default
    System.out.println(sf.format(d));
    System.out.println(sf.parse(sf.format(d)));

    System.out.println("Date, Local is China");
    System.out.println(DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA).format(d));
    System.out.println(DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA).format(d));
    System.out.println(DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA).format(d));
    System.out.println(DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA).format(d));

    System.out.println("-------------Date and Time, Local is USA");
    System.out.println(
        DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(d));
    System.out.println(
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.US).format(d));
    System.out.println(
        DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Locale.US).format(d));
    System.out.println(
        DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.US).format(d));

    /*SimpleDateFormat.setTimeZone()
    The TimeZone set by this method is overwritten by a setCalendar call.
    The TimeZone set by this method may be overwritten as a result of a call to the parse method.
     */
    System.out.println("-------------date time with time zone");
    sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println("default         " + sf.format(new Date()));

    sf.setTimeZone(TimeZone.getTimeZone("GMT"));
    System.out.println("GMT             " + sf.format(new Date()));
    System.out.println("parse again     " + sf.parse(sf.format(new Date())));

    sf.setTimeZone(TimeZone.getTimeZone("UTC"));
    System.out.println("UTC             " + sf.format(new Date()));

    sf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    System.out.println("Beijing         " + sf.format(new Date()));

    System.out.println("===============  LocalTime LocalDate");
    // The Instant class represents a point in time
    // measured to the nanosecond.
    // It forms the basis of the time measurements in Java 8 date-time API.
    // The Instant measures the time starting from the “epoch” (Jan. 1, 1920)
    // and is time-zone ignorant.
    //
    System.out.println(Instant.now());
    System.out.println(ZonedDateTime.now(ZoneOffset.UTC));

    // Java 7
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.HOUR, 15);
    cal.getTime();
    // Java 8
    LocalTime lt = LocalTime.now().plus(15, HOURS);
    LocalDate ld = LocalDate.now().plusDays(1);
    System.out.println(lt + "  " + ld);
    // the new Date-Time types are immutable and it allows them to be thread-safe and cacheable.​
    LocalDate.of(2014, 2, 15).atTime(12, 15, 15);

    // enums
    ChronoUnit w = ChronoUnit.WEEKS;
    DayOfWeek dw = DayOfWeek.MONDAY;
    Month m = Month.AUGUST;

    //
    System.out.println("-------------clock");
    Clock clock = Clock.systemDefaultZone();
    // clock = Clock.systemUTC();
    System.out.println(LocalTime.now(clock));

    // Period and Duration
    // The Duration is a time-based amount of time,
    // such as ‘24.6 seconds’

    // the Period is a date-based amount of time,
    // such as ‘3 years, 2 months and 6 days’.
    System.out.println("-------------TemporalAdjusters");
    TemporalAdjusters.firstDayOfMonth();
    LocalDate nextTues = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
    System.out.println(nextTues);

    // Time Zones
    // represents Time-Zones.
    // There are two kinds of ZoneIds: fixed offsets and geographical regions
    System.out.println("------------- Time Zones");
    System.out.println(ZoneId.of("Asia/Shanghai"));
    System.out.println(ZoneId.systemDefault());

    ZoneId.getAvailableZoneIds().stream()
        .filter(e -> e.startsWith("Asia"))
        .sorted()
        .forEach(e -> System.out.print(e + ", "));
    ZoneId zid = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(8));
    System.out.println(zid);
    LocalDateTime ldt = LocalDateTime.ofInstant(new Date().toInstant(), zid);
    System.out.println("Beijing:" + ldt);
    ZonedDateTime zdt = ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.of("Asia/Shanghai"));
    System.out.println("Beijing:" + zdt);
  }
}
