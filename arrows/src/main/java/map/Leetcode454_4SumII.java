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

package map;

import java.util.HashMap;
import java.util.Map;

public class Leetcode454_4SumII {
    // O(N^2)
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        // counts is also the number of group of index
        Map<Integer, Integer> ABSumToCounts = new HashMap<>();

        for(int i=0; i<A.length; i++) {
            for(int j=0; j<B.length; j++) {
                int sum = A[i] + B[j];
                ABSumToCounts.put(sum, ABSumToCounts.getOrDefault(sum, 0) + 1);
            }
        }

        int result=0;
        for(int i=0; i<C.length; i++) {
            for(int j=0; j<D.length; j++) {
                result += ABSumToCounts.getOrDefault(0-(C[i]+D[j]), 0);
            }
        }
        return result;
    }
}
