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

package java_override;

/**
 * A method signature is part of the method declaration.
 * It is the combination of the method name and the parameter list.
 * <pre>
 * The reason for the emphasis on just the method name and parameter list is because of overloading.
 * It's the ability to write methods that have the same name but accept different parameters.
 * The Java compiler is able to discern the difference between the methods through their method signatures.
 *
 * 2 methods are only difference in return type will not be allowed to pass compile.
 */
public class Overloading {
    private String f(int in) {
        return in + "";
    }

    private String f(String in) {
        return in;
    }

//    private int f(String in) {
//        return 1;
//    }
}
