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

package array.duplicated;

/**
 * <pre>
 * Get the length of a array which keep at most 2 duplicated elements of a given sorted array
 * e.g.:
 *      122233334445578999 ->  1223344557899
 *
 *  This algorithm not apply to getting the result array.
 */
public class KeepAtMost2DuplicatedInSortedArray {

    public static int AtMost2DuplicatedOf(int[] sorted) {
        int size = 1;
        boolean noKept = true;
        for (int i = 1; i < sorted.length; i++) {
            int v = sorted[i];
            boolean same = (v ^ sorted[i - 1]) == 0;
            if (same && noKept) {
                sorted[size++] = v;
                noKept = false;
                continue;
            }
            if (!same) {
                sorted[size++] = v;
                noKept = true;
            }
        }
        return size;
    }

    //  This algorithm not apply to getting the result array.
    public static int LengthOfAtMost2DuplicatedOf(int[] sorted) {
        int size = 1;
        for (int i = 1; i < sorted.length; i++) {
            int v = sorted[i];
            boolean same = (v ^ sorted[i - 1]) == 0;
            if (!same || same && i > 1 && (v ^ sorted[i - 2]) != 0) {
                size++;
                continue;
            }
        }
        return size;
    }
}
