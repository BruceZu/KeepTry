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

package string;

public class Numeronym {
  /*
  Question comes from https://www.1point3acres.com/bbs/thread-800813-1-1.html

  Not clear what is the meaing of the `m`.
  Just assume `m` means at most keep m minors for each major,
  when there are more than `m` minors in a major,
  the first m-1 one are handled as usual,
  but left minors [mth,last] will be merged as the m-th one

  Java:
    String.split("\\."); // when it is . need escape
   */
  public static String compress(String input, int m) {
    StringBuilder r = new StringBuilder();
    // assume input is valid, else need validation in advance
    String[] majs = input.split("/");
    for (int i = 0; i < majs.length; i++) {
      String maj = majs[i];
      String[] mins = maj.split("\\.");
      int j = 0;
      int sum = 0;
      for (; j < Math.min(m - 1, mins.length); j++) {
        String min = mins[j];
        sum += min.length();
        r.append(min.charAt(0));
        r.append(min.length() - 2);
        r.append(min.charAt(min.length() - 1));
        if (j != mins.length - 1) r.append(".");
      }
      if (j < mins.length) {
        r.append(maj.charAt(sum));
        sum = 0;
        for (; j < mins.length; j++) sum += mins[j].length();
        r.append(sum - 2);
        r.append(maj.charAt(maj.length() - 1));
      }
      if (i != majs.length - 1) r.append("/");
    }
    return r.toString();
  }

  public static void main(String args[]) {
    System.out.println(
        compress("stripe.com/checkout/payments/customer.maria.bay.area.next.job", 3)
            .equals("s4e.c1m/c6t/p6s/c6r.m3a.a12b"));
  }
}
