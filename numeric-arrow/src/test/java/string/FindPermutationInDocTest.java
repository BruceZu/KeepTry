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

public class FindPermutationInDocTest {
    private String[] given = {"snow", "fly", "fly"};
    private String[] docHaving = {"snow", "fly", "fly"};
    private String[] docHaving2 = {"snow", "snow", "fly", "fly"};
    private String[] docHaving3 = {"snow", "fly", "fly", "fly"};
    private String[] docHaving4 = {"hi", "snow", "fly", "fly", "yes"};


    @Test(timeout = 3000L, expected = Test.None.class)
    public void testHavePermutation() {
        Assert.assertTrue(FindPermutationInDoc.hasPermutationIn(given, docHaving));
        Assert.assertTrue(FindPermutationInDoc.hasPermutationIn(given, docHaving2));
        Assert.assertTrue(FindPermutationInDoc.hasPermutationIn(given, docHaving3));
        Assert.assertTrue(FindPermutationInDoc.hasPermutationIn(given, docHaving4));
    }
}
