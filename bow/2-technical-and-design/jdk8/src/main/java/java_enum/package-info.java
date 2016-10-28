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
 * <pre>
 * 1 Type safety and value safety.
 * 2 Guaranteed singleton.
 * 3 Ability to define and override methods.
 * 4 Ability to use values in switch statement case statements without qualification.
 * 5 Built-in methods: ordinal(). values(), valuesOf()
 * 6 Serialization by name not by value, which offers a degree of future-proofing.
 * 7 EnumSet and EnumMap classes.
 * 8 form a range, namespace, and help to perform more comprehensive code analysis through type & value safety checks.
 * 9 Meaningful Printing
 *
 * If our Enum type has no more that 64 elements (most of real-life Enum examples will qualify for this),
 * the implementations RegularEnumSet store the elements in a single long value,
 * each Enum instance in question will be associated with a bit of this 64-bit long long.
 * Adding an element to an EnumSet is simply just setting the proper bit to 1,
 * removing it is just setting that bit to 0.
 * Testing if an element is in the Set is just one bitmask test!
 *
 * to represent fixed set of things e.g. status of object
 * like ON and OFF for a button
 * or START, IN PROGRESS and DONE for a Task.
 * Enum provide compile time type safety and better debugging assistent than String or Integer.
 * use Enum to impelment State machine, days of week, month of year
 *
 * valueOf() is a static method which takes a string argument and can be used to convert a String into an enum
 *
 * Enum String pattern and enum int pattern, where we used public static final constants
 * to represent collection of well known fixed number of things e.g. DayOfWeek.
 * There was lot of problem with that approach e.g. you don't have a dedicated enum type,
 * Since it's String variable, which represent day of week, it can take any arbitrary value.
 * Similarly enum int pattern can take any arbitrary value, compiler doesn't prevent those.
 * By using Enum, you get this type-safety and compiler checking for you.
 *
 */

package java_enum;