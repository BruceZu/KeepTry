//  Copyright 2017 The keepTry Open Source Project
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

package oop;

public class PrimaryTypeClass {
    int i1 = 0;
    int i2;
    Integer i3 = 0;
    Integer i4 = new Integer(0);

    void m() {
        int i5;
        System.out.println(i1 == i2);
        //  System.out.println(i5); need initial
        System.out.println(i1 == i3);
        System.out.println(i2 == i3);
        System.out.println(i4 == i3); // false
    }
    public static void main(String[] args) {
        new PrimaryTypeClass().m();
    }
}
