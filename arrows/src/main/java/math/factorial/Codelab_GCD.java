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

package math.factorial;

public class Codelab_GCD {
    /**
     * <pre>
     * Given 2 non negative integers m and n, find gcd(m, n)
     *
     * GCD of 2 integers m and n is defined as the greatest integer g such that g is a divisor of both m and n.
     * Both m and n fit in a 32 bit signed integer.
     *
     * @see <a href="https://codelab.interviewbit.com/problems/gcd/">code lab</a>
     * m : 6
     * n : 9
     *
     * Codelab_GCD(m, n) : 3
     */
    public static int gcd(int A, int B) {
        if (A == 0) {
            return B;
        }
        int nextA = B % A;
        int nextB = A;
        return gcd(nextA, nextB);
    }
}
