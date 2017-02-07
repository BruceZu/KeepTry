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

package string.supplementary_characters;

import hash.Leetcode242ValidAnagram;

import java.nio.charset.Charset;

/**
 * <img src="../../../resources/supplementary_characters.png" height="400" width="600"><br>
 */
public class SupplementaryCharacters {
    /**
     * <pre>
     * Code points are the numbers that can be used in a coded character set.
     * A coded character set defines a range of valid code points, but doesn't necessarily assign characters to all
     * those code points.
     * The valid code points for Unicode are U+0000 to U+10FFFF.
     * Unicode 4.0 assigns characters to 96,382 of these more than a million code points.
     *
     * Supplementary characters are characters with code points in the range
     * 10000 to  10FFFF. 1048576 (1M)
     * that is, those characters that could not be represented in the original 16-bit design of Unicode.
     *
     * The set of characters from U+0000 to U+FFFF is sometimes referred to as the Basic Multilingual Plane (BMP).
     * Thus, each Unicode character is either in the BMP or a supplementary character.
     *
     * Surrogates are code points from two special ranges of Unicode values,
     * reserved for use as the leading, and trailing values of paired code units in UTF-16.
     *
     * Leading, also called high, surrogates are from
     * D800 to DBFF ,
     * and trailing, or low, surrogates are from
     * DC00 to DFFF .
     *
     * They are called surrogates, since they do not represent characters directly, but only as a pair.
     *
     * a char represents a 'UTF-16 code unit'.
     * J2SE specifications now use the terms 'code point' and 'UTF-16 code unit'
     * APIs usually use the name 'codePoint' for variables of type 'int' that represent code points,
     * while 'UTF-16 code units' of course have type 'char'.
     *
     * = low-level APIs that work on individual code points,
     * Character.toCodePoint(char high, char low), which converts two UTF-16 code units to a code point,
     * Character.toChars(int codePoint),           which converts the given code point to one or two UTF-16 code units, wrapped into a char[].
     *
     *
     * Character.codePointAt(char[] a, int index)
     * String.codePointBefore(int index)
     *
     * Note that most methods that accept a code point do not check whether the given int value is in the range of valid Unicode code points
     * (only the range from 0x0 to 0x10FFFF is valid)
     * Character.isValidCodePoint(int )
     *
     * But most not all code point-based methods perform functions for the complete range of Unicode characters
     * that older char based methods performed for BMP characters. Here are some typic
     * examples:
     * Character.isLetter(int codePoint) identifies letters according to the Unicode standard.
     * Character.isJavaIdentifierStart(int codePoint) // determines whether a code point can start an identifier according to the Java Language Specification.
     * Character.UnicodeBlock.of(int codePoint) // looks up the Unicode block that the code point belongs to.
     *
     * Character.toUpperCase(int codePoint) converts the given code point to its uppercase equivalent.
     * While this method does support supplementary characters,
     * it still cannot work around the fundamental issue that some case conversions cannot be done correctly on a character-by-character basis.
     * The German character "ß", for example, should be converted to "SS", which requires use of the String.toUpperCase method.
     *
     * Representing Supplementary Characters in Source Files:
     * where the character encoding used cannot
     * represent the characters directly, they are represented by the two consecutive Unicode escapes
     * for the two code units in the UTF-16 representation of the character. For example,
     * the character U+20000 is written as "\uD840\uDC00"
     * You probably don't want to figure out these escape sequences yourself;
     * it's best to write in an encoding that supports the supplementary characters that
     * you need and then use a tool such as native2ascii to convert to escape sequences.
     * The Unicode standard always uses hexadecimal numbers, and writes them with the prefix "U+",
     * so the number for "A" is written as "U+0041".
     *
     * UTF 8 in Java
     * For J2SE 5.0, the documentation is being updated to uniformly call it "modified UTF-8."
     * The incompatibility between modified UTF-8 and standard UTF-8 stems from two differences.
     *
     * First, modified UTF-8 represents the character U+0000 as the two-byte sequence 0xC0 0x80,
     * whereas standard UTF-8 uses the single byte value 0x0.
     *
     * <strong>Second, modified UTF-8 represents supplementary characters by separately encoding the two surrogate code units
     * of their UTF-16 representation. Each of the surrogate code units is represented by three bytes, for a total of six bytes.
     * Standard UTF-8, on the other hand, uses a single four byte sequence for the complete character.</strong>
     *
     * Modified UTF-8:
     * is used by the Java Virtual Machine and the interfaces attached to it
     * (such as the Java Native Interface, the various tool interfaces, or Java class files),
     * in the
     * java.io.DataInput
     * java.io.DataOutput interfaces
     * and classes implementing or using them,
     * and for serialization.
     * The Java Native Interface provides routines that convert to and from modified UTF-8.
     *
     * Standard UTF-8:
     * is supported by the String class, by the
     * java.io.InputStreamReader
     * java.io.OutputStreamWriter classes,
     * java.nio.charset facilities,
     * and many APIs layered on top of them.
     *
     * Since modified UTF-8 is incompatible with standard UTF-8, it is critical not to use one where
     * the other is needed.
     * Modified UTF-8 can only be used with the Java interfaces described above.
     * In all other cases, in particular for data streams that may come from or may be interpreted by
     * software that's not based on the Java platform, standard UTF-8 must be used.
     * The Java Native Interface
     * routines that convert to and from modified UTF-8 cannot be used when standard UTF-8 is required.
     * {@link string.CovertLetterToNumber}
     * {@link Leetcode242ValidAnagram}
     * @see <br> <a href="http://www.oracle.com/us/technologies/java/supplementary-142654.html">supplementary </a>
     * <br> <a href="https://en.wikibooks.org/wiki/Unicode/Character_reference/D000-DFFF">UTF</a>
     * <br> <a href="https://en.wikipedia.org/wiki/UTF-16#UCS-2">UTF-16#UCS-2</a>
     * <br> <a href="http://www.utf8everywhere.org/">UTF8</a>
     * <br> <a href="https://en.wikipedia.org/wiki/Plane_(Unicode)#Basic_Multilingual_Plane">BMP</a>
     */
    public static void main(String[] args) {
        System.out.println(Charset.defaultCharset());
        if (Character.isSurrogatePair('\uD801', '\uDC00')) { // valid pair check
            int codePoint = Character.toCodePoint('\uD801', '\uDC00'); // get code point
            System.out.println(Character.isLetter(codePoint));  // see is letter or not letter.
        }
        // get code points
        System.out.println(Character.isLetter(Character.codePointAt("\uD801\uDC00", 0)));
        System.out.println(Character.isLetter(Character.codePointAt(new char[]{'\uD801', '\uDC00'}, 0, 2)));
        System.out.println(Character.isLetter("\uD801\uDC00".codePointBefore(2)));

        // inserting code points into a character sequence,
        System.out.println(new StringBuffer().appendCodePoint("\uD801\uDC00".codePointBefore(2)));
        System.out.println(new StringBuilder().appendCodePoint("\uD801\uDC00".codePointBefore(2)));
        System.out.println(new String(new int[]{"\uD801\uDC00".codePointBefore(2)}, 0, 1));

        //identify the char values
        System.out.println(Character.isHighSurrogate('\uD801'));
        System.out.println(Character.isLowSurrogate('\uDC00'));
        // determines whether a code point needs to be converted to one or two chars.
        System.out.println(Character.charCount("\uD801\uDC00".codePointBefore(2)));

        //
        System.out.println(Character.toUpperCase('\u00df'));
        System.out.println(Character.isValidCodePoint("\uD801\uDC00".codePointBefore(2)));
        System.out.println(Character.toChars("\uD801\uDC00".codePointBefore(2)));

        //
        char[] cs = Character.toChars(0x20000);
        System.out.println("hi:  " + Integer.toHexString((int) cs[0]));
        System.out.println("low: " + Integer.toHexString((int) cs[1]));
    }

    String[] newStrings(int[] codePoints) {
        String[] result = new String[codePoints.length];
        char[] codeUnits = new char[2];
        for (int i = 0; i < codePoints.length; i++) {
            int count = Character.toChars(codePoints[i], codeUnits, 0);
            result[i] = new String(codeUnits, 0, count);
        }
        new String();
        return result;
    }
}
