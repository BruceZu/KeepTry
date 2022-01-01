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

package design_pattern_singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Enum Singleton handles lot of stuff for you e.g. controlled instance creation, Serialization
 * safety thread-safe. You don’t need to worry about double checked locking and volatile variable
 * anymore.
 */
public enum E implements Serializable {
  // enum values are static fields which hold instances of that enum type,
  // and initialization order of static fields depends on their position.
  INSTANCE;
  private Logger log = LoggerFactory.getLogger(E.class);

  E() {
    log.info("initial");
  }
}

interface MyInterface {
  String str = "";

  int get();

  void set(int i);
}

enum IMPLEMENTATION implements MyInterface {
  INSTANCE {
    @Override
    public void set(int i) {
      log2.info("set b");
      b = i;
    }
  };

  private Logger log = LoggerFactory.getLogger(IMPLEMENTATION.class);
  private static Logger log2 = LoggerFactory.getLogger(IMPLEMENTATION.class);

  IMPLEMENTATION() {
    log.info("initial");
  }

  private static int b;
  private int a;

  public static MyInterface getInstance() {
    return IMPLEMENTATION.INSTANCE;
  }

  public static void main(String[] args) {
    try {
      Class.forName("design_pattern_singleton.E").newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    //        MyInterface myinterface = IMPLEMENTATION.getInstance();
    //        myinterface.set(1);
    //        System.out.println(myinterface.get());
    //        MyInterface myinterface2 = IMPLEMENTATION.getInstance();
    //        myinterface.set(1);
    //        System.out.println(myinterface2.get());
    //        myinterface2.set(2);
    //        System.out.println(myinterface.get());
  }

  @Override
  public int get() {
    return a;
  }
}
