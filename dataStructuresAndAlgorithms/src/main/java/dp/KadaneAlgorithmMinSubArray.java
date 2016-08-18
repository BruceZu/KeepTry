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

package dp;

public class KadaneAlgorithmMinSubArray {
    public static int minimum(int[] arr) {
        int maxEndsHere = arr[0], peak = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxEndsHere = Math.min(arr[i], maxEndsHere += arr[i]);
            peak = Math.min(maxEndsHere, peak);
        }
        return peak;
    }

    // Alternative
    public static int minimum2(int[] arr) {
        int sumTop;
        int rightDelta = arr[0], sum = arr[0];
        if (arr[0] >= 0) {
            sumTop = arr[0];
        } else {
            sumTop = 0;
        }
        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];
            if (sum - sumTop < rightDelta) {
                rightDelta = sum - sumTop;
            }
            if (sum > sumTop) {
                sumTop = sum;
            }
        }
        return rightDelta;
    }

    /**
     * <pre>
     *   mininum:
     *     -2, 1, -3, 4, -1, 2, 1, -5, 4                -> -5
     *     -2, -3, 4, -1, -2, 1, 5, -3, 4               -> -5
     *     -1, -2, -2, -1, -3                           -> -9
     *     1, 2, 2, 1, 3                                -> 1
     *     0, -1, -2, -1, 0, 1, 2, 1, 0, -1, -2, -1, 0  -> -4
     */
    public static void main(String[] args) {
        System.out.println(minimum2(new int[]{1, 2}));
        System.out.println(minimum2(new int[]{2, 1}));
        System.out.println(minimum2(new int[]{-1, -2}));
        System.out.println(minimum2(new int[]{-2, -1}));
        System.out.println(minimum2(new int[]{1, -2}));
        System.out.println(minimum2(new int[]{-2, 1}));
        System.out.println(minimum2(new int[]{-1, 2}));
        System.out.println(minimum2(new int[]{2, -1}));
    }
}
