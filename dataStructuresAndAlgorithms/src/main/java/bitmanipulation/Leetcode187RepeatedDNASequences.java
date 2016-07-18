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

package bitmanipulation;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 187. Repeated DNA Sequences
 *
 * Difficulty: Medium
 * All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG".
 * When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.
 *
 * Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.
 *
 * For example,
 *
 * Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",
 *
 * Return:
 * ["AAAAACCCCC", "CCCCCAAAAA"].
 * Company Tags LinkedIn
 * Tags Hash Table Bit Manipulation
 * ============================================
 * 1 use 2 bits to encode A, C, G, and T, thus translate a 10-letter-long sequences (substrings) to a 20 bits
 * int number, value from 0 to 0xfffff;
 * 2 with the number as index of a int array, length is 0xfffff + 1, thus without the HashMap as counter.
 *
 * Improvement:
 * The following 3 methods have the same idea as above with some performance improvement, as a result
 * the runtime beats 100% in leetcode site. July 18, 2016
 *
 *   1> encode:
 *      a map A, C, G, and T directly to their code.
 *      b (arr[i++] - 'A' + 1) % 5; // get: A -> 1, C -> 3, G -> 2, T -> 0
 *   2> counter:
 *      use only one byte[]. default is 0; just check counter[code] <2; avoid each time ++;
 *   3> 2 loops:
 *      avoid check index <9 in the second loop.
 *   4> fix length of ArrayList, and other arrays.
 *      avoid space reallocation
 */

public class Leetcode187RepeatedDNASequences {
    public List<String> findRepeatedDnaSequences_OriginalIdea(String s) {
        List<String> rv = new ArrayList<>();
        char[] arr = s.toCharArray();
        if (arr.length < 11) {
            return new ArrayList<>();
        }
        int[] words = new int[0xfffff + 1];
        byte[] map = new byte[20];
        map['C' - 'A'] = 1;
        map['G' - 'A'] = 2;
        map['T' - 'A'] = 3;
        
        for (int i = 0; i < arr.length - 9; i++) {
            int v = 0;
            for (int j = i; j < i + 10; j++) {
                v <<= 2;
                v |= map[arr[j] - 'A'];
            }
            words[v]++;
            if (words[v] == 2) {
                rv.add(s.substring(i, i + 10));

            }
        }
        return rv;
    }

    public List<String> findRepeatedDnaSequences(String s) {
        char[] arr = s.toCharArray();
        if (arr.length < 11) {
            return new ArrayList<>();
        }

        byte[] map = new byte['U'];
        map['A'] = 0;
        map['C'] = 1;
        map['G'] = 2;
        map['T'] = 3;

        List<String> r = new ArrayList<>(arr.length >> 1);
        byte[] counter = new byte[0x100000];
        int code = 0;

        int i = 0;
        while (i <= 8) {
            code <<= 2;
            code |= map[arr[i++]];
        }
        while (i < arr.length) {
            code <<= 2;
            code |= map[arr[i]];
            code &= 0xfffff;
            if (counter[code] < 2) {
                if (++counter[code] == 2) {
                    r.add(s.substring(i - 9, i + 1));
                }
            }
            i++;
        }
        return r;
    }

    public List<String> findRepeatedDnaSequences2(String s) {
        char[] arr = s.toCharArray();
        if (arr.length < 11) {
            return new ArrayList<>();
        }

        List<String> r = new ArrayList<>(arr.length >> 1);
        byte[] counter = new byte[0x100000];
        int code = 0;

        int i = 0;
        while (i <= 8) {
            code <<= 2;
            code |= (arr[i++] - 'A' + 1) % 5;
        }
        while (i < arr.length) {
            code <<= 2;
            code |= (arr[i] - 'A' + 1) % 5;
            code &= 0xfffff;
            if (counter[code] < 2) {
                if (++counter[code] == 2) {
                    r.add(s.substring(i - 9, i + 1));
                }
            }
            i++;
        }
        return r;
    }

    public List<String> findRepeatedDnaSequences3(String s) {
        char[] arr = s.toCharArray();
        if (arr.length < 11) {
            return new ArrayList<>();
        }

        final String[] r = new String[arr.length >> 1];
        int size = 0;

        byte[] counter = new byte[0x100000];
        int code = 0;

        int i = 0;
        while (i <= 8) {
            code <<= 2;
            code |= (arr[i++] - 'A' + 1) % 5;
        }
        while (i < arr.length) {
            code <<= 2;
            code |= (arr[i] - 'A' + 1) % 5;
            code &= 0xfffff;
            if (counter[code] < 2) {
                if (++counter[code] == 2) {
                    r[size++] = s.substring(i - 9, i + 1);
                }
            }
            i++;
        }

        final int len = size;
        return new ArrayList<String>() {{
            for (int i = 0; i < len; i++) {
                add(r[i]);
            }
        }};
    }
}
