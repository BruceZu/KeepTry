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
 *     A lambda expression provides a way to represent a one-method interface using an expression.
 *     A lambda expression is a block of code that you can pass around so it can be executed later
 *
 *  three examples of typical scenarios in Java that can now be replaced by Lambda expressions
 *
 * 1 In Swing GUI programming, when a button is clicked, a callback action is invoked.
 *  The callback action is typically implemented in an anonymous listener class or
 *  by constructing an instance of a class that implements the listener interface.
 *
 * 2 When work needs to be executed in a separate thread, that work can be put into the run method of an anonymous Runnable class
 * or a class implementing the Runnable interface and providing a concrete implementation of the run() method can be
 * instantiated and passed to a Thread object.
 *
 * 3 In the Collections API, a Comparator object is passed, for example, to a sort() method. The Comparator interface is
 * implemented in an anonymous class typically.
 *
 * Such interfaces depicted in the above examples are called Functional Interfaces.
 * A Functional Interface is an interface with just one abstract method declared in it.
 * A lambda expression can be provided whenever an object of such a functional interface
 * with a single abstract method is expected.
 * A Lambda expression is thus a method without a true declaration.
 * There is no need for a name, return value, or access modifier.
 *
 * There is a subtle difference between an anonymous class and Lambda expression,
 * 1  which is the use of ‘this’ keyword.
 * For a lambda expression ‘this’ keyword resolves to the enclosing class where the lambda expression is written.
 * For an anonymous class ‘this’ keyword resolves to anonymous class.
 *
 * 2 Another difference between a lambda expression and anonymous class is
 * in the way these two are compiled.
 * The Java compiler compiles a lambda expression and converts it into a private method of the class.
 * It uses the invokedynamic instruction that was added in Java 7 to bind this method dynamically.
 */
package lambda;