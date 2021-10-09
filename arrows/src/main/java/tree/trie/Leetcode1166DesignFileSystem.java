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

package tree.trie;

import java.util.HashMap;
import java.util.Map;

public class Leetcode1166DesignFileSystem {
  /*
    Leetcode 1166. Design File System

    You are asked to design a file system that allows you to create new paths and associate them
    with different values.

    The format of a path is one or more concatenated strings of the form:
    / followed by one or more lowercase English letters.
    For example, "/leetcode" and "/leetcode/problems" are valid paths while
    an empty string "" and "/" are not.

    Implement the FileSystem class:

    bool createPath(string path, int value)
       Creates a new path and associates a value to it if possible and
       returns true. Returns false if the path already exists or its parent path doesn't exist.
    int get(string path) Returns the value associated with path or
       returns -1 if the path doesn't exist.

    Input:
    ["FileSystem","createPath","get"]
    [[],["/a",1],["/a"]]
    Output:
    [null,true,1]
    Explanation:
    FileSystem fileSystem = new FileSystem();

    fileSystem.createPath("/a", 1); // return true
    fileSystem.get("/a"); // return 1
    Example 2:

    Input:
    ["FileSystem","createPath","createPath","get","createPath","get"]
    [[],["/leet",1],["/leet/code",2],["/leet/code"],["/c/d",1],["/c"]]
    Output:
    [null,true,true,2,false,-1]
    Explanation:
    FileSystem fileSystem = new FileSystem();

    fileSystem.createPath("/leet", 1); // return true
    fileSystem.createPath("/leet/code", 2); // return true
    fileSystem.get("/leet/code"); // return 2
    fileSystem.createPath("/c/d", 1); // return false because the parent path "/c" doesn't exist.
    fileSystem.get("/c"); // return -1 because this path doesn't exist.


    Constraints:

    The number of calls to the two functions is less than or equal to 104 in total.
    2 <= path.length <= 100
    1 <= value <= 109
  */

  /*
  O(M) time. M is the length of path.
     spend O(M) on
      - find the last "/" of the path,
      - obtain the parent string.
  O(K) space. K represents the number of unique paths added.
  */
  class FileSystem_ {

    HashMap<String, Integer> paths;

    public FileSystem_() {
      this.paths = new HashMap<>();
    }

    public boolean createPath(String path, int value) {
      if (path.isEmpty()
          || (path.length() == 1 && path.equals("/"))
          || this.paths.containsKey(path)) {
        return false;
      }
      String parent = path.substring(0, path.lastIndexOf("/"));
      if (parent.length() > 1 && !this.paths.containsKey(parent)) {
        return false;
      }

      this.paths.put(path, value);
      return true;
    }

    public int get(String path) {
      return this.paths.getOrDefault(path, -1);
    }
  }

  /*
    Trie approach.
         pros:  save on space.
         cons:  get operation is not O(1)

   Time Complexity:
   create() O(T), T components.
   get() O(T)

   Space Complexity:
   create() O(T),  In the worst case, none of the paths will have any common prefixes. T nodes
   get() O(1).

  */
  class FileSystem {
    class TrieNode {
      String path;
      int v = -1;
      Map<String, TrieNode> map = new HashMap<>();

      TrieNode(String name) {
        this.path = name;
      }
    }

    TrieNode root;

    public FileSystem() {
      this.root = new TrieNode("");
    }
    // assume the format of a path is valid
    public boolean createPath(String path, int value) {
      String[] paths = path.split("/");
      TrieNode n = root; // cur node

      for (int i = 1; i < paths.length; i++) {
        String p = paths[i];
        if (!n.map.containsKey(p)) {
          // If it doesn't and it is the last node, add it to the Trie.
          if (i == paths.length - 1) {
            n.map.put(p, new TrieNode(p));
          } else return false;
        }
        n = n.map.get(p);
      }

      // Value not equal to -1 means the path already exists in the trie.
      if (n.v != -1) return false;
      n.v = value;
      return true;
    }
    // assume the format of a path is valid
    public int get(String path) {
      String[] paths = path.split("/");
      TrieNode n = root;
      for (int i = 1; i < paths.length; i++) {
        String p = paths[i];
        // For each component, we check if it exists in the current node's dictionary.
        if (!n.map.containsKey(p)) return -1;
        n = n.map.get(p);
      }

      return n.v;
    }
  }
  // followup:
  //   path can be directory or file，
  //   get all files in that directory or subdirectories

}
