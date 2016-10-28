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

package array;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @see <a href="https://www.hackerrank.com/contests/citrix-code-master/challenges/server-grid-failure-problem">hacker rank</a>
 */
public class HackerrankServerGridFailureProblem {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int rows = s.nextInt();
        int cols = s.nextInt();
        String[] errs = new String[rows * cols];
        int size = 0;
        String err = null;
        try {
            s.nextLine();
        } catch (NoSuchElementException e) {
            // input may be no error server input
            // care used for hackr rank
            for (int i = 1; i <= rows; i++) {
                for (int j = 1; j <= cols; j++) {
                    System.out.println(String.format("server [%s,%s]", i, j)); // care: need (); it is i here
                }
            }
            return; // care
        }
        while (true) {// care
            try {
                err = s.nextLine();
            } catch (NoSuchElementException e) {
                break; // care used for hackr rank
            }
            if (err.length() == 0) { //care
                break; // used in my laptop
            }

            errs[size++] = err;
        }

        int[] candidator = new int[rows * cols + 1];
        int maxWeight = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            String ei = errs[i];
            String r = ei.substring(ei.indexOf("[") + 1, ei.indexOf(","));
            String c = ei.substring(ei.indexOf(",") + 1, ei.indexOf("]"));

            int row = Integer.valueOf(r);
            int col = Integer.valueOf(c);
            int key;
            if (row - 1 >= 1 && col - 1 >= 1) {
                key = (row - 1 - 1) * rows + col - 1;

                candidator[key]++;
                maxWeight = Math.max(maxWeight, candidator[key]);
            }
            if (row - 1 >= 1) {
                key = (row - 1 - 1) * rows + col;

                candidator[key]++;
                maxWeight = Math.max(maxWeight, candidator[key]);
            }
            if (row - 1 >= 1 && col + 1 <= cols) {
                key = (row - 1 - 1) * rows + col + 1;

                candidator[key]++;
                maxWeight = Math.max(maxWeight, candidator[key]);
            }
            if (col - 1 >= 1) {
                key = (row - 1) * rows + col - 1;

                candidator[key]++;
                maxWeight = Math.max(maxWeight, candidator[key]);
            }
            if (col + 1 <= cols) {
                key = (row - 1) * rows + col + 1;

                candidator[key]++;
                maxWeight = Math.max(maxWeight, candidator[key]);
            }
            if (row + 1 <= rows && col - 1 >= 1) {
                key = (row) * rows + col - 1;

                candidator[key]++;
                maxWeight = Math.max(maxWeight, candidator[key]);
            }
            if (row + 1 <= rows) {
                key = (row) * rows + col;

                candidator[key]++;
                maxWeight = Math.max(maxWeight, candidator[key]);
            }
            if (row + 1 <= rows && col + 1 <= cols) {
                key = (row) * rows + col + 1;

                candidator[key]++;
                maxWeight = Math.max(maxWeight, candidator[key]);
            }
        }
        for (int i = 0; i < candidator.length; i++) {
            if (candidator[i] == maxWeight) {
                System.out.println(String.format("server [%s,%s]", i / rows + 1, i % rows));  // care: need () if not using String.format() ; it is i here
            }
        }
    }
}
