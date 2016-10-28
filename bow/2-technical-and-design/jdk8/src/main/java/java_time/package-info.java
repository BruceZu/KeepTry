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