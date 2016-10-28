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

package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <pre>
 * Lambda expressions can be considered as anonymous methods—methods without a name.
 * With lambda it saved
 *  - anonymous sub class of {@link java.util.function.Predicate Predicate} or same like class
 *    with only one abstract method which takes only one argument of type Person.
 *  - interface code out of {},
 *  - the {}, If you specify a single expression, then the Java runtime evaluates the expression and
 *  <strong>then returns</strong> its value. Alternatively, you can use a return statement,
 *  A return statement is not an expression; in a lambda expression, you must enclose statements in braces ({}).
 *  However, you do not have to enclose a void method invocation in braces.
 *  The basic syntax of a lambda is:
 *      either
 *           (parameters) -> expression
 *      or
 *           (parameters) -> { statements; }
 *
 * Aggregate operations. filter, map, and forEach are aggregate operations.
 * Aggregate operations process elements from a stream, not directly from a collection
 *
 * A stream carries values from a source, such as collection, through a pipeline.
 *
 * A pipeline is a sequence of stream operations, which in this example is filter-> map->forEach.
 *
 * Aggregate operations typically accept lambda expressions as parameters,
 * enabling you to customize how they behave.
 *
 *  Aggregate operations and parallel streams enable you to implement parallelism with non-thread-safe collections
 *  provided that you do not modify the collection while you are operating on it.
 *
 *  see {@link java.lang.invoke.InnerClassLambdaMetafactory InnerClassLambdaMetafactory}
 *  see {@link Package java.lang.invoke invoke}
 *
 *  @see <a href="http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html"> jdk 8 whats new</a>
 *
 *  @see <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#syntax"> syntax of Lambda Expressions</a>
 *
 *  @see <a href="https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html"> Parallelism </a>
 *
 *  @see <a href="http://cr.openjdk.java.net/~briangoetz/lambda/lambda-translation.html"> Translation of Lambda Expressions April 2012 </a>
 */
public class Person {

    enum Sex {
        MALE, FEMALE
    }

    public static <P, N> void printPerson(List<P> roster,
                                          Predicate<P> test,
                                          Function<P, N> mapper,
                                          Consumer<N> s) {
        for (P p : roster) {
            if (test.test(p)) {
                s.accept(mapper.apply(p));
            }
        }

        Map<Sex, List<P>> byGender = roster.stream()
                .collect(Collectors.groupingBy(p -> ((Person) p).gender));
    }

    interface Service<T> {
        void provide(T p);
    }

    String name;
    Sex gender;

    public static Sex getGender(Person p) {
        return p.gender;
    }

    public Person(String name, Sex gender) {
        this.name = name;
        this.gender = gender;
    }

    public static void main(String[] args) {
        // 1 one parameter
        List<Person> persons = Arrays.asList(new Person[]{
                new Person("Jack", Sex.MALE),
                new Person("Jenny", Sex.FEMALE),
                new Person("Alice", Sex.FEMALE)});

        printPerson(persons,
                p -> p.gender.equals(Sex.FEMALE),
                p -> p.name,
                n -> System.out.println(n));

        // aggregate operations
        persons.stream()
                .filter(p -> p.gender.equals(Sex.FEMALE))
                .map(p -> p.name)
                .forEach(n -> System.out.println(n));
    }

    static class Calculator {
        public int operateBinary(int a, int b, IntegerMath op) {
            return op.operation(a, b);
        }

        interface IntegerMath {
            int operation(int a, int b);
        }

        public static void main(String[] args) {
            // 2 more parameters
            Calculator myApp = new Calculator();
            IntegerMath addition = (a, b) -> a + b;
            IntegerMath subtraction = (a, b) -> a - b;
            System.out.println("40 + 2 = " + myApp.operateBinary(40, 2, addition));
            System.out.println("20 - 10 = " + myApp.operateBinary(20, 10, subtraction));
        }
    }

    static class LambdaScopeTest {
        public int x = 0;

        class FirstLevel {
            public int x = 1;

            void methodInFirstLevel(int x) {

                /**
                 <pre>
                 The following statement causes the compiler to generate
                 the error in statement A:
                 "local variables referenced from a lambda expression must be <strong> final or effectively final</srong>"
                 */
                // x = 99;

                int z = 9;
                Consumer<Integer> myConsumer = (y) -> {
                    // int z=10; // Statement B
                    //
                    // substitute the parameter x in place of y in the declaration or add Statement B
                    // then the compiler generates an error:
                    // The compiler generates the error "variable is already defined..."
                    // because the lambda expression does not introduce a new level of scoping
                    System.out.println("x = " + x); // Statement A
                    System.out.println("y = " + y);
                    System.out.println("this.x = " + this.x);
                    System.out.println("LambdaScopeTest.this.x = " + LambdaScopeTest.this.x);

                };
                myConsumer.accept(x + 1);
            }
        }

        static void doit(Runnable r) {
            r.run();
        }

        static <T> T doit(Callable<T> c) throws Exception {
            return c.call();
        }

        public static void main(String... args) throws Exception {
            // 3 Accessing Local Variables of the Enclosing Scope
            LambdaScopeTest st = new LambdaScopeTest();
            LambdaScopeTest.FirstLevel fl = st.new FirstLevel();
            fl.methodInFirstLevel(23);

            // 4 Target Types
            doit(() -> {
                String s1 = "done";
                String s2 = "all done";
            });

            doit(() -> "done");
            doit(() -> {
                String s1 = "done";
                String s2 = "all done";
                return s1 + s2;
            });
        }
    }

    // As interface function
    interface Foo {
        int exec(int x);
    }

    class Bar {
        public Foo action2() {
            return (x) -> x + 2;
            /*return new Foo() {
                @Override
                public int exec(int x) {
                    return  x + 2;
                }
            };*/
        }
    }
    /**
     * lambda <a href="http://cr.openjdk.java.net/~briangoetz/lambda/lambda-state-4.html"> link </a>
     * defender method <a href="http://cr.openjdk.java.net/~briangoetz/lambda/Defender%20Methods%20v4.pdf"> link </a>
     */
}
