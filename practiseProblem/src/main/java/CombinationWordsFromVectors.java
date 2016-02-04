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

import java.util.Vector;

/*
  You're given a vector of vectors of words, e.g.:
  [['quick', 'lazy'], ['brown', 'black', 'grey'], ['fox', 'dog']].

   Write a generalized function that prints all combinations of one word from the first vector,
   one word from the second vector, etc.
   NOTE: the number of vectors and number of elements within each vector may vary.

   For the input above, it should print (in any order):
   quick brown fox
   quick brown dog
   quick black fox
   quick black dog
   ...
   lazy grey dog

   require: Non-Recursive
 */
public class CombinationWordsFromVectors {
    private static boolean hasNext(int[] cursor, Vector<Vector<String>> v) {
        for (int i = cursor.length - 1; i >= 0; i--) {
            int currentIndex = cursor[i];
            if (currentIndex + 1 > v.elementAt(i).size() - 1) {
                cursor[i] = 0;
                continue;
            }
            cursor[i] += 1;
            return true;
        }
        return false;
    }

    static String allCombinationsOf(Vector<Vector<String>> v) {
        // Non-Recursive
        int[] cursor = new int[v.size()];
        StringBuffer sb = new StringBuffer();
        while (true) {
            for (int i = 0; i < v.size(); i++) {
                sb.append((v.elementAt(i)).elementAt(cursor[i])).append(" ");
            }
            sb.append("\n");
            if (!hasNext(cursor, v)) {
                break;
            }
        }
        return sb.toString();
    }

    static String allCombinationsOf2(Vector<Vector<String>> v) {
        // Recursive
        StringBuilder result = new StringBuilder();
        recursive(0, result, null, v);
        return result.toString();
    }

    private static void recursive(int current, StringBuilder sb, String temp, Vector<Vector<String>> v) {
        Vector<String> cv = v.elementAt(current);
        temp = current == 0 ? "" : temp + " ";
        for (int i = 0; i < cv.size(); i++) {
            if (current == v.size() - 1) {
                sb.append(temp + cv.elementAt(i)).append(" \n");
                continue;
            }
            recursive(current + 1, sb, temp + cv.elementAt(i), v);
        }
    }
}
