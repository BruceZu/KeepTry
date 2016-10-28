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

import java.util.HashMap;
import java.util.Map;
import java.util.UnknownFormatConversionException;

/**
 * how about if there is supplementary character
 * how about there is invalid supplementary character.
 */
public class SortString {

    public String sort(String inputString, String sortOrder) throws UnknownFormatConversionException {
        // corner case check
        if (inputString == null || inputString.length() == 0) {
            return "";
        }
        Map<String, Integer> times = new HashMap();
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (Character.isSurrogate(c)) {
                if (Character.isHighSurrogate(c)
                        && i + 1 < inputString.length() && Character.isLowSurrogate(inputString.charAt(i + 1))) {
                    String key = c + "" + inputString.charAt(i + 1);
                    Integer n = times.get(key);
                    times.put(key, n == null ? 1 : n + 1);
                    i++; // care

                } else {
                    throw new UnknownFormatConversionException("invalid supplementary character in " + inputString);
                }
            } else {
                Integer n = times.get(c);
                times.put("" + c, n == null ? 1 : n + 1);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sortOrder.length(); i++) {
            char c = sortOrder.charAt(i);
            String key;
            if (Character.isSurrogate(c)) {
                if (Character.isHighSurrogate(c)
                        && i + 1 < sortOrder.length() && Character.isLowSurrogate(sortOrder.charAt(i + 1))) {
                    key = c + "" + sortOrder.charAt(i + 1);
                    i++; // care
                } else {
                    throw new UnknownFormatConversionException("invalid supplementary character in " + sortOrder);
                }
            } else {
                key = "" + c;
            }

            if (times.containsKey(key)) {
                int t = times.get(key);
                while (t-- != 0) {
                    sb.append(key);
                }
            }
        }
        return sb.toString();
    }
}
