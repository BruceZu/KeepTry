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

package greedy;

import java.util.Arrays;

public class Leetcode135Candy {
    /**
     * O(N)
     * <pre>
     *
     * <a href="https://leetcode.com/problems/candy/?tab=Description">LeetCode</a>
     * current rating is equal to the previous one -> give 1 candy.
     * current rating is greater than the previous one -> give him (previous + 1) candies.
     * current rating is less than the previous one ->
     * Total number of candies for "descending" children can be found through arithmetic progression formula (1+2+...+peak number).
     * need to update our peak child if his number of candies is greater than the old number
     */
    static int candy(int[] ratings) {
        if (ratings.length <= 1) {
            return ratings.length;
        }

        int[] candyNum = new int[ratings.length];
        Arrays.fill(candyNum, 1); // step 1

        for (int i = 1; i < ratings.length; i++) { // step 2 from 1
            if (ratings[i] > ratings[i - 1]) {
                candyNum[i] = candyNum[i - 1] + 1;
            }
        }
        for (int i = ratings.length - 1; i > 0; i--) { // step 3
            if (ratings[i - 1] > ratings[i]) {
                candyNum[i - 1] = Math.max(candyNum[i] + 1, candyNum[i - 1]);
            }
        }
        int sum = 0;
        for (int i = 0; i < ratings.length; i++) { // step 4
            sum += candyNum[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(candy(new int[]{1, 3, 3, 5, 7, 4, 4, 2})); // 13
        System.out.println(candy(new int[]{4, 2, 3, 6, 8, 7, 6, 5, 1})); // 23
        System.out.println(candy(new int[]{4, 2, 3, 4, 1})); //9
        System.out.println(candy(new int[]{0})); // 1
        System.out.println(candy(new int[]{2, 1})); // 3
        System.out.println(candy(new int[]{1, 2, 2})); // 4
    }
}
