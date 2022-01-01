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
/*  https://www.journaldev.com/12850/java-9-private-methods-interfaces
                public| final|abstract|static
 field  default   1      1              1
 method default   1             1

 illegal combination for method
    - any 2 of final|static|abstract, static requires method body
    - private and abstract
 interface can have:
   - default method, Java 8
   - static method, Java 8
   - main method,
   - private method, java 9
   - private static method, java 9
   - static inner class
   - inner interface
*/

public interface My_Interface extends Serializable, Cloneable {
  public static final String NAME = ""; // must initialize field value.
  String F = "a";

  public abstract String getName();

  private String getMyName() {
    return "";
  }

  private static void settMyName(String name) {
    //
  }

  interface myInnerInterface {
    public abstract String getName();
  }
  // Object.clone();
  // Cloneable

  static String my_method() {
    return NAME;
  }

  default String method() {
    return "";
  }

  static class InnerClass {}

  public static void main(String[] args) {
    //
  }
}
