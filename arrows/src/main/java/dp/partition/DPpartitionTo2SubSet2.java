//  Copyright 2017 The KeepTry Open Source Project
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

package dp.partition;

import java.util.Arrays;

public class DPpartitionTo2SubSet2 {
    /**
     * performance improved.
     * All we care about is finding all the true entries. At the beginning,
     * only the 0th entry is true. If we keep the location of the rightmost true entry in a variable,
     * => we can always start at that spot and go left instead of starting at the right end of the table.
     * => sort C[] first. the rightmost true entry will move to the right as slowly as possible.
     * => care about T[S/2] only
     *
     * <pre>
     *  4 3 2 1
     *
     *  0  1  2   3  4  5  6  7  8  9  10
     *  *
     *  1            *
     *  1         *  1        *
     *  1     1   1  1  1  1  1     *
     *  1  *  1   1  1  1  1  1  *  1  *
     *
     *
     *  1 2 3 4
     *
     *  0  1  2   3  4  5  6  7  8  9  10
     *  *
     *  1  *
     *  1  1  *   *
     *  1  1  1   1  *  *  *
     *  1  1  1   1  1  1  1  *  *  *   *
     *  runtime complexity： O(nS)
     */
    public static boolean can_Improved(int[] nums) {
        // Todo corner case checking
        Arrays.sort(nums); // O(nlogn)
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) return false;
        boolean[] T = new boolean[sum / 2 + 1];
        T[0] = true;

        int r = 0; // rightest Index where T[r] is true
        for (int num : nums) {
            for (int si = r; si >= 0; si--) {
                if (T[si]) {
                    T[si + num] = true;
                    if (si + num == sum / 2) return true;
                }
            }
            r = Math.min(sum / 2, r + num);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(can_Improved(new int[] {4, 6, 1}));
        int[] arr = {3, 3, 3, 1};
        System.out.println("The minimum difference between 2 sets is " + findMin(arr));
        arr = new int[] {2, 3, 3, 2};
        System.out.println("The minimum difference between 2 sets is " + findMin(arr));
        arr = new int[] {3, 1, 4, 2, 2, 1};
        System.out.println(findMin(arr) == 1);
    }

    // Returns the minimum value of the difference of the two sub-sets.
    // runtime complexity： O(nS)
    public static int findMin(int nums[]) {
        // Todo: corner case checking
        int sum = Arrays.stream(nums).sum();
        boolean dp[] =
                new boolean
                        [sum / 2 + 1]; // sum may be odd or even. take care its length is sum/2 +1
        // not sum/2
        dp[0] = true;

        int R = 0;
        outLoop:
        for (int num : nums) {
            for (int si = R; si >= 0; si--) {
                if (dp[si] && (si + num <= sum / 2)) {
                    dp[si + num] = true;
                    if (si + num == sum / 2) break outLoop;
                }
            }
            R = Math.min(sum / 2, R + num);
        }

        int impossible = Integer.MAX_VALUE;
        for (int s = sum / 2; s >= 0; s--) {
            if (dp[s] == true) {
                return sum - 2 * s;
            }
        }
        return impossible;
    }
}
