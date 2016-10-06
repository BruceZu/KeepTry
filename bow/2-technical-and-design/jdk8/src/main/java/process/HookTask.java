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

package process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class HookTask {
    Logger log = LoggerFactory.getLogger(HookTask.class);

    private final Path hook;
    private final List<String> args;
    private StringWriter output;
    private int exit;
    private Process ps;
    private File pwd;

    protected HookTask(Path hook, List<String> args, File pwd) {
        this.hook = hook;
        this.args = args;
        this.pwd = pwd;
    }

    public String getOutput() {
        return output != null ? output.toString() : null;
    }

    protected void runHook() {
        try {
            List<String> argv = new ArrayList<>(1 + args.size());
            argv.add(hook.toAbsolutePath().toString());
            argv.addAll(args);

            ProcessBuilder pb = new ProcessBuilder(argv);
            pb.redirectErrorStream(true);

            Map<String, String> env = pb.environment();
            env.put("user", "me");
            if (pwd != null) {
                pb.directory(pwd);
            }

            ps = pb.start();
            ps.getOutputStream().close();

            try (InputStream is = ps.getInputStream()) {
                output = new StringWriter();
                InputStreamReader input = new InputStreamReader(is);
                char[] buffer = new char[4096];
                int n;
                while ((n = input.read(buffer)) != -1) {
                    output.write(buffer, 0, n);
                }
            } finally {
                ps.waitFor(); //block
                exit = ps.exitValue();
            }
        } catch (InterruptedException iex) {
            // InterruptedExeception - timeout or cancel
        } catch (Throwable err) {
            log.error("Error running hook " + hook.toAbsolutePath(), err);
        } finally {
            // close resource
        }

        if (exit == 0) {
            log.debug("hook[" + getName() + "] exitValue:" + exit);
        } else {
            log.info("hook[" + getName() + "] exitValue:" + exit);
        }

        BufferedReader br =
                new BufferedReader(new StringReader(output.toString()));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                log.info("hook[" + getName() + "] output: " + line);
            }
        } catch (IOException iox) {
            log.error("Error writing hook output", iox);
        }
    }

    protected String getName() {
        return hook.getFileName().toString();
    }

    public static void main(String[] args) {
        HookTask task =
                new HookTask(new File(HookTask.class.getResource("hook.sh").getPath()).toPath(),
                        Arrays.asList(new String[]{"valve of the first parameter"}),
                        new File("."));
        task.runHook();
    }
}
