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

package java_double;

public class Java_Double {

    public static void main(String[] args) {
        System.out.println(Double.MIN_VALUE);// Double.MIN_VALUE is positive, > 0
        // why it is so designed?
        System.out.println(Math.min(Double.MIN_VALUE, 0.0d));

        //doing floating point arithmetic they don't pay enough attention to
        // Double.INFINITY,
        // NaN,
        // -0.0
        // and other rules that govern the arithmetic calculations involving them.
        System.out.println(1.0 / 0.0); // Infinity
        System.out.println(-1.0 / 0.0); // -Infinity
        System.out.println(-0.0);  // -0.0
        System.out.println(0.0 / 0.0); // NaN


        // x == Double.NaN always evaluates to false,
        // even if x itself is a NaN. To test if x is a NaN,
        // one should use the method call Double.isNaN(x) to check if given number is NaN or not.
        //
        // If you know SQL, this is very close to NULL there.

        System.out.println(1 == Double.NaN);
        System.out.println(Double.NaN == Double.NaN);
        System.out.println(Double.isNaN(0));
        System.out.println(Double.isNaN(Double.NaN));
    }
}
