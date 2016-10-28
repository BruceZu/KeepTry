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
 * inheritance
 *
 * Constructors are not members,
 * so they are not inherited by subclasses,
 * but the constructor of the superclass can be invoked from the subclass.
 *
 * == The 'super' keyword is similar to 'this' keyword.
 * Following are the scenarios where the super keyword is used.
 * It is used to differentiate the members of superclass from the members of subclass, if they have same names.
 * It is used to invoke the superclass constructor from subclass.
 *
 * If a class is inheriting the properties of another class,
 * the subclass automatically acquires the default constructor of the superclass.
 * But if you want to call a parameterized constructor of the superclass,
 * you need to use the super keyword as shown below.
 *   super(values);
 *
 * == IS-A Relationship
 * the 'instanceof' operator
 * == that Java does not support multiple inheritance.
 *
 * polymorphism
 * overridden methods
 * public interface Vegetarian{}
 * public class Animal{}
 * public class Deer extends Animal implements Vegetarian{}
 * Now, the Deer class is considered to be polymorphic since this has multiple inheritance. Following are true for the above examples −
 *
 * A Deer IS-A Animal
 * A Deer IS-A Vegetarian
 * A Deer IS-A Deer
 * A Deer IS-A Object
 */
package OOD;