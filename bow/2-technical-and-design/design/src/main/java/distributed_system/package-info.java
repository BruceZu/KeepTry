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