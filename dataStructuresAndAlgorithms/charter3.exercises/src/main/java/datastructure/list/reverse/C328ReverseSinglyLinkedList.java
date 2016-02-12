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

package datastructure.list.reverse;

import java.util.List;

/*
Describe in detail an algorithm for reversing a singly linked list L using only a
constant amount of additional space.
 */

public class C328ReverseSinglyLinkedList {
    public static <T> List<T> reverse(List<T> l) {
        int index = 1;
        int elements = l.size();
        for (int i = 1; i <= elements - 1; i++) {
            l.add(0, l.get(index));
            index = index + 2;
        }
        return l.subList(0, elements);
    }

    public static <T> List<T> reverse2(List<T> l) {
        int index = 1;
        int elements = l.size();
        for (int i = 1; i <= elements - 1; i++) {
            l.add(0, l.remove(index));
            index = index + 1;
        }
        return l.subList(0, elements);
    }
}
