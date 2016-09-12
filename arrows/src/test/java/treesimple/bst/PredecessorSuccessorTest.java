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

package treesimple.bst;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static treesimple.bst.PredecessorSuccessor.go;

public class PredecessorSuccessorTest {
    @Test(timeout = 30L, expected = Test.None.class)
    public void test() {
        /**
         *
         *                              17
         *                     /
         *               11
         *            /       \
         *          9         13
         *        /   \      /     \
         *       8    10   12      15
         *                        /  \
         *                      14   16
         */

        PredecessorSuccessor.Node root = new PredecessorSuccessor.Node();
        root.key = 17;
        PredecessorSuccessor.Node n11 = new PredecessorSuccessor.Node();
        n11.key = 11;
        PredecessorSuccessor.Node n9 = new PredecessorSuccessor.Node();
        n9.key = 9;
        PredecessorSuccessor.Node n13 = new PredecessorSuccessor.Node();
        n13.key = 13;
        PredecessorSuccessor.Node n8 = new PredecessorSuccessor.Node();
        n8.key = 8;
        PredecessorSuccessor.Node n10 = new PredecessorSuccessor.Node();
        n10.key = 10;
        PredecessorSuccessor.Node n12 = new PredecessorSuccessor.Node();
        n12.key = 12;
        PredecessorSuccessor.Node n15 = new PredecessorSuccessor.Node();
        n15.key = 15;
        PredecessorSuccessor.Node n14 = new PredecessorSuccessor.Node();
        n14.key = 14;
        PredecessorSuccessor.Node n16 = new PredecessorSuccessor.Node();
        n16.key = 16;

        root.left = n11;
        n11.left = n9;
        n11.right = n13;
        n9.left = n8;
        n9.right = n10;
        n13.left = n12;
        n13.right = n15;
        n15.left = n14;
        n15.right = n16;
        Assert.assertEquals(Arrays.toString(go(17f, null)), "null");
        Assert.assertEquals(Arrays.toString(go(17f, n14)), "[key = 14.0, null]");
        Assert.assertEquals(Arrays.toString(go(13f, root)), "[key = 12.0, key = 14.0]");
        Assert.assertEquals(Arrays.toString(go(13.5f, root)), "[key = 13.0, key = 14.0]");
        Assert.assertEquals(Arrays.toString(go(18f, root)), "[key = 17.0, null]");
        Assert.assertEquals(Arrays.toString(go(8f, root)), "[null, key = 9.0]");
        Assert.assertEquals(Arrays.toString(go(1f, root)), "[null, key = 8.0]");
    }
}
