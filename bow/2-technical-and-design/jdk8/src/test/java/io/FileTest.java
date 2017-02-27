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

package io;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.Map;
import java.util.jar.JarFile;

public class FileTest {

    @Test(timeout = 220L, expected = Test.None.class)
    public void tmpFileTest() throws ClassNotFoundException, IOException {
        CodeSource srcjar = FileTest.class.getProtectionDomain().getCodeSource();
        if (srcjar != null) {
            URL url = srcjar.getLocation();
            System.out.println("read from jar:" + url);
            try (InputStream in = url.openStream()) {
                File tmp = createTempFile("tmp_name_prefix_", ".zip", false);
                try (FileOutputStream out = new FileOutputStream(tmp)) {
                    final byte[] buf = new byte[4096];
                    int n;
                    while ((n = in.read(buf, 0, buf.length)) > 0) {
                        out.write(buf, 0, n);
                    }
                }

                JarFile jar = new JarFile(tmp);
                Map.Entry att = jar.getManifest().getMainAttributes().entrySet().iterator().next();
                Assert.assertEquals(att.getKey().toString(), "Manifest-Version");
                Assert.assertEquals(att.getValue().toString(), "1.0");
            } catch (IOException e) {
                e.printStackTrace();
                Assert.assertEquals(e.toString(), "?");
            }
        }
    }
    
    private File createTempFile(String prefix, String suffix, boolean keep)
            throws IOException {
        String tmpBase;
        tmpBase = System.getenv("MY_TEST_TEMP");
        // 1 user set system env

        tmpBase = System.getProperty("user.home");
        // 2 JVM default

        tmpBase = System.getenv("HOME");
        // 3 system env
        
        File tmpParent = null; // new File(tmpBase);
        File tmp = new File(tmpParent, "tmp");
        // 4 if parent is null JVM's default temporary directory

        final File app = File.createTempFile("my_", "_app", null /*tmp*/);
        // 5  tmp directory is decided by buck

        if (app.delete() && app.mkdir()) {
            // remove all permissions,
            app.setWritable(false, false /* all */);
            app.setReadable(false, false /* all */);
            app.setExecutable(false, false /* all */);
            //  add back only the owner permissions.
            app.setWritable(true, true /* owner only */);
            app.setReadable(true, true /* owner only */);
            app.setExecutable(true, true /* owner only */);
            if (!keep) {
                app.deleteOnExit();
            }
        }

        if (app != null) {
            File d = new File(app, prefix + suffix);
            if (d.createNewFile()) {
                if (!keep) {
                    d.deleteOnExit();
                }
                System.out.println("write to " + d);
                return d;
            }
        }

        File d = File.createTempFile(prefix, suffix, null);
        if (!keep) {
            d.deleteOnExit();
        }
        System.out.println("write to :" + d);
        return d;
    }
}
