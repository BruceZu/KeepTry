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

// C524 Isabel has an interesting way of summing up the values in an array A of n integers,
// where n is a power of two. She creates an array B of half the size of A and sets
// B[i] = A[2i]+ A[2i+ 1], for i = 0,1, . . . , (n/2)− 1. If B has size 1, then she
// outputs B[0]. Otherwise, she replaces A with B, and repeats the process. What is
// the running time of her algorithm?
public class IsabelSum {
    // O(N-1)
    private static long sum(long[] data, long[] by) {
        for (int i = 0; i < by.length; i++) {
            by[i] = data[2 * i] + data[2 * i + 1];
        }
        if (by.length == 1) {
            return by[0];
        }
        return sum(by, new long[by.length >> 1]);
    }

    public static long isabelSum(long[] data) throws UnsupportedOperationException {
        double log = Math.log(data.length) / Math.log(2);
        if (log != (long) log) {
            throw new UnsupportedOperationException();
        }
        return sum(data, new long[data.length >> 1]);
    }
}
