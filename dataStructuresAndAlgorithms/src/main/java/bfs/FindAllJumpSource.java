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

package bfs;

/**
 * <pre>
 *      array  [ 1 3 0 2 4 7]
 *       index   0 1 2 3 4 5
 *      input:  dest-node: A[0]
 *      output: all the source nodes: (A[1], A[3], A[4])
 *
 *  the number of array is the allowed steps from that index to jump.
 *  it is allowed to jump back or forward
 *
 *
 *      ----------------
 *     |                |
 *     v                |
 *    A[0]--> A[1] --> A[4]
 *            ^ |       |
 *           /  |       v
 *          /   |--> A[2] <---
 *         /                  |
 *       A[3]-------------->A[5]
 *
 *
 * Create graph from the end A[0] and BFS from end A[0].
 */
public class FindAllJumpSource {
}
