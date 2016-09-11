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
 *
      what are they? encapsulate instantiation
      when to use them?
 *
 *    1 Static Factory Method:
 *    all the objects it can create, the objects have the same parent class,
 *    or implement the same interface.
 *    The parameters for your call tell the factory which class to create.
 *    the client didn't know what was going to be returned,
 *    the client is expecting a type that matches the parent class /interface.
 *
 *      “a static factory method is not the same as the Factory Method pattern from Design Patterns
 *      [Gamma95, p. 107]. The static factory method described in this item has no direct equivalent
 *      in Design Patterns.” -- Joshua Bloch
 *      “Interfaces can’t have static methods, so by convention,
 *      static factory methods for an interface named Type are put in a noninstantiable
 *      class (Item 4) named Types.” -- Joshua Bloch
 *      “A class can provide a public static factory method, which is simply a static method that
 *      returns an instance of the class.” -- Joshua Bloch in “Effective Java”
 *
 *      in object's class;
 *      or be a single class.
 *
 *      1 Control over instantiation. Singleton. Pool Pattern is based on a factory.
 *
 *      2 Loose coupling, separation of concerns.
 *                           FileSystemLogger and DBLogger.
 *                           the client: use an interface of logger (ILogger)
 *                           factory:  implementation, object of different subclass or different constructors
 *                           Using an IoC container to resolve your dependencies allows you even more flexibility.
 *                           There is no need to update each call to the constructor whenever you change the dependencies of the class.
 *
 *      3 Encapsulation. nested dependencies, complicated process, put in factory, easier to maintain, avoid code duplication.
 *                        you can't have direct access to them,
 *                        using the factory to act as a knowledge center for producing needed objects.
 *
 *      4 Disambiguation:  multiple constructors (with very different behaviors)
 *                         making many different constructor easy to read.
 *      cases:
 *          String.valueOf()
 *          Logger log = LoggerFactory.getLogger(MyClass.class);
 *
 *
 *    2 Factory Method:
 *       “Define an interface for creating an object,
 *        but let subclasses decide which class to instantiate.
 *        Factory Method lets a class defer instantiation to subclasses” - Gang of Four
 *
 *       the client maintains a reference to the abstract Creator,
 *       but instantiates it with one of the subclasses.
 *       Note that the client doesn’t really know what the type of the object is,
 *       just that it is a child of the parent.
 *       may have a abstract class layer
 *
 *        client doesn't know what concrete classes it will be required to create at runtime,
 *        but just wants to get a class that will do the job.
 *
 *        testable. as you can mock interfaces
 *
 *        Cases:
 *        Iterator
 *
 *        The Spring Framework is based on a factory method pattern.
 *        The ApplicationContext implements the BeanFactory Interface
 *        JDBC uses factory methods to get Connections, Statements, and other objects to work with.
 *        Which gives you flexibility to change your back-end database without changing your DAO layer
 *
 *    3 Abstract Factory:
 *        “Provide an interface for creating families of related
 *        or dependent objects without specifying their concrete classes” - Gang of Four
 *
 *        the factory interface have multiple factory methods that are conceptually linked
 *        create a family of related products
 *        When to use: "a system should be configured with one of multiple families of products,
 *        you want to provide a class library of products, and you want to reveal just their interfaces,
 *        not their implementations".- Gang of Four
 *
 *         DAO (Data Access Object) frameworks; insert();update();delete();read()
 *         GWT-ORM?
 *         CrudRepository from Spring
 *         Abstract Factory creates objects through composition.
 *   Relation
 *         Abstract Factory classes are often implemented with Factory Methods,
 *         but they can be implemented using Prototype.
 *         Factory Methods are usually called within Template Methods.
 *         Factory Method: creation through inheritance. Prototype: creation through delegation.
 *
 */
package design_pattern.factory;