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

package bitmanipulation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * first Number, char, and left:
 *
 * #16
 *   0  1  2  3
 *   w  o  r  d
 *   ------------
 *            4
 *   w (o  r  d)
 *   1  o (r  d)
 *      2  r (d)
 *         3  d
 *  #8
 *      0  1  2
 *      o  r  d
 *      ---------
 *            3
 *      o (r  d)
 *      1  r (d)
 *         2  d
 *  #4
 *         0  1
 *         r  d
 *         ------
 *            2
 *         r (d)
 *         1  d
 *  #2
 *            0
 *            d
 *            ---
 *            1
 *            d
 *
 *  Note: the improved version can not pass Leetcode,
 *        but in my laptop the result is right with its test data.
 *            only run that case in leetcode is also ok.
 */
/*Idea version
   public static List<String> generateAbbreviations(String word) {
        List<String> r = new ArrayList<String>();
        int len = word.length();
        r.add(len == 0 ? "" : String.valueOf(len));
        for (int i = 0; i < len; i++)
            for (String right : generateAbbreviations(word.substring(i + 1))) {
                String leftNum = i != 0 ? String.valueOf(i) : "";
                r.add(leftNum + word.substring(i, i + 1) + right);
            }
        return r;
    }
 */
public class Leetcode320GeneralizedAbbreviation4 {
    private static Map<Integer, String[]> cache = new HashMap<>();

    public static List<String> generateAbbreviations(String word) {
        return Arrays.asList(call(word.toCharArray(), 0));
    }

    public static String[] call(char[] cs, int f/*from*/) {
        int l = cs.length;
        int width = l - f;
        String[] re = new String[1 << width];
        int size = 0;

        re[size++] = width == 0 ? "" : String.valueOf(width);
        for (int i = f; i < l; i++) {
            String[] rs = cache.get(i + 1);
            if (rs == null) {
                rs = call(cs, i + 1);
            }
            for (String ri : rs) {
                StringBuilder s = new StringBuilder();
                int left = i - f;
                if (left != 0) {
                    s.append(left);
                }
                s.append(cs[i]).append(ri);
                re[size++] = s.toString();
            }
        }
        cache.put(f, re);
        return re;
    }
}
