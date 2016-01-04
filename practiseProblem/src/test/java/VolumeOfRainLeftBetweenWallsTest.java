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

public class VolumeOfRainLeftBetweenWallsTest {

    private int[] intPut_MaxAtLeft = {5, 4, 3, 2, 1};
    private int[] intPut_MaxAtRight = {1, 2, 3, 4, 5};
    private int[] intPut_MaxAtMiddle = {1, 2, 3, 4, 5, 4, 3, 2, 1};

    private int[] caseInterview = {2, 5, 1, 2, 3, 4, 7, 7, 6};
    private int[] caseInterview2 = {2, 5, 1, 3, 1, 2, 1, 7, 7, 6};
    private int[] caseInterview3 = {6, 1, 4, 6, 7, 5, 1, 6, 4};


    @Test(timeout = 3000L, expected = Test.None.class)
    public void test() {
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(intPut_MaxAtLeft), 0);
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(intPut_MaxAtMiddle), 0);
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(intPut_MaxAtRight), 0);

        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(caseInterview), 10);
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(caseInterview2), 17);
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(caseInterview3), 13);
    }
}
