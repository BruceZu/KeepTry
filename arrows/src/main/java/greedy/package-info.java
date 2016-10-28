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

/**
 * <pre>
 *
 *  Greedy:
 *    "the best local choice among all feasible choices available on that step".
 *    a greedy algorithm never reconsiders its choices
 *    Greedy algorithms are looking at a local point and doing some choice with the data at this point.
 *    You don't want to try all intermediate places. You goto the nearest intermediate place.
 *
 *    we can make whatever choice seems best at the moment and then solve the subproblems that arise later.
 *    The choice made by a greedy algorithm may depend on choices made so far but not on future choices or
 *    all the solutions to the subproblem.  So, we suppose that our local optimal choice will also be global,
 *    but sometimes, it fails.
 *
 *    "the best local choice among all feasible choices available on that step.
 *
 */
package greedy;