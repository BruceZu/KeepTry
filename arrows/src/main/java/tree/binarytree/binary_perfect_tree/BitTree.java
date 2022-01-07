//  Copyright 2022 The KeepTry Open Source Project
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

package tree.binarytree.binary_perfect_tree;

public class BitTree {
  int[][] bit; // perfect binary tree, rule: only both child value is 1 then parent is 1
  int NUM_LEVEL;
  /*
   Level
    0             0
                /    \
    1        0          1
           /  \        /  \
    2     0     0      1    1
         / \   / \   / \  /  \
    3   0  0  0  1  1  1  1  1
        |  |  |  |  |  |  |  |
  index 0  1  2  3  4  5  6  7
  */

  // start is the index at the bottom level,
  // Run time O(length)
  // Space O(1)
  public void clr(int start, int length) {
    int level = NUM_LEVEL - 1;
    int f = start, t = start + length - 1; // [f, t] on current level
    while (level >= 0 && f <= t) {
      for (int i = f; i <= t; i++) bit[level][i] = 1;
      f = f / 2;
      t = t / 2;
      level--;
    }
  }

  public void set(int start, int length) {
    int level = NUM_LEVEL - 1;
    int f = start, t = start + length - 1; // [f, t] on current level
    while (level >= 0 && f <= t) {
      for (int i = f; i <= t; i++) bit[level][i] = 1;
      if (f == t) {
        if (bit[level][t ^ 1] == 1) { // sibling
          f = f / 2;
          t = t / 2;
        } else break;
      } else {
        f = (bit[level][t ^ 1] == 1) ? t / 2 : ++t / 2;
        t = (bit[level][t ^ 1] == 1) ? t / 2 : --t / 2;
      }
      level--;
    }
  }
}
