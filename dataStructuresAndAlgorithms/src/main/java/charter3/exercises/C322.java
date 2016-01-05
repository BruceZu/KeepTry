//  Copyright 2016 The Minorminor Open Source Project
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

package charter3.exercises;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
Write a method, shuffle(A), that rearranges the elements of array A so that every
possible ordering is equally likely. You may rely on the nextInt(n) method of
the java.util.Random class, which returns a random number between 0 and n−1
inclusive.
 */
public class C322 {
    static int[] shuffle(int[] a) {
        Random r = new Random();

        Set<Integer> checker = new HashSet(a.length);
        int size = checker.size();
        int[] oldIndexesOrder = new int[a.length];

        while (size < a.length) {
            int t = r.nextInt(a.length);
            checker.add(t);
            if (size != checker.size()) {
                oldIndexesOrder[size] = t;
                size = checker.size();
            }
        }

        int[] re = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            re[i] = a[oldIndexesOrder[i]];
        }
        return re;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.print(Arrays.toString(shuffle(a)));
    }
}
