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

package map;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SortMapByValue {
    public static <K extends Comparable<? super K>, V extends Comparable<? super V>>
    Map sortByValueInDescendingOrder(final Map<K, V> map) {
        Map re = new TreeMap(new Comparator<K>() {
            @Override
            public int compare(K o1, K o2) {
                if (map.get(o1) == null || map.get(o2) == null) {
                    return -o1.compareTo(o2);
                }
                int result = -map.get(o1).compareTo(map.get(o2));
                if (result != 0) {
                    return result;
                }
                return o1.compareTo(o2);
            }
        }
        );
        re.putAll(map);
        return re;
    }

    public static <K extends Comparable<? super K>, V extends Comparable<? super V>>
    Map.Entry<K, V>[] sortedArrayByValueInDescendingOrder(final Map<K, V> map) {

        Map.Entry<K, V>[] result = new Map.Entry[map.size()];
        map.entrySet().toArray(result);

        Arrays.sort(result, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int result = -o1.getValue().compareTo(o2.getValue());
                if (result != 0) {
                    return result;
                }
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return result;
    }
}
