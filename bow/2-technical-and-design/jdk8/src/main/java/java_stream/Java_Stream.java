package java_stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Differences between a Stream and a Collection:
 *      A stream does not store data.
 *      An operation on a stream does not modify its source, but simply produces a result.
 *      Collections have a finite size, but streams do not.
 *      Like an Iterator, a new stream must be generated to revisit the same elements of the source.
 *
 * We can obtain Streams in the following ways:
 *      From a Collection via the stream() and parallelStream() methods;
 *      From an array via Arrays.stream(Object[]);
 *      From static factory methods on the stream classes,
 *         such as Stream.of(Object[]);
 *         IntStream.range(int, int) o
 *         Stream.iterate(Object, UnaryOperator);
 *     The lines of a file from BufferedReader.lines();
 *     Streams of file paths from the methods in Files;
 *     Streams of random numbers from Random.ints();
 *     Other stream-bearing methods:
 *         BitSet.stream(),
 *         Pattern.splitAsStream(java.lang.CharSequence),
 *         JarFile.stream().
 *
 *  Perspectives >
 * Java 8 – An Introductory article to Java Streams API
 * 3/5/2015 , written by Manumeet Mukund
 * Categories: Java Modernization
 * Java Streams APICollections are the fundamental to many programming tasks in Java. However, despite being necessary for almost any Java application, manipulating collections is a nightmare in some cases, especially with a large collection of elements. In Java 8, the language designers have made programmer’s life easier by in​troducing Streams. With the introduction of Stream API, programmer can manipulate collections of data in a declarative​​​ way. In addition to this, stream can be processed in parallel without writing multithreading logic.
 *
 * Background
 *
 * Prior to JDK 8, Collections could only be managed through iterators with the use of for, for-each or while loops. It means that the computer is instructed to execute the algorithm steps.
 *
 *
 *
 * The above approach has following tailbacks:​
 *
 * We need to express how the iteration will take place, this requires an external iteration as the client program to handle the traversal.
 * The program is sequential; there is no way to do it in parallel.
 * The code is verbose.
 * Java 8 - Stream APIs
 *
 * Java 8 introduces Stream API to circumvent the above-mentioned shortcomings. It also allows us to leverage the other changes, e.g., lambdaexpression, method reference, functional interface and internal iteration introduced via forEach () method.
 *
 * The above logic of the program can be rewritten in a single line:
 *
 * The Streamed content is filtered with the Filter method, which takes a predicate as its argument. The Filter method returns a new instance of Stream<T>. The mapfunction is used to transform each element of the stream into a new element. The map returns a Stream<R>, in the above example, we used mapToInt;to get the IntStream. Finally, IntStream has a sum operator, which calculates the sum of all elements in the stream, and we get the expected result so cleanly.
 *
 * API overview
 *
 * The chief type in the JDK 8 Stream API package java.util.stream is the Stream interface. The types IntStream, LongStream, and DoubleStream are streams over objects and the primitive int, long and double types.
 *
 * Differences between a Stream and a Collection:
 *
 * A stream does not store data.
 * An operation on a stream does not modify its source, but simply produces a result.
 * Collections have a finite size, but streams do not.
 * Like an Iterator, a new stream must be generated to revisit the same elements of the source.
 * We can obtain Streams in the following ways:
 *
 * From a Collection via the stream() and parallelStream() methods;
 * From an array via Arrays.stream(Object[]);
 * From static factory methods on the stream classes, such as Stream.of(Object[]);IntStream.range(int, int) or Stream.iterate(Object, UnaryOperator);
 * The lines of a file from BufferedReader.lines();
 * Streams of file paths from the methods in Files;
 * Streams of random numbers from Random.ints();
 * Other stream-bearing methods: BitSet.stream(), Pattern.splitAsStream(java.lang.CharSequence), and JarFile.stream().
 *
 * Stream Operations:
 *
 * 1 Intermediate Operations
 *      Stateless Operations,
 *       such as filter and map, retain no state from previous element.
 *      Stateful Operations,
 *        such as distinct and sorted, may have state from the previous elements.
 * 2 Terminal Operations
 *   Operations, such as Stream.forEach or IntStream.sum,
 *   may traverse the stream to produce a result or a side-effect.
 *   When the terminal operation is performed,
 *   the stream pipeline is considered consumed, and can no longer be used.
 *
 * 3 Reductional Operations
 *    are reduce () and collect () and the special ones: sum (), max (), or count ().
 *
 * 4 Parallelism
 */
public class Java_Stream {

    /**
     * <pre>
     * ---benefit of stream ---
     * tailbacks:​
     *      We need to express how the iteration will take place, this requires an external iteration.
     *      The program is sequential; there is no way to do it in parallel.
     *      The code is verbose.
     */
    public static int f(List<Integer> l) {
        Iterator<Integer> it = l.iterator();
        int r = 0;
        while (it.hasNext()) {
            Integer i = it.next();
            if (i > 5) {
                r += i;
            }
        }
        return r;
    }

    public static int fStream(List<Integer> l) {
        int sumOfBigger6 = l.stream().reduce(0, (r, e) -> {
            if (e > 6) {
                r += e;
            }
            return r;
        });
        int sum = l.stream().reduce(0, Integer::sum);
        return sum - sumOfBigger6;
    }

    public static int fStream2(List<Integer> l) {
        int sumOfBigger6 = l.stream().filter(t -> t > 6).reduce(0, (a, b) -> a += b);
        int sum = l.stream().reduce(0, Integer::sum);
        return sum - sumOfBigger6;
    }

    public static int fStream3(List<Integer> l) {
        int sumOfBigger6 = l.stream().filter(t -> t > 6).mapToInt(e -> e).sum();
        int sum = l.stream().reduce(0, Integer::sum);
        return sum - sumOfBigger6;
    }

    /*---operations---*/
    public static void operations() {
        List<String> l = Arrays.asList("Spring", "Hibernate", "ReactJS", "AngularJS", "Ab", "abc", "bc", "ab");
        l.stream().sorted().forEach(e -> {
            System.out.println(e);
        });

        l.stream().filter(e -> e.toLowerCase().startsWith("a")).forEach(e -> {
            System.out.println(e);
        });
        int sum = Arrays.asList(2, 4, 2, 1, 1, 3).stream().map(e -> e * e).distinct().sorted().mapToInt(e -> e).sum();
        List<Integer> r = Arrays.asList(2, 4, 2, 1, 1, 3).stream().map(e -> e * e).distinct().sorted().collect(Collectors.toList());
    }
}
