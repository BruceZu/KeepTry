/**
 * ConcurrentHashMap
 * <pre>
 * ConcurrentHashMap is specially designed for concurrent use i.e. more than one thread.
 * By default it simultaneously allows 16 threads to read and write from Map
 * without any external synchronization.
 *
 * It is also very scalable because of stripped locking technique
 * used in the internal implementation of ConcurrentHashMap class.
 *
 * Unlike Hashtable and Synchronized Map, it never locks whole Map, instead,
 * it divides the map into segments and locking is done on those.
 *
 * Though it performs better if a number of reader threads are greater than
 * the number of writer threads.
 *
 * ===
 * Collections.synchronizedMap() and Collections.synchronizedList(),
 * provide a basic conditionally thread-safe implementation of Map and List.
 * However, several factors make them unsuitable for use in highly concurrent applications,
 * for example, their single collection-wide lock is an impediment to scalability and it
 * often becomes necessary to lock a collection for a considerable time during iteration
 * to prevent ConcurrentModificationException.
 *
 * ConcurrentHashMap and CopyOnWriteArrayList implementations provide
 * much higher concurrency while preserving thread safety,
 * with some minor compromises in their promises to callers.
 *
 * ConcurrentHashMap and CopyOnWriteArrayList are not necessarily useful everywhere
 * you might use HashMap or ArrayList, but are designed to optimize specific common situations.
 *
 * Many concurrent applications will benefit from their use.
 *
 * ConcurrentHashMap introduced the concept of segmentation,
 * how large it becomes only certain part of it get locked to provide thread safety so many other readers
 * can still access map without waiting for iteration to complete.
 * ==
 * CopyOnWriteArrayList is a concurrent Collection class introduced in Java 5 Concurrency API
 * along with its popular cousin ConcurrentHashMap in Java.
 *
 * CopyOnWriteArrayList implements List interface like ArrayList, Vector and LinkedList
 * but its a thread-safe collection and it achieves its thread-safety in a slightly different way
 * than Vector or other thread-safe collection class.
 *
 * As name suggest CopyOnWriteArrayList creates copy of underlying ArrayList with every
 * mutation operation e.g. add or set.
 *
 * Normally CopyOnWriteArrayList is very expensive because it involves costly Array copy
 * with every write operation
 *
 * but its very efficient if you have a List where Iteration outnumber mutation
 *
 * e.g. you mostly need to iterate the ArrayList and don't modify it too often.
 *
 * Iterator of CopyOnWriteArrayList is fail-safe and doesn't throw ConcurrentModificationException
 * even if underlying CopyOnWriteArrayList is modified once Iteration begins
 *
 * because Iterator is operating on separate copy of ArrayList.
 * Consequently all the updates made on CopyOnWriteArrayList is not available to Iterator.
 *
 * CopyOnWriteArrayList is a thread-safe collection while ArrayList is not thread-safe
 *
 * Iterator of ArrayList is fail-fast and throw ConcurrentModificationException once detect any modification
 * in List once iteration begins
 * but Iterator of CopyOnWriteArrayList is fail-safe and doesn't throw ConcurrentModificationException.
 *
 *
 *  Iterator of CopyOnWriteArrayList doesn't support remove operation while
 *  Iterator of ArrayList supports remove() operation.
 *
 *
 */
package map;