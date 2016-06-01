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

package random;

import com.google.common.truth.Truth;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
 java.util.Random object, which is a pseudo-random number generator,
 that is, an object that computes, or "generates," a sequence of numbers that are statistically random.

 Such a generator needs a place to start, however,which is its seed.

 The sequence of numbers generated for a given seed will always be the same.
 In our program, we set the seed to the current time in milliseconds since January 1, 1970
 (using the method System.currentTimeMillis), which will be different each time we run our program.
 */
public class RandomAndArraysTest {
    Logger log = LoggerFactory.getLogger(RandomAndArraysTest.class);

    @Test(timeout = 90000L, expected = Test.None.class)
    public void test() {
        Random random = new Random(System.currentTimeMillis());
        int size = 0;
        Set randoms = new HashSet<Integer>();
        int border = 100;
        while (size++ < border) {
            int it = random.nextInt(border);
            //log.info(it+"\n");
            randoms.add(it);
        }
        Truth.assertThat(randoms.size()).isNotEqualTo(size);

        Integer[] asArray = new Integer[randoms.size()];
        randoms.toArray(asArray);

        Integer[] clone = asArray.clone();
        Truth.assertThat(Arrays.equals(clone, asArray)).isTrue();

        Arrays.sort(asArray);
        Truth.assertThat(Arrays.equals(clone, asArray)).isFalse();
    }
}
