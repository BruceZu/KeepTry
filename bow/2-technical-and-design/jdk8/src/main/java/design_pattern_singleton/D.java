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

package design_pattern_singleton;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * Without volatile modifier it's possible for another thread in Java
 * to see half initialized state of _instance variable,
 * but with volatile variable guaranteeing happens-before relationship,
 * all the write will happen on volatile _instance before any read of _instance variable.
 * double checked locking of Singleton.
 * Intention is to minimize cost of synchronization and improve performance,
 * by only locking critical section of code, the code which creates instance of Singleton class.
 */
// double checked locking of Singleton.
public class D implements Cloneable, Serializable {

  // static and volatile
  private static volatile D INSTANCE;

  // static
  public static D getInstance() {
    if (INSTANCE == null) {
      // lock on class
      synchronized (D.class) {
        if (INSTANCE == null) {
          //                    1. using SecurityManager
          //
          // http://technonstop.com/java-singleton-reflection-and-lazy-initialization
          //                    AccessController.doPrivileged(new PrivilegedAction<Object>() {
          //                        public Object run() {
          INSTANCE = new D();
          //                            return null;
          //                        }
          //                    });

        }
      }
    }
    return INSTANCE;
  }

  // 1   private constructor
  private D() throws UnsupportedOperationException {
    // 2 Reflection can access private fields and methods,
    // which opens a threat of another instance
    //        1. using SecurityManager
    //        http://docs.oracle.com/javase/tutorial/security/index.html
    //        ReflectPermission perm = new ReflectPermission("suppressAccessChecks", "");
    //        AccessController.checkPermission(perm);

    //        2. using StackTrace
    UnsupportedOperationException e =
        new UnsupportedOperationException("This is singleton. reject reflection operation!");
    StackTraceElement[] s = e.getStackTrace();
    if (s.length >= 2) {
      String caller = s[1].getClassName();
      String me = D.class.getName();
      if (!caller.equals(me)) throw e;
    }
    // construct ....
  }

  // 3
  @Override
  protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException("This is singleton");
  }

  /**
   * 4 You can prevent this by using readResolve() method, since during serialization readObject()
   * is used to create instance and it return new instance every time but by using readResolve you
   * can replace it with original Singleton instance. I have shared code on how to do it in my post
   * Enum as Singleton in Java. This is also one of the reason I have said that use Enum to create
   * Singleton because serialization of enum is taken care by JVM and it provides guaranteed of
   * that. http://stackoverflow.com/questions/1168348/java-serialization-readobject-vs-readresolve
   */
  @Serial
  private Object readResolve() throws ObjectStreamException {
    return INSTANCE;
  }

  public static void main(String[] args) {
    String classname = "design_pattern_singleton.DBSingleton";

    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader == null) loader = D.class.getClassLoader();
    try {
      Class c = loader.loadClass(classname);
      c.newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
    }

    try {
      Class.forName(classname).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
