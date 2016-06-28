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

package stack;

import java.util.Stack;

/**
 * Sort the stack using only Stack API, without using other data structure
 */
public class SortStack<T extends Comparable<T>> {
    /**
     * Ascending from bottom to top
     */
    int sorted;

    private void bubble(Stack<T> s, T max) {
        if (s.empty() || s.size() == sorted) {
            s.push(max);
            sorted++;
            return; // note return
        }

        T currentTop = s.pop();
        if (max.compareTo(currentTop) < 0) {
            T tmp = max;
            max = currentTop;
            currentTop = tmp;
        }

        bubble(s, max);
        s.push(currentTop);
    }

    public Stack<T> sortAscending(Stack<T> s) {
        sorted = 0;
        if (s == null || s.size() <= 1) {
            return s;
        }

        while (sorted != s.size()) {
            bubble(s, s.pop());
        }
        return s;
    }
}
