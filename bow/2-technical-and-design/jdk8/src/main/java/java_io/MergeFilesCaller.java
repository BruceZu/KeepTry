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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java_io.MergeFiles.mergeLogs;

public class MergeFilesCaller {
  public static void main(String[] args) throws Exception {
    // This require IntellijIDEA set the log directory as resource
    String inputDir = ClassLoader.getSystemResource("sample_logs").getPath();
    String outputFile =
        ClassLoader.getSystemResource("expected").getPath() + File.separator + "merged.log";
    mergeLogs(inputDir, outputFile);

    File actual = new File(outputFile);
    if (!actual.exists()) {
      System.out.printf("Output file %s does not exist.%n", outputFile);
      System.exit(1);
    } else {
      List<String> expectedContent =
          Files.readAllLines(
              Paths.get(ClassLoader.getSystemResource("expected/expected.log").toURI()));
      List<String> actualContent = Files.readAllLines((actual.toPath()));
      if (actualContent.size() != expectedContent.size()) {
        System.err.printf(
            "Merged logs file, does not match the expected content. "
                + "Merged logs contain %d lines, expected %d lines.%n",
            actualContent.size(), expectedContent.size());
        System.exit(1);
      }
      for (int line = 0; line < actualContent.size(); line++) {
        if (!actualContent.get(line).equals(expectedContent.get(line))
            && LogUtils.readTimestamp(actualContent.get(line))
                != LogUtils.readTimestamp(expectedContent.get(line))) {
          System.err.printf(
              "Merged logs file, does not match the expected content. "
                  + "At line %d%n\tExpected: %s %n\tActual:   %s%n",
              line + 1, expectedContent.get(line), actualContent.get(line));
          System.exit(1);
        }
      }
      System.out.println("Test passed.");
    }
  }
}
