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

package invokedynamic;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * @see <a href="http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html"> jdk 8 whats new</a>
 * @see <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/vm/multiple-language-support.html"> multiple language support</a>
 * @see <a href="http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html">Oracle Nashorn: A Next-Generation JavaScript Engine for the JVM 2014 </a>
 */
public class InvokedynamicInstruction {
    public static void main(String[] args) throws Exception {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        engine.eval("function p(s) { print(s) }");
        engine.eval("p('Hello Nashorn');");
        engine.eval(new FileReader("library.js"));
        // TODO: 8/13/16
    }
}
