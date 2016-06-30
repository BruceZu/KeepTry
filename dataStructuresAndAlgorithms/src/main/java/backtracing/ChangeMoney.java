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

package backtracing;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ChangeMoney {
    private int[] currencys;
    private int solutonsNum;

    public ChangeMoney(int[] currencys) {
        this.currencys = currencys;
    }
    
    private void select(int from, int target, List solution) {
        for (int i = from; i < currencys.length; i++) {
            int v = currencys[i];
            int nextTarget = target - v;
            if (nextTarget == 0) {
                solution.add(currencys[i]);
                solutonsNum++;
                System.out.println(solution.toString());

                solution.remove(solution.size() - 1);
                return;
            }
            if (nextTarget < 0) {
                return;
            }
            solution.add(currencys[i]);
            select(i, nextTarget, solution);
            solution.remove(solution.size() - 1);
        }
    }

    public void changeWays(int target) {
        Arrays.sort(currencys);
        solutonsNum = 0;

        List<Integer> solution = new LinkedList<>();
        select(0, target, solution);

        System.out.println("in total:" + solutonsNum);
    }

    public static void main(String[] args) {
        ChangeMoney cm = new ChangeMoney(new int[]{1, 2, 5});
        cm.changeWays(4);
    }
}
