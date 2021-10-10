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

//  javax.xml.bind.annotation.adapters does not exist
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

// Assume: 2 files are same or not depends on their sha2 only
// Thread safe
public class DuplicatedFiles {
    private ThreadLocal<Map<String, List<Path>>> localMap;// sha2 to related file.

    // For file > 500 MB
    private String sha2OfBigger(File f) {
        try (FileChannel ch = new RandomAccessFile(f, "r").getChannel()) {
            int size = (int) Math.min(f.length(), Integer.MAX_VALUE);
            MappedByteBuffer byteBuffer // 2G
                    = ch.map(FileChannel.MapMode.READ_ONLY, 0, size);

            MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
            long offset = 0;
            byte[] array = new byte[Integer.MAX_VALUE >> 2];
            while (size != 0) {
                while (byteBuffer.remaining() > 0) {
                    int numInArray = Math.min(array.length, byteBuffer.remaining());
                    byteBuffer.get(array, 0, numInArray);
                    sha2.update(array, 0, numInArray);
                }
                offset += size;
                size = (int) Math.min(Integer.MAX_VALUE, f.length() - offset);
                byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, offset, size);
            }
            return new String(sha2.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "\\";
        }
    }

    private String sha2Of(File file) {
        if (file.length() > Integer.MAX_VALUE >> 2) {
            return sha2OfBigger(file);
        }

        try (FileChannel ch = new FileInputStream(file).getChannel()) {
            byte[] arry = new byte[1024 * 8];
            ByteBuffer byteBuffer = ByteBuffer.wrap(arry);

            int size = ch.read(byteBuffer);
            MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
            while (size != -1) {
                sha2.update(arry, 0, size);
                byteBuffer.rewind();
                size = ch.read(byteBuffer);
            }

            return new String(sha2.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "\\";
        }
    }

    private void call(Path p) {
        if (p.toFile().isFile()) {
            String key = sha2Of(p.toFile());
            List<Path> paths = localMap.get().get(key);
            if (paths == null) {
                paths = new ArrayList<>();
                localMap.get().put(key, paths);
            }
            paths.add(p);
        } else {
            duplicatedFilesUnder(p);
        }
    }

    public DuplicatedFiles() {
        localMap = ThreadLocal.withInitial(() -> new HashMap<>());
    }

    // Assume no changes happen to the file content underling the given path during the process.
    // Assume the path located in the same computer as the running programming.
    public void duplicatedFilesUnder(Path path) {
        try (DirectoryStream<Path> d = Files.newDirectoryStream(path);) {
            d.forEach(this::call);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        localMap.get().values().stream()
                .filter((paths) -> {
                    return paths.size() > 1;
                })
                .forEach((paths) -> {
                    System.out.println("Dupliated: ");
                    for (Path p : paths) {
                        System.out.println("--" + p.toString());
                    }
                });
    }

    public static void main(String[] args) {
        DuplicatedFiles o = new DuplicatedFiles();
        o.duplicatedFilesUnder( new File("/home/go-along-road-quick-on-time-4-ye/IdeaProjects/keepTry/").toPath());
        o.print();
    }
}
