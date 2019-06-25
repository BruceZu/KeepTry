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

package unionfind;

/**
 * <pre>
 *     scenario: disjoint sets. e.g. islands, groups of machines
 *     data structure:
 *                int[] or HashMap to keep:
 *                all nodes' id/tag: 0 ~ array length -1
 *
 * with Pass Compress the tree will be updated eventually to be end up with tree height of 1.
 *        n1
 *      / |  \
 *  n2 n3 .... n7 n8
 *  <a href="https://en.wikipedia.org/wiki/Disjoint-set_data_structure">wiki<a/>
 *  "Using both path compression, splitting, or halving and union by rank or size ensures
 *  that the amortized time per operation is only alpha (n)
 *  which is optimal, where  alpha (n) is the inverse Ackermann function.
 *  This function has a value  alpha (n)<5
 *  for any value of n that can be written in this physical universe,
 *  so the disjoint-set operations take place in essentially constant time."
 */
interface Relation<E> {
    E next(E e);

    void setNext(E key, E value);
}

interface Rank<E, Integer> {
    Integer rank(E e);

    void increase(E key);
}

public interface UnionFind<E> {
    E find(E e);

    void union(E e1, E e2);
}
