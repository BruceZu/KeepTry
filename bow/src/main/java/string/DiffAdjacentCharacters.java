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

package string;

import map.SortMapByValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.max;

/**
 * Input a string, return a new string that all adjacent characters are not the same.
 * For example:
 * input:  google.
 * output: gogole.
 */
public class DiffAdjacentCharacters {

    private class RawData implements Iterable {
        Map.Entry<Character, Integer>[] data;
        int usedIndex = -1;

        public RawData(Map.Entry<Character, Integer>[] data) {
            this.data = data;
        }

        @Override
        public Iterator iterator() {
            return new Iterator() {
                @Override
                public boolean hasNext() {
                    return usedIndex + 1 < data.length;
                }

                @Override
                public Iterable<Character> next() {
                    usedIndex++;
                    final Map.Entry<Character, Integer> current = data[usedIndex];
                    return new Iterable<Character>() {
                        @Override
                        public Iterator<Character> iterator() {
                            return new Iterator<Character>() {
                                char c = current.getKey();
                                int times = current.getValue();

                                @Override
                                public boolean hasNext() {
                                    return times > 0;
                                }

                                @Override
                                public Character next() {
                                    times--;
                                    return c;
                                }

                                @Override
                                public void remove() {
                                    throw new UnsupportedOperationException();
                                }
                            };
                        }
                    };
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    public String diffAdjacentCharactersOf(String in) {
        if (in.length() <= 2 || !Pattern.compile("(\\w)\\1").matcher(in).find()) {
            return in;
        }

        char[] arr = in.toCharArray();
        Map<Character, Integer> charToTimes = new HashMap();
        int maxTimes = 0;
        for (int i = 0; i < arr.length; i++) {
            Integer times = charToTimes.get(arr[i]);
            if (times != null && times + 1 > maxTimes) {
                maxTimes = times + 1;
            }
            charToTimes.put(arr[i], times == null ? 1 : times + 1);
        }

        if (in.length() % 2 == 0 ? maxTimes > in.length() / 2 : maxTimes > in.length() / 2 + 1) {
            return "None";
        }

        Map.Entry<Character, Integer>[] sortedArray = SortMapByValue.sortedArrayByValueInDescendingOrder(charToTimes);
        Iterator<Iterable<Character>> charTimes = new RawData(sortedArray).iterator();
        Iterator<Character> one = null, afterOne = null;
        if (charTimes.hasNext()) {
            one = charTimes.next().iterator();
            if (charTimes.hasNext()) {
                afterOne = charTimes.next().iterator();
            }
        }
        if (one == null || afterOne == null) {
            return "None";
        }

        StringBuilder result = new StringBuilder();
        while (true) {
            while (one.hasNext() && afterOne.hasNext()) {
                result.append(one.next());
                result.append(afterOne.next());
            }
            if (!one.hasNext()) {
                if (charTimes.hasNext()) {
                    one = charTimes.next().iterator();
                    continue;
                }
                break;
            }
            if (!afterOne.hasNext()) {
                if (charTimes.hasNext()) {
                    afterOne = charTimes.next().iterator();
                    continue;
                }
                break;
            }
        }

        if (!one.hasNext() && !afterOne.hasNext()) {
            return result.toString();
        }

        Iterator<Character> left = null;
        if (one.hasNext()) {
            result.append(one.next());
            if (one.hasNext()) {
                left = one;
            } else {
                return result.toString();
            }
        }

        if (afterOne.hasNext()) {
            left = afterOne;
        }


        char[] already = result.toString().toCharArray();
        int index = 0;

        StringBuilder r = new StringBuilder();
        while (left.hasNext()) {
            r.append(already[index++]);
            r.append(left.next());
        }

        return r.append(result.toString().substring(index)).toString();
    }


    private static void swap(int i, int j, char[] arr) {
        char tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
    }

    public static String diffAdjacentCharacters(String inStr) {
        if (inStr.length() <= 2) {
            return inStr;
        }
        Matcher m = Pattern.compile("(\\w)\\1").matcher(inStr);
        if (!m.find()) {
            return inStr;
        }
        int start = m.start(1);
        char[] in = inStr.toCharArray();
        int indexLastRepeatFromHead = -1;
        for (int i = max(1, start); i < in.length; i++) {
            int indexFrom = -1;
            if (in[i] != in[i - 1]) {
                continue;
            }
            indexFrom = i - 1;
            char ci = in[i];
            int j = i + 1;
            for (; j < in.length; j++) {
                if (in[j] == ci) {
                    int maxTimes = j - indexFrom + 1;
                    if (in.length % 2 == 0 ? maxTimes > in.length / 2 : maxTimes > in.length / 2 + 1) {
                        return "None";
                    }
                    continue;
                }
                break;
            }

            if (j != in.length) {
                swap(i, j, in);
            } else {
                j = indexLastRepeatFromHead == -1 ? 0 : indexLastRepeatFromHead;
                for (; j < indexFrom; j++) {
                    if (in[j] != ci && (j == 0 ? ci != in[j + 1] : ci != in[j + 1] && ci != in[j - 1])) {// Attention
                        swap(i, j, in);
                        indexLastRepeatFromHead = j;
                        break;
                    }
                }
                if (j == indexFrom) {
                    return "None";
                }
            }
        }
        return new String(in);
    }
}



