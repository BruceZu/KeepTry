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
 * 1F 2F 3U 4U-> 4U 3U 2F 1F
 * 1U 2U 3F 4S   2U 1U 4S 3F
 *
 * other ideas:
 * import java.text.*;
 * import java.math.*;
 * import java.util.regex.*;
 * @see <a href="https://www.hackerrank.com/contests/citrix-code-master/challenges/find-the-priority/submissions/code/6978702">hacker rank</a>
 */
class TestCategory implements Comparable {
    String name;
    int[] si;
    int size;

    // Descending order
    public int compareTo(Object oth) {
        TestCategory o = (TestCategory) oth;
        if (this.size > o.size) {
            return -1;
        } else if (this.size < o.size) {
            return 1;
        } else {
            if (size == 0) {//care
                return 0;
            }
            for (int i = 0; i < size; i++) {
                if (this.si[i] > o.si[i]) {
                    return -1;
                } else if (this.si[i] < o.si[i]) {
                    return 1;
                }
            }
            return 0;
        }
    }

    public TestCategory(String name, int v) {
        this.name = name;
        this.si = new int[4];
        si[0] = v;
        size = 1;
    }

    // Severity Index (SI)
    public TestCategory addSIInDescendingOrder(int si) {
        if (si <= this.si[size - 1]) {
            this.si[size++] = si;
            return this;
        }
        int insertTo = -1;
        for (int i = 0; i < size; i++) {
            if (si > this.si[i]) {
                insertTo = i;
                break;
            }
        }
        for (int i = size; i > insertTo; i--) {
            this.si[i] = this.si[i - 1];
        }
        this.si[insertTo] = si;
        size++;
        return this;
    }

    public static List<TestCategory> parseToSortedCategorysInDescendingOrder(String str) {
        Map<String, TestCategory> map = new HashMap();
        String[] cs = str.split(" ");
        for (String c : cs) {
            int v = c.charAt(0);
            String name = "" + c.charAt(1);
            TestCategory tmp = map.get(name);
            map.put(name, tmp == null ? new TestCategory(name, v) : tmp.addSIInDescendingOrder(v));
        }
        List<TestCategory> r = new ArrayList<>(map.values());
        Collections.sort(r);
        return r;
    }


    public static List<TestCategory> maxUnstable(List<TestCategory> a, List<TestCategory> b) {
        Iterator<TestCategory> ai = a.iterator();
        Iterator<TestCategory> bi = b.iterator();
        while (ai.hasNext()) {
            TestCategory ac = ai.next();
            TestCategory bc = bi.next();
            if (ac.compareTo(bc) < 0) {
                return a;
            } else if (ac.compareTo(bc) > 0) {
                return b;
            }
        }
        return a; // same
    }
}

public class HackerrankFindthePriority {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        List<TestCategory> max = null;
        int r = 0;

        int nums = s.nextInt();
        s.nextLine();
        for (int i = 1; i <= nums; i++) {
            if (max == null) {
                max = TestCategory.parseToSortedCategorysInDescendingOrder(s.nextLine());
                r = i;
            } else {
                List<TestCategory> newMax = TestCategory.maxUnstable(max, TestCategory.parseToSortedCategorysInDescendingOrder(s.nextLine()));
                if (newMax != max) {
                    max = newMax;
                    r = i;
                }
            }
        }
        System.out.println(r);
    }
}
