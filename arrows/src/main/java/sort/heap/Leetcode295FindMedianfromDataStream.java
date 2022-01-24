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

package sort.heap;

import java.util.PriorityQueue;

/*
Leetcode 295. Find Median from Data Stream
The median is the middle value in an ordered integer list.
If the size of the list is even, there is no middle value and the median
is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
Implement the MedianFinder class:

MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far.
Answers within 10-5 of the actual answer will be accepted.


Input
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
Output
[null, null, null, 1.5, null, 2.0]

Explanation
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0


Constraints:

-10^5 <= num <= 10^5
There will be at least one element in the data structure before calling findMedian.
At most 5 * 104 calls will be made to addNum and findMedian.


Follow up:

If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
 -  Segment Trees
If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
*/
public class Leetcode295FindMedianfromDataStream {

  class MedianFinder {
    private PriorityQueue<Integer> L;
    private PriorityQueue<Integer> R;

    /*
    keep size and order relation:
       - l size== R size or l size == R size + 1
       - l.peak() <= R.peek()
     */
    public MedianFinder() {
      L = new PriorityQueue<>((a, b) -> -a.compareTo(b));
      R = new PriorityQueue<>();
    }

    // O(logN) time O(N) space
    public void addNum(int num) {
      // keep size relation
      if (L.size() == R.size()) {
        L.offer(num);
      } else { // L size =  R size + 1
        R.offer(num);
      }
      // Keep order relation
      if (!R.isEmpty()) {
        while (L.peek() > R.peek()) { // size keep unchanged
          R.offer(L.poll());
          L.offer(R.poll());
        }
      }
    }
   // O(1)
    public double findMedian() {
      if (L.size() == R.size()) {
        return (L.peek() * 1L + R.peek()) * 0.5;
      }
      return L.peek();
    }
  }
}

/*
             after     L.offer(num);
                       then now the new peak is
                         - old one: it <= R peak one
                         - new one: old one <= new one;
                       R.offer(L.poll());
             order is kept.

*/
class MedianFinder {
  PriorityQueue<Integer> L = new PriorityQueue<>((a, b) -> -a.compareTo(b));
  PriorityQueue<Integer> R = new PriorityQueue();

  public void addNum(int num) {
    // keep order relation  L.peak() <= R.peak();
    // need not care the empty case
    L.offer(num);
    R.offer(L.poll());

    // keep size relation L size >= R size
    if (L.size() < R.size()) {
      L.offer(R.poll());
    }
  }

  public double findMedian() {
    if (L.size() == R.size()) return (L.peek() + R.peek()) / 2.0;
    else return L.peek();
  }
}
