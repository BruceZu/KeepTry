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

package design_pattern.factory_method;

import java.util.ArrayList;
import java.util.Iterator;

interface CarFactory {
    Car getCar(); // why we need this interface??
}

//the real factory with an implementation of the getCar() factory method
public class Scenario implements CarFactory {



    public static void main(String[] args) {
        CarFactory carFactory = new Scenario(); // user need care the detail?
        Car myCar = carFactory.getCar();
        Iterator it= new ArrayList<>().iterator();
        myCar.drive();
    }
}
