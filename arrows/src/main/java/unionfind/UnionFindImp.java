//  Copyright 2019 The KeepTry Open Source Project
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UnionFindImp<E> implements UnionFind<E> {
    private Relation<E> relation;
    private Rank<E, Integer> rank;

    private void initial(Set<E> elements) {
        Map<E, E> map = new HashMap(elements.size());
        Map<E, Integer> mapRank = new HashMap(elements.size());

        elements.stream()
                .forEach(
                        o -> {
                            map.put(o, o);
                            mapRank.put(o, 0);
                        });
        relation =
                new Relation<E>() {
                    @Override
                    public E next(E o) {
                        return map.get(o);
                    }

                    @Override
                    public void setNext(E key, E value) {
                        map.put(key, value);
                    }
                };

        rank =
                new Rank<E, Integer>() {
                    @Override
                    public Integer rank(E e) {
                        return mapRank.get(e);
                    }

                    @Override
                    public void increase(E key) {
                        mapRank.put(key, mapRank.get(key) + 1);
                    }
                };
    }

    public UnionFindImp(final Set<E> elements) {
        initial(elements);
    }

    /**
     * Using both 'path compression' and 'union by rank' ensures that the amortized time per
     * operation is only O(alpha (n)). alpha (n) is the inverse Ackermann function. This function
     * has a value alpha (n)<5 for any value of n that can be written in this physical universe, so
     * the disjoint-set operations take place in essentially constant time.
     */
    // O(alpha (n))
    // prefer path compression to path halving or path splitting
    public E find(E v) {
        if (relation.next(v) != v) {
            relation.setNext(v, find(relation.next(v)));
        }
        return relation.next(v);
    }

    // O(alpha (n))
    // union by rank
    public void union(E r1, E r2) {
        r1 = find(r1);
        r2 = find(r2);
        if (r1 == r2) return;
        E el /* equal or less than*/, o /* other*/;
        if (rank.rank(r1) <= rank.rank(r2)) {
            el = r1;
            o = r2;
        } else {
            el = r2;
            o = r1;
        }

        relation.setNext(el, o);
        if (rank.rank(el) == rank.rank(o)) {
            rank.increase(o);
        }
    }
}
