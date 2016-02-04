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

package bitwise;

public class BitOperation {
    /**
     * @param input int number
     * @return  binary string in the format of two's complement with 32 length
     */
    public static String binaryFormat(int input) {
        // Java using two's complement to represent binary number. (sign-and-magnitude, ones' complement, two's complement, excess-N)
        // the left first bit is sign bit.
        // for positive integer, sign-and-magnitude= ones' complement= two's complement
        // for negative integer, except the sign bit, two's complement = its Absolute value's ones' complement +1.

        // for 8 bytes integer scope is [1111 1111 , 0111 1111], its [-127 , 127]
        // 0 is[0000 0000] and [1000 0000]is -128,
        // involve the sign bit in computing and let subtraction becomes addition.

        String in = Integer.toBinaryString(input);
        if (32 - in.length() > 0)
            return String.format("%0" + (32 - in.length()) + "d%" + in.length() + "s", 0, in);
        return in;
    }
}
