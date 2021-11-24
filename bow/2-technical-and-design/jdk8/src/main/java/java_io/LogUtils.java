//  Copyright 2021 The KeepTry Open Source Project
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

package java_io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LogUtils {
    public static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S");

    /**
     * List the .log files in the directory passed and create a {@link BufferedReader} for each log
     * file in the directory.
     */
    // TODO Debug: NullPointerException,
    // TODO: handle exceptions.
    // TODO: close the readers somewhere.
    public static List<BufferedReader> createReaders(String inputDir) throws Exception {
        List<BufferedReader> logFileReaders = new ArrayList<>();
        File[] files = new File(inputDir).listFiles((dir, name) -> name.endsWith(".log"));
        for (File file : files) {
            logFileReaders.add(new BufferedReader(new FileReader(file)));
        }
        return logFileReaders;
    }

    /**
     * Extract time stamp from the given log line, parse the time stamp string and return the epoch
     * time.
     */
    public static long readTimestamp(String logLine) throws Exception {
        return DATE_FORMATTER.parse(logLine.substring(0, logLine.indexOf("  "))).getTime();
    }
}
