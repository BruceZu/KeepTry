//  Copyright 2018 The KeepTry Open Source Project
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

package array;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *   <a href="Given an array_of_ints[], find the highest product of k integers in the array.">describe</a>
 *    DP in O(nk) time.
 *       f(i, k) = max(A[i] <0 ? A[i] *fmin(i+1, k-1) : A[i]*   f(i+1,k-1),    f(i+1, k));
 *    fmin(i, k) = min(A[i] <0 ? A[i] *   f(i+1, k-1) : A[i]*fmin(i+1,k-1), fmin(i+1, k));
 *
 *    Greedy solution  O(nlogn). (or O(nlogk) with 2 heaps)
 *    So when k < logn, DP is better
 *    bottom up approach is not easy as top-down+cache. because for each (i,k)
 *    need prepare  f() and fmin() for the next step.
 */
public class MaxProduct {
    private float[] A;
    private boolean verbose;
    private Map<String, Double> cache;

    public Map<String, Double> getCache() {
        return cache;
    }

    public MaxProduct(float[] array, boolean v) {
        A = array;
        verbose = v;
        cache = new HashMap<>();
    }
    // top-down + cache
    public double f(int i, int ki) {
        if (ki > A.length) {
            throw new IllegalArgumentException("k should <= array length");
        }

        int scope = (A.length - 1) - (i - 1);
        String key = i + "-" + ki;
        Double r = cache.get(key);
        if (r != null) {
            return r;
        }

        if (scope == ki) {

            r = 1d;
            for (int j = i; j < A.length; j++) {
                r *= A[j];
            }
            if (verbose)
                System.out.println(
                        String.format(
                                "f(from index %s; scope is %s, %s), return %s. cache it",
                                i, scope, ki, r));
            cache.put(key, r);
            return cache.get(key);
        }
        if (ki == 1) {

            r = Double.MIN_VALUE;
            for (int j = i; j < A.length; j++) {
                r = Math.max(r, A[j]);
            }
            if (verbose)
                System.out.println(
                        String.format(
                                "f(from index %s; scope is %s, %s), return %s. cache it",
                                i, scope, ki, r));
            cache.put(key, r);
            return cache.get(key);
        }
        if (verbose)
            System.out.println(
                    String.format(
                            "f(%s, %s) = Math.max(A[%s]=%s *%s(%s,%s), f(%s, %s))",
                            i, ki, i, A[i], A[i] < 0 ? "fmin" : "f", i + 1, ki - 1, i + 1, ki));
        r = Math.max(A[i] * (A[i] < 0 ? fmin(i + 1, ki - 1) : f(i + 1, ki - 1)), f(i + 1, ki));
        if (verbose) System.out.println(String.format("f(%s, %s) = %s. cache it", i, ki, r));

        cache.put(key, r);
        return cache.get(key);
    }

    public double fmin(int i, int ki) {
        if (ki > A.length) {
            throw new IllegalArgumentException("k should <= array length");
        }

        int scope = (A.length - 1) - (i - 1);
        String key = i + "-" + ki + "-min";
        Double r = cache.get(key);
        if (r != null) {
            return r;
        }

        if (scope == ki) {
            r = 1d;
            for (int j = i; j < A.length; j++) {
                r *= A[j];
            }
            if (verbose)
                System.out.println(
                        String.format(
                                "fmin(from index %s; scope is %s, %s), return %s. cache it",
                                i, scope, ki, r));
            cache.put(key, r);
            return cache.get(key);
        }
        if (ki == 1) {
            r = Double.MAX_VALUE;
            for (int j = i; j < A.length; j++) {
                r = Math.min(r, A[j]);
            }
            if (verbose)
                System.out.println(
                        String.format(
                                "fmin(from index %s; scope is %s, %s), return %s. cache it",
                                i, scope, ki, r));
            cache.put(key, r);
            return cache.get(key);
        }

        if (verbose)
            System.out.println(
                    String.format(
                            "fmin(%s, %s) = Math.min(A[%s]=%s *%s(%s,%s), fmin(%s, %s))",
                            i, ki, i, A[i], A[i] < 0 ? "f" : "fmin", i + 1, ki - 1, i + 1, ki));
        r = Math.min(A[i] * (A[i] < 0 ? f(i + 1, ki - 1) : fmin(i + 1, ki - 1)), fmin(i + 1, ki));
        if (verbose) System.out.println(String.format("fmin(%s, %s) = %s. cache it", i, ki, r));

        cache.put(key, r);
        return cache.get(key);
    }

    // --------------------------------------------------------------------------------------
    public static void main(String[] args) {
        Double expected = (-3) * (-3) * 4 * 5 * 6d;
        verify(new float[] {6, 2, 3, 4, -3, 5, -3}, 5, expected, false);
        verify(new float[] {-3, 2, 3, 4, 5, 6, -3}, 5, expected, false);
        verify(new float[] {-3, -3, 2, 3, 4, 5, 6}, 5, expected, false);

        expected = (-4) * (-0.8f) * 0.5f * 2d;
        verify(new float[] {-4, -0.2f, -0.8f, 0, 0.2f, 0.5f, 2}, 4, expected, false);
        expected = (-4) * (-0.8f) * 0.5f * 2d * 0.2f;
        verify(new float[] {-4, -0.2f, -0.8f, 0, 0.2f, 0.5f, 2}, 5, expected, false);
    }

    public static void verify(float[] A, int k, Double expected, boolean verbose) {
        MaxProduct mp = new MaxProduct(A, verbose);
        Double r = mp.f(0, k);
        if (!r.equals(expected)) {
            System.out.println(r);
            System.out.println(expected);
            Map<String, Double> ca = mp.getCache();
            System.out.println(ca.toString());
        } else {
            System.out.println("Pass");
        }
    }
}
