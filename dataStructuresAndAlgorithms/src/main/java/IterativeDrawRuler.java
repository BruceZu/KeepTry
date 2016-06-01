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

// C529
public class IterativeDrawRuler {
    private static void my_drawLine(int tickLength) {
        for (int j = 0; j < tickLength; j++)
            System.out.print("-");
    }

    private static void my_drawInterval(int centralLength) {
        if (centralLength > 1) { // otherwise, do nothing
            my_drawInterval(centralLength - 1); // recursively draw top interval
            my_drawLine(centralLength); // draw center tick line (without label)
            my_drawInterval(centralLength - 1); // recursively draw bottom interval
            return;
        }
        System.out.println();
        my_drawLine(1);
        System.out.println();

    }

    // recursion implementation
    public static void my_drawRuler(int nInches, int majorLength) {
        my_drawLine(majorLength);
        System.out.print("0");
        for (int i = 1; i <= nInches; i++) {
            my_drawInterval(majorLength - 1);
            my_drawLine(majorLength);
            System.out.print(i);
        }
    }

    // loop implementation
    public static void drawRuler(int nInches, int majorLength) {
        /**
         Provide a nonrecursive implementation of the drawInterval method for the English
         ruler project of Section 5.1.2. There should be precisely 2c −1 lines of
         output if c represents the length of the center tick. If incrementing a counter from
         0 to 2c−2, the number of dashes for each tick line should be exactly one more
         than the number of consecutive 1’s at the end of the binary representation of the
         counter.
         */
        // To-do
    }
}
