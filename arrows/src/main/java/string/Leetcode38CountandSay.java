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

package string;

public class Leetcode38CountandSay {

    // can using cache in real life
    // recursion
    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        char[] pre = countAndSay(n - 1).toCharArray();

        int num = 1;
        char c = pre[0];
        StringBuilder r = new StringBuilder();
        for (int i = 1; i < pre.length; i++) {
            if (pre[i] == pre[i - 1]) {
                num++;
            } else {
                r.append(num).append(c);
                num = 1;
                c = pre[i];
            }
        }
        return r.append(num).append(c).toString();
    }

    //loop
    public String countAndSay2(int n) {
        if (n == 1) {
            return "1";
        }
        String pre = "1";
        for (int th = 2; th <= n; th++) {
            char[] precs = pre.toCharArray();
            int num = 1;
            char c = precs[0];
            StringBuilder r = new StringBuilder();

            for (int i = 1; i < precs.length; i++) {
                if (precs[i] == precs[i - 1]) {
                    num++;
                } else {
                    r.append(num).append(c);
                    num = 1;
                    c = precs[i];
                }
            }
            pre = r.append(num).append(c).toString();
        }
        return pre;
    }
}
