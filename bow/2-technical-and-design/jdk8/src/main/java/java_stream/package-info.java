/**
 * <pre>
 * Java 8  enhance the Collections framework and related APIs.
 * Functionality was added to perform bulk operations on data.
 * The bulk data operations include both
 * serial (on the calling thread) and
 * parallel (using many threads) versions of the operations.
 *
 * Lambda expressions are generally used to express these operations.
 *
 * These automatically parallelizable bulk data operations are also commonly referred to as
 * "filter/map/reduce”.
 *
 * The java.util.stream API has been added to Java 8.
 * The Collection interface has been extended with stream() and parallelStream() default methods
 * to get the Stream for sequential and parallel execution.
 *
 * The serial implementation helps bridge operations on existing collections
 * to parallel operations that do not change the threading model of the application.
 *
 * The parallel implementation provides the opportunity to accelerate operations upon
 * large amounts of data by
 * dividing the task between multiple threads (processors).
 * This builds upon the java.util.concurrency Fork / Join features introduced in Java 7.
 *
 * For both the serial and parallel implementations
 * an "eager" mode and a "lazy" mode are possible.
 *
 * In eager mode operations upon data are performed directly upon the data at the time the operation function is invoked.
 * In lazy mode the operations upon the data are deferred until the final result is requested.
 *
 * Lazy mode operation allows the implementation more optimization opportunities
 * based upon reorganization of the data
 * and operations to be performed.
 */
package java_stream;