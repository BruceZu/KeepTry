//  Copyright 2017 The keepTry Open Source Project
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

package list;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MergeSortedLists {

    /**
     * Assume the sub-lists are sorted.
     * O(n*logk) k is the number of sub lists
     */
    static public List<Integer> merge(List<List<Integer>> toBeMerged) {
        // Todo: input check
        Queue<IntegeriWithItsList> q = new PriorityQueue();
        // init
        for (List<Integer> l : toBeMerged) {
            q.offer(new IntegeriWithItsList(l, 0));
        }
        List<Integer> result = new ArrayList<>();

        while (!q.isEmpty()) {
            IntegeriWithItsList head = q.poll();
            result.add(head.list.get(head.indexOfV));

            if (head.indexOfV + 1 < head.list.size()) {
                head.indexOfV++;
                q.offer(head);
            }
        }
        return result;
    }

    static class IntegeriWithItsList implements Comparable {
        public Integer indexOfV;
        public List<Integer> list;

        IntegeriWithItsList(List<Integer> list, Integer indexOfV) {
            this.list = list;
            this.indexOfV = indexOfV;
        }

        @Override
        public int compareTo(@NotNull Object o) {
            IntegeriWithItsList ol = (IntegeriWithItsList) o;
            return this.list.get(indexOfV).compareTo(ol.list.get(ol.indexOfV));
        }
    }

    //-----------------------------------------------------------------------------
    public static void main(String[] args) {
        List<List<Integer>> toBeMerged = new ArrayList<>();
        toBeMerged.add(Arrays.asList(1, 3, 5, 6));
        toBeMerged.add(Arrays.asList(2, 7, 9, 10));
        toBeMerged.add(Arrays.asList(4, 8, 11, 12));
        System.out.println(Arrays.toString(merge(toBeMerged).toArray()));
    }
}
