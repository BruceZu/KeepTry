//  Copyright 2021 The KeepTry Open Source Project
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

package sort;

import java.util.PriorityQueue;
import java.util.Queue;

public class Leetcode973KClosestPointstoOrigin {
  /*
      973. K Closest Points to Origin

    Given an array of points where points[i] = [xi, yi]
    represents a point on the X-Y plane and an integer k,
    return the k closest points to the origin (0, 0).

    The distance between two points on the X-Y plane is the Euclidean distance
     (i.e., √(x1 - x2)2 + (y1 - y2)2).

    You may return the answer in any order.
    The answer is guaranteed to be unique (except for the order that it is in).


    Input: points = [[1,3],[-2,2]], k = 1
    Output: [[-2,2]]
    Explanation:
    The distance between (1, 3) and the origin is sqrt(10).
    The distance between (-2, 2) and the origin is sqrt(8).
    Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
    We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].

    Input: points = [[3,3],[5,-1],[-2,4]], k = 2
    Output: [[3,3],[-2,4]]
    Explanation: The answer [[-2,4],[3,3]] would also be accepted.



    Constraints:
        1 <= k <= points.length <= 10^4
        -10^4 < xi, yi < 10^4

  */
  /*
  Idea: keep a max heap with size of K
  O(N*longK) time. O(K) space
   */
  public int[][] kClosest(int[][] points, int k) {
    Queue<int[]> q = // max heap
        new PriorityQueue<>((b, a) -> a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]);
    for (int[] p : points) {
      q.offer(p);
      if (q.size() > k) q.poll();
    }
    int[][] r = new int[k][];
    while (k > 0) r[--k] = q.poll();
    return r;
  }
}
