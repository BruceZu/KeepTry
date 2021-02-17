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
    // the question say there is at least 2 elements
    int l = 0, r = height.length - 1;
    int res = 0;
    while (l < r) { // when l==r, no container and need not calculate
      // calculate current container water.
      int lv = height[l], rv = height[r];
      res = Math.max(res, (r - l) * Math.min(lv, rv));
      // For next loop to find a higher one from the lower side as reducing the width of container.
      if (lv <= rv) {
        int cur = l;
        while (cur < r && height[cur] <= lv) cur++;
        l = cur;

      } else {
        int cur = r;
        while (cur > l && height[cur] <= rv) cur--;
        r = cur;
      }
    }
    return res;
  }
}
