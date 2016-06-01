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

package array;

// C520
// rearranges an array of integer values so that all the even values appear before all the odd values.
// it is same like C521
// (C521 Given an unsorted array, A, of integers and an integer k, describe a recursive algorithm for rearranging
// the elements in A so that all elements less than or equal to k come before any elements larger than k.
// What is the running time of your algorithm on an array of n values?)

public class OddAheadEvenArray {
    // recursive method, running time O(N)
    private static void rearranges(int[] array, int indexEven, int currentIndex) {
        if (currentIndex == array.length) {
            return;
        }

        int who = array[currentIndex] & 1;
        if (who == 0 && indexEven == -1) {
            indexEven = currentIndex;
        }
        if (who == 1 && indexEven != -1) {
            // swap
            array[indexEven] ^= array[currentIndex];
            array[currentIndex] ^= array[indexEven];
            array[indexEven] ^= array[currentIndex];
            indexEven++; //
        }

        rearranges(array, indexEven, currentIndex + 1);
    }


    public static void rearranges(int[] array) {
        rearranges(array, -1, 0);
    }

    // no recursion implementation, running time O(N)
    public static void rearranges2(int[] array) {
        int indexEven = -1;
        for (int i = 0; i < array.length; i++) {
            int check = (array[i] & 1);
            //    even
            //odd even
            if (check == 0 && indexEven == -1) {
                indexEven = i;
                continue;
            }
            // even odd
            if (check == 1 && indexEven != -1) {
                // swap
                array[indexEven] ^= array[i];
                array[i] ^= array[indexEven];
                array[indexEven] ^= array[i];

                indexEven++; //
            }
            // even even
            // odd  odd
            //      odd
        }
    }
}
