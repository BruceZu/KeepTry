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

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static array.FirstRepeatedString.firstDuplicate;

public class FirstRepeatedStringTest {
    @Test(timeout = 50l, expected = Test.None.class)
    public void testFirstRepeatedString() {
        Assert.assertEquals(null, firstDuplicate(null));
        Assert.assertEquals(null, firstDuplicate(new String[]{}));
        Assert.assertEquals(null, firstDuplicate(new String[]{"a"}));
        Assert.assertEquals(null, firstDuplicate(new String[]{"a", "b"}));
        Assert.assertEquals(null, firstDuplicate(new String[]{"a", "b", "c"}));
        Assert.assertEquals("a", firstDuplicate(new String[]{"a", "b", "a"}));
        Assert.assertEquals("a", firstDuplicate(new String[]{"a", null, "a", null}));
        Assert.assertEquals("nullnull", firstDuplicate(new String[]{"a", "b", null, null}));
        Assert.assertEquals("nullnull", firstDuplicate(new String[]{null, null, "a", "b",}));

        HashMap map = new HashMap();
        Assert.assertEquals(null, map.get(null));
        map.put(null, 1);
        Assert.assertEquals(1, map.get(null));
        map.put(null, 2);
        Assert.assertEquals(2, map.get(null));
        map.put(null, null);
        Assert.assertEquals(null, map.get(null));

        HashSet set = new HashSet();
        set.add(null);
        Assert.assertEquals(1, set.size());
        Assert.assertTrue(set.contains(null));
    }
}
