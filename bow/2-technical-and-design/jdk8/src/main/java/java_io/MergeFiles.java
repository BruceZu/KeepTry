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

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MergeFiles {
  static class Entry implements Comparable<Entry> {
    public BufferedReader file;
    public String line;

    public Entry(BufferedReader b, String log) {
      this.file = b;
      this.line = log;
    }

    @Override
    public int compareTo(@NotNull Entry o) {
      long myTime = 0, other = 0;
      try {
        myTime = LogUtils.readTimestamp(this.line);
        other = LogUtils.readTimestamp(o.line);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (myTime == other) return 0;
      else if (myTime < other) return -1;
      else return 1;
    }
  }
  /**
   * Merge all *.log files in inputDir and write the output file to outputFile. Assume each log
   * file's entries are sorted by time.
   *
   * @param inputDir Absolute path to the directory where the log files are located.
   * @param outputFile Absolute path to the output file
   */
  // TODO: Add validations.
  public static void mergeLogs(String inputDir, String outputFile) throws Exception {
    List<BufferedReader> files = LogUtils.createReaders(inputDir);
    Queue<Entry> q = new PriorityQueue<>();
    for (BufferedReader f : files) {
      String l = f.readLine();
      if (l != null) q.offer(new Entry(f, l));
    }

    try (PrintWriter w = new PrintWriter(new FileWriter(outputFile))) {
      while (!q.isEmpty()) {
        Entry e = q.poll();
        w.println(e.line);
        w.flush(); // flush buffered content
        String next = e.file.readLine();
        if (next != null) q.offer(new Entry(e.file, next));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      for (BufferedReader f : files) {
        f.close();
      }
    }
  }
}
