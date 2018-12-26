//  Copyright 2017 The keepTry Open Source Project
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

package string.String_searching_algorithm.Knuth_Morris_Pratt_algorithm;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class TestKMP {
  @Parameterized.Parameters(name = "{index} {0} {1} {2}")
  public static Iterable<Object[]> data() {

    return Arrays.asList(
        new Object[][] {
          {"ABCABD", "CA", 2},
          {"ABAABAAC", "CA", -1},
          {"ABAABAAC", "ABAAC", 3},
          {"ABYABYABYABZ", "ABYABYABZ", 3},
          {"AAAAB", "AAAB", 1},
          {"AAAAB", "", 0},
          {"", "", 0},
          {"", "AA", -1},
          {null, "CA", -1},
          {"", null, -1},
          {"ABC ABCDAB ABCDABCDABDE", "ABCDABD", 15},
          {"CGTGCCTACTTACTTACTTACTTACGCGAA", "CTTACTTAC", 8},
          {"BBBBBBBBBB", "ABBBB", -1},
          {"ABABDABACDABABCABAB", "ABABCABAB", 10},
          {"dddddddddd", "ddddds", -1}
        });
  }

  private String txt;
  private String str;
  private int expected;

  public TestKMP(Object text, Object str, Object expected) {
    this.txt = (String) text;
    this.str = (String) str;
    this.expected = (Integer) expected;
  }

  @Test(timeout = 100L, expected = Test.None.class)
  public void testKMP() {
    Assert.assertEquals(expected, KMP.firstMatchPosition(txt, str));
    Assert.assertEquals(expected, KMP.forceWay(txt, str));
  }
}
