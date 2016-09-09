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

package design_pattern.static_factory_method;


import design_pattern.factory_method.IObject;

/**
 * <pre>
 * factory method Pattern
 * It is good idea to use 'factory methods' inside object when:
 *
 * Object's class is designed so that objects it creates were specified by sub-classes
 * Object's class doesn't know what exact sub-classes it have to create
 * Object's class delegates its duties to auxiliary sub-classes and doesn't know what exact class will take these duties
 *
 * It is good idea to use 'abstract factory class' when:
 *
 * Your object shouldn't depend on how its inner objects are created and designed
 * Group of linked objects should be used together and you need to serve this constraint
 * Object should be configured by one of several possible families of linked objects that will be a part of your parent object
 * It is required to share child objects showing interfaces only but not an implementation
 */

public class DefaultFactory {// interface can not have static method()

    //We know that when to create an object of required functionality(s) but type of object will remain undecided
    // or it will be decided by dynamic parameters being passed.
    // decouples the client system from the actual implementation classes through the abstract types and factories.

    public static IObject create() { // interface have no static method
        // decide which type's object to create and return
        return null;
    }

    public static IObject create(Object[] runtimeInformation) {// interface have no static method
        // decide which type's object to create and return
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

