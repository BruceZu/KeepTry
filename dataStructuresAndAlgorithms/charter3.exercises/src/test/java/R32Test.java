//  Copyright 2016 The Minorminor Open Source Project
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

import org.junit.Assert;
        import org.junit.Test;

public class R32Test {
    @Test(timeout = 30L, expected = Test.None.class)
    public void test() {
        String[] array = new String[]{"a", "b", "c", "d"};
        String[] expected = new String[4];
        R32.randomPickFromTillEmpty(array);
        Assert.assertArrayEquals(array, expected);
    }
}
