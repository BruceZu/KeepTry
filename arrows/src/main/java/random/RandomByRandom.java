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

import java.util.Random;

/**
 * using a rand5 function design a rand7 function
 * using a rand7 function design a rand10 function
 */
public class RandomByRandom {
    private static Random r = new Random();

    private static int random5() {
        return r.nextInt(5);
    }

    /**
     * <pre>
     *    random5 : 0, 1, 2, 3, 4
     * A  So using 5 * random5() to making sure r is uniformly distributed.
     *      < 5 : over lap.
     *      > 5 : there is gap.
     *
     *         step: 5
     *      So the r is [ 0, 1, 2, 3, 4;      0
     *                    5, 6, 7, 8, 9;      1
     *                   10,11,12,13,14;      2
     *                   15,16,17,18,19;      3
     *                   20,21,22,23,24 ]     4
     *      So at most it can construct random25(). or add more like:
     *
     *      125 * random5() + 25 * random5() + 5 * random5() + random5()
     *
     *      For those < 25, to make sure the result is uniformly distributed:
     * B    need cut some element
     * C    need %
     */
    public static int random7() {
        int r = 5 * random5() + random5();
        while (r >= 21) {
            r = 5 * random5() + random5();
        }
        return r % 7;
    }

    /**
     * <pre>
     * random5:    0  1  2  3  4
     *      %2:    0  1  0  1  0
     * so need cut out 2
     * or cut out 4
     * or cut out 2, 3 and 4
     * to make sure result is uniformly distributed.
     */
    public static int rand2() {
        int r = random5();
        while (r == 4) {
            r = random5();
        }
        return r % 2;
    }

    /**
     * <pre>
     *  r is      0  1
     *            2  3
     *            4  5
     *            6  7
     */
    public static int rand7() {
        int r = 4 * rand2() + 2 * rand2() + rand2();
        while (r == 7) {
            r = 4 * rand2() + 2 * rand2() + rand2();
        }
        return r;
    }
}
