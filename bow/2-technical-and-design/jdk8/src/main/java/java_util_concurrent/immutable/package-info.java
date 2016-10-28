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
 * immutable class is a class which once created, it’s contents can not be changed.
 * Immutable objects are the objects whose state can not be changed once constructed. Example- String & all java wrapper classes.
 * Mutable objects are the objects whose state can be changed once constructed.
 * cases:
 *      String;
 *      LocalDate, LocalTime and LocalDateTime classes (since 1.8)
 *      All primitive wrapper classes (such as Boolean, Character, Byte, Short, Integer, Long, Float, and Double) are immutable.
 *      Money and Currency API (slated for Java9) should be immutable, too.
 *      Incidentally, the array-backed Lists (created by Arrays.asList(myArray)) are structurally-immutable.
 *
 *
 * Immutable: cannot be changed after construction,it cannot have its field values changed.
 * benefit and why we need immutable: don't have to worry about things like thread safety.
 *                                    you cannot change the state of the class
 *
 * how to make it:
 * It:
 *      - make it final,it can’t be extended.
 *
 * Field:
 *      - make all the fields private, direct access is not allowed.
 *      - make all the fields final, it’s value can be assigned only once.
 *      - Initialize all the fields via a constructor when the class is constructed
 *      - instance fields must be immutable as well, or their state never changed in the immutable class.
 *           If the instance fields include references to mutable objects:
 *               mutable objects passed to the constructor;
 *               *** Never store references to external ****, create copies, and store references to the copies.
 *               Don't share references to the mutable objects. create copies of your internal mutable objects, to avoid returning the originals
 *               Don't provide methods that modify the mutable objects.
 *
 * Method:
 *      - does not provide any mutating methods. 'setter' methods that modify fields or objects referred to by fields.
 *      - performing deep copy.
 *      - in the 'getter' methods to return a copy, deep clone, rather than returning the actual object reference
 *
 * Constructor:
 *      -A more sophisticated approach is to make the constructor private and construct instances in factory methods.
 *
 * http://download.oracle.com/javase/tutorial/essential/concurrency/imstrat.html
 * http://keaplogik.blogspot.com/2015/07/java-immutable-classes-simplified.html
 *
 * @see <a href = "http://stackoverflow.com/questions/5124012/examples-of-immutable-classes"></a>
 */
package java_util_concurrent.immutable;