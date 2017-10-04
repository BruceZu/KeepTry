//  Copyright 2017 The KeepTry Open Source Project
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

package jvm;

public class Pool {
    public static void main(String[] args) {
        // constant pool
        // https://stackoverflow.com/questions/13098143/why-does-the-behavior-of-the-integer-constant-pool-change-at-127
        // https://stackoverflow.com/questions/23252767/string-pool-vs-constant-pool
        //"If the value p being boxed is
        // true, false,
        // a byte,
        // or a char in the range \u0000 to \u007f,
        // or an int or short number between -128 and 127 (inclusive),
        // then let r1 and r2 be the results of any two boxing conversions of p.
        // It is always the case that r1 == r2."
        String s = "aa";
        String s2 = "aa";
        if (s == s2) System.out.println("=yes=");
        else System.out.println("=no=");

        Integer i = 5;
        Integer i2 = 5;
        if (i == i2) System.out.println("=yes=");
        else System.out.println("=no=");

        i = 128;
        i2 = 128;
        if (i == i2) System.out.println("=yes=");
        else System.out.println("=no=");

        Float f = 5f;
        Float f2 = 5f;
        if (f == f2) System.out.println("=yes=");
        else System.out.println("=no=");
    }
}
