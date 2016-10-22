/**
 * <pre>
 * hash function
 *   1
 *   2
 *
 * when there are many data saved in hashmap, the performance....
 *
 * ====== table size
 * The table, initialized on first use, and resized as
 * necessary. When allocated, <strong>length is always a power of two.</strong>
 * (We also tolerate length zero in some operations to allow
 * bootstrapping mechanics that are currently not needed.)
 *
 * transient Node<K,V>[] table;
 *
 * static final int tableSizeFor(int cap) {
 * int n = cap - 1;
 * n |= n >>> 1;
 * n |= n >>> 2;
 * n |= n >>> 4;
 * n |= n >>> 8;
 * n |= n >>> 16;
 * return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
 * }
 *
 * ======how to calculate hash
 *
 * ======how to map hash to index of table
 *   n = tab.length
 *   Node<K,V> first = tab[(n - 1) & hash]
 *
 * ======what is rehash and when?
 *  load factor of around 0.75
 *
 *  ======Java 8:
 *  Hash collisions have negative impact on the lookup time of HashMap.
 *   TreeNode
 *   Java 8 has come with the following improvements/changes of HashMap objects in case of high collisions.
 *     -- The alternative String hash function added in Java 7 has been removed.
 *     -- Buckets containing a large number of colliding keys will store their entries in
 *        <strong>a balanced tree</strong> instead of a linked list after certain threshold is reached.
 *        A poor hash function that always returns
 *        the same bucket can effectively turn a HashMap into a linked list.
 *        Performance degrades to O(n) instead of O(1).This ensures performance of order O(log(n))
 *        even in worst ​​case scenarios where the hash function is not distributing keys properly.
 *
 *        In Java 8, HashMap replaces linked list with a binary tree
 *        when the number of elements in a bucket reaches certain threshold.
 *             TREEIFY_THRESHOLD = 8
 *        HashMap promotes list into binary tree, using
 *        hash code as a branching variable.
 *        If two hashes are different but ended up in the same bucket, one is considered bigger
 *        and goes to the right.
 *        If hashes are equal, HashMap hopes that the keys are Comparable,
 *        so that it can establish some order.
 *        This is not a requirement of HashMap keys,
 *        but apparently a good practice.
 *        <strong>If keys are not comparable,
 *        don't expect any performance improvements in case of heavy hash collisions.</strong>

 *        This JDK 8 change applies only to HashMap, LinkedHashMap and ConcurrentHashMap.
 *
 *  @see <a href="http://openjdk.java.net/jeps/180">JEP 180</a>
 *  @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html">Oracle doc</a>
 */
package design_HashMap;