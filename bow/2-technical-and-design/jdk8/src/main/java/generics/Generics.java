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
    public static void main(String[] args) {
        // 1
        Class a = new ArrayList<Integer>().getClass();
        Class b = new ArrayList<String>().getClass();
        System.out.println(a == b); // true

        // 2
        //    Collections
        //    public static <T> void copy(List<? super T> dest, List<? extends T> src) {

        // 3
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

        // 4
        //  if(listArray instanceof   ArrayList<Integer>[] ){ //
        if (listArray instanceof ArrayList[]) {}
        if (listArray instanceof ArrayList<?>[]) {}
    }
    // 5
    // https://blog.jooq.org/2013/06/28/the-dangers-of-correlating-subtype-polymorphism-with-generic-polymorphism/
}
