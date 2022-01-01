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

package exception;

public class Java_Try_Catch_Finally {
  // finally block will execute even if you put a return statement in the try block or catch block
  // but finally block won't run if you call System.exit() from try or catch block.
  public static void main(String[] args) {
    try {
      // return;
      throw new NullPointerException("");

    } catch (Exception e) {
      // e is not available in finally block
      throw new NullPointerException("e in Catch block");
      // return;
      // System.exit(0);

    } finally {
      System.out.println(
          " run even return in try or catch block \n not run if  System.exit(0) in try or catch block");
      // if no exception happened in finally block the no-caught exception in try-catch block will
      // be lost,
      // else it will be lost
      throw new NullPointerException("e in finally block");
    }
  }
}
