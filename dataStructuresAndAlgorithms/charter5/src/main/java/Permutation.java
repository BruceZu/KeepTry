//  Copyright 2016 The Minorminor Open Source Project
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {

    // list all k or <=k size permutation of n unique elements.
    // n can be bigger than 64.
    // O(K^N) running time.
    //  @param only  If true, only show permutations of permutationSize,
    //               else also show permutations which size is less than permutationSize.
    public static void my_permutationOf(List<Integer> uniqueList, int permutationSize, List<Integer> permutation, boolean only) {
        if (permutation == null) {
            assert 0 < permutationSize && permutationSize <= uniqueList.size();
            permutation = new ArrayList<>(permutationSize);
            if (!only) {
                System.out.println(Arrays.toString(permutation.toArray()));
            }
        }
        if (permutationSize <= 64) {
            // Todo using bitwise trick
        }
        for (int elment : uniqueList) {
            if (permutation.contains(elment)) {
                continue;
            }
            permutation.add(elment);
            if (!only) {
                System.out.println(Arrays.toString(permutation.toArray()));
            } else if (permutation.size() == permutationSize) {
                System.out.println(Arrays.toString(permutation.toArray()));
            }
            if (permutation.size() < permutationSize) {
                my_permutationOf(uniqueList, permutationSize, permutation, only);
            }
            permutation.remove(permutation.size() - 1);
        }
    }
}
