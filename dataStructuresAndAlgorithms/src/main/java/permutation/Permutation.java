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

package permutation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Permutation {

    // list all k or <=k size permutation of n unique elements.
    // n can be bigger than 64.
    // O(K^N) running time.
    //
    //      1  2  3
    //   1  .
    //   2     .
    //   3         .
    //
    //      1  2  3
    //   1  .
    //   2        .
    //   3     .
    //
    //      1  2  3
    //   1     .
    //   2  .
    //   3        .
    //
    //      1  2  3
    //   1        .
    //   2  .
    //   3     .
    //
    //      1  2  3
    //   1     .
    //   2        .
    //   3  .
    //
    //      1  2  3
    //   1        .
    //   2     .
    //   3  .


    public static void permutation(List<Integer> l /* unique list */,
                                   int pSize,
                                   LinkedList<Integer> p, /* permutation */
                                   boolean only /* only show permutations with pSize, or all cases with size <= pSize  */,
                                   Set<List<Integer>> r /* result */) {
        if (l == null) {
            r = null;
            return;
        }
        if (l.size() == 0 || pSize == 0) {
            return;
        }
        // init
        if (p == null) {
            assert 0 < pSize && pSize <= l.size();
            p = new LinkedList<>();
        }
        // start
        for (int e : l) {
            if (p.contains(e)) {
                continue;
            }
            p.add(e);
            if (!only) {
                r.add((List<Integer>) p.clone());
            } else if (p.size() == pSize) {
                r.add((List<Integer>) p.clone());
            }
            if (p.size() < pSize) {
                permutation(l, pSize, p, only, r);
            }
            p.remove(p.size() - 1);
        }
    }

    private static void print(Set<List<Integer>> result) {
        for (List<Integer> i : result) {
            System.out.println(Arrays.toString(i.toArray()));
        }
    }

    public static void main(String[] args) {
        Set<List<Integer>> result = new HashSet<>();
        permutation(Arrays.asList(1, 2, 3), 2, null, false, result);
        print(result);
        System.out.println("---");

        result = new HashSet<>();
        permutation(Arrays.asList(1, 2, 3), 2, null, true, result);
        print(result);
        System.out.println("---");

        result = new HashSet<>();
        permutation(Arrays.asList(1, 2, 3), 3, null, true, result);
        print(result);
        System.out.println("---");

        result = new HashSet<>();
        permutation(Arrays.asList(1), 1, null, false, result);
        print(result);
    }
}
