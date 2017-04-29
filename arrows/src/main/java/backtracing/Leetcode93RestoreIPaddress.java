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

package backtracing;

import java.util.ArrayList;
import java.util.List;

public class Leetcode93RestoreIPaddress {
    // Given a string containing only digits,
    // restore it by returning all possible valid IP address combinations.
    static public List<String> restoreIpAddresses2(String s) {
        List<String> results = new ArrayList();
        if (s == null || s.length() < 4 || s.length() > 12) {
            return results;
        }
        backtracking(s.toCharArray(), 0, new ArrayList<Integer>(), results, 1);
        return results;
    }

    // O(??)
    static private void backtracking(char[] s, int from, ArrayList<Integer> cur, List<String> r, int count) {
        if (from == s.length && count == 5) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < 3) {
                sb.append(cur.get(i)).append(".");
                i++;
            }
            sb.append(cur.get(i));
            r.add(sb.toString());
            return;
        }
        for (int i = from; i <= from + 2 && i < s.length; i++) {
            int curIp = Integer.valueOf(new String(s, from, i - from + 1));
            if (curIp <= 255) {
                cur.add(curIp);
                backtracking(s, i + 1, cur, r, count + 1);
                cur.remove(cur.size() - 1);
            }
            if (s[from] == '0') {
                break;
            }
        }
    }

    public static void main(String[] args) {
        // null;
        // "";
        // "010010"
        restoreIpAddresses2("0000");
    }

    //O(N^3)
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<String>();
        int len = s.length();
        for (int i = 1; i < 4 && i < len - 2; i++) {
            for (int j = i + 1; j < i + 4 && j < len - 1; j++) {
                for (int k = j + 1; k < j + 4 && k < len; k++) {
                    String s1 = s.substring(0, i),
                            s2 = s.substring(i, j),
                            s3 = s.substring(j, k),
                            s4 = s.substring(k, len);
                    if (isValid(s1)
                            && isValid(s2)
                            && isValid(s3)
                            && isValid(s4)) {
                        res.add(s1 + "." + s2 + "." + s3 + "." + s4);
                    }
                }
            }
        }
        return res;
    }

    public boolean isValid(String s) {
        if (s.length() > 3
                || (s.charAt(0) == '0' && s.length() > 1)
                || Integer.parseInt(s) > 255)
            return false;
        return true;
    }
}
