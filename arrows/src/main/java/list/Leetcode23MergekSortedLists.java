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

package list;

import java.util.LinkedList;
import java.util.List;

public class Leetcode23MergekSortedLists {
  /*
    23. Merge k Sorted Lists

     an array of k linked-lists lists, each linked-list is sorted in ascending order.
     Merge all the linked-lists into one sorted linked-list and return it.


     Input: lists = [[1,4,5],[1,3,4],[2,6]]
     Output: [1,1,2,3,4,4,5,6]
     Explanation: The linked-lists are:
     [
       1->4->5,
       1->3->4,
       2->6
     ]
     merging them into one sorted list:
     1->1->2->3->4->4->5->6

     Input: lists = []
     Output: []

     Input: lists = [[]]
     Output: []

     Constraints:

         k == lists.length
         0 <= k <= 10^4
         0 <= lists[i].length <= 500
         -10^4 <= lists[i][j] <= 10^4
         lists[i] is sorted in ascending order.
         The sum of lists[i].length won't exceed 10^4.
  */
  /*
  Idea:
   select current min one each loop
   O(N*L) time.  N is node number, L is list array length
   O(1) space, Note here.
   */
  public ListNode mergeKLists_(ListNode[] list) {
    if (list == null || list.length == 0) return null;
    boolean has = true;
    ListNode dummy = new ListNode();
    ListNode cur = dummy;
    while (has) {
      int min = -1; // index
      for (int i = 0; i < list.length; i++) {
        if (list[i] != null && (min == -1 || list[i].val < list[min].val)) {
          min = i;
        }
      }
      if (min != -1) {
        cur.next = list[min];
        cur = cur.next;
        list[min] = list[min].next; // reason: min is index, not node itself.
      } else has = false;
    }
    return dummy.next;
  }

  /*
   Above algorithm cost time on the loop especially when there is more and more
   list[i] is null

  Idea:
    Find a way to improve O(N*L) time to O(N*logL) time
    O(logL) time means trying priority queue, binary search, TreeSet.
    With priority queue. The space will be O(L)

   Divide and Conquer
   O(N*logL) time, in total O(logL) loops, in each loop each node is accessed once.
             N is the node number
   O(1) space. Note here. if using a min heap the space will be L

   */
  public ListNode mergeKLists(ListNode[] list) {
    if (list == null || list.length == 0) return null;
    int r = list.length - 1;
    while (r > 0) { // O(logL) time
      for (int i = 0; i <= r; i++) {
        if ((i & 1) == 0) list[i >>> 1] = list[i];
        else list[i >>> 1] = merge2(list[i >>> 1], list[i]);
      }
      r = r >>> 1;
    }
    return list[0];
  }

  private ListNode merge2(ListNode a, ListNode b) {
    ListNode dummy = new ListNode();
    ListNode cur = dummy;
    while (a != null && b != null) {
      if (a.val < b.val) {
        cur.next = a;
        a = a.next;
      } else {
        cur.next = b;
        b = b.next;
      }
      cur = cur.next;
    }
    cur.next = a == null ? b : a;
    return dummy.next;
  }

  // --------------------------------------------------------------------------
  class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }
  /*
  Follow up 1
  give sorted arrays int[][] a
  if using min heap, with O(NlogL) time and O(L) space, this is not best solution
  Queue<int[v, i, j]> q : triple type in min heap, v= a[i][j]
       int[] top=q.poll();
       if(top[2]!=a[i].length-1){
         top[2]++;
         q.offer(top);
       }
  Follow up 2
    if any give array is too large to be consumed in memory
  Follow up 3
    if any give array is too large to be consumed in memory, but all value in the range 1-10
    int[] A=new int[11];
    A[v] keep the frequency of v and keep them in order.
  */
  /*

  Queston:
     - takes 3 parameters
       - “list1” - list of signed integers sorted in ascending order
       - “list2" - list of signed integers sorted in ascending order
       - “maxSize” - maximum number of elements in the result
     - output
       - merged list of list1 and list2, maintaining the ascending sort,
         of up to “maxSize” elements in length

     Example:
       ( [0,2,6], [-1,7,9], 4) = [-1,0,2,6]

       assume
        - list1 and list 2 will not be null? yeah
    */

  /*
   Runtime and space is O(maxSize)
  */
  public static List<Integer> mergeListWithSize(int[] list1, int[] list2, int maxSize) {
    List<Integer> ans = new LinkedList<>();
    if (list1 == null && list2 == null) return ans;
    int[] list;
    if (list1 == null || list2 == null) {
      list = list1 == null ? list2 : list1;
      if (maxSize > list.length) maxSize = list.length;
      int i = 0;
      while (i < list.length && ans.size() != maxSize) ans.add(list[i++]);
      return ans;
    }

    if (maxSize <= 0) return ans;
    if (maxSize > list1.length + list2.length) maxSize = list1.length + list2.length;

    int i = 0, j = 0;
    while (0 <= i && i < list1.length && 0 <= j && j < list2.length) {
      if (list1[i] < list2[j]) {
        ans.add(list1[i]);
        i++;
        if (ans.size() == maxSize) break;
      } else {
        ans.add(list2[j]);
        j++;
        if (ans.size() == maxSize) break;
      }
    }
    if (ans.size() != maxSize) {
      if (i == list1.length) {
        while (ans.size() != maxSize && j < list2.length) {
          ans.add(list2[j++]);
        }
      } else {
        while (ans.size() != maxSize && i < list1.length) {
          ans.add(list1[i++]);
        }
      }
    }
    return ans;
  }
  // test cases need cover all code lines
  /*
   Follow up:   public static List<Integer> mergeListWithSize(List<List<Integer>>, int maxSize) {

   }
  */
}
