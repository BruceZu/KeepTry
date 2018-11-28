//  Copyright 2018 The KeepTry Open Source Project
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Use method references if they make your code CLEARER.
 *
 * <p>For example, you can avoid the one method restriction by grouping all your code in a static
 * method, and create a reference to that method instead of using a class or a lambda expression
 * with many lines.
 *
 * <a href="https://www.codementor.io/eh3rrera/using-java-8-method-reference-du10866vx">reference</a>
 * To use a method reference, first need a lambda expression with one method.
 * And to use a lambda expression, first need a functional interface, an interface with just one
 * abstract method.
 *
 * <p>In other words:
 *
 * <pre>
 * Instead of using
 * AN ANONYMOUS CLASS
 *
 * you can use
 * A LAMBDA EXPRESSION
 *
 * And if this just calls
 * one method, you can use
 * A METHOD REFERENCE
 *
 * There are four types of method references:
 *
 * 1> A method reference to a static method.
 *
 *      (args) -> Class.staticMethod(args)
 *      Class::staticMethod
 *
 *  instead of the . operator, we use the :: operator,
 *  and that we don't pass arguments to the method reference.
 *  In general, we don't have to pass arguments to method references.
 *  However, arguments are treated depending on the type of method reference.
 *  In this case, any arguments (if any) taken by the method are passed automatically behind the curtains.
 *  Where ever we can pass a lambda expression that just calls a static method,
 *  we can use a method reference.
 *
 * 2> A method reference to an instance method of an object of a particular type.
 *      (obj, args) -> obj.instanceMethod(args)
 *      ObjectType::instanceMethod
 *
 *  Where an instance of an object is passed, and one of its methods is executed with some optional(s) parameter(s).
 *  This time, the conversion is not that straightforward. First, in the method reference,
 *  we don't use the instance itself—we use its type.
 *  we don't pass any arguments to the method.
 *  an instance of the object is the parameter of the lambda expression.
 *
 * 3> A method reference to an instance method of an existing object.
 *      (args) -> obj.instanceMethod(args)
 *      obj::instanceMethod
 *
 *   an instance defined somewhere else is used, and the arguments (if any) are
 *   passed behind the curtains like in the static method case.
 *   use any object visible by an anonymous class/lambda expression
 *
 * 4> A method reference to a constructor.
 *
 *      (args) -> new ClassName(args)
 *      ClassName::new
 *  create a new object and we just reference a constructor of the class with the keyword new.
 *  Like in the other cases, arguments (if any) are not passed in the method reference.
 *  the constructor "method name" is new
 */
public class MethodReferences {
    public static void main(String[] args) {
        Consumer<String> c = s -> System.out.println(s);
        c = System.out::println;

        // 4 >
        // constructor takes no argument
        // Using an anonymous class
        Supplier<List<String>> s =
                new Supplier<List<String>>() {
                    @Override
                    public List<String> get() {
                        return new ArrayList<String>();
                    }
                };
        List<String> l = s.get();

        // Using a lambda expression
        s = () -> new ArrayList<String>();
        l = s.get();

        // Using a method reference
        s = ArrayList::new;
        l = s.get();

        // constructor takes 1 argument
        // Using an anonymous class
        Function<String, Integer> f =
                new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        return new Integer(s);
                    }
                };
        Integer i = f.apply("100");

        // Using a lambda expression
        f = p -> new Integer(p);
        i = f.apply("100");

        // Using a method reference
        f = Integer::new;
        i = f.apply("100");

        // constructor takes two arguments
        // Using a anonymous class
        BiFunction<String, String, java.util.Locale> bf =
                new BiFunction<String, String, Locale>() {
                    @Override
                    public Locale apply(String lang, String country) {
                        return new Locale(lang, country);
                    }
                };
        java.util.Locale loc = bf.apply("en", "UK");

        // Using a lambda expression
        bf = (lang, country) -> new Locale(lang, country);
        loc = bf.apply("en", "UK");

        // Using a method reference
        bf = Locale::new;
        loc = bf.apply("en", "UK");
    }
}

// 1>
class Numbers {
    public static boolean isMoreThanFifty(int n1, int n2) {
        return (n1 + n2) > 50;
    }

    public static List<Integer> findNumbers(List<Integer> l, BiPredicate<Integer, Integer> p) {
        List<Integer> newList = new ArrayList<>();
        for (Integer i : l) {
            if (p.test(i, i + 10)) {
                newList.add(i);
            }
        }
        return newList;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(12, 5, 45, 18, 33, 24, 40);

        // Using an anonymous class
        findNumbers(
                list,
                new BiPredicate<Integer, Integer>() {
                    @Override
                    public boolean test(Integer i1, Integer i2) {
                        return Numbers.isMoreThanFifty(i1, i2);
                    }
                });

        // Using a lambda expression
        findNumbers(list, (i1, i2) -> Numbers.isMoreThanFifty(i1, i2));

        // Using a method reference
        findNumbers(list, Numbers::isMoreThanFifty);
    }
}
// 2-1>
class Shipment {
    public double calculateWeight() {
        double weight = 0;
        // Calculate weight
        return weight;
    }

    public static List<Double> calculateOnShipments(
            List<Shipment> l, Function<Shipment, Double> f) {
        List<Double> results = new ArrayList<>();
        for (Shipment s : l) {
            results.add(f.apply(s));
        }
        return results;
    }

    public static void main(String[] args) {
        List<Shipment> l = new ArrayList<Shipment>();

        // Using an anonymous class
        calculateOnShipments(
                l,
                new Function<Shipment, Double>() {
                    @Override
                    public Double apply(Shipment s) { // The object
                        return s.calculateWeight(); // The method
                    }
                });

        // Using a lambda expression
        calculateOnShipments(l, s -> s.calculateWeight());

        // Using a method reference
        calculateOnShipments(l, Shipment::calculateWeight);
    }
}

// 2-2>
interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}

class Sum {
    Integer doSum(String s1, String s2) {
        return Integer.parseInt(s1) + Integer.parseInt(s1);
    }

    public static void main(String[] args) {
        TriFunction<Sum, String, String, Integer> lambda =
                new TriFunction<Sum, String, String, Integer>() {
                    @Override
                    public Integer apply(Sum s, String arg1, String arg2) {
                        return s.doSum(arg1, arg1);
                    }
                };
        System.out.println(lambda.apply(new Sum(), "1", "4"));

        lambda = (Sum s, String arg1, String arg2) -> s.doSum(arg1, arg1);
        System.out.println(lambda.apply(new Sum(), "1", "4"));

        lambda = Sum::doSum; //  using type not object instance
        System.out.println(lambda.apply(new Sum(), "1", "4"));
    }
}

// 3 >

class Car {
    int id;
    protected String color;
    // More properties
    // And getter and setters
}

class Mechanic {
    public void fix(Car c) {
        System.out.println("Fixing car " + c.id);
    }

    public static void execute(Car car, Consumer<Car> c) {
        c.accept(car);
    }

    public static void main(String[] args) {

        final Mechanic mechanic = new Mechanic();
        Car car = new Car();

        // Using an anonymous class
        execute(
                car,
                new Consumer<Car>() {
                    @Override
                    public void accept(Car c) {
                        mechanic.fix(c);
                    }
                });

        // Using a lambda expression
        execute(car, c -> mechanic.fix(c));

        // Using a method reference
        execute(car, mechanic::fix);

        Consumer<String> c = System.out::println;
        c.accept("Hello");
    }
}
