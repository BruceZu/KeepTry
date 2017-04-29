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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;

public class JavaByteBuffer {
    private static File readfile = new File("/home/go-along-road-quick-on-time-4-ye/Downloads/sanguomjzyzngpjb.apk");
    private static File writefile = new File("/home/go-along-road-quick-on-time-4-ye/w.out");

    @SuppressWarnings({"UnusedAssignment"})
    private static void readRawBytes() {
        try (final FileInputStream fis = new FileInputStream(readfile);
             FileChannel fc = fis.getChannel();) {
            /**
             *  using FileChannel.read( ByteBuffer dst ), to read the next chunk of the file into a pre-allocated ByteBuffer.
             *  This approach is can be more efficient, especially when you slew over most of the data, or access it via the backing array.
             *  It will pay off if for example you were processing just a 4-byte field in a 512-byte record,
             *  since only the bytes you need are copied from the buffer, not the entire record.
             */
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 15);
            showStatsOfChannelAndByteBuffer("newly allocated read\n ", fc, buffer);

            // -1 means eof.
            int bytesRead = fc.read(buffer);

            showStatsOfChannelAndByteBuffer("after first read\n ", fc, buffer);
            showStatsOfChannelAndByteBuffer("before flip\n ", fc, buffer);

            buffer.flip();

            showStatsOfChannelAndByteBuffer("after flip\n ", fc, buffer);

            byte[] receive = new byte[1024];
            buffer.get(receive);

            showStatsOfChannelAndByteBuffer("after first get\n ", fc, buffer);

            buffer.get(receive);

            showStatsOfChannelAndByteBuffer("after second get\n ", fc, buffer);

            buffer.clear();

            showStatsOfChannelAndByteBuffer("after clear\n ", fc, buffer);

            bytesRead = fc.read(buffer);

            showStatsOfChannelAndByteBuffer("after second read\n ", fc, buffer);
            showStatsOfChannelAndByteBuffer("before flip\n ", fc, buffer);

            buffer.flip();

            showStatsOfChannelAndByteBuffer("after flip\n ", fc, buffer);
        } catch (IOException e) {
            // fix me
        }
    }

    private static void showStatsOfChannelAndByteBuffer(String where, FileChannel fc, Buffer b) throws IOException {
        System.out.println(where +
                " channelPosition: " + fc.position() +
                " bufferPosition: " + b.position() +
                " limit: " + b.limit() +
                " remaining: " + b.remaining() +
                " capacity: " + b.capacity());
    }

    private static void writeRawBytes() {
        try (final FileOutputStream fos = new FileOutputStream(writefile);
             FileChannel fc = fos.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 15);
            showStatsOfChannelAndByteBuffer("newly allocated write", fc, buffer);

            for (int i = 0; i < 10; i++) {
                buffer.clear();
                showStatsOfChannelAndByteBuffer("after clear", fc, buffer);

                for (int j = 0; j < 100; j++) {
                    buffer.put("hello".getBytes("8859_1"/* encoding */));
                    showStatsOfChannelAndByteBuffer("after put", fc, buffer);
                }

                showStatsOfChannelAndByteBuffer("before flip", fc, buffer);
                buffer.flip();
                showStatsOfChannelAndByteBuffer("after flip", fc, buffer);
                // write a chunk of raw bytes, up to 15K bytes long
                fc.write(buffer);
                showStatsOfChannelAndByteBuffer("after write", fc, buffer);
            }
        } catch (IOException e) {
            //fix me
        }
    }

    // If your file is small enough to fit in your virtual address space all at once,
    private static void readingWithMappedByteBuffer() {
        // reading a file with memoryMapping
        File file = new File("/home/go-along-road-quick-on-time-4-ye/Downloads/JavaScript_Enlightenment.pdf");
        try (FileInputStream fis = new FileInputStream(file)) {
            FileChannel fc = fis.getChannel();
            fc.position(file.length() / 200000 - 2);
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, file.length() / 200000 - 2, file.length() / 100000);
            int limit = buffer.limit(); // bytes in buffer
            for (int offset = 0; offset < limit; offset++) {
                byte b = buffer.get(offset);
                System.out.println(b);
            }
        } catch (IOException e) {
            //fix me
        }
    }

    public static void main(String[] args) throws IOException {
        // readRawBytes();
        // writeRawBytes();
        // readingWithMappedByteBuffer();
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
