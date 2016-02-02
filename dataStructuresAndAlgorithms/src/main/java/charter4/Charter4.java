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

package charter4;

public class Charter4 {
    /*
       Big-O
       http://ssp.impulsetrain.com/big-o.html
       https://en.wikipedia.org/wiki/Big_O_notation
       1, logn, n, nlog n, n2, n3, 2^n.

       = 1 = case in Java : O(1)
       array A.length and A[j].

       = 2 = case in Java : O(n)
       computing the maximum element of an array of n numbers .

       = 3 = case in Java : O(logn)
     */
    public static double arrayMax(double[] data) {
        int n = data.length;
        double currentMax = data[0];
        for (int j = 1; j < n; j++) {
            if (data[j] > currentMax) {
                currentMax = data[j];
            }
        }
        return currentMax;
    }

    /*
       we update the current biggest in an iteration of the loop only if the current
       element is bigger than all the elements that precede it. If the sequence is given to
       us in random order, the probability that the j th element is the largest of the first j
       elements is 1/ j (assuming uniqueness). Hence, the expected number of times we
       update the biggest (including initialization) is the nth Harmonic number.
       It can be shown that Hn is O(logn).

       1+1/2 + 1/3 + 1/4 + … + 1/n
       the nth Harmonic number.  Hn is O(logn).
       Leetcode50 pow(x,n) is another case

     */

    // = 4 = case in Java : O(n2)
    public static String repeat1(char c, int n) {
        String answer = "";
        for (int j = 0; j < n; j++) {
            answer += c;
        }
        return answer;
    }

    /*
       the creation of a new string as a result of a concatenation, requires time that is proportional
       to the length of the resulting string.
       The first time through this loop, the result has length 1,
       the second time through the loop the result has length 2,
       and so on,
       until we reach the final string of length n. Therefore, the overall time taken by this
       algorithm is proportional to 1+2+···+n,
       O(n2)

       = 5 = case in Java : O(n3)
       three-way set disjointness, the worst-case running time of this method

       = 6 = case in Java : O(n2)
     */

    public static boolean disjoint2(int[] groupA, int[] groupB, int[] groupC) {
        for (int a : groupA) {
            for (int b : groupB) {
                if (a == b)
                    for (int c : groupC) {

                        if (a == c) {
                            return false;
                        }
                    }
            }
        }
        return true;
    }

    /*
     The test a == b is evaluated O(n2) times.
     The rest of the time spent depends upon how many matching (a,b) pairs exist. As we
     have noted, there are at most n such pairs; therefore, the management of the loop
     over C and the commands within the body of that loop use at most O(n2) time.
     The total time spent is O(n2).

      = 7 = case in Java : O(nlogn)
      Given an array with n elements. The best sorting algorithms (including those used by Array.sort in Java)
      guarantee a worst-case running time of O(nlog n).
      Once the data is sorted, the subsequent loop runs in O(n) time, and so
      the entire unique2 algorithm runs in O(nlog n) time.

      = 8 = case in Java :O(n)
      given a sequence x consisting of n numbers, we
      want to compute a sequence a such that aj is the average of elements x0, . . . ,xj , for
      j = 0, . . . ,n−1.
     */

    public static double[] prefixAverage2(double[] x) {
        int n = x.length;
        double[] a = new double[n];
        double total = 0;
        for (int j = 0; j < n; j++) {
            total += x[j];
            a[j] = total / (j + 1);
        }
        return a;
    }

    /*
     = Java:
     1> the creation of a new string as a result of a concatenation, requires time that is proportional
     2> an array uses a consecutive block of memory. The j th element can be found, not by iterating through the array one element at a time,
     but by validating the index, and using it as an offset from the beginning of the array in determining the appropriate memory address.
     3> arrays are represented internally with an explicit variable that records the length of the array.

     = Math
     1>
     n^n > n! > n^(n/2)

     Σ (i=1 to n) of [log2i] is Ω(n log n)
     Σ (i=1 to n) of [log2i] is O(n log n)
     Σ (i=1 to n) of [log2i] is Θ(n log n)

     2>
     1+1/2 + 1/3 + 1/4 + … + 1/n
     the nth Harmonic number.  Hn is O(logn).
     An evil king has n bottles of wine, and a spy has just poisoned one of them.
     Unfortunately, they do not know which one it is. The poison is very deadly; just
     one drop diluted even a billion to one will still kill. Even so, it takes a full month
     for the poison to take effect. Design a scheme for determining exactly which
     one of the wine bottles was poisoned in just one month’s time while expending
     O(logn) taste testers.

     https://www.quora.com/We-have-n-bottles-of-wine-and-one-of-them-is-poisoned-How-can-we-determine-which-one-of-the-bottles-is-poisoned-in-one-month-while-expending-only-O-log-n-tasters
     3>
     Given real numbers a > 0, b > 1, d > 1, we have:
     b^(logd(a)) = a^(logd(b))
     */
}
