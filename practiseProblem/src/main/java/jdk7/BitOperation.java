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

package jdk7;

public class BitOperation {
    /**
     * @param input int number
     * @return  binary string with 32 length
     */
    public static String bitFormat(int input) {
        String in = Integer.toBinaryString(input);
        if (32 - in.length() > 0)
            return String.format("%0" + (32 - in.length()) + "d%" + in.length() + "s", 0, in);
        return in;
    }
}
