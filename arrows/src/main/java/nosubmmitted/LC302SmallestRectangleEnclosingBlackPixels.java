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

package nosubmmitted;

/**
 * Difficulty: Hard <pre>
 * An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel.
 * The black pixels are connected, i.e., there is only one black region.
 * Pixels are connected horizontally and vertically. Given the location (x, y) of
 * one of the black pixels, return the area of the smallest (axis-aligned) rectangle
 * that encloses all black pixels.
 * <p/>
 * For example, given the following image:
 * <p/>
 * [
 * "0010",
 * "0110",
 * "0100"
 * ]
 * and x = 0, y = 2,
 * Return 6.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags Binary Search
 */
public class LC302SmallestRectangleEnclosingBlackPixels {
    public int minArea(char[][] image, int x, int y) {
        return 0;
    }

    // beat 67.6 , one of the fast currently
    public int minArea2(char[][] image, int x, int y) {
        int m = image.length;
        int n = image[0].length;
        int start = y;// y is column
        int end = n - 1;
        int mid;
        // find last column containing black pixel
        while (start < end) {
            mid = start + (end - start) / 2 + 1;
            if (checkColumn(image, mid)) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        int right = start;

        start = 0;
        end = y;
        // find first column containing black pixel
        while (start < end) {
            mid = start + (end - start) / 2;
            if (checkColumn(image, mid)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        int left = start;

        start = x; // x is row
        end = m - 1;
        // find first row containing black pixel
        while (start < end) {
            mid = start + (end - start) / 2 + 1;
            if (checkRow(image, mid)) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        int down = start;

        start = 0;
        end = x;
        // find first row containing black pixel
        while (start < end) {
            mid = start + (end - start) / 2;
            if (checkRow(image, mid)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        int up = start;

        return (right - left + 1) * (down - up + 1);
    }

    private boolean checkColumn(char[][] image, int col) {
        for (int i = 0; i < image.length; i++) {
            if (image[i][col] == '1') {
                return true;
            }
        }
        return false;
    }

    private boolean checkRow(char[][] image, int row) {
        for (int j = 0; j < image[0].length; j++) {
            if (image[row][j] == '1') {
                return true;
            }
        }
        return false;
    }

    // same as above with description

    /**
     * <pre>
     * We would expand from a 1 * 1 black square, aggressively expand the 4 boundaries,
     * roughly half of the remaining spaces. if we don't cut any black pixel, we would go back half.
     * This is exactly the process of binary search.
     *
     * The idea is simple but dealing with boundaries can be tricky. For ranges [0, n) and [0, m),
     * the four pointers are starting at 0, n, 0, m initially. This will handle width = 1 or height = 1 gracefully.
     */
    public int minArea3(char[][] image, int x, int y) {
        int m = image.length, n = image[0].length;
        int colMin = binarySearch(image, true, 0, y, 0, m, true);
        int colMax = binarySearch(image, true, y + 1, n, 0, m, false);
        int rowMin = binarySearch(image, false, 0, x, colMin, colMax, true);
        int rowMax = binarySearch(image, false, x + 1, m, colMin, colMax, false);
        return (rowMax - rowMin) * (colMax - colMin);
    }

    public int binarySearch(char[][] image, boolean horizontal, int lower, int upper, int min, int max, boolean goLower) {
        while (lower < upper) {
            int mid = lower + (upper - lower) / 2;
            boolean inside = false;
            for (int i = min; i < max; i++) {
                if ((horizontal ? image[i][mid] : image[mid][i]) == '1') {
                    inside = true;
                    break;
                }
            }
            if (inside == goLower) {
                upper = mid;
            } else {
                lower = mid + 1;
            }
        }
        return lower;
    }

    //  same as above with desciption
    /**
     * <pre>
     * Suppose we have a 2D array
     *
     * "000000111000000"
     * "000000101000000"
     * "000000101100000"
     * "000001100100000"
     *  project the 2D array to 1D array is
     *
     * "000001111100000"
     *
     * the 1D projection array is connected.
     * This means we can do a binary search in each half to find the boundaries, if we know one
     * black pixel's position.
     *
     * To find the left boundary, do the binary search in the [0, y) range and find the first
     * column vector who has any black pixel.
     *
     * To determine if a column vector has a black pixel is O(m) so the search in total is O(m log n)
     *
     * We can do the same for the other boundaries. The area is then calculated by the boundaries.
     * Thus the algorithm runs in O(m log n + n log m)
     */
    private char[][] image;

    public int minArea4(char[][] iImage, int x, int y) {
        image = iImage;
        int m = image.length, n = image[0].length;
        int left = searchColumns(0, y, 0, m, true);
        int right = searchColumns(y + 1, n, 0, m, false);
        int top = searchRows(0, x, left, right, true);
        int bottom = searchRows(x + 1, m, left, right, false);
        return (right - left) * (bottom - top);
    }

    private int searchColumns(int i, int j, int top, int bottom, boolean opt) {
        while (i != j) {
            int k = top, mid = (i + j) / 2;
            while (k < bottom && image[k][mid] == '0') ++k;
            if (k < bottom == opt)
                j = mid;
            else
                i = mid + 1;
        }
        return i;
    }

    private int searchRows(int i, int j, int left, int right, boolean opt) {
        while (i != j) {
            int k = left, mid = (i + j) / 2;
            while (k < right && image[mid][k] == '0') ++k;
            if (k < right == opt)
                j = mid;
            else
                i = mid + 1;
        }
        return i;
    }
}