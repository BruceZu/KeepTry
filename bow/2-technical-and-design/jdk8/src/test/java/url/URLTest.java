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

package url;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;


public class URLTest {
    private static volatile File myArchive;
    private static volatile File myHome;
    private static final Map<Path, FileSystem> zipFileSystems = new HashMap<>();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test(timeout = 110L, expected = Test.None.class)
    public void classForTest() throws ClassNotFoundException {
        expectedException.expect(ClassNotFoundException.class);
        expectedException.expectMessage("URLTest.class");
        Class.forName("URLTest.class");
        Assert.fail("should not reach this");
    }

    @Test(timeout = 110L, expected = ClassNotFoundException.class)
    public void classForTest2() throws ClassNotFoundException {
        Class.forName("URLTest.class");
        Assert.fail("should not reach this");
    }

    @Test(timeout = 110L, expected = Test.None.class)
    public void classForTest3() {
        try {
            Class.forName("URLTest.class");
        } catch (ClassNotFoundException e) {
            Assert.assertEquals(e.getMessage(), "URLTest.class");
        }
    }

    @Test(timeout = 110L, expected = Test.None.class)
    public void classLoaderTest() throws ClassNotFoundException {
        Class self = Class.forName("url.URLTest");

        Assert.assertEquals(self.getCanonicalName(), URLTest.class.getCanonicalName());
        Assert.assertEquals(self.getCanonicalName(), "url.URLTest");
        Assert.assertEquals(self.getClassLoader(), URLTest.class.getClassLoader());
    }

    @Test(timeout = 160L, expected = Test.None.class)
    public void getResourceTest() throws ClassNotFoundException, IOException {
        // ClassLoader.getResource()
        // The name should not have a leading “/”.
        final ClassLoader classLoader = URLTest.class.getClassLoader();
        final String className = URLTest.class.getName().replace('.', '/') + ".class";

        Assert.assertEquals(className, "url/URLTest.class");
        final URL classUrl = classLoader.getResource(className);
        Assert.assertNull(classLoader.getResource("/" + className));

        // Class.getResource()
        Assert.assertTrue(URLTest.class.getResource("").getPath().endsWith("url/"));

        if (classUrl.getProtocol() == "file") {
            String path = URLTest.class.getResource("/").getPath();
            Assert.assertTrue(path.endsWith("bow/"));
        }
        if (classUrl.getProtocol() == "jar") {
            Assert.assertTrue(URLTest.class.getResource("/").getPath().endsWith("classes/"));
        }
        Assert.assertNull(URLTest.class.getResource(className));
        URL sameUrl = URLTest.class.getResource("/" + className);
        Assert.assertEquals(sameUrl, classUrl);

        sameUrl = URLTest.class.getResource(URLTest.class.getSimpleName() + ".class");
        Assert.assertEquals(sameUrl, classUrl);


        File pathf = new File(classUrl.getPath());
        if (classUrl.getProtocol() == "file") {
            Assert.assertTrue(pathf.isFile() && pathf.getParentFile().isDirectory());
        }
        if (classUrl.getProtocol() == "jar") {
            Assert.assertFalse(pathf.isFile());
            Assert.assertFalse(pathf.getParentFile().isDirectory());
        }

        Assert.assertEquals(pathf.getName(), "URLTest.class");

        // Class.getResource() and ClassLoader.getResource()
        Assert.assertEquals(URLTest.class.getResource("/"), classLoader.getResource(""));

        // Class.getProtectionDomain().getCodeSource()
        CodeSource codeSrcOfThisDomain = URLTest.class.getProtectionDomain().getCodeSource();
        if (codeSrcOfThisDomain != null) {
            URL urlOfCodeSrcOfThisDomain = codeSrcOfThisDomain.getLocation();
            if (classUrl.getProtocol() == "file") {
                Assert.assertTrue(urlOfCodeSrcOfThisDomain.getPath()
                        .endsWith("/bow/"));
            }

            if (classUrl.getProtocol() == "jar") {
                System.out.println(urlOfCodeSrcOfThisDomain.getPath());
                Assert.assertTrue(urlOfCodeSrcOfThisDomain.getPath()
                        .endsWith("url.URLTest%23testsjar.jar"));
            }
        }
    }
}
