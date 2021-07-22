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

package tree.segment_tree;

import java.util.function.BiFunction;

/*
  build ST with a full and balanced binary tree.
  all operation is top-down-top
  ST nodes number at most is N + N - 1
   - N = 2^{ceil(log^L)}
   - L is the length of original flat array A[]
  ST use 1-based index array, the length is 2 * N, and A[i] child index is 2 * i, 2 * i^1
*/
class SegmentTreeRMQWithFullBalancedBT {
  private final BiFunction<Integer, Integer, Integer> f;
  private final int[] ST; // 1-based index array
  private final int L;

  SegmentTreeRMQWithFullBalancedBT(int[] A) {
    f = (a, b) -> Math.min(a, b); // for range minimum query (RMQ)
    L = A.length;
    int N = (int) Math.pow(2, (int) Math.ceil(Math.log(L) / Math.log(2)));
    ST = new int[N << 1];
    build(A);
  }

  private void build(int[] A) {
    build(1, 0, L - 1, A);
  }

  // top-down, O(N) time
  private void build(int idx, int l, int r, int[] A) {
    if (l == r) {
      ST[idx] = A[l]; // Leaf node
      return;
    }
    int m = l + r >>> 1;
    build(idx << 1, l, m, A);
    build(idx << 1 ^ 1, m + 1, r, A);
    ST[idx] = f.apply(ST[idx << 1], ST[idx << 1 ^ 1]); // merge from bottom-up
  }

  /* top-down
  l, r, i are index of original flat array A[]
  Assume i in [l, r]
  v is A[i]
  idx is the index of ST[]
  ST[idx] node's application covers the index range [l,r] in A[]
  This function update ST[] when A[i] is updated to value v;
  O(logN)
   */
  private void update(int idx, int l, int r, int i, int v) {
    if (l == r) {
      ST[idx] = v; // leaf node
      return;
    }
    int m = l + r >>> 1;
    if (i <= m) update(idx << 1, l, m, i, v);
    else update(idx << 1 ^ 1, m + 1, r, i, v);
    ST[idx] = f.apply(ST[idx << 1], ST[idx << 1 ^ 1]);
  }

  public void update(int i, int v) {
    update(1, 0, L - 1, i, v);
  }

  /* top-down
  l,r,x,y are index of original flat array A[]
  Assume initially [l, r] contains [x,y]
  ST[idx] node's application covers index range[l, r]
  application: the min/max/sum/... of elements in the index range[x,y] in A[]

  query() Idea: recursion with status-machine conversion
  4 status formed by [l, r], [x,y]
      EMPTY:     y <  l || r < x ; no overlap
      FULL:      x <= l && r <= y; [l, r] is covered by [x, y]

      SIDE:      x <= l or r <= y, some left or right part of [l, r] is not in [x, y], other part in [x, y]
      IN_MIDDLE: l < x  and  y < r, left and right side of [l, r] is not in [x, y], middle part in [x, y]

      EMPTY and FULL is status to stop recursion

  initial status can be one of MIDDLE, FULL, SIDE.
   m = l+r >>> 1,
  keep [x,y] immutable, meanwhile use m cut [l, r]
  each half and [x,y] form one of 4 status

    - EMPTY -> end the conversion, no valid result;
    - FULL  -> end the conversion, check ST[idx] which is cover [l,r]
               [l,r] match exactly the queried range
    - MIDDLE  --- need continue cut into 2 haves ---> EMPTY  MIDDLE || SIDE  SIDE || SIDE EMPTY
    - SIDE    --- need continue cut into 2 haves--->  SIDE, FULL || EMPTY FULL || SIDE, EMPTY

   O(logN) time.

  */
  private int query(int idx, int l, int r, int x, int y) {
    // EMPTY:  [l ,r]  are all outside [x,y]
    //    default value for not applied index range[l,r]
    //    for sum: 0
    //    for min: Integer.MAX_VALUE (current code is for range minimum query (RMQ))
    //    for max: Integer.MIN_VALUE,
    if (y < l || r < x) return Integer.MAX_VALUE; // EMPTY
    if (x <= l && r <= y) return ST[idx]; // FULL
    // MIDDLE or SIDE, need cut
    int m = l + r >>> 1;
    return f.apply(query(idx << 1, l, m, x, y), query(idx << 1 ^ 1, m + 1, r, x, y));
  }

  public int query(int x, int y) {
    return query(1, 0, L - 1, x, y);
  }

  // Other features ============================================================
  int[] Z; // Z[idx] keeps lazy increment(s) of each element in index range covered by ST[idx]
  /* top-down
  O(logL) time.
  */
  private void updateLazy(int idx, int l, int r, int x, int y, int c) {
    if (Z == null) Z = new int[ST.length];
    // push lazy and new increment(s) of each element out of reached scope down to direct children
    if (Z[idx] != 0) {
      ST[idx] += (r - l + 1) * Z[idx]; // this is for pre sum only
      if (l != r) {
        Z[idx << 1] += Z[idx];
        Z[idx << 1 ^ 1] += Z[idx];
      }
      Z[idx] = 0;
    }

    if (y < l || r < x) return; // EMPTY
    if (x <= l && r <= y) { // FULL
      ST[idx] += (r - l + 1) * c; // this is for pre sum only
      if (l != r) {
        Z[idx << 1] += c;
        Z[idx << 1 ^ 1] += c;
      }
      return;
    }

    // SIDE || MIDDLE
    int m = l + r >>> 1;
    updateLazy(idx << 1, l, m, x, y, c);
    updateLazy(idx << 1 ^ 1, m + 1, r, x, y, c);
    ST[idx] = f.apply(ST[idx << 1], ST[idx << 1 ^ 1]); // keep above nodes updated
  }

  // increase a value v on each element in index range [x,y] of original flat array A[]
  // update ST accordingly
  public void updateLazy(int x, int y, int c) {
    updateLazy(1, 0, L - 1, x, y, c);
  }

  private int queryLazy(int idx, int l, int r, int x, int y) {
    // push lazy increment(s) of each element out of reached scope down to direct children
    if (Z[idx] != 0) { // this node is lazy
      ST[idx] += (r - l + 1) * Z[idx]; // this is for pre sum only
      if (l != r) { // update lazy[] for children nodes which is not processed yet
        Z[idx << 1] += Z[idx];
        Z[idx << 1 ^ 1] += Z[idx];
      }
      Z[idx] = 0;
    }

    // query for arr[i..j]
    // default value for not applied index range[l,r]
    // for sum: 0
    // for min: Integer.MAX_VALUE
    // for max: Integer.MIN_VALUE,
    int d = 0;
    if (l > y || r < x) return d; // EMPTY
    if (x <= l && r <= y) return ST[idx]; // FULL
    // MIDDLE || SIDE
    int m = l + r >>> 1;
    return f.apply(queryLazy(idx << 1, l, m, x, y), queryLazy(idx << 1 ^ 1, m + 1, r, x, y));
  }

  public int queryLazy(int x, int y) {
    return queryLazy(1, 0, L - 1, x, y);
  }
}
