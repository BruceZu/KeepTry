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

package map;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SortMapByValueTest {

    @Test(timeout = 30l, expected = Test.None.class)
    public void testSortByValueInDescendingOrder() {
        char[] arr = "googler".toCharArray();
        Map<Character, Integer> charToTimes = new HashMap();
        for (int i = 0; i < arr.length; i++) {
            Integer times = charToTimes.get(arr[i]);
            charToTimes.put(arr[i], times == null ? 1 : times + 1);
        }
        Map sortedByTimes = SortMapByValue.sortByValueInDescendingOrder(charToTimes);
        Assert.assertEquals(charToTimes.toString(), "{g=2, e=1, r=1, o=2, l=1}");
        Assert.assertEquals(sortedByTimes.toString(), "{g=2, o=2, e=1, l=1, r=1}");
        Assert.assertEquals(sortedByTimes.containsKey('a'), false);
        Assert.assertEquals(sortedByTimes.get('a'), null);
        Assert.assertEquals(sortedByTimes.get('g'), 2);
        Assert.assertEquals(sortedByTimes.equals(charToTimes), true);
    }
}
