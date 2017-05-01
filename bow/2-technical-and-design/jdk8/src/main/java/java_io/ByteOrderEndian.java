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

package java_io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ByteOrderEndian {

    /**
     * Endian issues are an example of the general encoding problem - data needs
     * to represent an abstract concept, and later the concept needs to be created from the data.
     *
     * Big endian machine: Stores data big-end first.
     * When looking at multiple bytes, the first byte (lowest address) is the biggest.
     * Little endian machine: Stores data little-end first.
     * When looking at multiple bytes, the first byte is smallest.
     * the big-endian / little-endian naming comes from Gulliver's Travels,
     * where the Lilliputans argue over whether to break eggs on the little-end or big-end.
     * <pre>
     * Issues with byte order are sometimes called the NUXI problem:
     * UNIX stored on a big-endian machine can show up as NUXI on a little-endian one.
     *
     * The standard network order is actually big-endian
     * To convert data to network order, machines call a function hton (host-to-network)
     * Similarly, there is a function ntoh (network to host) used to read data off the network.
     * Remember that a single byte is a single byte, and order does not matter.
     *
     * Remember that a single byte is a single byte, and order does not matter.
     *
     * Solution 1: Use a Common Format
     * The easiest approach is to agree to a common format for sending data over the network.
     * The standard network order is actually big-endian,
     *
     * Use java.io.Data(Input/Output)Stream: those always load and store
     * numbers in network order.  (If the other side of the connection is
     * C(++), of course, you should still use the those macros there.)
     *
     * Java always uses Network byte order (whenever the
     * byte order is visible, at least).
     * This doesn't mean that the internal representations are kept in network order,
     * but there is NO way to expose the internal representatio
     *
     * If you are sending data from Java to a non-Java program, do the translation
     * on the other side. Only the other side knows what byte ordering it has. Let
     * it call ntohl() and htonl().
     *
     * Solution 2: Use a Byte Order Mark (BOM)
     * The other approach is to include a magic number, such as 0xFEFF,
     * before every piece of data. If you read the magic number and it is 0xFEFF,
     * it means the data is in the same format as your machine, and all is well.
     *
     * If you read the magic number and it is 0xFFFE (it is backwards),
     * it means the data was written in a format different from your own. You'll have to translate it.
     *
     * 1 using 1 byte to keep BOM is better
     * 2 this is an addition byte
     * 3 how to know which solution is underlying
     *
     * Unicode uses a BOM when storing multi-byte data
     * (some Unicode character encodings can have 2, 3 or even 4-bytes per character).
     * XML avoids this mess by storing data in UTF-8 by default, which stores Unicode information one byte at a time.
     * Because endian issues don't matter for single bytes".
     *
     *  Little-endian machines let you read the lowest-byte first, without reading the others.
     *  You can check whether a number is odd or even (last bit is 0) very easily,
     */
    public static void main(String[] args) throws IOException {
        ByteBuffer bbuf = ByteBuffer.allocate(16);
        bbuf.put("abcdefg".getBytes(Charset.forName("UTF-16")));
        System.out.println(Arrays.toString(bbuf.array()));


        // Create a buffer and set it as little-endian
        ByteBuffer origBuff = ByteBuffer.allocate(100);
        origBuff.order(ByteOrder.LITTLE_ENDIAN);

        ByteBuffer slice = origBuff.slice();
        System.out.println(slice.order());
        System.out.println(ByteOrder.nativeOrder());
    }
}
