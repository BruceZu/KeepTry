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

package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <pre>
 *     test cases belongs to one of the four category (Functional ,UX, Security, Performance).
 *     Severity Index (SI) is a number between 1-9
 *     Result for a feature will be given in following format.
 *     E.g  2F 3U 5S 9P
 *     Rules are following:
 *      compare category number; category SI; nothing to do with category name.
 *
 * feature test cases =>   parsed to be'category ->  number of this category;  SI value array'. then compare
 * 1F 2F 3U 4U  => U->2; [4,3] *     F-> 2;[2,1]
 * 1U 2U 3F 4S     U->2; [2,1]       F-> 1;[3]    S-> 1;[4]
 *
 * 1F 2P 3U 4S     s-> 1; [4]       U-> 1; [3]
 * 1F 2P 3U 6S     s-> 1; [6] *
 * 1F 2P 3U 5S     s-> 1; [5]
 *
 * 6F 2U 3S 4P     f-> 1;[6]   p->1;[4]
 * 6F 2U 3S 5P     f-> 1;[6]   p->1;[5] *
 *
 * 3F 4F 3S 4P     f-> 2;[4,3]
 * 2F 5F 3S 7P     f-> 2;[5,2] *
 * 1F 1P 2S 3U
 *
 * 4F 5F 7F 8P     f-> 3;[7,5,4] *
 * 2F 3U 5U 8P     u-> 2;[5,3]
 *
 * 4F 5F 7F 8F     f-> 4;[8,7,5,4] *
 * 9U 3U 5U 9P     u-> 3;[9,5,3]
 *
 * @see <a href="https://www.hackerrank.com/contests/citrix-code-master/challenges/find-the-priority/submissions/code/6978702">hacker rank</a>
 */
public class HackerrankFindthePriority {
    static class TestCaseCategory implements Comparable {
        String category;
        int[] SIValue;
        int numberOfCategory;

        // Descending order
        public int compareTo(Object oth) {
            TestCaseCategory o = (TestCaseCategory) oth;
            if (this.numberOfCategory > o.numberOfCategory) {
                return -1;
            } else if (this.numberOfCategory < o.numberOfCategory) {
                return 1;
            } else {
                for (int i = 0; i < numberOfCategory; i++) {
                    if (this.SIValue[i] > o.SIValue[i]) {
                        return -1;
                    } else if (this.SIValue[i] < o.SIValue[i]) {
                        return 1;
                    }
                }
                return 0;
            }
        }

        public TestCaseCategory(String category, int SI) {
            this.category = category;
            this.SIValue = new int[4];
            SIValue[0] = SI;
            numberOfCategory = 1;
        }

        // Severity Index (SI)
        public TestCaseCategory addSIInDescendingOrder(int si) {
            if (si <= this.SIValue[numberOfCategory - 1]) {
                this.SIValue[numberOfCategory++] = si;
                return this;
            }
            int insertTo = -1;
            for (int i = 0; i < numberOfCategory; i++) {
                if (si > this.SIValue[i]) {
                    insertTo = i;
                    break;
                }
            }
            for (int i = numberOfCategory; i > insertTo; i--) {
                this.SIValue[i] = this.SIValue[i - 1];
            }
            this.SIValue[insertTo] = si;
            numberOfCategory++;
            return this;
        }

        public static List<TestCaseCategory> parseToSortedCategorysInDescendingOrder(String str) {
            Map<String, TestCaseCategory> featureTestCases = new HashMap();
            String[] sic = str.split(" ");
            for (String curSIC : sic) {
                int SI = curSIC.charAt(0);
                String category = "" + curSIC.charAt(1);
                TestCaseCategory tmp = featureTestCases.get(category);
                featureTestCases.put(category, tmp == null ? new TestCaseCategory(category, SI) : tmp.addSIInDescendingOrder(SI));
            }
            List<TestCaseCategory> r = new ArrayList<>(featureTestCases.values());
            Collections.sort(r);
            return r;
        }


        public static List<TestCaseCategory> maxUnstable(List<TestCaseCategory> a, List<TestCaseCategory> b) {
            Iterator<TestCaseCategory> ai = a.iterator();
            Iterator<TestCaseCategory> bi = b.iterator();
            while (ai.hasNext()) {
                TestCaseCategory ac = ai.next();
                TestCaseCategory bc = bi.next();
                if (ac.compareTo(bc) < 0) {
                    return a;
                } else if (ac.compareTo(bc) > 0) {
                    return b;
                }
            }
            return a; // same
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        List<TestCaseCategory> max = null;
        int r = 0;

        int nums = s.nextInt();
        s.nextLine();
        for (int i = 1; i <= nums; i++) {
            if (max == null) {
                max = TestCaseCategory.parseToSortedCategorysInDescendingOrder(s.nextLine());
                r = i;
            } else {
                List<TestCaseCategory> newMax = TestCaseCategory.maxUnstable(max, TestCaseCategory.parseToSortedCategorysInDescendingOrder(s.nextLine()));
                if (newMax != max) {
                    max = newMax;
                    r = i;
                }
            }
        }
        System.out.println(r);
    }
}
