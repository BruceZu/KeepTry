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

import java.util.ArrayList;
import java.util.List;

/**
 * 271. Encode and Decode Strings
 * https://leetcode.com/problems/encode-and-decode-strings/
 * Difficulty: Medium <pre>
 * Design an algorithm to encode a list of strings to a string.
 * The encoded string is then sent over the network and is decoded back to the original list of strings.
 * <p/>
 * Machine 1 (sender) has the function:
 * <p/>
 * string encode(vector<string> strs) {
 * // ... your code
 * return encoded_string;
 * }
 * Machine 2 (receiver) has the function:
 * vector<string> decode(string s) {
 * //... your code
 * return strs;
 * }
 * So Machine 1 does:
 * <p/>
 * string encoded_string = encode(strs);
 * and Machine 2 does:
 * <p/>
 * vector<string> strs2 = decode(encoded_string);
 * strs2 in Machine 2 should be the same as strs in Machine 1.
 * <p/>
 * Implement the encode and decode methods.
 * <p/>
 * Note:
 * The string may contain any possible characters out of 256 valid ascii characters.
 * Your algorithm should be generalized enough to work on any possible characters.
 * Do not use class member/global/static variables to store states.
 * Your encode and decode algorithms should be stateless.
 * Do not rely on any library method such as eval or serialize methods.
 * You should implement your own encode/decode algorithm.
 * <p/>
 * Hide Company Tags Google
 * Hide Tags String
 * Hide Similar Problems (E) Count and Say (H) Serialize and Deserialize Binary Tree
 */
public class LC271EncodeandDecodeStrings {
    // Your Codec object will be instantiated and called as such:
    // Codec codec = new Codec();
    // codec.decode(codec.encode(strs));
    class Codec {
        /** beat 95.5%
         * I try to encode a string like len#string this way,
         * for example {"", "abc", "ab", "2#"},
         * it will encode it to "0#3#abc2#ab2#2#",
         * then decode it by reading the length first,
         * followed by reading the string with the length.
         * @param strs
         * @return
         */
        public String encode(List<String> strs) {
            if(strs == null ) return null;
            if(strs.size() == 0) return "";
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i< strs.size();i++){
                sb.append(strs.get(i).length()).append("#").append(strs.get(i));
            }
            return sb.toString();
        }

        // Decodes a single string to a list of strings.
        public List<String> decode(String s) {
            if(s == null) return null;
            List<String> res = new ArrayList();
            int i = 0;
            while(i < s.length()){
                int seperatorIndex =  s.indexOf("#",i);
                int num = Integer.parseInt(s.substring(i,seperatorIndex));
                res.add(s.substring(seperatorIndex+1, seperatorIndex + num+1));
                i = seperatorIndex + num +1;
            }
            return res;
        }
    }
}
