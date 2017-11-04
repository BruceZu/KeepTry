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

package stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * <a href="http://www.informatik.uni-ulm.de/acm/Locals/2003/html/judge.html"> Problem H: Largest Rectangle in a Histogram </a>
 */
public class Leetcode84LargestRectangleinHistogram {

    /**
     * performance: use an array to simulate the stack
     * @param heightOfBarAt
     * @return
     */
    // O(N)
    static int largestRectangleArea2(int[] heightOfBarAt) {
        Stack<Integer> ascendingBarsIndex = new Stack<>();
        int max_area = 0;
        for (int iIndex = 0; iIndex <= heightOfBarAt.length; iIndex++) {
            int iHeight = iIndex == heightOfBarAt.length ? 0
                    : heightOfBarAt[iIndex];
            while (!ascendingBarsIndex.isEmpty()
                    && heightOfBarAt[ascendingBarsIndex.peek()] > iHeight) {

                int height = heightOfBarAt[ascendingBarsIndex.pop()];
                int width = ascendingBarsIndex.isEmpty() ? iIndex : iIndex - ascendingBarsIndex.peek() - 1;
                max_area = Math.max(height * width, max_area);
            }
            ascendingBarsIndex.push(iIndex);
        }
        return max_area;
    }

    /**
     * -----------------------------------------legend---------------------------------------------------
     * <pre>
     * Stack saves index of bar, and only those bars whose height <= next bar.
     * so it is in ascending order, from bottom to top.
     * so before pushing in the next bar if the index at top of stack is greater than the next bar
     * need pop and calculate the area and update the max area of rectangle.
     *
     * if the next bar which will be pushed has the same height with the top one in stack.
     * Skip it to improve performance
     */
    static int largestRectangleArea(int[] heightOfBarAt) {

        Stack<Integer> ascendingBarsIndex = new Stack<>();
        int max_area = 0;
        for (int iIndex = 0; iIndex < heightOfBarAt.length; iIndex++) {
            if (ascendingBarsIndex.empty()
                    || heightOfBarAt[ascendingBarsIndex.peek()] <= heightOfBarAt[iIndex]) {
                ascendingBarsIndex.push(iIndex);
            } else {
                while (!ascendingBarsIndex.isEmpty() &&
                        heightOfBarAt[ascendingBarsIndex.peek()] > heightOfBarAt[iIndex]) {
                    int topIndex = ascendingBarsIndex.pop();
                    int height = heightOfBarAt[topIndex];
                    int width = ascendingBarsIndex.isEmpty() ? iIndex : iIndex - ascendingBarsIndex.peek() - 1;
                    int area = height * width; // area with the bar at top index as height
                    max_area = max_area < area ? area : max_area;
                }
                ascendingBarsIndex.push(iIndex);
            }
        }

        while (ascendingBarsIndex.empty() == false) { // remaining bars in stack
            int topIndex = ascendingBarsIndex.pop();
            int height = heightOfBarAt[topIndex];
            int width = ascendingBarsIndex.isEmpty() ? heightOfBarAt.length : heightOfBarAt.length - ascendingBarsIndex.peek() - 1;
            int area = height * width;
            max_area = max_area < area ? area : max_area;
        }
        return max_area;
    }

    /**
     * <pre>
     * 给一个数组,代表一组人的身高。然后输出一个数组,表示在当前人之后的所有
     * 比他高的人里,离他最近的人的身高。
     * 比如输入是[3, 6, 7,   2, 3]
     * 输出就是  [6, 7, null, 3, null]。
     * <a href="http://www.geeksforgeeks.org/find-next-smaller-next-greater-array/"> next greater and smaller</a>
     * O(n)
     */
    static int[] nextHigher(int[] heightOf) {
        Stack<Integer> ascendingHeight = new Stack<Integer>();
        int[] nextNearHigher = new int[heightOf.length];

        for (int i = heightOf.length - 1; i >= 0; i--) {
            while (!ascendingHeight.isEmpty() && ascendingHeight.peek() <= heightOf[i]) {
                ascendingHeight.pop();
            }
            nextNearHigher[i] = ascendingHeight.isEmpty() ? -1 : ascendingHeight.peek();
            ascendingHeight.push(heightOf[i]);
        }
        return nextNearHigher;
    }

    static int[] nextSmaller(int[] heightOf) {
        Stack<Integer> decendingHeight = new Stack<>();
        int[] nextNearSmaller = new int[heightOf.length];
        for (int i = heightOf.length - 1; i >= 0; i--) {
            while (!decendingHeight.isEmpty()
                    && decendingHeight.peek() >= heightOf[i]) {
                decendingHeight.pop();
            }
            nextNearSmaller[i] = decendingHeight.isEmpty() ? -1 : decendingHeight.peek();
            decendingHeight.push(heightOf[i]);
        }
        return nextNearSmaller;
    }

    //------------------------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new int[]{9, 1, 2, 8, 6, 2, 1, 3, 6, 7, 2, 3}));
        System.out.println(Arrays.toString(nextHigher(new int[]{9, 1, 2, 8, 6, 2, 1, 3, 6, 7, 2, 3})));
        System.out.println(Arrays.toString(nextSmaller(new int[]{9, 1, 2, 8, 6, 2, 1, 3, 6, 7, 2, 3})));
        System.out.println();
        System.out.println(largestRectangleArea(new int[]{6, 2, 5, 4, 5, 1, 6})); // 12
        System.out.println(largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3})); // 10
        System.out.println(largestRectangleArea(new int[]{6, 2, 5, 4, 5, 2, 6})); // 14
        System.out.println(largestRectangleArea(new int[]{2, 4, 5, 6})); // 12
        System.out.println();
        System.out.println(largestRectangleArea2(new int[]{6, 2, 5, 4, 5, 1, 6})); // 12
        System.out.println(largestRectangleArea2(new int[]{2, 1, 5, 6, 2, 3})); // 10
        System.out.println(largestRectangleArea2(new int[]{6, 2, 5, 4, 5, 2, 6})); // 14
        System.out.println(largestRectangleArea2(new int[]{2, 4, 5, 6})); // 12
    }
}
