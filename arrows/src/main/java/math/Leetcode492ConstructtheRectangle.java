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

package math;

/**
 * <pre>
 * <a href="https://leetcode.com/problems/construct-the-rectangle/?tab=Description">LeetCode</a>
 */
public class Leetcode492ConstructtheRectangle {
    /**
     * caset 8 and 10
     */
    static public int[] constructRectangle(int area) {
        int candidateL = (int) Math.sqrt(area);
        while (!(area % candidateL == 0)) {
            candidateL--;
        }
        return new int[]{area / candidateL,candidateL};
    }

    public static void main(String[] args) {
        constructRectangle(2);
        constructRectangle(1);
    }
}
