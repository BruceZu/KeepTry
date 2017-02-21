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

package tree.heap;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <pre>
 *
 * <a href='https://leetcode.com/problems/kth-largest-element-in-an-array/'> Leet Code<</a>
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
public class Leetcode215KthLargestElementinanArray {
    public static int findKthLargest(int[] nums, int k) {
        Queue<Integer> result = new PriorityQueue<Integer>() {
            @Override
            public boolean offer(Integer i) {
                if (this.size() < k) {
                    return super.offer(i); // care super
                } else {
                    if (i > this.peek()) {
                        this.remove();
                        return super.offer(i); // care super
                    } else {
                        return true;
                    }
                }

            }
        };

        for (int i : nums) {
            result.offer(i);
        }
        return result.peek();
    }

    public int findKthLargest2(int[] nums, int k) {
        final PriorityQueue<Integer> re = new PriorityQueue<>();
        for (int n : nums) {
            re.offer(n);

            if (re.size() > k) {
                re.poll();
            }
        }
        return re.peek();
    }

    //-------------------------
    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    }
}
