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

package design_pattern.factory;


class Thing {
    public Thing(String name,
                 Object dependency1,
                 Object dependency2) {
        // TODO: 9/6/16
    }
}

public class MyFactory implements IFactory {
    @Override
    public Thing GetThing(String name) {
        return new Thing(name,
                new Object(), new Object());
    }

    public static void main(String arg[]) {
        DefaultProducer defaultProducer = new SpecificProducer(true);
        defaultProducer.taskToBeDone();
    }
}

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

interface Deliverable {
    String f();
}

class SpecificDeliverable implements Deliverable {
    boolean todayIsHoliday;

    public SpecificDeliverable(boolean todayIsHoliday) {
        this.todayIsHoliday = todayIsHoliday;
    }

    @Override
    public String f() {
        /***SPECIFIC TASK CAN BE WRITTEN HERE***/
        return null;
    }
}

abstract class DefaultProducer {
    public Deliverable taskToBeDone() {
        return factoryMethodPattern();
    }

    //We know that when to create an object of required functionality(s) but type of object will remain undecided
    // or it will be decided by dynamic parameters being passed.
    // decouples the client system from the actual implementation classes through the abstract types and factories.
    protected abstract Deliverable factoryMethodPattern();
}

class SpecificProducer extends DefaultProducer {
    boolean todayIsHoliday;

    SpecificProducer(boolean todayIsHoliday) {
        this.todayIsHoliday = todayIsHoliday;
    }


    protected Deliverable factoryMethodPattern() {
        return new SpecificDeliverable(todayIsHoliday);
    }
}




