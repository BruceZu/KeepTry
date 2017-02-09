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

package celebrity;

/**
 * <a href="https://leetcode.com/problems/find-the-celebrity/">LeetCode</a>
 */
public class Leetcode277FindtheCelebrity {
    /* The knows API is defined in the parent class Relation.*/
    boolean knows(int a, int b) {
        return true;
    }

    // a knows b then a is not celebrity,
    // a does not know b then b is not celebrity.
    //  case:  a know b, b know a; return -1
    //  case:  a does not know b, b does not know a. return -1;
    public int findCelebrity(int n) {
        int candidator = 0;
        for (int to = 1; to < n; to++) {
            if (knows(candidator, to)) {
                candidator = to;
            }
        }
        for (int to = 0; to < n; to++) {
            if (candidator != to && (
                    !knows(to, candidator) || knows(candidator, to))) {
                return -1;
            }
        }
        return candidator;
    }
}
