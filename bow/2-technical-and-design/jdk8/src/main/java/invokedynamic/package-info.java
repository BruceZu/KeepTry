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
 * Nashorn Engine
 * <pre>
 * The JavaScript engine in Java 7 was replaced by another JavaScript engine in Java 8 called Nashorn.
 * This is a lightweight high-performance JavaScript runtime
 * that can be used stand-alone through the command-line tool
 * and also as an embedded scripting engine inside Java applications.
 * Java types can be implemented and extended from JavaScript
 * making Java and JavaScript interoperable and the integration seamless.
 *
 * The Nashorn engine is based on JSR 292 and uses the invokedynamic keyword that was added to Java 7.
 * Until Java 7, the JavaScript engine was based on Mozilla Rhino.
 * Nashorn is a play on Rhino and means rhinoceros in German.
 *
 * By making use of Nashorn, a software developer can embed JavaScript in a Java application
 * and also invoke Java methods and classes from JavaScript code.
 *
 * Manually analyzing large code bases and making changes to code, testing, and deploying the code base
 * is both time-consuming and fraught with risks.
 *
 * Nagarro has an automated solution to analyze code bases and identify the specific points
 * in the code where changes need to be made.
 *
 * Large portions of the migration can also be automated.
 * // todo read
 * @see <a href="http://www.nagarro.com/us/en/perspectives/post/33/Java-8-Nashorn-Engine-for-JavaScript-Interoperability-and-Performance">
 *     Nashorn-Engine</a>
 */
package invokedynamic;