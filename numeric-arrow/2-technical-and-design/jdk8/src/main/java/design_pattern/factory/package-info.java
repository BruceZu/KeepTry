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
 *    1> what is abstract Factory pattern,  Factory method pattern
 *    2> why we need it
 *
 *
 *    'Factory pattern' is a wrong concept
 *     abstract factory provides methods to create family of related products.
 *     each method in the Abstract factory is an Factory method in itself. whose concrete implementations will be provided by subclasses
 *
 *                             shopkeeper (Provides interface to create family of related objects)
 *      cloth       factory for rich, factory  for poor
 *     spring
 *     summer
 *     winter
 *     autumn
 *
 *    Use the Abstract Factory pattern when clients must be decoupled from product classes.
 *    Especially useful for program configuration and modification.
 *
 *     information used to create a new object including
 *     1 user care
 *     2 user without having to know, e.g. the dependencies of the Thing
 *     with factory to hide the <strong> <2> which maybe nested, and the process</strong>,
 *     it can be considered as an alternative to constructors.
 *
 *     it is useful when you need a complicated process for constructing the object.
 *     the process can be put in factory, makes it easier to maintain.
 *
 *     useful for testability?
 *
 *
 *     when the construction need a dependency that you do not want for the actual class ??
 *     when you need to construct different objects ??
 *
 *     static factory methods are completely different from the Gang of Four: Factory Method design pattern.
 *     factory class
 *
 *     Factory classes can be abstracted/interfaced away as necessary, whereas factory methods are lighter weight
 *     (and also tend to be testable, since they don't have a defined type, but they will require a well-known registration point)
 *
 *     Factory classes are useful for
 *         when the object type that they return has a private constructor,
 *         when different factory classes set different properties on the returning object,
 *         when a specific factory type is coupled with its returning concrete type in different situations,
 *         If you only have one class that's consuming the factory, then you can just use a factory method within that class.
 *    Factory class for almost every aggregate association
 *
 *   Factory:
 *          Creates objects without exposing the instantiation logic to the client.
 *    Factory method :
 *          Define an interface for creating an object, but let the subclasses decide which class to instantiate.
 *          The Factory method lets a class defer instantiation to subclasses
 *    when to use:
 *         client doesn't know what concrete classes it will be required to create at runtime,
 *         but just wants to get a class that will do the job.
 *
 *  The factory petter uses polymorphism, dependency injection and Inversion of control.
 *  Factory Method:
 *      uses inheritance (indirection is vertical e.g. createThing())
 *  Abstract Factory:
 *     uses composition (indirection is horizontal e.g. getFactory().createThing() )
 *
 * Factory pattern:
 *    you produce implementations (Apple, Banana, Cherry, etc.) of a particular interface -- say, IFruit.
 *    it instantiates things which implement IFruit
 *  Abstract Factory pattern:
 *    you produce implementations of a particular Factory interface -- e.g., IFruitFactory.
 *    Each of those knows how to create different kinds of fruit.
 *
 *    ==2==
 *                      When to use: Client just need a class and does not care about which concrete implementation it is getting.
 *    Factory Method,   When to use: Client doesn't know what concrete classes it will be required to create at runtime,
 *                      but just wants to get a class that will do the job.
 *    Abstract Factory, When to use: When your system has to create multiple families of products or you want to provide
 *                      a library of products without exposing the implementation details.
 */
package design_pattern.factory;