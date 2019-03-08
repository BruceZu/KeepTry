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

package generics;

import java.util.ArrayList;
import java.util.List;

public class Generics {

    private static <E> void privateHelper(List<E> list /*Got int the fly*/, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    public static void swap(List<?> list, int i, int j) {
        privateHelper(list, i, j);
        System.out.println(list.toString());
    }

    public static <T> void copy(List<? super T> dest, List<? extends T> src) {}

    public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        T temp = list.get(0);
        for (T cur : list) {
            if (cur.compareTo(cur) > 0) {
                temp = cur;
            }
        }
        return temp;
    }

    public static <T> List<? super T> copy(List<? extends T> src) {
        List<? super T> dest = new ArrayList<>();
        for (T t : src) {
            dest.add(t);
        }
        return dest;
    }

    public static void main(String[] args) {
        Class a = new ArrayList<Integer>().getClass();
        Class b = new ArrayList<String>().getClass();
        System.out.println(a == b); // true

        List<Integer>[] listArray = new ArrayList[1];
        ArrayList<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);

        listArray[0] = list;
        for (List<Integer> e : listArray) {
            for (Integer i : e) {
                System.out.println(i);
            }
        }

        // 4 runtime will erase the generic type
        // illegal
        //        if (listArray instanceof ArrayList<Integer>[]) {
        //            System.out.println("true");
        //        }
        if (listArray instanceof ArrayList[]) {
            System.out.println("true");
        }
        if (listArray instanceof ArrayList<?>[]) {
            System.out.println("true");
        }

        swap(list, 0, 1);
    }
    // 5
    // https://blog.jooq.org/2013/06/28/the-dangers-of-correlating-subtype-polymorphism-with-generic-polymorphism/
}
