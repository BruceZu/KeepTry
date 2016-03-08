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

package recursion;

public class Fibonacci {
    // Requires an exponential number of calls to the method. runtime >2^(N/2),  Ω(2^N))
    public static long my_bad_fibonacci(int n) {
        assert n >= 0;
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return my_bad_fibonacci(n - 1) + my_bad_fibonacci(n - 2);
    }

    // O(N)
    private static long my_fibonacci(int lastLast, int last, int indexOfLast, int indexTarget) {
        if (indexOfLast < indexTarget) {
            return my_fibonacci(last, lastLast + last, indexOfLast + 1, indexTarget);
        }
        return last;
    }

    public static long my_fibonacci(int indexTarget) {
        return my_fibonacci(0, 1, 1, indexTarget);
    }
}
