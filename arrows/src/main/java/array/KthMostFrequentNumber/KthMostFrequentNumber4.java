package array.KthMostFrequentNumber;

import java.util.Arrays;
import java.util.Map;

import static array.KthMostFrequentNumber.NTimes.getFrequent;

public class KthMostFrequentNumber4 {
    //O(n) time and O(n) space solution
    public static Integer usingQuickSelect(int[] arr, int kth) {
        Map<Integer, NTimes> map = getFrequent(arr);
        NTimes[] ntimes = new NTimes[map.size()];
        map.values().toArray(ntimes);
      //todo  QuickSelect.introSelectKth(ntimes, 0, arr.length - 1, kth);
        System.out.println(Arrays.toString(ntimes));
        return kth <= ntimes.length ? ntimes[ntimes.length - kth].v : null;
    }
    // Todo: http://www.capacode.com/string/k-most-frequent-items-with-linear-time-solution/
}
