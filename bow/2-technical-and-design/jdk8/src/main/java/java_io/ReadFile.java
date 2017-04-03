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

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.Scanner;


public class ReadFile {
    /**
     * <pre>
     *
     *     File         vs Object
     *     InputStream  vs Reader
     *     OutputStream vs Writer
     *
     *
     * Conclusions
     * For the best Java read performance, there are four things to remember:
     * Minimize I/O operations by reading an array at a time, not a byte at a time.
     *      An 8Kbyte array is a good size.
     * Minimize method calls by getting data an array at a time, not a byte at a time.
     *      Use array indexing to get at bytes in the array.
     * Minimize thread synchronization locks if you don't need thread safety.
     *      Either make fewer method calls to a thread-safe class, or use a non-thread-safe class like FileChannel and MappedByteBuffer.
     * Minimize data copying between the JVM/OS, internal buffers, and application arrays.
     *      Use FileChannel with memory mapping, or a direct or wrapped array ByteBuffer.
     * <a href="http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly">link </a>
     * <a href="http://stackoverflow.com/questions/7002510/can-multiple-threads-see-writes-on-a-direct-mapped-bytebuffer-in-java">link </a>
     * <a href="http://stackoverflow.com/questions/9570055/mappedbytebuffer-sliding-window">link </a>
     */
    public static void readFIle() {
        try { // It is better to process bytes only
            try (FileChannel ch = new FileInputStream("file.txt").getChannel()) {
                StringBuffer lineBuf = new StringBuffer();
                byte[] bArray = new byte[1024 * 8];
                ByteBuffer bb = ByteBuffer.wrap(bArray);
                int nRead;
                while ((nRead = ch.read(bb)) != -1) {
                    for (int i = 0; i < nRead; i++) {
                        byte oneByte = bArray[i];
                        if (oneByte == '\n') {
                            System.out.println(lineBuf);
                            lineBuf.delete(0, lineBuf.length());
                        } else if (oneByte != '\r') {
                            // it assumes the value of the byte means the same thing in Unicode as it does in
                            // whatever encoding is used in the file. This isn't always the case.
                            // https://coderanch.com/t/201866/java/Parsing-huge-file-reading-memory
                            lineBuf.append((char) oneByte);// assuming reading in a standard ASCII text file
                        }
                    }
                    bb.clear();
                }
            }

            /**
             It is better to process non-supplementary character only
             using MappedByteBuffer, CharBuffer,
             Note: MappedByteBuffer is not thread safe as it is a subclass of Buffer,
             */
            try (FileChannel fChannel = new FileInputStream("file.txt").getChannel();) {
                // FileChannel can be access by
                //  RandomAccessFile
                //  FileInputStream (for read only)
                //  FileOutputStream (for write only)
                ReadableByteChannel readByteChannel = new FileInputStream("file.txt").getChannel();

                // We can also map specific part of a file instead of entire file when it is huge file.
                MappedByteBuffer mappedByteBuffer = fChannel.map(FileChannel.MapMode.READ_ONLY, 0, fChannel.size());


                CharsetDecoder charsetDecoder = Charset.forName("US-ASCII").newDecoder();

                int bytesToDecode = 1024 * 8; // what about supplementary character??

                CharBuffer out = CharBuffer.allocate(bytesToDecode);
                // can also using get(byte array) then decode the byte array. (MappedBuffer play only with Byte[])
                CoderResult result = charsetDecoder.decode(mappedByteBuffer, out, true);
                System.out.println(out);
                out.rewind();
                while (result.isOverflow() || mappedByteBuffer.hasRemaining()) {
                    result = charsetDecoder.decode(mappedByteBuffer, out, true);
                    System.out.println(out);
                    out.rewind();
                }
            }

            // using InputStreamReader
            // it is better to process bytes -> char when the file has no line \n, reading char block
            try (BufferedReader in =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new FileInputStream("unicode-example-utf8.txt"), "UTF8"));) {
                // Test file from http://www.i18nguy.com/unicode/unicode-example-intro.htm
                // Specifically the Example Data, CSV format:
                //     http://www.i18nguy.com/unicode/unicode-example-utf8.zip
                char buff[] = new char[1024 * 8]; // or whatever size...
                // each reader explicitly declared you wont' have to worry about getting stuck somewhere "mid-byte"
                // in a multi-byte UTF8 character.
                int n;
                while (-1 != (n = in.read(buff, 0, buff.length))) {
                    System.out.println(n);
                }
            }
            // using InputStreamReader
            // it is better to process bytes -> char when the file has line \n, reading line by line
            try (BufferedReader br =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new FileInputStream("file.txt"), "UTF-8"));) {

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                String s = new String(new byte[]{}, "UTF-8");
            }
            // or
            try (BufferedReader reader = new BufferedReader(new FileReader("filename.txt"));) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // grep for "foo"
                    if (line.indexOf("foo") >= 0) {
                        // line contains "foo" - precess it
                    }
                    // String data is discarded at the end of each loop iteration
                    // unless you specifically save it elsewhere
                }
            }

            // file copying using NIO channel ---------------------------------------------------
            try (FileChannel inChannel = new FileInputStream("src.txt").getChannel();
                 FileChannel outChannel = new FileOutputStream("dest.txt").getChannel();) {
                // Many operating systems can transfer bytes directly
                // from the filesystem cache to the target channel without actually copying them.
                inChannel.transferTo(0, inChannel.size(), outChannel);
            }
            // ------------------------------------------------------------------------------
            // text file use the BufferedReader,
            // binary then BufferedInputStream class.
            // In most of the cases we don't need the complete data of a file at once,
            // read the file line by line or some data range/block and process the data.
            try (
                    Scanner scan = new Scanner(new File("data.txt"));) {
                while (scan.hasNextLine()) {
                    String strLine = scan.nextLine();
                    //print data on console
                    System.out.println(strLine);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFileOptions(String[] args) {
        String inFileStr = "test-in.jpg";
        String outFileStr = "test-out.jpg";
        long startTime, elapsedTime;  // for speed benchmarking
        int bufferSizeKB = 8;
        int bufferSize = bufferSizeKB * 1024;

        try {
            try (FileChannel in = new FileInputStream(inFileStr).getChannel();
                 FileChannel out = new FileOutputStream(outFileStr).getChannel()) {

                ByteBuffer bytebuf = ByteBuffer.allocate(bufferSize);
                int bytesCount;
                while ((bytesCount = in.read(bytebuf)) > 0) {
                    // flip the buffer which set the limit to current position, and position to 0.
                    bytebuf.flip();
                    out.write(bytebuf);
                    bytebuf.clear();     // For the next read
                }
            }

            try (FileChannel in = new FileInputStream(inFileStr).getChannel();
                 FileChannel out = new FileOutputStream(outFileStr).getChannel()) {
                // Allocate a "direct" ByteBuffer
                ByteBuffer bytebuf = ByteBuffer.allocateDirect(bufferSize);

                int bytesCount;
                while ((bytesCount = in.read(bytebuf)) > 0) { // Read data from file into ByteBuffer
                    // flip the buffer which set the limit to current position, and position to 0.
                    bytebuf.flip(); // for output
                    out.write(bytebuf);  // Write data from ByteBuffer to file
                    bytebuf.clear();     // For the next input
                }
            }

            // Using FileChannel with transferTo()
            try (FileChannel in = new FileInputStream(inFileStr).getChannel();
                 FileChannel out = new FileOutputStream(outFileStr).getChannel()) {
                in.transferTo(0, in.size(), out);

            }

            // Using Buffered Stream I/O
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFileStr));
                 BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFileStr))) {
                int bytesCount;
                while ((bytesCount = in.read()) != -1) {
                    out.write(bytesCount);
                }
            }

            // Using a programmer-managed 8K byte-array for Disk I/O
            try (FileInputStream in = new FileInputStream(inFileStr);
                 FileOutputStream out = new FileOutputStream(outFileStr)) {

                byte[] byteArray = new byte[bufferSize];    // byte-array
                int bytesCount;
                while ((bytesCount = in.read(byteArray)) != -1) {
                    out.write(byteArray, 0, bytesCount);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void readPrimitiveData() {
        // The DataInputStream is convenient class for reading the primitive data from the input stream.
        // The data read by DataInputStream  is independent of the OS and machine.
        // It can read the data from any input stream.
        // The DataInputStream class can also read the data from output stream written by any other program.
        // The DataInputStream is not thread safe.

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("textfile.txt")))) {

        } catch (Exception e) {
            // Fix me
        }
    }

}
