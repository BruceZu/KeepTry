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

package array.KthMostFrequentNumber;

import java.util.HashMap;
import java.util.Map;

public class NTimes {
    int v;
    int times;

    public NTimes(int v, int times) {
        this.v = v;
        this.times = times;
    }

    public NTimes increateTimes() {
        this.times++;
        return this;
    }

    @Override
    public String toString() {
        return v + "->" + times;
    }

    public static Map getFrequent(int[] arr) {
        Map<Integer, NTimes> map = new HashMap(arr.length);
        for (int i = 0; i < arr.length; i++) {
            int k = arr[i];
            NTimes kts = map.get(k);
            map.put(k, kts == null ? new NTimes(k, 1) : kts.increateTimes());
        }
        return map;
    }
}