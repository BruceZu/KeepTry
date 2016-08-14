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

package functionalinterfaces;

import java.util.function.Function;

import static java.lang.System.out;

/**
 * @see <a href="http://openjdk.java.net/projects/jdk8/"> jdk 8</a>
 * @see <a href="http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html"> jdk 8 whats new</a>
 */
public class FunctionalInterfaces {
    public static void main(String[] args) {
        Function<String, String> str = (name) -> "@" + name;
        Function<String, Integer> l = (name) -> name.length();
        Function<String, Integer> l2 = String::length;
        for (String s : args) {
            out.println(l2.apply(s));
        }
    }
}
