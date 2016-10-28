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

package array.KthMostFrequentNumber;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import static array.KthMostFrequentNumber.KthMostFrequentNumber2.usingFixSizeTreeSet;
import static array.KthMostFrequentNumber.KthMostFrequentNumber3.usingFixSizeHeap;
import static array.KthMostFrequentNumber.KthMostFrequentNumber4.usingQuickSelect;
import static array.KthMostFrequentNumber.NTimes.getFrequent;

public class KthMostFrequentNumber {

    public static Integer usingSort(int[] arr, int kth, Comparator smallerFirst) {
        Map<Integer, NTimes> map  = getFrequent(arr);
        NTimes[] ntimes = new NTimes[map.size()];
        map.values().toArray(ntimes);
        Arrays.sort(ntimes, smallerFirst); // NlogN
        System.out.println(Arrays.toString(ntimes));

        return kth <= ntimes.length ? ntimes[ntimes.length - kth].v : null;
    }

    /*----------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        int[] test = new int[]{
                2,
                3,
                0,
                6,
                1,
                5, 5, 5,
                3, 3, 3,
                4, 4, 4, 4,
                2, 2, 2, 2,
                9, 9, 9,
                8, 8,
                7, 7, 7, 7, 7};
        //[0->1, 1->1, 6->1, 8->2, 5->3, 9->3, 3->4, 4->4, 2->5, 7->5]
        Comparator<NTimes> c = (l, r) -> {
            int result = Integer.compare(l.times, r.times); /* order by times firstly, smallest first or on the top */
            if (result == 0) {
                return Integer.compare(l.v, r.v);/* for those with same times, order by v */
            }
            return result;
        };

        System.out.println(usingSort(test, 4, c));
        System.out.println(usingFixSizeTreeSet(test, 4, c));
        System.out.println(usingFixSizeHeap(test, 4, c));
        System.out.println(usingQuickSelect(test, 4));
    }
}