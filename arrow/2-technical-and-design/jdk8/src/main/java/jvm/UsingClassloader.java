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

package jvm;

import concurrency.VolatileTest;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class UsingClassloader {
    // Parent class loader:
    // usually consulted first, avoids loading same class  several times
    // however in a Java EE web module classes are searched first
    // In Java EE each WAR module of an EAR gets its own class loader. allows separate
    // namespaces for applications is same container
//
//    The three prominent classloader models are:
//
//    1  the default in the JDK - ask parent, then me
//    2  common in plugins, servlets, and places where you want isolation - ask me, then parent
//    3  sibling (common in dependency models like OSGi, Eclipse, etc)
//
//
    //    In a web environment - where the application server creates a hierarchy of class loaders,
    //    resembling something like
    //
    //    javahome/lib - as root
    //    appserver/lib - as child of root
    //    webapp/WEB-INF/lib - as child of child of root
    //    etc
    // class loaders are able to delegate to the parent class loader, they cannot delegate back down the tree.

    //class loaders:
    //  Bootstrap : core java classes from jdk/jre/rt.jar. its implementation is natively inside JVM
    //  Extension : child of Bootstrap, for classes in extension directory: 'jre/lib/ext'
    //  System: application classloader, for classed in CLASSPATH and application's entry point class. 'public static void main()' of class
    public void play() {
        System.out.println("done");
    }

    public static void main(String[] args) {
        new UsingClassloader().play();
        // 1 check if in the classpath
        // Servlet container will search class from
        // -- WEB-INF/classes/
        // -- WEB-INF/lib/*.jar
        //
        System.out.println(Arrays.toString(((URLClassLoader) UsingClassloader.class.getClassLoader()).getURLs()));

        // 2 check if the class is the expected one
        final String binaryName = VolatileTest.class.getName();
        String resourceName = binaryName.replace(".", "/") + ".class";
        URL resource = UsingClassloader.class.getClassLoader().getResource(resourceName);
        System.out.println(resource);
        // javap -private <class>

        // test.  take care of class loader leak caused by reference.
        try {
            File f = new File(resource.getFile());
            URL vivid = f.getParentFile().getParentFile().toURI().toURL();
            URLClassLoader cl = new URLClassLoader(new URL[]{vivid}) {

                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    if (binaryName.equals(name)) {
                        return findClass(name);
                    }
                    return super.loadClass(name);
                }
            };

            Object o = cl.loadClass(binaryName).newInstance();
            o.getClass().getMethod("myName").invoke(o);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
