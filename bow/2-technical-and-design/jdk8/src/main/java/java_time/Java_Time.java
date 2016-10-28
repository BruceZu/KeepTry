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

    /***
     * <pre>
     *     see {@link Date}  {@link Calendar}  {@link DateFormat}
     *     The new Date and Time APIs are thread-safe, immutable, cacheable,
     *     and represent a point in time measured to the nanosecond
     *     and have the option for backward compatibility.
     *     It borrows the ideas from Joda-Time
     *     and allows the programmers to capitalize on
     *     the features which were not available in java.util.Date and Calendar.
     *
     * java.util.Date has no specific time zone,
     * the value within a java.util.Date is the number of milliseconds since the Unix epoch,
     * which occurred at midnight January 1st 1970, UTC.
     * The same epoch could also be described in other time zones,
     * but the traditional description is in terms of UTC.
     * As it's a number of milliseconds since a fixed epoch,
     * the value within java.util.Date is the same around the world at any particular instant,
     * regardless of local time zone.
     * I suspect the problem is that you're displaying it via an instance of Calendar
     * which uses the local timezone,
     * or possibly using Date.toString()
     * which also uses the local timezone,
     * or a SimpleDateFormat instance, which, by default, also uses local timezone.
     */
    public static void main(String[] args) {
        Date d = new Date();
        String s = d.toString();
        System.out.println("-------------default:\n" + s);
        try {
            System.out.println("------------- Date ");
            DateFormat df = DateFormat.getDateInstance();
            String sfull = df.format(d);
            System.out.println(sfull);
            Date d2 = df.parse(sfull);
            System.out.println(d2.toString());

            System.out.println("------------- Time");
            DateFormat tf = DateFormat.getTimeInstance();
            sfull = tf.format(d);
            System.out.println(sfull);
            d2 = tf.parse(sfull);
            System.out.println(d2.toString());

            System.out.println("------------- Date Time");
            DateFormat dtf = DateFormat.getDateTimeInstance();
            sfull = dtf.format(d);
            System.out.println(sfull);
            d2 = dtf.parse(sfull);
            System.out.println(d2.toString());

            System.out.println("------------- SimpleDateFormat");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();// new
            sfull = simpleDateFormat.format(d);
            System.out.println(sfull);
            d2 = simpleDateFormat.parse(sfull);
            System.out.println(d2.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println("-------------Date, Local is China");
        String myString = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA).format(d);
        System.out.println(myString);
        myString = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA).format(d);
        System.out.println(myString);

        myString = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA).format(d);
        System.out.println(myString);

        myString = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA).format(d);
        System.out.println(myString);

        System.out.println("-------------Date and Time, Local is USA");
        myString = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(d);
        System.out.println(myString);
        myString = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.US).format(d);
        System.out.println(myString);

        myString = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Locale.US).format(d);
        System.out.println(myString);

        myString = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.US).format(d);
        System.out.println(myString);

        //
        System.out.println("-------------GWT date time");
        SimpleDateFormat dfWithTimeZone = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        //Local time zone
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            System.out.println("USA     " + dateFormatLocal.parse(dfWithTimeZone.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dfWithTimeZone.setTimeZone(TimeZone.getTimeZone("GMT"));
        //dfWithTimeZone.setTimeZone(TimeZone.getTimeZone("UTC"));
        //Time in GMT
        try {
            System.out.println("GMT     " + dateFormatLocal.parse(dfWithTimeZone.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dfWithTimeZone.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        try {
            System.out.println("Beijing " + dateFormatLocal.parse(dfWithTimeZone.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Java 8: Getting current time in UTC/GMT
        System.out.println("-------------LocalTime LocalDate");
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
        //the new Date-Time types are immutable and it allows them to be thread-safe and cacheable.​
        LocalDate.of(2014, 2, 15).atTime(12, 15, 15);

        // enums
        ChronoUnit w = ChronoUnit.WEEKS;
        DayOfWeek dw = DayOfWeek.MONDAY;
        Month m = Month.AUGUST;

        //
        System.out.println("-------------clock");
        Clock clock = Clock.systemDefaultZone();
        //clock = Clock.systemUTC();
        System.out.println(LocalTime.now(clock));

        //Period and Duration
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

        ZoneId.getAvailableZoneIds().stream().filter(e -> e.startsWith("Asia")).sorted().forEach(e -> System.out.print(e + ", "));
        ZoneId zid = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(8));
        System.out.println(zid);
        LocalDateTime ldt = LocalDateTime.ofInstant(new Date().toInstant(), zid);
        System.out.println("Beijing:" + ldt);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.of("Asia/Shanghai"));
        System.out.println("Beijing:" + zdt);
    }
}
