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

package design_pattern.factory.static_factory_method;

public class DefaultFactory {// interface can not have static method()

    //  type of object will remain undecided or it will be decided by dynamic parameters being passed.
    //  decouples

    public static IObject create() { // interface have no static method
        // decide which type
        return null;
    }

    public static IObject create(Object[] runtimeInformation) {// interface have no static method

        // e.g. FileSystemLogger or DBLogger
        return null;
    }
}

class Senario {
    public static void main(String[] args) {
        IObject object = DefaultFactory.create(new String[]{"runtime special"}); //  The detail have not to be exposure to user?
        object.f();
    }
}

