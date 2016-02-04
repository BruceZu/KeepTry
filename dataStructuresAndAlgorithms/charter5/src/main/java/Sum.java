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

public class Sum {
    // Linear Recursion
    // Running time is O(n)
    public static int my_linearSum(int[] data, int n) {
        if (n == data.length) {
            n = n - 1;
        }
        if (n == 0) {
            return data[0];
        }
        return data[n] + my_linearSum(data, n - 1);
    }

    // Binary recursion case:
    // O(logn) amount of additional space,
    // Running time is still O(n)
    public static int my_binarySum(int[] data, int startIndex, int endIndex) {
        if (startIndex + 1 == endIndex) {
            return data[startIndex] + data[endIndex];
        }
        if (startIndex == endIndex) {
            return data[startIndex];
        }

        int mid = (startIndex + endIndex) / 2;
        return my_binarySum(data, startIndex, mid)
                + my_binarySum(data, mid + 1, endIndex);
    }
}
