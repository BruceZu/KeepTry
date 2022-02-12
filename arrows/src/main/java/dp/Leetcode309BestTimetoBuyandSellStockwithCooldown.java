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

package dp;

public class Leetcode309BestTimetoBuyandSellStockwithCooldown {

  /*
   At any moment, we can only be in one state.
    -  must sell the stock before you buy again)
    -  sell your stock, cooldown one day
    https://en.wikipedia.org/wiki/Finite-state_machine
    Assemble the above states and actions into a state machine,
    So, 3 status and 3 Action:

    1 ---(sell)---> coo down(0)
    1 ---(keep)---> 1 ||  0---(buy)--> 1
    0---(keep)--> 0   || coo down(0) ---( forced cool)--> 0

    At last, check the bigger one of profit of 0 and coo down(0)
    Need not care 1 which bought in the stock at the last price point, which only leads
    to the reduction of profits.

    Use Integer.MIN_VALUE as the initial profit value of `sold` and `hold`
    which are intended to render the paths that start from these two states impossible.

  O(N) time O(1) space
  */
  public int maxProfit2(int[] p) {
    if (p.length == 0) return 0;

    int pre0 = Integer.MIN_VALUE;
    int preCool0 = 0; // only after cool down could buy
    int pre1 = Integer.MIN_VALUE;

    for (int i = 0; i < p.length; i++) {
      int curCool0 = pre1 + p[i];
      int cur1 = Math.max(preCool0 - p[i], pre1); // current hold
      int cur0 = Math.max(preCool0, pre0); // current not hold
      pre0 = curCool0;
      pre1 = cur1;
      preCool0 = cur0;
    }
    return Math.max(pre0, preCool0);
  }

  public int maxProfit(int[] prices) {
    int cool = Integer.MIN_VALUE, h1 = Integer.MIN_VALUE, h0 = 0;
    for (int p : prices) {
      int tmp = cool;
      cool = h1 + p;
      h1 = Math.max(h1, h0 - p);
      h0 = Math.max(h0, tmp);
      // Notice the order: s, then h, then r.
      // at last here s has been updated and not the previous s, that is why need a `preS`
    }
    return Math.max(cool, h0);
  }
}
