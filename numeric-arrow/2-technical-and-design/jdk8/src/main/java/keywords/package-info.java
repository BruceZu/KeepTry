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

/**
 * <pre>
 *  final finally finalize
 *
 * final:
 *    mark a variable   “unchangeable”,
 *    make a method not “overrideable”
 *    make a class not “inheritable".
 *
 *   initialized at the time of:
 *     -- creation
 *     -- in Constructor.
 *
 *   is a keyword.
 *
 *
 * finally:
 *   in a try/catch statement to execute code “always"，
 *   Java 7 has a new try with resources statement that you can use to automatically close resources
 *   that explicitly or implicitly implement java.io.Closeable or java.lang.AutoCloseable
 *
 *   it avoid having cleanup code accidentally bypassed by a return, continue, or break。
 *   This block is used to close the resources like database connection, I/O resources.
 *
 *   is a block.
 *
 * finalize ()
 *    is called when an object is garbage collected.
 *    You rarely need to override it. An example:
 *
 *    protected void finalize() {
 *       //free resources (e.g. unallocate memory)
 *      super.finalize();
 *
 *    }
 *
 *    Should not be used to release non-memory resources like file handles, sockets, database connections etc
 *    because Java has only a finite number of these resources
 *
 *    you do not know when the garbage collection is going to kick in to release these non-memory resources
 *    through the finalize() method.
 *    write system resources release code in finalize() method before getting garbage collected.
 *
 *   is the last chance for object to perform any clean-up
 *   but since it’s not guaranteed that whether finalize () will be called, its bad practice to keep resource till finalize call.
 *
 *   is a method.
 */
package keywords;