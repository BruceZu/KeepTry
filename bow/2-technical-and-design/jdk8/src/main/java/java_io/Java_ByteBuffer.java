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
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static java.nio.channels.FileChannel.MapMode.READ_WRITE;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;

/**
 * <pre>
 * The methods on ByteBuffer are good for reading and writing single ints, floats.
 * If you want to write multiple values of these different primitive types,
 * it's often more efficient or convenient to create a wrapper buffer of type IntBuffer,
 * FloatBuffer etc by calling ByteBuffer.asIntBuffer() etc.
 *
 * 1 efficient random access. The ByteBuffer class arrived in JDK 1.4 as part of the java.nio package,
 * and combines larger-than-byte data operations with random access.
 *
 * 2 for direct-ByteBuffer / MappedByteBuffer. Note that direct buffers are created outside of heap:
 *
 * a > Unaffected by gc cycles
 * b > Performance boost: In stream IO, read calls would entail system calls, which require a context-switch
 * between user to kernel mode and vice versa, which would be costly especially if file is being accessed
 * constantly. However, with memory-mapping this context-switching is reduced
 * as data is more likely to be found in memory (MappedByteBuffer).
 * If data is available in memory, it is accessed directly without invoking OS, i.e., no context-switching.
 *
 * c > Page sharing: Memory mapped files can be shared between processes as they are allocated
 * in process's virtual memory space and can be shared across processes.
 *
 * Direct ByteBuffers
 *
 * There are three ways to create a ByteBuffer: wrap(), allocate(), and allocateDirect().
 * The API docs for this last method are somewhat vague on exactly where the buffer will be allocated,
 * stating only that “the Java virtual machine will make a best effort to perform native I/O operations
 * directly upon it,” and that they “may reside outside of the normal garbage-collected heap.”
 * In practice, direct buffers always live outside of the garbage-collected heap.
 *
 * As a result, you can store data in a direct buffer without paying the price (ie, pauses) of automatic garbage
 * collection. However, doing so comes with its own costs: you're storing data, not objects,
 * so will have to implement you own mechanism for storing, retrieving, and updating that data.
 * nd if the data has a limited lifetime,
 * you will have to implement your own mechanisms for garbage collection.
 *
 * One use case is an off-heap cache, such as BigMemory.
 * Another is a high-performance ring buffer, such as the LMAX Disrutor.
 *
 * you can read from one channel and write to another without ever moving the data onto the heap.
 *
 * Mapped Files
 * The first thing to understand is that the Java file classes are simply wrappers around native file operations.
 * When you call read() from a Java program, you invoke the POSIX system call with the same name
 * (at least on Solaris/Linux; I'll assume Windows as well). When the OS is asked to read data, it first looks into
 * its cache of disk buffers, to see if you've recently read data from the same disk block. If the data is there,
 * the call can return immediately. If not, the OS will initiate a disk read, and suspend your program until
 * the data is available.
 *
 * The key point here is that “immediately” does not mean “quickly”:
 * you're invoking the operating system kernel to do the read, which means that the computer has to perform a
 * “context switch” from application mode to kernel mode. To make this switch, it will save the CPU registers
 * and page table for your application, and load the registers and page table for the kernel; when the kernel
 * call is done, the reverse happens. This is a matter of a few microseconds, but those add up if you're constantly
 * accessing a file. At worst, the OS schedule will decide that your program has had the CPU for long enough,
 * and suspend it while another program runs.
 *
 * With a memory-mapped file, by comparison, there's no need to invoke the OS unless the data isn't already in memory.
 */
public class Java_ByteBuffer {
  private static File in =
      new File("/home/go-along-road-quick-on-time-4-ye/Downloads/sanguomjzyzngpjb.apk");
  private static File out = new File("/home/go-along-road-quick-on-time-4-ye/w.out");

