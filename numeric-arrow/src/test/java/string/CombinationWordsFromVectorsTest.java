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

import org.junit.Assert;
import org.junit.Test;
import string.CombinationWordsFromVectors;

import java.util.Collections;
import java.util.Vector;

public class CombinationWordsFromVectorsTest {
    @Test(timeout = 3000L, expected = Test.None.class)
    public void test() {
        Vector<Vector<String>> va = new Vector();
        Vector<String> v = new Vector();
        Collections.addAll(v, "quick", "lazy");
        Vector<String> v2 = new Vector();
        Collections.addAll(v2, "brown", "black", "grey");
        Vector<String> v3 = new Vector();
        Collections.addAll(v3, "fox", "dog");
        Collections.addAll(va, v, v2, v3);
        String expected = "quick brown fox \n" +
                "quick brown dog \n" +
                "quick black fox \n" +
                "quick black dog \n" +
                "quick grey fox \n" +
                "quick grey dog \n" +
                "lazy brown fox \n" +
                "lazy brown dog \n" +
                "lazy black fox \n" +
                "lazy black dog \n" +
                "lazy grey fox \n" +
                "lazy grey dog \n";
        Assert.assertEquals(CombinationWordsFromVectors.allCombinationsOf(va), expected);
        Assert.assertEquals(CombinationWordsFromVectors.allCombinationsOf2(va), expected);
    }
}
