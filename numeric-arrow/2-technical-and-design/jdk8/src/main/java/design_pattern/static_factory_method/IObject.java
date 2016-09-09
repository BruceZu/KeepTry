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

public interface IObject {
    String f();
}

class SpecificObject1 implements IObject {

    public SpecificObject1(String special) {
        //
    }

    @Override
    public String f() {
        /***SPECIFIC TASK 1 CAN BE WRITTEN HERE***/
        return null;
    }
}

class SpecificObject2 implements IObject {

    public SpecificObject2(String special) {
        //
    }

    @Override
    public String f() {
        /***SPECIFIC TASK 2 CAN BE WRITTEN HERE***/
        return null;
    }
}