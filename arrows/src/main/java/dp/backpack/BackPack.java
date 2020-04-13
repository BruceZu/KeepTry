//  Copyright 2020 The KeepTry Open Source Project
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

package dp.backpack;

public class BackPack {
    // initial: dp[0] =0;
    // dp[j] = Integer.MAX_value; 1<= j <= W;
    // for scenario where the best solution's cost == W
    //
    // initial: dp[j]= 0; 0<= j <= W;
    // for scenario where the best solution's cost <= W

    private void as01Pack(int cost, int value, int dp[]) {
        int W = dp.length - 1; // dp length = 0 .. W;
        for (int i = W; i >= cost; i--) {
            dp[i] = Math.max(dp[i], dp[i - cost] + value);
        }
    }

    private void asCompletePack(int cost, int value, int dp[]) {
        int W = dp.length - 1; // dp length = 0 .. W;
        for (int i = cost; i <= W; ++i) {
            dp[i] = Math.max(dp[i], dp[i - cost] + value);
        }
    }

    private void asMultiplePack(int cost, int value, int n, int dp[]) {
        int W = dp.length - 1; // dp length = 0 .. W;
        // Queue to keep the elements used for compare during calculate each dp[j];
        // new element is always attached to the tail, the most right one, of this
        // queue.
        int[] q = new int[W];
        // Queue used to calculate the max value of at most n[i] latest elements in q;
        // the max one is the header, the most left one, of this queue
        int[] q2 = new int[W];

        for (int g = 0; g < cost; g++) {// group by j%cost[i]
            // index of head and tail of q; head <= tail.
            int h = -1, t = -1;
            // index of head and tail of q2; head <= tail.
            int h2 = -1, t2 = -1;

            for (int j = 0, cnt = 0; j <= W; j += cost, cnt++) {
                int x = dp[j] - cnt * value; // new element used to compare. attached in q.
                // ========================== [calculate the max] START==========================
                q[++t] = x;
                if (t - h > n + 1) {// valid window length is n+1;
                    h++; // h point the invalid one just out of valid window
                    int stale = q[h];
                    if (q2[h2] == stale) {
                        h2++;
                    }
                }
                if (t2 == -1) {
                    t2++;
                    h2++;
                    q2[t2] = x;
                    // need not compare. only one element
                } else {
                    while (h2 <= t2 && q2[t2] < x) { // q2 is a descending queue, only <
                        t2--;
                    }
                    q2[++t2] = x;
                }
                int max = q2[h2];
                // ========================== [calculate the max] END==========================
                dp[j] = max + cnt * value;
            }
        }
    }

    /**
     * current kind of object may be 01 backpack, complete backpack or multiple
     * backpack
     *
     * @param dp:    max value achieved by a backpack of W and first kinds of
     *               objects.
     * @param cost:  current object cost
     * @param n:     current object: number that can be used
     * @param value: current object value
     */
    public void pack(int dp[], int cost, int n, int value) {
        int W = dp.length - 1; // dp length = 0 .. W;  W is the top limitation of the backpack
        if (n == 0 || cost == 0)
            return; // it is initial value.
        if (n == 1) { // 01 backpack
            as01Pack(cost, value, dp);
        } else if (n * cost >= W) { // complete backpack (n*cost >= W)
            asCompletePack(cost, value, dp);
        } else { // assume only 3 options: 01, complete and multiple backpack
            asMultiplePack(cost, value, n, dp);
        }
    }
}
