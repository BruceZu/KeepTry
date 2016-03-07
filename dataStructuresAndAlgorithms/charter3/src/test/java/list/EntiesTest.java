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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.truth.Truth.assertThat;

public class EntiesTest {
    static private Logger log = LoggerFactory.getLogger(EntiesTest.class);

    private short length;
    private short score;
    private String name;
    private Entries es;

    @Before
    public void setup() {
        length = 5;
        score = 8;
        name = "name";
        es = new Entries(length);
    }

    @Test(timeout = 20L, expected = Test.None.class)
    public void addElementInDescending() throws Exception {
        boolean descending = true;
        for (int i = 0; i <= length; i++) {
            es.addInOrder(new Entry(score++, name + i), descending);
        }

        StringBuilder expected = new StringBuilder();
        expected.append("[");
        expected.append("[ name : name5, score : 13], ");
        expected.append("[ name : name4, score : 12], ");
        expected.append("[ name : name3, score : 11], ");
        expected.append("[ name : name2, score : 10], ");
        expected.append("[ name : name1, score :  9]");
        expected.append("]");
        assertThat(es.toString()).isEqualTo(expected.toString());
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void addElementInAscending() throws Exception {
        boolean descending = true;
        for (int i = length; i >= 0; i--) {
            es.addInOrder(new Entry(score++, name + i), !descending);
        }

        StringBuilder expected = new StringBuilder();
        expected.append("[");
        expected.append("[ name : name5, score :  8], ");
        expected.append("[ name : name4, score :  9], ");
        expected.append("[ name : name3, score : 10], ");
        expected.append("[ name : name2, score : 11], ");
        expected.append("[ name : name1, score : 12]");
        expected.append("]");
        assertThat(es.toString()).isEqualTo(expected.toString());
    }

    @Test(timeout = 10L, expected = Test.None.class)
    public void deleteOneElement() throws Exception {
        boolean descending = true;
        for (int i = 0; i <= length; i++) {
            es.addInOrder(new Entry(score++, name + i), descending);
        }
        es.delete((short) 2);
        StringBuilder expected = new StringBuilder();
        expected.append("[");
        expected.append("[ name : name5, score : 13], ");
        expected.append("[ name : name4, score : 12], ");
        expected.append("[ name : name2, score : 10], ");
        expected.append("[ name : name1, score :  9],");
        expected.append(" null");
        expected.append("]");
        assertThat(es.toString()).isEqualTo(expected.toString());
    }
}

