//  Copyright 2017 The keepTry Open Source Project
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

package math;

public class Leetcode223RectangleArea {
    static public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int left = Math.max(A, E);
        int right = Math.min(C, G);
        right = Math.max(right, left);

        int bottom = Math.max(B, F);
        int top = Math.min(D, H);
        top = Math.max(top, bottom);

        int overlap = (right - left) * (top - bottom);
        return (C - A) * (D - B) + (G - E) * (H - F) - overlap;
    }

    public static void main(String[] args) {
        System.out.println(computeArea(1, 1, 2, 3, 3, 2, 4, 4));
    }

    // ------------------------------------------------------------------------------
    static public int legendComputeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int overLap = widthOrHeight(A, C, E, G) * widthOrHeight(B, D, F, H);
        return (C - A) * (D - B) + (G - E) * (H - F) - overLap;
    }

    static private int widthOrHeight(int A, int C, int E, int G) {
        if (C < E || A > G)
            return 0;
        return Math.min(C, G) - Math.max(A, E);
    }
}
