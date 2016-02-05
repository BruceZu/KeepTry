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

public class BinarySearchAndLoopSearch {
    // Tail recursion
    private static boolean my_binarySearch(int target, int[] array, int startIndex, int endIndex) {
        if (startIndex == 0 && endIndex == array.length - 1) {
            if (target > array[array.length - 1] || target < array[0]) {
                return false;
            }

            if (target == array[0]) {
                return true;
            }
            if (target == array[array.length - 1]) {
                return true;
            }

            if (array.length <= 2) {
                return false;
            }
        }
        int mid = (startIndex + endIndex) / 2;
        if (mid == startIndex) { //
            return false;
        }

        if (array[mid] == target) {
            return true;
        }

        if (array[mid] < target) {
            return my_binarySearch(target, array, mid, endIndex);
        }

        return my_binarySearch(target, array, startIndex, mid);
    }

    public static boolean my_binarySearch(int target, int[] array) {
        return my_binarySearch(target, array, 0, array.length - 1);
    }

    private static boolean my_whileSearch(int target, int[] array, int startIndex, int endIndex) {
        if (startIndex == 0 && endIndex == array.length - 1) {
            if (target > array[array.length - 1] || target < array[0]) {
                return false;
            }

            if (target == array[0]) {
                return true;
            }
            if (target == array[array.length - 1]) {
                return true;
            }

            if (array.length <= 2) {
                return false;
            }
        }

        int mid = (startIndex + endIndex) / 2;
        while (mid != startIndex) { //
            if (array[mid] == target) {
                return true;
            }
            if (array[mid] < target) {
                startIndex = mid;
            } else {
                endIndex = mid;
            }
            mid = (startIndex + endIndex) / 2;
        }
        return false;
    }

    public static boolean my_whileSearch(int target, int[] array) {
        return my_whileSearch(target, array, 0, array.length - 1);
    }
}
