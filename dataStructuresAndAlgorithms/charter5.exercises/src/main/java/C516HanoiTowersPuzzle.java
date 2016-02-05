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

/*
   C     T      A
   |     |      |
   |     |      |
   |     |      _
   |     |     ___
   |     |    _____

   The smallest is on the top and the largest is on the bottom.
   The puzzle is to move all the disks from peg A to peg C,
   moving one disk at a time, so that we never place a larger disk on top of a smaller one.
 */
public class C516HanoiTowersPuzzle {
    public static void move(int n, String from, String to, String by) {
        if (n == 2) {
            System.out.println(String.format("move %d from %s to %s", 1, from, by));
            System.out.println(String.format("move %d from %s to %s", 2, from, to));
            System.out.println(String.format("move %d from %s to %s", 1, by, to));
            return;
        }
        move(n - 1, from, by, to);
        System.out.println(String.format("move %d from %s to %s", n, from, to));
        move(n - 1, by, to, from);
    }
}
