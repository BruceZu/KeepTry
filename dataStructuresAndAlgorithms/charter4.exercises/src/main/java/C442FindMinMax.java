//  Copyright 2016 The Minorminor Open Source Project
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * finding both the minimumand maximum of n numbers
 * using fewer than 3n/2 comparisons
 */
public class C442FindMinMax {
    public static int[] findMinimumAndMaximumOf(int[] numbers) {
        assert numbers.length >= 2;
        List<Integer> bigList = new ArrayList<>(numbers.length / 2);
        List<Integer> smallerList = new ArrayList<>(numbers.length / 2);
        int i = 1;
        for (; i < numbers.length; i = i + 2) {
            if (numbers[i] > numbers[i - 1]) {
                bigList.add(numbers[i]);
                smallerList.add(numbers[i - 1]);
            } else {
                bigList.add(numbers[i - 1]);
                smallerList.add(numbers[i]);
            }
        }
        if ((numbers.length & 1) == 1) {
            if (numbers[numbers.length - 1] > numbers[numbers.length - 2]) {
                bigList.add(numbers[numbers.length - 1]);
            } else {
                smallerList.add(numbers[numbers.length - 1]);
            }
        }
        Iterator<Integer> iBig = bigList.iterator();
        int biggest = iBig.next();
        while (iBig.hasNext()) {
            int current = iBig.next();
            if (current > biggest) {
                biggest = current;
            }
        }
        Iterator<Integer> iSmall = smallerList.iterator();
        int smallest = iSmall.next();
        while (iSmall.hasNext()) {
            int current = iSmall.next();
            if (current < smallest) {
                smallest = current;
            }
        }
        return new int[]{biggest, smallest};
    }
}


