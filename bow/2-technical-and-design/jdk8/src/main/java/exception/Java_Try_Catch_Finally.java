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
            return;
            // System.exit(0);

        } finally {
            System.out.println("I run even there is return in try or catch block \n" +
                    "I will not run if it is instead System.exit(0)");
        }
    }
}


