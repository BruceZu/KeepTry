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

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class UnionFindTest {
    @Test(timeout = 10L, expected = Test.None.class)
    public void testUnion() {
        UnionFind uf = new UnionFind();
        Random r = new Random();
        int id = r.nextInt(100);
        Assert.assertTrue(id == uf.findRoot(id));

        uf.union(1, 2);
        Assert.assertTrue(uf.findRoot(2) == uf.findRoot(1));
        Assert.assertEquals(1, uf.treeHeight(1));
        Assert.assertEquals(1, uf.treeHeight(2));

        uf.union(4, 5);
        Assert.assertTrue(uf.findRoot(4) == uf.findRoot(5));
        Assert.assertEquals(1, uf.treeHeight(4));
        Assert.assertEquals(1, uf.treeHeight(5));

        Assert.assertTrue(uf.findRoot(4) != uf.findRoot(1));
        uf.union(1, 5);
        Assert.assertTrue(uf.findRoot(4) == uf.findRoot(1));

        Assert.assertEquals(2, uf.treeHeight(1));
        Assert.assertEquals(2, uf.treeHeight(2));
        Assert.assertEquals(2, uf.treeHeight(4));
        Assert.assertEquals(2, uf.treeHeight(5));

        uf.union(7, 8);
        Assert.assertEquals(1, uf.treeHeight(7));
        Assert.assertEquals(1, uf.treeHeight(8));

        uf.union(1, 8);
        Assert.assertEquals(2, uf.treeHeight(7));
        Assert.assertEquals(2, uf.treeHeight(8));
        Assert.assertEquals(2, uf.treeHeight(1));
        Assert.assertEquals(2, uf.treeHeight(2));
        Assert.assertEquals(2, uf.treeHeight(4));
        Assert.assertEquals(2, uf.treeHeight(5));

        uf.union(10, 11);
        uf.union(12, 13);
        uf.union(11, 13);// height of 2

        uf.union(20, 21);
        uf.union(22, 23);
        uf.union(21, 23);// height of 2

        uf.union(13, 23);// height of 3

        uf.union(30, 31);
        uf.union(32, 33);
        uf.union(31, 33);// height of 2

        uf.union(40, 41);
        uf.union(42, 43);
        uf.union(41, 43);// height of 2

        uf.union(33, 43);// height of 3

        uf.union(23, 43);// height of 4

        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(11));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(12));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(13));

        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(20));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(21));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(22));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(23));

        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(30));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(31));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(32));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(33));

        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(40));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(41));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(42));
        Assert.assertTrue(uf.findRoot(10) == uf.findRoot(43));

        Assert.assertEquals(4, uf.treeHeight(10));
        Assert.assertEquals(4, uf.treeHeight(11));
        Assert.assertEquals(4, uf.treeHeight(12));
        Assert.assertEquals(4, uf.treeHeight(13));

        Assert.assertEquals(4, uf.treeHeight(20));
        Assert.assertEquals(4, uf.treeHeight(21));
        Assert.assertEquals(4, uf.treeHeight(22));
        Assert.assertEquals(4, uf.treeHeight(23));

        Assert.assertEquals(4, uf.treeHeight(30));
        Assert.assertEquals(4, uf.treeHeight(31));
        Assert.assertEquals(4, uf.treeHeight(32));
        Assert.assertEquals(4, uf.treeHeight(33));

        Assert.assertEquals(4, uf.treeHeight(40));
        Assert.assertEquals(4, uf.treeHeight(41));
        Assert.assertEquals(4, uf.treeHeight(42));
    }
}
