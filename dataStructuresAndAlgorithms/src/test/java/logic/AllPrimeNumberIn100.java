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

package logic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AllPrimeNumberIn100 {

    /**
     * A prime number is a whole number greater than 1,
     * whose only two whole-number factors are 1 and itself.
     * The first few prime numbers are 2, 3, 5, 7, 11, 13, 17, 19, 23, and 29.
     *
     * @param n
     * @return
     */
    private static boolean isPrimeNumber(int n) {
        long m = Math.round(Math.pow(n * 1d, 0.5d)); // note : long, not int.
        for (long i = m; i >= 2; i--) {
            if (n / i * i == n) {
                return false;
            }
        }
        return true;
    }

    public static Set<Integer> primeNumbersLessThan(int n) {
        Set<Integer> r = new HashSet<>();
        for (int i = 2; i < n; i++) {
            if (isPrimeNumber(i)) {
                System.out.println(i + ",");
                r.add(i);
            }
        }
        return r;
    }

    public static void randomPrimeNumbersLessThan(int n) {
        Random r= new Random();
        int i= r.nextInt(n);
        if(isPrimeNumber(i)){
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        int i=0;
        while(i++<100) {
            randomPrimeNumbersLessThan(100);
        }
    }
}
