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

class A {
    static void m() {
        System.out.println("A");
    }
}

class B extends A {
    static void m() {
        System.out.println("b");
    }
}

class C extends B {
    static void m() {
        System.out.println("c");
    }
}

class D extends C {
    static void m() {
        System.out.println("d");
    }
}

public class Overriding {
    public static void main(String[] args) {
        // - The methods marked public, final or static can not be overridden
        A a = new A();
        a.m();
        A b = new B();
        b.m();

        A c = new C();
        c.m();
        C c2 = new C();
        c2.m();
    }
}
