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

package list;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShuffleListTest {
    private Node l;
    private Node r;
    private Node l2;
    private Node r2;

    @Before
    public void init() {
        int i = 1;
        l = new Node(1, null);
        Node t = l;
        while (i < 2) {
            i++;
            Node n = new Node(i, null);
            t.next = n;
            t = n;
        }

        i = 3;
        r = new Node(3, null);
        t = r;
        while (i < 4) {
            i++;
            Node n = new Node(i, null);
            t.next = n;
            t = n;
        }

        i = 5;
        l2 = new Node(5, null);
        t = l2;
        while (i < 7) {
            i++;
            Node n = new Node(i, null);
            t.next = n;
            t = n;
        }

        i = 8;
        r2 = new Node(8, null);
        t = r2;
        while (i < 9) {
            i++;
            Node n = new Node(i, null);
            t.next = n;
            t = n;
        }
    }

    @Test(timeout = 1000l, expected = Test.None.class)
    public void shuffleTest() {
        StringBuilder sb = new StringBuilder();
        ShuffleList.toString(l, sb);
        assertEquals(sb.toString(), "12");

        sb = new StringBuilder();
        ShuffleList.toString(r, sb);
        assertEquals(sb.toString(), "34");

        sb = new StringBuilder();
        ShuffleList.toString(ShuffleList.shuffle(l, r), sb);
        assertEquals(sb.toString(), "1324");

        sb = new StringBuilder();
        ShuffleList.toString(l2, sb);
        assertEquals(sb.toString(), "567");

        sb = new StringBuilder();
        ShuffleList.toString(ShuffleList.shuffle(l, l2), sb);
        assertEquals(sb.toString(), "1536274");

        sb = new StringBuilder();
        ShuffleList.toString(r2, sb);
        assertEquals(sb.toString(), "89");

        sb = new StringBuilder();
        ShuffleList.toString(ShuffleList.shuffle(r2, l), sb);
        assertEquals(sb.toString(), "819536274");
    }
}
