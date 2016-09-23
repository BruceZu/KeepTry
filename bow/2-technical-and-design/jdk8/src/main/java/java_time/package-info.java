/**
 * // todo
 * <pre>
 * @see <a href="https://www.credera.com/blog/technology-insights/java/java-8-part-3-hashmap-java-time/"> java time</a>
 * in JDK 7
 * 1 the existing classes (such as java.util.Date and SimpleDateFormatter) aren’t thread-safe,
 * leading to potential concurrency issues for users.
 * 2 Some of the date and time classes also exhibit quite poor API design.
 *  For example, years in java.util.Date start at 1900, months start at 1, and days start at 0.
 *
 *
 *  1 The new JDK 8API avoids some issues by ensuring that all its core classes are immutable
 *  and represent well-defined values.
 *
 *  2 The new API allows supports different calendaring systems used in other areas of the world,
 *  such as Japan or Thailand, that don’t necessarily follow ISO-8601.
 *
 *  3 The new API also provides LocalDate and LocalTime.
 *    They are local in the sense that they represent date and time from the context of the observer,
 *    such as a local calendar or your clock.
 *    There is also a composite class called LocalDateTime,
 *    which is a pairing of LocalDate and LocalTime.
 *
 *  4 The new Time API prefers enums over integer constants for months and days of the week.
 *    One useful class is DateTimeFormatter for converting datetime objects to strings.
 *
 *
 *

 *
 *
 * */
package java_time;