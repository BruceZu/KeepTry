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

package greedy;

import java.util.*;
import java.util.function.Function;

/**
 * <pre>
 * You are given a list of size , initialized with zeroes. You have to perform  operations on the list and
 * output the maximum of final values of all the  elements in the list. For every operation,
 * you are given three integers ,  and  and you have to add value  to all the elements ranging
 * from index  to (both inclusive).
 *
 * Input Format
 *
 * First line will contain two integers  and  separated by a single space.
 * Next  lines will contain three integers ,  and  separated by a single space.
 * Numbers in list are numbered from  to .
 *
 * Constraints
 *
 * 3 <= M <= 10^7
 * 1 <= N <= 2 * 10^5
 * 1 <= a <= b <= M
 * 0 <= k <= 10^9
 *
 * Output Format
 *
 * A single line containing maximum value in the updated list.
 *
 * Sample Input
 *
 * 5 3
 * 1 2 100
 * 2 5 100
 * 3 4 100
 * Sample Output
 *
 * 200
 * Explanation
 *
 * After first update list will be  100 100 0 0 0.
 * After second update list will be 100 200 100 100 100.
 * After third update list will be  100 200 200 200 100.
 * So the required answer will be   200.
 * ----------------------------------------------------------------
 *
 * Integer.MAX_VALUE:              2  147  483  647
 * Long.MAX_VALUE):   9 223 372  036  854  775  807
 */
public class HackerrankLastMaxmum {
    static class Block {
        long heigh;
        Integer start; // 0 based index
        Integer end; // 0 based index

        public Block(int start, int end, long heigh) {

            this.heigh = heigh;
            this.start = start;
            this.end = end;
        }
    }

    // Pass all tests on hackerrank https://www.hackerrank.com/challenges/crush
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int validIndex = scan.nextInt();
        int caseCount = scan.nextInt();
        Block[] sortedStarts = new Block[caseCount];
        Block[] sortedEnds = new Block[caseCount];
        for (int i = 0; i < caseCount; i++) {
            // todo verify the index
            Block b = new Block(scan.nextInt() - 1, scan.nextInt() - 1, scan.nextInt());
            sortedStarts[i] = b;
            sortedEnds[i] = b;
        }
        Arrays.sort(sortedStarts, Comparator.comparing((p) -> p.start)); // NlogN
        Arrays.sort(sortedEnds, Comparator.comparing((p) -> p.end));

        int curEnd = 0;
        long curSum = sortedStarts[0].heigh;
        Set<Block> sumBackEnd = new HashSet();
        sumBackEnd.add(sortedStarts[0]);
        long max = sortedStarts[0].heigh;

        for (int i = 1; i < caseCount; i++) {
            Block bi = sortedStarts[i];
            while (sortedEnds[curEnd].end < bi.start) {
                sumBackEnd.remove(sortedEnds[curEnd]);
                curSum -= sortedEnds[curEnd].heigh;
                curEnd++;
            }
            curSum += bi.heigh;
            sumBackEnd.add(bi);
            max = Math.max(max, curSum);
        }
        System.out.print(max);
    }
}
