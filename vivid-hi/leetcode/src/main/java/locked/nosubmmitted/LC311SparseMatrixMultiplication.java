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

package locked.nosubmmitted;

/**
 * Difficulty: Medium
 * <p/>
 * <pre>
 * Given two sparse matrices A and B, return the result of AB.
 *
 * You may assume that A's column number is equal to B's row number.
 *
 * Example:
 *
 * A = [
 *   [ 1, 0, 0],
 *   [-1, 0, 3]
 * ]
 *
 * B = [
 *   [ 7, 0, 0 ],
 *   [ 0, 0, 0 ],
 *   [ 0, 0, 1 ]
 * ]
 *
 *
 *      |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
 * AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
 *      | 0 0 1 |
 * Hide Company Tags LinkedIn Facebook
 * Hide Tags Hash Table
 */
public class LC311SparseMatrixMultiplication {
    // beat 94.39
    public int[][] multiply(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, nB = B[0].length;
        int[][] C = new int[m][nB];

        for (int i = 0; i < m; i++) {
            for (int k = 0; k < n; k++) {
                if (A[i][k] != 0) {
                    for (int j = 0; j < nB; j++) {
                        if (B[k][j] != 0) C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
        return C;
    }


    // good example case of python beat 98%
    // checking with any() definitely speeds things up. Other than that,
    // I used a bit of trick to skip all the zeros in vector multiplication, since the matrix is sparse.

    /** <pre>
     def multiply(self, A, B):

     result=[[0]*len(B[0]) for _ in xrange(len(A))]

     #store indexes non-zero elements in each line of B.  Checking with any() first at this point speeds up from 124ms to 96ms
     Bi=[[i for i,v in enumerate(l) if v] if any(l) else [] for l in B]

     for i in xrange(len(A)):
     if not any(A[i]):
     continue
     for j in xrange(len(A[0])):
     if A[i][j]:
     for k in Bi[j]:
     result[i][k]+=A[i][j]*B[j][k]

     return result
     */
}
