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

public class Leetcode169MajorityElement2 {
    public static int majorityElement(int[] num) {
        int major = num[0];
        int count = 1;
        int size = num.length;
        for (int i = 1; i < size; i++) {
            int v = num[i];
            if (v == major) {
                count++;
                continue;
            }
            if (count != 0) {
                count--;
                continue;
            }
            major = v;
            count = 1;
        }
        return major;
    }
}
