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

package java_interface_abstractclass;

import java.io.Serializable;

public abstract class My_Abstrct implements My_Interface, Cloneable, Serializable {
    private interface myInnerInterface {
        void okayToDo();
    }

    static String my_name; // you can initialize its value later
    private String field;

    static {
        my_name = "";
    }
    // constructor

    public My_Abstrct() {}

    private My_Abstrct(String str) {}

    My_Abstrct(Object str) {}

    public My_Abstrct(int i) {}
    //

    public static void main(String[] args) {}

    protected abstract String child_name(int name);

    static final synchronized strictfp void action() {}

    transient volatile int b;
}

class Concrete extends My_Abstrct implements My_Interface {

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected String child_name(int name) {
        return null;
    }
}
