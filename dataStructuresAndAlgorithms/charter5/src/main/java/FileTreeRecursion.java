//  Copyright 2016 The Minorminor Open Source Project
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

public class FileTreeRecursion {
    // multiple recursion
    public static long my_diskUsage(File root) {
        if (root.isDirectory()) {
            long total = 0;
            for (String childname : root.list()) {
                File child = new File(root, childname);
                total += my_diskUsage(child);
                System.out.println(total + "\t" + child);
                return total;
            }
        }
        return root.length();
    }
}
