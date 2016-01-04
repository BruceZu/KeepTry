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

import java.util.HashMap;

// String to float
public class SToF {
    static HashMap<Character, Double> map = new HashMap<Character, Double>(10);

    public SToF() {
        for (int i = 0; i <= 9; i++) {
            map.put(Integer.toString(i).charAt(0), i * 1d); // Attention
        }
    }

    private void valid(char n) throws Exception {
        if (!map.containsKey(n)) {
            throw new Exception("wrong number in input:" + n);
        }
    }

    private double calculate(String in) throws Exception {
        double re = 0;
        int length = in.length();
        for (int i = 0; i < length; i++) {
            char c = in.charAt(i);
            valid(c);
            re += Math.pow(10d, length - i - 1d) * map.get(c); // This function require double
        }
        return re;
    }

    public double toFloat(String in) throws Exception {
        in.trim();
        double result = 0;
        boolean negtive = false;
        if (in.charAt(0) == '+') {
            in = in.substring(1);
        }
        if (in.charAt(0) == '-') {
            in = in.substring(1);
            negtive = true;
        }

        String[] twoParts = in.split("\\."); // Attention
        if (twoParts.length > 2) {
            throw new Exception("multiple dot founded");
        }
        result = calculate(twoParts[0]);

        if (twoParts.length == 2) {
            String r = twoParts[1];
            result += calculate(r) / Math.pow(10, r.length());
        }
        return result * (negtive == true ? -1d : 1d);
    }
}
