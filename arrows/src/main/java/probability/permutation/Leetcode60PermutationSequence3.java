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

package probability.permutation;

public class Leetcode60PermutationSequence3 {
    /**
     * <pre>
     *   in a loop to decide the result's current number from 0~ n-1
     *      using the
     *          (k - 1) / facs[i - 1]
     *      get the index of current choice scope from where current number can pick
     *      adjust it to current number's index: the index 0 of current scope.
     *      others is kept in older order to be as next number's choice scope.
     *
     *      in next loop k will be k %  facs[i - 1];
     *      if k ==0 now just reverse current choice scope and done.
     *
     *   Note:
     *          n is 1 ~ 9  ====> Character.forDigit(i , 10)
     *
     *   Record:
     *          5 minutes finish, 5 minutes bugfree.
     *
     * @see <a href ="https://leetcode.com/problems/permutation-sequence/">leetcode</a>
     */
    public String getPermutation(int n, int k) {
        //  input check
//        assert (n >= 1 && n <= 9);
//        if (n == 1) {
//            return "1";
//        }

//        if (k < 1 || factorial(n) < k) {
//            return "";
//        }

        int factorial = 1;
        char[] re = new char[n];
        for (int i = 1; i <= n; i++) {
            re[i - 1] = (char) (i + '0');
            factorial *= i;
        }

        for (int i = 0; i < n; i++) {
            factorial = factorial / (n - i);

            int ci = i + (k - 1) / factorial; // the choice index start from i as 0
            char civ = re[ci];
            for (int j = ci; j > i; j--) {
                re[j] = re[j - 1];
            }
            re[i] = civ;

            k = k % factorial;
            k = k == 0 ? factorial : k;
        }

        return new String(re);
    }
}
