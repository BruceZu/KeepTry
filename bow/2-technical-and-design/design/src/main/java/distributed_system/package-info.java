/**
 * <pre>
 *
 * Zookeeper:
 * Chapter 14 of <<Hadoop: The Definitive Guide>>
 * which has ~35 pages explaining essentially what ZooKeeper does,
 * followed by an example of a configuration service.
 *
 * Zookeeper is a CP system (Refer CAP Theorem) that provides Consistency and Partition tolerance.
 * Replication of Zookeeper state across all the nods makes it an eventually consistent distributed service.
 *
 * any newly elected leader will updates its followers with missing proposals or with a snap shot of the state,
 * if the followers has many proposals missing.
 * <a href="http://stackoverflow.com/questions/3662995/explaining-apache-zookeeper">zookeeper</a>
 * <hi><a href="https://www.quora.com/What-is-the-actual-role-of-ZooKeeper-in-Kafka">ZooKeeper-in-Kafka</a>
 * <hi><a href="http://massivetechinterview.blogspot.com/2015/08/kafka-internal.html">kafka-internal</a>
 */
package distributed_system;