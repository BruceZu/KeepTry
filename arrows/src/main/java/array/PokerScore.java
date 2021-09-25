//  Copyright 2021 The KeepTry Open Source Project
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

package array;

public class PokerScore {
  /*
  Return score of cards
  Assume each card value is valid:
      cards[i] is one of '2','3','4','5','6','7','8','9','10','J','Q','K','A'
      duplicated card number <=4
      cards.length <=42
  Rules used to calculate score of cards
   -A any continuous cards score with number >=5 is greater than that of any 3 cards with same value
   -B any 3 cards score with same value is greater than that of any 2 cards with same value
   -C any 2 cards score with same value is greater than that of any card value.
   -D card A score is greater than that of card 'K'
  O(N) time, space
  */
  private int score(String[] cards) {
    int[] v = new int[15];
    for (String c : cards) {
      switch (c) {
        case "A":
          v[14]++;
          break;
        case "K":
          v[13]++;
          break;
        case "Q":
          v[12]++;
          break;
        case "J":
          v[11]++;
          break;
        default:
          v[Integer.valueOf(c)]++;
      }
    }
    // Rule A
    int startOfContinues = -1, i = 0, len = 0;
    while (i < v.length) {
      while (i < v.length && v[i] == 0) i++;
      int start = i;
      while (i < v.length && v[i] != 0) i++;
      int curLen = i - start;
      if (curLen >= 5 && curLen > len) {
        len = curLen;
        startOfContinues = start;
      }
    }
    if (len != 0) return 14 * (len - 1) + startOfContinues;
    // Rule B,C,D
    int maxScore = 0;
    for (int j = 0; j < v.length; j++) maxScore = Math.max(maxScore, 14 * (v[i] - 1) + i);
    return maxScore;
  }
  }
