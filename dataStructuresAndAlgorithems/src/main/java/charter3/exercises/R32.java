// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package charter3.exercises;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;

class R32 {
    private static Logger log = LoggerFactory.getLogger(R32.class);
    private static Random random = new Random(System.currentTimeMillis());

    public static void randomPickFromTillEmpty(Object[] array) {
        log.info("a given array is " + Arrays.toString(array));
        int entryNumber = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                entryNumber++;
            }
        }
        while (entryNumber != 0) {
            int index = random.nextInt(array.length);

            if (array[index] != null) {
                array[index] = null;
                entryNumber--;
            }
            log.info(String.format("The element on index %d is deleted: %s", index, Arrays.toString(array)));
        }
        log.info(String.format("Done %s", Arrays.toString(array)));
    }
}
