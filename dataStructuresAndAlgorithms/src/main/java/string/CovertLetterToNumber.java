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

package string;

/**
 * <pre>
 * convert a~b or A~Z to 1~26, and split them by ', '.
 * e.g. "a1b2 c" => "1, 1, 2, 2,  3"; "A1B2 C" => "1, 1, 2, 2,  3"
 *
 * followup: predefine, a => 47, b => 55
 *
 * @see
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Character.html">Character</a>
 * <a href="http://unicode.org/cgi-bin/GetUnihanData.pl?codepoint=2070E">Supplementary Characters</a>
 */
public class CovertLetterToNumber {

    public static String convert(String in) {
        if (in == null || in == "") {
            return in;
        }
        StringBuilder sb = new StringBuilder();
        char[] cs = in.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (Character.isSurrogate(c)
                    && i + 1 < cs.length
                    && Character.isSurrogatePair(c, cs[i + 1])) {
                sb.append(c).append(cs[i + 1]);
                i++;
            } else if (Character.isLetter(c)) {
                if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
                    sb.append(Character.isLowerCase(c) ? (c - (int) 'a' + 1) : (c - (int) 'A' + 1));
                } else {
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
            if (i != cs.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    // Recognize a supplementary character with its UTF-16 encoding
    // E.g.: highSurrogate = '\uD841'; lowSurrogate = '\uDF0E';
    public static void printSupplementaryCharacter(char h /* highSurrogate */,
                                                   char l /* lowSurrogate */) {
        int v = Character.toCodePoint(h, l);
        if (Character.isHighSurrogate(h)
                && Character.isLowSurrogate(l)
                && Character.isValidCodePoint(v)
                && Character.isLetter(v)) {
            System.out.println("check " + new String(Character.toChars(v)) + "'s int value: " + v);
        }
    }

    // Recognize supplementary character in a string, e.g. s = "2\uD841\uDF0E1\uD8410";
    public static void printSupplementaryCharacter(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isSurrogate(s.charAt(i))
                    && i + 1 < s.length()
                    && Character.isSurrogatePair(s.charAt(i), s.charAt(i + 1))) {
                char[] sc = new char[2];
                sc[0] = s.charAt(i);
                sc[1] = s.charAt(i + 1);
                System.out.println(new String(sc));
                i++;
            }
        }
    }
}
