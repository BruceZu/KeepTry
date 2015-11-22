// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package charter3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encrypt a char by replacing each letter with the letter that is three letters after it
 * in the alphabet for that language.
 * Only support letters, uppercase and lowercase.
 */
public class CaesarCipher {

    private static Logger log = LoggerFactory.getLogger(CaesarCipher.class);

    private static final int ALPHABET_SIZE = 26;

    private static final char[] ALPHABET = new char[ALPHABET_SIZE];
    private static final char[] alphabet = new char[ALPHABET_SIZE];
    private static final char[] ENCRYPTED_A = new char[ALPHABET_SIZE];
    private static final char[] encrypted_a = new char[ALPHABET_SIZE];
    private static final char[] DECRYPTED_A = new char[ALPHABET_SIZE];
    private static final char[] decrypted_a = new char[ALPHABET_SIZE];
    private static final int STEP = 3; //CaesarCipher
   /*
    * index       0     1     2         3     4     5    6     ... 22   23   24   25
    * decrypted   X      Y    Z         A                          T   U     V    W
    * alphabet    A      B    C         D     E     F    G     ... W   X     Y    Z
    * encrypted   D                     G                          Z   A     B    C
    */

    static {
        int index;
        char c;

        for (c = 'A', index = 0; c <= 'Z'; c++) {
            ALPHABET[index++] = c;
        }

        for (c = 'a', index = 0; c <= 'z'; c++) {
            alphabet[index++] = c;
        }

        for (index = 0; index < ALPHABET_SIZE; index++) {
            ENCRYPTED_A[index] = ALPHABET[(index + STEP) % ALPHABET_SIZE];
            encrypted_a[index] = alphabet[(index + STEP) % ALPHABET_SIZE];

            DECRYPTED_A[ENCRYPTED_A[index] - 'A'] = ALPHABET[index];
            decrypted_a[encrypted_a[index] - 'a'] = alphabet[index];
        }
    }

    private static boolean inAlphabet(char c) {
        return 'A' <= c && c <= 'Z' ||
                'a' <= c && c <= 'z';
    }

    private static int indexInAlphabet(char c) {
        if (!inAlphabet(c)) {
            return -1;
        }
        if (c <= 'Z') {
            return c - 'A';
        }
        return c - 'a';
    }

    public static char encryptChar(char plainChar) {
        int it = plainChar;

        if ('A' <= it && it <= 'Z') {
            if ((it + STEP - ('A' - 1)) % ALPHABET_SIZE == 0) {
                return (char) ((it + STEP - ('A' - 1)) % ALPHABET_SIZE + ALPHABET_SIZE + ('A' - 1));
            }
            return (char) ((it + STEP - ('A' - 1)) % ALPHABET_SIZE + ('A' - 1));
        }

        if ('a' <= it && it <= 'z') {
            if ((it + STEP - ('a' - 1)) % ALPHABET_SIZE == 0) {
                return (char) ((it + STEP - ('a' - 1)) % ALPHABET_SIZE + ALPHABET_SIZE + ('a' - 1));
            }
            return (char) ((it + STEP - ('a' - 1)) % ALPHABET_SIZE + ('a' - 1));
        }
        return plainChar;
    }

    public static char decryptChar3(char c) {
        if (inAlphabet(c)) {
            int index = indexInAlphabet(c);

            if (c <= 'Z') {
                return DECRYPTED_A[index];
            }
            return decrypted_a[index];
        }
        return c;
    }

    public static char decryptChar2(char c) {
        if (inAlphabet(c)) {
            int index = indexInAlphabet(c);
            int indexOfTargetInAlpha = (index - (ENCRYPTED_A[index] - ALPHABET[index]) + ALPHABET_SIZE) % ALPHABET_SIZE;
            if (c <= 'Z') {
                return ALPHABET[indexOfTargetInAlpha];
            }
            return alphabet[indexOfTargetInAlpha];
        }
        return c;
    }

    public static char decryptChar(char encryptedChar) {
        int it = encryptedChar;

        if ('A' <= it && it <= 'Z') {
            if ((it - ('A' - 1) + ALPHABET_SIZE - STEP) % ALPHABET_SIZE == 0) {
                return (char) ((it - ('A' - 1) + ALPHABET_SIZE - STEP) % ALPHABET_SIZE + ALPHABET_SIZE + ('A' - 1));
            }
            return (char) ((it - ('A' - 1) + ALPHABET_SIZE - STEP) % ALPHABET_SIZE + ('A' - 1));
        }

        if ('a' <= it && it <= 'z') {
            if ((it - ('a' - 1) + ALPHABET_SIZE - STEP) % ALPHABET_SIZE == 0) {
                return (char) ((it - ('a' - 1) + ALPHABET_SIZE - STEP) % ALPHABET_SIZE + ALPHABET_SIZE + ('a' - 1));
            }
            return (char) ((it - ('a' - 1) + ALPHABET_SIZE - STEP) % ALPHABET_SIZE + ('a' - 1));
        }
        return encryptedChar;
    }


    public static String encrypt(String plainText) {
        char[] charArray = plainText.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = encryptChar(charArray[i]);
        }
        return new String(charArray);
    }

    public static String decrypt(String encryptedText) {
        char[] charArray = encryptedText.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = decryptChar(charArray[i]);
        }
        return new String(charArray);
    }

    public static String decrypt2(String encryptedText) {
        char[] charArray = encryptedText.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = decryptChar2(charArray[i]);
        }
        return new String(charArray);
    }

    public static String decrypt3(String encryptedText) {
        char[] charArray = encryptedText.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = decryptChar3(charArray[i]);
        }
        return new String(charArray);
    }
}
