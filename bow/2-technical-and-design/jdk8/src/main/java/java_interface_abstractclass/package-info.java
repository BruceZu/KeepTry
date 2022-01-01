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
 *  1 what is them
 *  2 difference of them
 *  3 when we need each of them
 *
 * =======
 *  2  differences between an abstract class and an interface:
 *
 *                                             Abstract classes    interface
 *        can have constants  fields                Y                Y
 *        method stubs (methods without a body)     Y                Y
 *        Methods:
 *          defined methods                         Y
 *          constructor                             Y
 *          main()                                  Y                Y
 *
 *        Method Visibility:
 *          static                                  Y                 Y && need method boday
 *          final                                   Y                 NO
 *          public                                  Y                 Y (default)
 *          abstract                                Y                 Y (default)
 *          protected                               Y
 *          private                                 Y
 *          default                                 ?                  Y
 *        Method
 *            synchronized                          Y                  N
 *            strictfp                              Y                  N
 *            [Serialization]                       Y                  N
 *        Field Visibility:
 *          static                                  Y                  Y (default)
 *          final                                   Y                  Y (default)
 *          abstract                                Y                  NO
 *        Filed
 *             transient                            Y                  N
 *             must initial value                   N                  Y
 *
 *       Extends and implements
 *            concrete child class:  must define the abstract methods   must
 *            abstract class:        does not have to                   does not have to
 *            an interface           X                                  does not have to
 *       Extends and implements - number
 *            concrete child class:                 1                   more
 *            abstract class:                       1                   more
 *            an interface                          X                   more
 *
 *       Extends and implements - visibility
 *
 *                            same or less restrictive visibility      the exact same visibility (public).
 *       static block                               Y                  N
 *       static inner class                         Y                  Y
 *
 *   =======
 *   3 :
 *
 *   interface:      for multiple unrelated objects, to implement a contract.
 *   abstract class: for multiple related   objects, to implement the same behaviour
 *
 * Consider using abstract classes if :
 *
 * - You want to share code among several closely related classes.
 * - You expect that classes that extend your abstract class have many common methods or fields, or require access modifiers other than public (such as protected and private).
 * - You want to declare non-static or non-final fields.
 *
 * Consider using interfaces if :
 *
 * -  expect that unrelated classes would implement your interface. For example, Serializable interface.
 * -  specify the behaviour of a particular data type, but not concerned about who implements its behaviour.
 * -  take advantage of multiple inheritance of type.
 *
 * abstract class establishes "is a" relation with concrete classes.
 * interface provides "has a" capability for classes.
 */
package java_interface_abstractclass;