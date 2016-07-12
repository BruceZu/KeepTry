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

package locked.nosubmmitted;

import static java.lang.Math.min;

/**
 * 157. Read N Characters Given Read4
 * https://leetcode.com/problems/read-n-characters-given-read4/
 * Difficulty: Easy <pre>
 * The API: int read4(char *buf) reads 4 characters at a time from a file.
 * <p/>
 * The return value is the actual number of characters read. For example, it returns 3 if there is only 3 characters left in the file.
 * <p/>
 * By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.
 * <p/>
 * Note:
 * The read function will only be called once for each test case.
 * <p/>
 * Hide Company Tags Facebook
 * Hide Tags String
 * Hide Similar Problems (H) Read N Characters Given Read4 II - Call multiple times
 * </pre>
 */
public class LC157ReadNCharactersGivenRead4 {
    /* The read4 API is defined in the parent class Reader4.
      int read4(char[] buf); */
    class Reader4 {
        int read4(char[] buf) {
            return 0;//todo
        }
    }

    class Solution extends Reader4 {

        /**
         * the fast one currently  beat 92.49%
         *
         * @param buf
         * @param n
         * @return
         */
        public int read(char[] buf, int n) {
            char[] buffer = new char[4];//temp buffer for each call to read4()
            int read = 0;//actual num of char read by the current call to read4()
            int hasRead = 0;//running total of char read
            while (true) {
                read = read4(buffer);//read4
                int toRead = min(read, n - hasRead);//determine how many char to copy over to buf from buffer
                for (int i = 0; i < toRead; i++) {//do the copy
                    buf[hasRead++] = buffer[i];
                }
                if (read < 4) return hasRead;
                if (hasRead == n) return n;
            }
        }

        /**
         * save as above
         */
        public int read2(char[] buf, int n) {
            if (n <= 0)
                return 0;
            char[] tmp = new char[4];
            int j = 0;
            while (true) {
                int readCount = read4(tmp);
                for (int i = 0; i < readCount && j < n; i++)
                    buf[j++] = tmp[i];
                if (j == n || readCount < 4)
                    break;
            }
            return j;
        }
    }
}
