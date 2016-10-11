package array.KthMostFrequentNumber;

import java.util.HashMap;
import java.util.Map;

public class NTimes {
    int v;
    int times;

    public NTimes(int v, int times) {
        this.v = v;
        this.times = times;
    }

    public NTimes increateTimes() {
        this.times++;
        return this;
    }

    @Override
    public String toString() {
        return v + "->" + times;
    }

    public static Map getFrequent(int[] arr) {
        Map<Integer, NTimes> map = new HashMap(arr.length);
        for (int i = 0; i < arr.length; i++) {
            int k = arr[i];
            NTimes kts = map.get(k);
            map.put(k, kts == null ? new NTimes(k, 1) : kts.increateTimes());
        }
        return map;
    }
}