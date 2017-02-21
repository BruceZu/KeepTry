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

package two_pointer;

public class Leetcode11ContainerWithMostWater {
    public static int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int result = 0;

        while (l < r) {
            if (height[l] <= height[r]) {
                result = Math.max(result, (r - l) * height[l]);

                int next = l + 1;
                while (next < r && height[next] <= height[l]) {
                    next++;
                }
                l = next;

            } else {
                result = Math.max(result, (r - l) * height[r]);
                int next = r - 1;
                while (next > l && height[next] <= height[r]) {
                    next--;
                }
                r = next;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 1}));
    }
}
