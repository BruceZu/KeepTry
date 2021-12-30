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

package math.mod;

public class ModularMultiplicativeInverse {
  // With Extended Euclidean algorithm
  // Assume a, b is no zero integer and a,b are conprime
  public static void inverse(int a, int b) {
    int rp_2, rp_1;
    int xp_2, xp_1;
    int yp_2, yp_1;

    rp_2 = a;
    rp_1 = b;

    xp_2 = 1;
    xp_1 = 0;

    yp_2 = 0;
    yp_1 = 1;
    while (rp_1 != 0) {
      int q = rp_2 / rp_1;

      int r = rp_2 - q * rp_1, x = xp_2 - q * xp_1, y = yp_2 - q * yp_1;

      rp_2 = rp_1;
      rp_1 = r;

      xp_2 = xp_1;
      xp_1 = x;

      yp_2 = yp_1;
      yp_1 = y;
    }
    System.out.println(
        String.format("%s*%s + %s*%s = GCD(%s,%s) = %s ", a, xp_2, b, yp_2, a, b, rp_2));
    System.out.println(String.format("GCP of %s and %s is %s", a, b, rp_2));
    System.out.println(
        String.format(
            "The modular multiplicative inverse of %s  modulo %s is %s, 1/%s mod %s is %s (mod %s)",
            a, b, xp_2, a, b, xp_2, b));
    System.out.println(
        String.format(
            "The modular multiplicative inverse of %s  modulo %s is %s, 1/%s mod %s is %s (mod %s)",
            b, a, yp_2, b, a, yp_2, a));
  }

  public static void main(String[] args) {
    inverse(9, 23);
  }
}
