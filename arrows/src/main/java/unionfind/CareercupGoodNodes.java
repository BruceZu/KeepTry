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

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * Google Interview Question for SDE is
 * <a href="https://www.careercup.com/question?id=5840928073842688">career cup</a>
 *
 *
 * objective is to form a tree with node 1 as the root.
 * Resolution Idea: Union Find.
 */
public class CareercupGoodNodes {
    public static int getMinimumNumber(int[] nextNodeIdOf) {
        int minimumNumber = 0;
        if (nextNodeIdOf[0] != 0) {// assume node 1's id is 0
            minimumNumber++;
            nextNodeIdOf[0] = 0;
        }

        minimumNumber += rootsOf(nextNodeIdOf) - 1;//all the connected components except for the one that contains 1
        return minimumNumber;
    }

    // number of connected components
    // all the connected components except for the one that contains 1
    // if it is back-hole shape graph, not a tree.
    // need to break its circle at the circle's any edge
    // to translate it into a tree.
    public static int rootsOf(int[] nextNodeIdOf) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < nextNodeIdOf.length; i++) {
            int cur = i;
            int next = nextNodeIdOf[i];

            Set<Integer> visited = new HashSet<>();
            visited.add(i);
            while (cur != next) {
                if (visited.contains(next)) {// black hole shape graph. every node always points to other one.
                    nextNodeIdOf[cur] = cur;
                    break; // care
                } else {
                    visited.add(next); // care: add it to visited set
                }

                cur = next;
                next = nextNodeIdOf[cur]; // care update current node id and next node id
            }
            result.add(cur);
        }
        return result.size();
    }

    // -------------------------------------------------------
    public static void main(String[] args) {
        int[] nextNodeIdOf = new int[]{0, 1, 2, 3, 4};
        System.out.println(getMinimumNumber(nextNodeIdOf));
        nextNodeIdOf = new int[]{4, 4, 4, 4, 4};
        System.out.println(getMinimumNumber(nextNodeIdOf));
        nextNodeIdOf = new int[]{1, 2, 0, 1, 2, 0, 7, 7};
        System.out.println(getMinimumNumber(nextNodeIdOf));
    }
}
