//  Copyright 2017 The KeepTry Open Source Project
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

package greedy;

import java.util.*;

/**
 * Get the intersection of 2 lists of intervals. 输入时两个List<Interval>. 这两个list都是排好序的（根据Interval的start, 和end排序， start小的在前）
 * 返回值是这两个list的intersection。
 * 举个简单例子:
 * list1 = {(1, 3), (4, 5), (7, 10), (8, 12)}
 * list2 = {(0, 2), (6, 6), (9, 19)}
 * 结果应该是 {(1,2), (9,12)}
 * 求两组 list 间发生的 shadow.
 * Follow up: outline of these 2 list.
 * Follow up: if each interval has different high:
 *      -- hide area/intersection
 *      -- outline
 */
public class HidenArea {

    /**
     * Test case:
     * {[1,5],[7,9]} =>  {[1,5],[7,9]}
     * {[1,5],[5,9]} =>  {[1,9]}
     * {[1,5],[2,9]} =>  {[1,9]}
     * {[1,5],[2,3]} =>  {[1,5]}
     *
     * O(list's length)
     */
    private static List<Integer[]> mergeOverlapIntervalsOf(List<Integer[]> sortedList) {
        if (sortedList == null | sortedList.size() <= 1) {
            return sortedList;
        }

        Iterator<Integer[]> it = sortedList.iterator();
        Stack<Integer[]> stack = new Stack<>();
        stack.add(it.next());

        while (it.hasNext()) {
            Integer[] cur = it.next();
            int preEnd = stack.peek()[1];
            int curStart = cur[0];
            int curEnd = cur[1];
            if (curStart > preEnd) {
                stack.push(cur);
            } else {
                stack.peek()[1] = Math.max(preEnd, curEnd);
            }
        }

        return new ArrayList<>(stack);
    }

    /**
     * Test case:
     * la ={(1, 3), (4, 5), (7, 12)} lb ={(0, 2), (6, 6), (9, 19)}  => {(1, 2), (9, 12)}
     * la ={(1, 2), (7, 8), (9, 10)} lb ={(3, 4), (5, 6)}  => { }
     * la ={(1, 2), (4, 8), (10, 11)} lb ={(3, 5), (7, 9)}  => {(4, 5),(7, 8)}
     * la ={(1, 2), (7, 9), (11, 15)} lb ={(5, 13), (18, 20)}  => {(7, 9),(11, 13)}
     *
     * @param sortedListA sorted List
     * @param sortedListB sorted List
     *
     * O(m+n)
     */
    public static List<Integer[]> intersectionOf(List<Integer[]> sortedListA, List<Integer[]> sortedListB) {
        if (sortedListA == null || sortedListB == null || sortedListA.size() == 0 || sortedListB.size() == 0) {
            return new ArrayList();
        }
        sortedListA = mergeOverlapIntervalsOf(sortedListA);
        sortedListB = mergeOverlapIntervalsOf(sortedListB);
        Iterator<Integer[]> itA = sortedListA.iterator(), itB = sortedListB.iterator();
        Integer[] a = itA.next(), b = itB.next();
        List<Integer[]> r = new ArrayList<>();
        while (true) {
            if (a[1] < b[0]) {
                if (!itA.hasNext()) break;
                a = itA.next();
            } else {
                int bEnd = b[1];
                if (bEnd <= a[0]) {
                    if (!itB.hasNext()) break;
                    b = itB.next();
                } else {
                    r.add(new Integer[]{Math.max(a[0], b[0]), Math.min(a[1], b[1])});
                    if (bEnd < a[1]) {
                        if (!itB.hasNext()) break;
                        b = itB.next();
                    } else if (bEnd == a[1]) {
                        if (!itB.hasNext()) break;
                        b = itB.next();
                        if (!itA.hasNext()) break;
                        a = itA.next();
                    } else {
                        if (!itA.hasNext()) break;
                        a = itA.next();
                    }
                }
            }
        }
        return r;
    }


    // -------------------------------------------------------------------
    private static void mergeOverlapIntervalsOfTest() {
        List<Integer[]> r = mergeOverlapIntervalsOf(
                Arrays.asList(new Integer[]{1, 5}, new Integer[]{2, 3})
        );
        Iterator<Integer[]> it = r.iterator();
        while (it.hasNext()) {
            System.out.println(Arrays.toString(it.next()));
        }
    }

    private static void intersectionOfTest() {
        List<Integer[]> la =
                Arrays.asList(new Integer[]{1, 2}, new Integer[]{7, 9}, new Integer[]{11, 15});
        List<Integer[]> lb =
                Arrays.asList(new Integer[]{5, 13}, new Integer[]{18, 20});

        List<Integer[]> r = intersectionOf(la, lb);
        System.out.println("result size:" + r.size());
        Iterator<Integer[]> it = r.iterator();
        while (it.hasNext()) {
            System.out.println(Arrays.toString(it.next()));
        }
    }
}
