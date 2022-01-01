//  Copyright 2021 The KeepTry Open Source Project
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

package java_references;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

// see final class Finalizer extends FinalReference<Object>
public class FinalRef {

  // understand finalize():
  private BufferedReader reader;

  public FinalRef() {
    InputStream input = this.getClass().getClassLoader().getResourceAsStream("file.txt");
    this.reader = new BufferedReader(new InputStreamReader(input));
  }

  public String readFirstLine() throws IOException {
    String firstLine = reader.readLine();
    return firstLine;
  }

  @Override
  public void finalize() {
    //  deprecated starting with Java 9 – and will eventually be removed.
    /*
    - lack of promptness. We cannot know when a finalizer runs since garbage collection may occur anytime.
      Thus, we may run out of resources before a clean-up happens, which may result in a system crash.
    - portability.  garbage collection algorithm is JVM implementation-dependent.
    - The performance cost. JVM must perform many more operations when constructing and destroying
      objects containing a non-empty finalizer.
    - lack of exception handling during finalization. If a finalizer throws an exception,
      the finalization process stops, leaving the object in a corrupted state without any notification.
     */
    try {
      /*
      the time at which the garbage collector calls finalizers is dependent
      on the JVM's implementation and the system's conditions, which are out of our control.
       */
      reader.close();
      System.out.println("Closed BufferedReader in the finalizer");

    } catch (IOException e) {
      System.out.println("No way to catch it");
    }
  }

  /*
      - When creating an object, a referent, that has a finalizer. The JVM creates an accompanying reference object
        of type java.lang.ref.Finalizer.
      - After the referent is ready for garbage collection, the JVM marks the reference object as ready for processing
        and puts it into a reference queue, static field queue in the java.lang.ref.Finalizer class.
      - Meanwhile, a special daemon thread,Thread finalizer = new FinalizerThread(tg); keeps running and looks for
        objects in the reference queue. When it finds one, it removes the reference object from the queue and
        invokes the finalize method of the referent.
     -  During the next garbage collection cycle, the referent will be discarded – when it's no longer referenced from a reference object.

    If a thread keeps producing objects at a high speed, the Finalizer thread cannot keep up.
    Eventually, the memory won't be able to store all the objects, and we end up with an OutOfMemoryError
  */
  public static void main(String[] args) throws ReflectiveOperationException {
    for (int i = 0; ; i++) {
      new FinalRef();

      if ((i % 1_000_000) == 0) {
        Class<?> fc = Class.forName("java.lang.ref.Finalizer");
        Field queue = fc.getDeclaredField("queue"); // static
        queue.setAccessible(true);
        ReferenceQueue<Object> q = (ReferenceQueue) queue.get(null);

        Field len = ReferenceQueue.class.getDeclaredField("queueLength");
        len.setAccessible(true);
        long queueLength = (long) len.get(q);
        System.out.format("There are %d references in the queue%n", queueLength);
      }
    }
  }

  //  without the use of finalize() method.
  class CloseableResource implements AutoCloseable {
    private BufferedReader reader;

    public CloseableResource() {
      InputStream input = this.getClass().getClassLoader().getResourceAsStream("file.txt");
      reader = new BufferedReader(new InputStreamReader(input));
    }

    public String readFirstLine() throws IOException {
      String firstLine = reader.readLine();
      return firstLine;
    }

    @Override
    public void close() {
      try {
        reader.close();
        System.out.println("Closed BufferedReader in the close method");
      } catch (IOException e) {
        // handle exception
      }
    }

    @Test
    public void whenTryWResourcesExits_thenResourceClosed() throws IOException {
      try (CloseableResource resource = new CloseableResource()) {
        String firstLine = resource.readFirstLine();
        assertEquals("baeldung.com", firstLine);
      }
    }
  }
}
