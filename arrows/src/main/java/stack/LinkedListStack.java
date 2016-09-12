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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LinkedListStack<T> implements IStack<T> {
    private List<T> d = new LinkedList();

    @Override
    public int size() {
        return d.size();
    }

    @Override
    public boolean isEmpty() {
        return d.isEmpty();
    }

    @Override
    public void push(T e) {
        d.add(0, e);
    }

    @Override
    public T peek() {
        if (d.isEmpty()) {
            return null;
        }
        return d.get(0);
    }

    @Override
    public T pop() {
        if (d.isEmpty()) {
            return null;
        }
        return d.remove(0);
    }

    @Override
    public String toString() {
        return Arrays.toString(d.toArray());
    }
}
