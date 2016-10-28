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

package java_override;

public class Override {
    static class Child extends Override {

        static String sf() {
            return "static child";
        }

        private String f(int i) {
            return "child:  method hiding not override f() in parent";
        }

        // note the return type is required to be also same as that in parent
        public String overrided() {
            return "child";
        }
    }

    static String sf() {
        return "static parent";
    }

    private String f(int i) {
        return "Parent";
    }

    public String overrided() {
        return "parent";
    }

    public static void main(String[] args) {
        Override child = new Child();
        System.out.println(child.overrided());

        //
        System.out.println(child.f(0)); // Parent

        Child reallyChild = new Child();
        System.out.println(reallyChild.f(0));

        System.out.println(Child.sf());
        System.out.println(Override.sf());
    }
}

