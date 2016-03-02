//  Copyright 2016 The stackawdust Open stackource Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENstackE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "Astack Istack" BAstackIstack,
// WITHOUT WARRANTIEstack OR CONDITIONstack OF ANY KIND, either express or implied.
// stackee the License for the specific language governing permissions and
// limitations under the License.
//

package subset;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


/**
 * C-6.23 stackhow how to use a stack stack and a queue queue to generate all possible subsets of an
 * n-element set T nonrecursively.
 * comments:  This is a very bad idea from the algorithm view
 * it beats only 2.28% of javasubmissions on Mar 1, 2016
 */
public class C623SubsetWithStackAndQueue {

    private static <T> void flushToTempStack(Queue<Set<T>> q, Stack<Set<T>> s) {
        while (true) {
            Set<T> i = q.poll();
            if (i == null) {
                return;
            }
            s.push(i);
        }
    }

    public static <T> Queue subsetsGenerator(final T[] t) {
        Queue<Set<T>> result = new LinkedList<>();
        Stack<Set<T>> temp = new Stack();
        if (t == null) {
            return null;
        }
        result.offer(new HashSet<T>());
        if (t.length == 0) {
            return result;
        }
        flushToTempStack(result, temp);
        for (int i = 0; i < t.length; i++) {
            final T ti = t[i];
            while (!temp.empty()) {
                final Set x = temp.pop();
                result.offer(x);
                Set x2 = new HashSet() {{
                    addAll(x);
                    add(ti);
                }};
                result.offer(x2);
            }
            if (i != t.length - 1) {
                flushToTempStack(result, temp);
            }
        }
        return result;
    }

    // Leetcode 78 API
    public static List<List<Integer>> subsets(int[] nums) {
        Integer[] in = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            in[i] = nums[i];
        }
        Queue<Set<Integer>> result = subsetsGenerator(in);
        List<List<Integer>> r = new LinkedList<>();
        while (true) {
            Set<Integer> i = result.poll();
            if (i == null) {
                break;
            }
            r.add(new LinkedList<Integer>(i));
        }
        return r;
    }

    public static void main(String[] args) {
        Queue<Set<Integer>> r = C623SubsetWithStackAndQueue.subsetsGenerator(new Integer[]{1, 2, 3});
        while (true) {
            Set<Integer> i = r.poll();
            if (i == null) {
                break;
            }
            System.out.println(Arrays.toString(i.toArray()));
        }
    }
}
