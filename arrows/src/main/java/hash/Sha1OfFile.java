//  Copyright 2017 The keepTry Open Source Project
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

package hash;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * <a href="https://en.wikipedia.org/wiki/SHA-1">SHA-1</a>
 * <a href="https://en.wikipedia.org/wiki/Cryptographic_hash_function">Cryptographic_hash_function</a>
 * "SHA-1 is no longer considered secure against well-funded opponents. In 2005, cryptanalysts found
 * attacks on SHA-1 suggesting that the algorithm might not be secure enough for ongoing use,[4]
 * and since 2010 many organizations have recommended its replacement by SHA-2 or SHA-3.[5][6][7]
 * Microsoft,[8] Google,[9] Apple[10] and Mozilla[11][12][13] have all announced that
 * their respective browsers will stop accepting SHA-1 SSL certificates by 2017.
 *
 * On February 23, 2017 CWI Amsterdam and Google announced they had performed a collision
 * attack against SHA-1,[14][15] publishing two dissimilar PDF files which produce
 * the same SHA-1 hash as proof of concept.[16]"
 */
public class Sha1OfFile {
    private static String calcSHA1(File file) throws
            IOException, NoSuchAlgorithmException {

        MessageDigest sha1 = MessageDigest.getInstance("SHA-1"); // todo algorithm here??
        try (InputStream input = new FileInputStream(file)) {

            byte[] buffer = new byte[8192];
            int len = input.read(buffer);

            while (len != -1) {
                sha1.update(buffer, 0, len);
                len = input.read(buffer);
            }
            return new HexBinaryAdapter().marshal(sha1.digest());
        }
    }

    public static void main(String[] args) {
        // HexBinaryAdapter().marshal(sha1.digest());
        final char[] hexCode = "0123456789ABCDEF".toCharArray();
        StringBuilder r = new StringBuilder();
        byte b = Byte.MAX_VALUE;
        r.append(hexCode[(b >> 4) & 0xF]);// higher left 4 bit
        r.append(hexCode[(b & 0xF)]);// lower right 4 bit

        //return complete hash
        System.out.println(r.toString());
        System.out.println(Integer.toString(Byte.MAX_VALUE, 2));
        System.out.println(Integer.toString(Byte.MIN_VALUE, 2));

        System.out.println(Integer.toString(0xff, 2));
        System.out.println(Byte.MAX_VALUE);
        System.out.println(Byte.MIN_VALUE);
        System.out.println(Byte.SIZE);
    }
}