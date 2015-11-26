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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WhiteAndRedBallsTest {
    @Test(timeout = 30000000000000L, expected = Test.None.class)
    public void test() {
        assertEquals(0, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(0, 0, 0), 0);

        assertEquals(0, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(1, 0, 0), 0);
        assertEquals(1, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(0, 1, 0), 0);

        assertEquals(0.75, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(1, 1, 0), 0);
        assertEquals(1, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(0, 2, 0), 0);
        assertEquals(0, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(2, 0, 0), 0);

        assertEquals(0, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(3, 0, 0), 0);
        assertEquals(1, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(0, 3, 0), 0);
        assertEquals(0.5, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(2, 1, 0), 0);
        assertEquals(0.88888896, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(1, 2, 0), 0.00000001);

        assertEquals(0, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(4, 0, 0), 0);
        assertEquals(1, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(0, 4, 0), 0);
        assertEquals(0.9375, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(1, 3, 0), 0.01);
        assertEquals(0.76, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(2, 2, 0), 0.01);
        assertEquals(0.830773, WhiteAndRedBalls.getProbabilityOfLastOneIsWhite(4, 4, 0), 0.01);

    }
}
