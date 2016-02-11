package search.tree;//  Copyright 2016 The Sawdust Open Source Project
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

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileTreeRecursion {
    // multiple recursion
    public static long diskUsage(File root) {
        if (root.isDirectory()) {
            long total = 0;
            File[] flist = root.listFiles();
            for (File childf : flist) {
                total += diskUsage(childf);
                System.out.println(total + "\t" + childf);
                return total;
            }
        }
        return root.length();
    }

    /**
     * P-5.27 Implement a recursive method with calling signature find(path, filename) that
     * reports all entries of the file system rooted at the given path having the given file
     * name.
     */
    public static void find(File path, String fname, List<File> r) throws IOException {
        File[] fs = path.listFiles();
        for (File f : fs) {
            if (f.getName().equalsIgnoreCase(fname)) {
                r.add(f);
            }
            if (f.isDirectory()) {
                find(f, fname, r);
            }
        }
    }
}
