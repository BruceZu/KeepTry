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

package set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixSizeSubset {

    // List all k size subset of n unique elements.
    // n can be bigger than 64.
    // O(N^K) running time.
    private static void fixSizeSubsets(List<Integer> list,
                                       int subSetSize,
                                       List<Integer> subSet,
                                       int indexFrom,
                                       int indexEnd) {
        if (subSetSize <= 64) {
            // Todo using bitwise trick
        }
        for (int index = indexFrom; index <= indexEnd; index++) {
            subSet.add(list.get(index));
            if (subSet.size() == subSetSize) {
                System.out.println(Arrays.toString(subSet.toArray()));
            }
            if (subSet.size() < subSetSize) {
                fixSizeSubsets(list, subSetSize, subSet,
                        index + 1,
                        endIndex(list.size(), subSetSize, list.size()));
            }
            subSet.remove(list.get(index));
        }
    }

    private static int endIndex(int arraySize, int requiredSubsetSize, int gotNumberm) {
        //        k=4
        //        h=1      k-h=3
        //
        // ...    0,    0,     0     0     x
        //             E=n-3   n-2   n-1   n
        //
        // E = n - (k - h)
        return arraySize - (requiredSubsetSize - gotNumberm);
    }

    public static void fixSizeSubsets(List<Integer> list,
                                      int subSetSize,
                                      List<Integer> subSet) {
        if (subSet == null) {
            assert 0 < subSetSize && subSetSize <= list.size();
            subSet = new ArrayList<>(subSetSize);
        }
        fixSizeSubsets(list, subSetSize, subSet, 0, endIndex(list.size(), subSetSize, 0));
    }
}