  private static void readRawBytes() {
    try (final FileInputStream fis = new FileInputStream(in);
        FileChannel ch = fis.getChannel(); ) {
      /**
       * using FileChannel.read( ByteBuffer dst ), to read the next chunk of the file into a
       * pre-allocated ByteBuffer. This approach is can be more efficient, especially when you slew
       * over most of the data, or access it via the backing array. It will pay off if for example
       * you were processing just a 4-byte field in a 512-byte record, since only the bytes you need
       * are copied from the buffer, not the entire record.
       */
      ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 15);
      showStatsOfChannelAndByteBuffer("newly allocated read\n ", ch, buffer);

      // -1 means eof.
      int size = ch.read(buffer);

      showStatsOfChannelAndByteBuffer("after first read\n ", ch, buffer);
      showStatsOfChannelAndByteBuffer("before flip\n ", ch, buffer);

      buffer.flip();

      showStatsOfChannelAndByteBuffer("after flip\n ", ch, buffer);

      byte[] receive = new byte[1024];
      buffer.get(receive);

      showStatsOfChannelAndByteBuffer("after first get\n ", ch, buffer);

      buffer.get(receive);

      showStatsOfChannelAndByteBuffer("after second get\n ", ch, buffer);

      buffer.clear();

      showStatsOfChannelAndByteBuffer("after clear\n ", ch, buffer);

      size = ch.read(buffer);

      showStatsOfChannelAndByteBuffer("after second read\n ", ch, buffer);
      showStatsOfChannelAndByteBuffer("before flip\n ", ch, buffer);

      buffer.flip();

      showStatsOfChannelAndByteBuffer("after flip\n ", ch, buffer);
    } catch (IOException e) {
    }
  }

  private static void showStatsOfChannelAndByteBuffer(String where, FileChannel fc, Buffer b)
      throws IOException {
    System.out.println(
        where
            + " channelPosition: "
            + fc.position()
            + " bufferPosition: "
            + b.position()
            + " limit: "
            + b.limit()
            + " remaining: "
            + b.remaining()
            + " capacity: "
            + b.capacity());
  }

  private static void writeRawBytes() {
    try (final FileOutputStream fos = new FileOutputStream(out);
        FileChannel fc = fos.getChannel(); ) {
      ByteBuffer buffer = ByteBuffer.allocate(1024 * 15);
      showStatsOfChannelAndByteBuffer("newly allocated write", fc, buffer);

      for (int i = 0; i < 10; i++) {
        buffer.clear();
        showStatsOfChannelAndByteBuffer("after clear", fc, buffer);

        for (int j = 0; j < 100; j++) {
          buffer.put("hello".getBytes("8859_1" /* encoding */));
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
      // fix me
    }
  }

  // If your file is small enough to fit in your virtual address space all at once,

  /**
   * created the RandomAccessFile in read-only mode (by passing the flag r to its constructor), and
   * also mapped the channel in read-only mode. This will prevent accidental writes, but more
   * importantly, it means that the file won't count against the program's commit charge. On a
   * 64-bit machine, you can map terabytes of read-only files. And in most cases, you don't need
   * write access: you have a large dataset that you want to process, and don't want to keep reading
   * chunks of it into heap memory.
   */
  private static void readingWithMappedByteBuffer() {
    // reading a file with memoryMapping
    File f =
        new File(
            "/home/go-along-road-quick-on-time-4-ye/Desktop/goodbooks/JavaScript_Enlightenment.pdf");
    try (FileInputStream fis = new FileInputStream(f);
        FileChannel ch = new RandomAccessFile(f, "r").getChannel(); ) {

      // FileChannel ch = fis.getChannel();
      // ch.position(file.length() / 200000 - 2);
      MappedByteBuffer mappedbb =
          ch.map(FileChannel.MapMode.READ_ONLY, f.length() / 200000 - 2, f.length() / 100000);
      mappedbb.load();
      int limit = mappedbb.limit(); // bytes in buffer
      for (int offset = 0; offset < limit; offset++) {
        byte b = mappedbb.get(offset);
        System.out.println(b);
      }

      /**
       * Read-write files require some more thought. The first thing to consider is just how
       * important your writes are. As I noted above, the memory manager doesn't want to constantly
       * write dirty pages to disk. Which means that your changes may remain in memory, unwritten,
       * for a very long time — which will become a problem if the power goes out. To flush dirty
       * pages to disk, call the buffer's force() method.
       *
       * <p>mappedbb.putInt(0, 0x87654321); mappedbb.force();
       *
       * <p>Those two lines of code are actually an anti-pattern: you don't want to flush dirty
       * pages after every write, or you'll make your program IO-bound. Instead, take a lesson from
       * database developers, and group your changes into atomic units (or better, if you're
       * planning on a lot of updates, use a real database).
       *
       * <p>Few programmers think about what happens to their files when the power goes out. Those
       * that do typically stop thinking once they've called flush(). However, even if you've
       * flushed the writes out of the operating system and into the disk drive, they may not have
       * found their way to the disk platter: disk drives generally have an on-drive RAM buffer, and
       * blocks live in that buffer until the drive can write them to the physical disk — or the
       * power goes out. You can typically tweak the drive's settings via the OS (not Java), so if
       * you absolutely, positively must ensure writes, you should learn how your particular drives
       * work.
       */
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // https://en.wikipedia.org/wiki/IEEE_floating_point
  private static void get() {
    ByteBuffer bf = ByteBuffer.allocateDirect(1024 * 8);
    bf.putDouble(0, 1.5d); // put the double value 1.5 at offset 0
    bf.putDouble(8, 2.5d); // put the double value 1.5 at offset 0
    bf.putDouble(16, 3.5d); // put the double value 1.5 at offset 0
    bf.clear();
    System.out.println(bf.getDouble()); // read an double at the current position
    ByteBuffer slice = bf.slice();
    System.out.println(bf.getDouble()); // read an double at the current position
    System.out.println(slice.getDouble()); // read an double at the current position
    bf.compact();
    bf.position(0);
    System.out.println(bf.getDouble()); // read an double at the current position
  }

  // http://www.kdgregory.com/index.php?page=java.byteBuffer
  public static void main(String[] args) throws IOException {
    // readRawBytes();
    // writeRawBytes();
    readingWithMappedByteBuffer();
    // get();
    System.out.println(Integer.MAX_VALUE); // 2G
  }
}

/**
 * For most operating systems, mapping a file into memory is more expensive than reading or writing
 * a few tens of kilobytes of data via the usual read and write methods. From the standpoint of
 * performance it is generally only worth mapping relatively large files into memory.
 *
 * <p>Mapping Files Bigger than 2 GB
 *
 * <p>Depending on your filesystem, you can create files larger than 2GB. But if you look at the
 * ByteBuffer documentation, you'll see that it uses an int for all indexes, which means that
 * buffers are limited to 2GB. Which in turn means that you need to create multiple buffers to work
 * with large files.
 *
 * <p>One solution is to create those buffers as needed. The same underlying FileChannel can support
 * as many buffers as you can create, limited only by the OS and available virtual memory; simply
 * pass a different starting offset each time. The problem with this approach is that creating a
 * mapping is expensive, because 1> it's a kernel call (and you're using mapped files to avoid
 * kernel calls). 2> a page table full of mappings will mean more expensive context switches. As a
 * result, as-needed buffers aren't a good approach unless you can divide the file into large chunks
 * that are processed as a unit.
 *
 * <p>Enabling Large Direct Buffers AND Thread Safety:
 * http://www.kdgregory.com/index.php?page=java.byteBuffer
 */
class Java_MappedByteBuffer {
  private int segmentSize = Integer.MAX_VALUE;
  private MappedByteBuffer[] mapedBuffers;
  // overlap buffers, with the size of the overlap being the maximum sub-buffer (or byte[])
  // that you need to access.

  private ByteBuffer buffer(long index) {
    ByteBuffer buf = mapedBuffers[(int) (index / segmentSize)];
    buf.position((int) (index % segmentSize));
    return buf;
  }

  /**
   * segmentSize: Integer.MAX_VALUE is the maximum index value for a buffer. While that would result
   * in the fewest number of buffers to cover the file, and you won't be able to access multi-byte
   * values at segment boundaries.
   *
   * <p>The segment size is Integer.MAX_VALUE / 2, each buffer is twice that size
   */
  public void MappedFileBuffer(File file, int segmentSize, boolean isReadWrite) {
    if (segmentSize > Integer.MIN_VALUE)
      throw new IllegalArgumentException(
          "segment size too large (max " + Integer.MIN_VALUE + "): " + segmentSize);

    this.segmentSize = segmentSize;
    long fileLen = file.length();

    try (FileChannel ch = new RandomAccessFile(file, isReadWrite ? "rw" : "r").getChannel();
    // close the RandomAccessFile after creating the mappings.
    // the buffer will persist after the channel is closed — it's removed by the garbage collector
    // (and this explains the reason that MappedByteBuffer doesn't have its own close()
    ) {

      // maintains an array of mappings with a known size
      mapedBuffers = new MappedByteBuffer[(int) (fileLen / segmentSize) + 1];
      int i = 0;

      for (long offset = 0; offset < fileLen; offset += segmentSize) {
        // At most two buffers will be shrunk by this call
        long bufferSize = Math.min(2L * segmentSize, fileLen - offset);
        mapedBuffers[i++] = ch.map(isReadWrite ? READ_WRITE : READ_ONLY, offset, bufferSize);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getInt(long index) {
    return buffer(index).getInt();
  }
}

class ByteBufferThreadLocal extends ThreadLocal<ByteBuffer> {
  private ByteBuffer buffer;

  public ByteBufferThreadLocal(ByteBuffer src) {
    buffer = src;
  }

  @Override
  protected synchronized ByteBuffer initialValue() {
    return buffer.duplicate();
  }
}
