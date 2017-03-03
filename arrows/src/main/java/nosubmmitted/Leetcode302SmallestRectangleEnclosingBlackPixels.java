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
 * <pre>
 * Difficulty: Hard
 * An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel.
 * The black pixels are connected, i.e., there is only one black region.
 * Pixels are connected horizontally and vertically.
 *
 * Given the location (x, y) of one of the black pixels,
 * return the area of the smallest (axis-aligned) rectangle
 * that encloses all black pixels.
 *
 * For example, given the following image:
 *
 * [
 *    "0010",
 *    "0110",
 *    "0100"
 * ]
 * and x = 0, y = 2,
 * Return 6.
 *
 *  Company Tags Google
 *  Tags Binary Search
 */
public class Leetcode302SmallestRectangleEnclosingBlackPixels {
    /**
     * <pre>
     * Suppose we have
     *
     * "000000111000000"
     * "000000101000000"
     * "000000101100000"
     * "000001100100000"
     *
     *  project the 2D array to 1D array is
     *
     * "000001111100000"
     *
     * m is row number, n is column number.
     * As the '1' is connected.
     * with the known pixel(x,y).
     * binary binarySearch left most column and right most column (exclude): O(m log n)
     * binary binarySearch top most row and down most row (exclude): O(n log m)
     *
     * O(m log n + n log m)
     */
    private char[][] image;

    public int minArea(char[][] iImage, int givenRow, int givenCol) {
        image = iImage;
        int rNumber = image.length, colNumber = image[0].length;

        int colFrom = binarySearch(0, givenCol, 0, rNumber, true);
        int colTo_Exclude = binarySearch(givenCol + 1, colNumber, 0, rNumber, false);

        int rowIndexFrom = binarySearch(0, givenRow, colFrom, colTo_Exclude, true);
        int rowIndexTo_Exclude = binarySearch(givenRow + 1, rNumber, colFrom, colTo_Exclude, false);

        return (colTo_Exclude - colFrom) * (rowIndexTo_Exclude - rowIndexFrom);
    }

    private int binarySearch(int colFrom, int colTo_Exclude,
                             int rowIndexFrom, int rowsNumber,
                             boolean isFindingTheFrom) {
        while (colFrom != colTo_Exclude) {
            int colMid = (colFrom + colTo_Exclude) / 2;

            int rowIndex = rowIndexFrom;
            while (rowIndex < rowsNumber && image[rowIndex][colMid] == '0') {
                rowIndex++;
            }

            if (rowIndex < rowsNumber == isFindingTheFrom) { // find '1'
                colTo_Exclude = colMid;
            } else {
                colFrom = colMid + 1;
            }
        }
        return colFrom; // or colTo_Exclude
    }

    // ---------------------------------------------------------------------------------------------------------
    public int Legend_minArea(char[][] image, int givenRow, int givenCol) {
        int rowNum = image.length;
        int colNum = image[0].length;
        int startCol = givenCol;// y is column
        int endCol = colNum - 1;
        int midCol;
        while (startCol < endCol) {
            midCol = (endCol + startCol) / 2 + 1;
            if (hashBlackInColOf(image, midCol)) {
                startCol = midCol;
            } else {
                endCol = midCol - 1;
            }
        }
        int rightMostCol = startCol;

        startCol = 0;
        endCol = givenCol;
        while (startCol < endCol) {
            midCol = (endCol + startCol) / 2;
            if (hashBlackInColOf(image, midCol)) {
                endCol = midCol;
            } else {
                startCol = midCol + 1;
            }
        }
        int leftMostCol = startCol;

        int startRow = givenRow; // x is row
        int endRow = rowNum - 1;
        int midRow;
        while (startRow < endRow) {
            midRow = startRow + (endRow - startRow) / 2 + 1;
            if (checkRow(image, midRow)) {
                startRow = midRow;
            } else {
                endRow = midRow - 1;
            }
        }
        int downMostRow = startRow;

        startRow = 0;
        endRow = givenRow;
        while (startRow < endRow) {
            midRow = startRow + (endRow - startRow) / 2;
            if (checkRow(image, midRow)) {
                endRow = midRow;
            } else {
                startRow = midRow + 1;
            }
        }
        int upMostRow = startRow;
        return (rightMostCol - leftMostCol + 1) * (downMostRow - upMostRow + 1);
    }

    private boolean hashBlackInColOf(char[][] image, int col) {
        for (int row = 0; row < image.length; row++) {
            if (image[row][col] == '1') {
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
}
