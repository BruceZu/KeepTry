//
//  Copyright (C) 2015 The Minorminor Open Source Project
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

/**
 * question comes from http://blog.jobbole.com/50705/
 */
public class VolumeOfRainLeftBetweenWalls {

    public static int calculateTheVolumeOfRainOf(int[] walls) {
        int v = 0;
        int left_highest_wall_value = walls[0];
        int right_highest_wall_value = walls[walls.length - 1];

        int i = 1, j = walls.length - 2;
        while(i <= j ) {
            if (left_highest_wall_value <= right_highest_wall_value) {
                if (walls[i] >= left_highest_wall_value) {
                    left_highest_wall_value = walls[i];
                    i++;
                    continue;
                }
                v += left_highest_wall_value - walls[i];
                i++;
                continue;
            }


            if (walls[j] >= right_highest_wall_value) {
                right_highest_wall_value = walls[j];
                j--;
                continue;
            }
            v += right_highest_wall_value - walls[j];
            j--;
        }

        return v;
    }
}

