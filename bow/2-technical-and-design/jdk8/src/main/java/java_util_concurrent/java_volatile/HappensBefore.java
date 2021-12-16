//  Copyright 2017 The KeepTry Open Source Project
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

package java_util_concurrent.java_volatile;

public class HappensBefore {

  private Object product = null;
  private volatile boolean hasNew;

  public Object consume() {
    while (!hasNew) {}

    System.out.println("Consumed");

    Object r = product;
    hasNew = false;
    return r;
  }

  public void put(Object o) {

    while (hasNew) {}

    System.out.println("new is ready");
    product = new Object();
    hasNew = true;
  }

  public static void main(String[] args) {
    HappensBefore target = new HappensBefore();
    new Thread(
            () -> {
              while (true) target.put(new Object());
            })
        .start();

    new Thread(
            () -> {
              while (true) target.consume();
            })
        .start();
  }
}
