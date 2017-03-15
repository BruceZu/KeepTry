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

package binarysearch;

public class Leetcode327CountofRangeSum {
    /**
     * For leetcode 327, applying the sequential recurrence relation (with j fixed)
     * on the pre-sum array, the subproblem C reads: find the number of elements out of
     * visited ones that are within the given range,
     * which again involves searching on "dynamic searching space"; applying the partition recurrence relation,
     * we have a subproblem C: for each element in the left half,
     * find the number of elements in the right half that are within the given range,
     * which can be embedded into the merging process using the two-pointer technique.
     */
}
