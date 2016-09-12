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

package nosubmmitted;

/**
 * 158. Read N Characters Given Read4 II - Call multiple times
 * https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/
 * <p/>
 * Difficulty: Hard
 * The API: int read4(char *buf) reads 4 characters at a time from a file.
 * <p/>
 * The return value is the actual number of characters read. For example, it returns 3 if there is only 3 characters left in the file.
 * <p/>
 * By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.
 * <p/>
 * Note:
 * The read function may be called multiple times.
 * <p/>
 * Hide Company Tags Bloomberg Google Facebook
 * Hide Tags String
 * Hide Similar Problems (E) Read N Characters Given Read4
 */


public class LC158ReadNCharactersGivenRead4IICallmultipletimes {
    class Reader4 {
        int read4(char[] buf) {
            return 0;//todo
        }
    }

    class Solution extends Reader4 {
    /* The read4 API is defined in the parent class Reader4.
      int read4(char[] buf); */
        /**
         * @param buf Destination buffer
         * @param n   Maximum number of characters to read
         * @return The number of characters read
         */
        private char[] readed = new char[4];
        private int readLoc = 0;
        private int writeLoc = 0;

        public int read(char[] buf, int n) {
            for (int i = 0; i < n; i++) {
                if (readLoc == writeLoc) {
                    writeLoc = read4(readed);
                    readLoc = 0;
                    if (writeLoc == 0)
                        return i;
                }
                buf[i] = readed[readLoc];
                readLoc++;
            }
            return n;
        }
    }
}
