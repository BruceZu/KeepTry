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

package tree.binary_search_tree;

import java.util.TreeSet;

/**
 * <pre>
 *   N light.
 *   2 status 1 or 0. on or off
 *   Assume all light initial status is off
 * */
public class LightToggleStatusTreeSet {
    private TreeSet<Integer> marks;

    public LightToggleStatusTreeSet() {
        marks = new TreeSet();
    }

    public void toggle(int start, int end) {
        if (marks.contains(start)) {
            marks.remove(start);
        } else {
            marks.add(start);
        }

        if (marks.contains(end + 1)) {
            marks.remove(end + 1);
        } else {
            marks.add(end + 1);
        }
    }

    public int status(int n) {
        // headSet() is O(1)
        // part view size() is O(k); TreeSet.size() is O(1)
        return marks.headSet(n, /* need inclusive*/ true).size() % 2;
    }

    // ----------------------------------------------------------------
    private static void print(LightToggleStatusTreeSet api, int to) {
        for (int i = 0; i < to; i++) {
            System.out.print(api.status(i) + ", ");
        }
        System.out.println("");
    }

    private static void index(int to) {
        for (int i = 0; i < to; i++) {
            System.out.print(i + ", ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        LightToggleStatusTreeSet api = new LightToggleStatusTreeSet();

        index(12);
        api.toggle(3, 5);
        print(api, 12);

        api.toggle(4, 7);
        print(api, 12);

        api.toggle(2, 8);
        print(api, 12);

        api.toggle(0, 2);
        print(api, 12);

        api.toggle(0, 9);
        print(api, 12);
    }
}
