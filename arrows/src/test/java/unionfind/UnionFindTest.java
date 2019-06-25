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

package unionfind;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

public class UnionFindTest {
    @Test(timeout = 1000L, expected = Test.None.class)
    public void testUnion() {
        Set<Integer> elements = IntStream.range(1, 100).boxed().collect(Collectors.toSet());
        UnionFind<Integer> uf = new UnionFindImp(elements);
        Random r = new Random();
        int id = r.nextInt(100);
        Assert.assertTrue(id == uf.find(id));

        uf.union(1, 2);
        Assert.assertTrue(uf.find(2) == uf.find(1));

        uf.union(4, 5);
        Assert.assertTrue(uf.find(4) == uf.find(5));

        Assert.assertTrue(uf.find(4) != uf.find(1));
        uf.union(1, 5);
        Assert.assertTrue(uf.find(4) == uf.find(1));

        uf.union(7, 8);
        uf.union(1, 8);

        uf.union(10, 11);
        uf.union(12, 13);
        uf.union(11, 13); // height of 2

        uf.union(20, 21);
        uf.union(22, 23);
        uf.union(21, 23); // height of 2

        uf.union(13, 23); // height of 3

        uf.union(30, 31);
        uf.union(32, 33);
        uf.union(31, 33); // height of 2

        uf.union(40, 41);
        uf.union(42, 43);
        uf.union(41, 43); // height of 2

        uf.union(33, 43); // height of 3

        uf.union(23, 43); // height of 4

        Assert.assertTrue(uf.find(10) == uf.find(11));
        Assert.assertTrue(uf.find(10) == uf.find(12));
        Assert.assertTrue(uf.find(10) == uf.find(13));

        Assert.assertTrue(uf.find(10) == uf.find(20));
        Assert.assertTrue(uf.find(10) == uf.find(21));
        Assert.assertTrue(uf.find(10) == uf.find(22));
        Assert.assertTrue(uf.find(10) == uf.find(23));

        Assert.assertTrue(uf.find(10) == uf.find(30));
        Assert.assertTrue(uf.find(10) == uf.find(31));
        Assert.assertTrue(uf.find(10) == uf.find(32));
        Assert.assertTrue(uf.find(10) == uf.find(33));

        Assert.assertTrue(uf.find(10) == uf.find(40));
        Assert.assertTrue(uf.find(10) == uf.find(41));
        Assert.assertTrue(uf.find(10) == uf.find(42));
        Assert.assertTrue(uf.find(10) == uf.find(43));
    }
}
