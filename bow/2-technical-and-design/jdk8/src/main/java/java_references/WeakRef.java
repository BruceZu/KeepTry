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

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class WeakRef {
  public static void main(String[] args) throws InterruptedException {
    /* https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ref/package-summary.html
            <1> garbage collector determines the reachability of the referent has changed to
            WeakReference or PhantomReference reachable, it will add the reference to the associated queue.
            SoftReference will not be put in queue before memory is not enough
            <2> queue does not keep track of the registered references
            If a registered reference becomes unreachable itself, then it will never be enqueued.
            It is the responsibility of the program using reference objects to ensure that the
            reference objects remain reachable for as long as the program is interested in their referents.

            If there is not `wr` to refer the new WeakReference(fox, q);
             then the WeakReference object will never enqueue.

          WeakHashMap: check the poll() part code to clean the reference object itself which is still reachable from
          table

         A "canonicalized" mapping: keep one instance of the object in question in memory
         and all others look up that particular instance via pointers or some such mechanism.
         E.g.
         - a Map (or HashTable) that maps from the employee number to a weak reference to the corresponding
           Employee object
         - WeakHashMap
    */
    Map<Long, WeakReference<Object>> employee = new HashMap<>();

    Object fox = new Object();
    ReferenceQueue q = new ReferenceQueue();
    PhantomReference wr = new PhantomReference(fox, q); // wr. as <2> said
    //  Reference wr = new PhantomReference(fox, q);

    fox = null; // only weak reachable now. as <1> said
    System.out.println(q.poll() == null);

    /*
     never invoke System.gc(); explicitly:
        It's costly
        It doesn't trigger the garbage collection immediately – it's just a hint for the JVM to start GC
        JVM knows better when GC needs to be called
        If we need to force GC, we can use jconsole for that.
    */
    System.gc(); // without this line just need sometime to let JVM garbage collector determines
    // that the reachability of the referent changes to expected one

    Reference top;
    while ((top = q.poll()) == null) {
      System.out.println("not in queue yet");
    }

    System.out.println("enqued, get it out");
    System.out.println(top == wr);
    System.out.println(
        wr.refersTo(null)); // the Soft and Weak reference is cleared when it is enqueued
  }
}
