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

package math.prime;

import java.math.BigInteger;

public class MaxPrime {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    // 2^74_207_281 - 1
    BigInteger bigPrime = BigInteger.ONE.shiftLeft(74_207_281).subtract(BigInteger.ONE);
    long mid = System.currentTimeMillis();

    String s = bigPrime.toString();
    long end = System.currentTimeMillis();

    System.out.printf(
        "Took %.3f seconds to create 2^74_207_281 - 1 and %.3f second toString it%n",
        (mid - start) / 1e3, (end - mid) / 1e3);
    System.out.println(s);
  }
}
