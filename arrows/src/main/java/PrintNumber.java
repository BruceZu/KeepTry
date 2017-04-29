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

import java.util.Arrays;

/**
 * print number in the order and lines like:
 * 1
 * 2 3
 * 4 5 6
 * 7 8 9 10
 * 11 12 13 14 15
 * 16
 */
public class PrintNumber {

    static private boolean printLine(int num, int[] arr, int start) {
        int count = 1;
        while (start < arr.length && count <= num) {
            System.out.print(arr[start] + " ");
            start++;
            count++;
        }
        System.out.println();

        if (count - 1 != num) {// care
            return false;
        }
        return true;
    }

    // O(nlogn)
    static public void print(int[] arr) {
        Arrays.sort(arr);
        int num = 1;
        int start = 0;
        while (true) {
            if (!printLine(num, arr, start)) {
                break;
            }
            start = start + num;
            num++;
        }
    }

    public static void main(String[] args) {
        print(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
    }
}
