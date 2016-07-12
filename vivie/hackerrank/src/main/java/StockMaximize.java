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

import java.util.Scanner;

//https://www.hackerrank.com/challenges/stockmax
public class StockMaximize {
    static private long calculate(int[] prices, int startIndex, int endIndex) {
        long re = 0;
        int i = startIndex, j = endIndex;
        for (; i != j; ) {
            if (prices[i] < prices[j]) {
                i++;
            } else {
                j--;
            }
        }

        int maxIndex = i;
        for (int k = startIndex; k < maxIndex; k++) { // attention
            re += prices[maxIndex] - prices[k];
        }
        if (endIndex - maxIndex > 1) {
            re += calculate(prices, maxIndex + 1, endIndex);
        }
        return re;
    }

    static public void calculateAndPrint(Scanner in) {
        int size = Integer.valueOf(in.nextInt());
        int[] prices = new int[size];
        for (int i = 0; i < size; i++) {
            prices[i] = in.nextInt();
        }
        System.out.println(calculate(prices, 0, prices.length - 1));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int tests = Integer.valueOf(in.nextInt());
        for (int i = 1; i <= tests; i++) {
            calculateAndPrint(in);
        }
    }
}
