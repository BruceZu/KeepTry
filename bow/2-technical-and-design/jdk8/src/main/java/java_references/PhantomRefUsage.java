//  Copyright 2019 The KeepTry Open Source Project
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PhantomRefUsage {
  /*   https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ref/package-summary.html
  calling the get() method always returns null
  Yet the PhantomReference still holds onto a reference to its referent.
  Why is this?
  designed to allow for pre-mortem cleanup.
  between `going to be garbage collected` and  `just before actually gets collected`
  do: clean up resources
  E.g. several objects collaborating together
       clean shared resource only when all objects are no longer using that shared resource.
       or when the last object becomes phantomly reachable, do the actual cleanup.
       or check the queue count the number of PhantomReference
   */
  static class LargeObjectReference extends PhantomReference<Object> {

    public LargeObjectReference(Object referent, ReferenceQueue<? super Object> q) {
      super(referent, q);
    }

    public void finalizeResources() {
      System.out.println("I am clearing it ...");
    }
  }

  public static void main(String[] args)
      throws InterruptedException, NoSuchFieldException, IllegalAccessException {
    ReferenceQueue<Object> q = new ReferenceQueue<>();
    List<LargeObjectReference> refs = new ArrayList<>();
    List<Object> list = new ArrayList<>();

    for (int i = 0; i < 10; ++i) {
      Object o = new Object(); // the hard reference only valid in for block
      LargeObjectReference r = new LargeObjectReference(o, q);
      System.out.println(r.refersTo(o));
      System.out.println(r.get() == null);

      list.add(o);
      refs.add(r);
    }

    list = null; // become phantom reachable
    System.gc(); //
    Reference<?> top;
    while ((top = q.poll()) == null) {
      System.out.println("no reference inqueue");
    }
    int i = 1;
    while ((top = q.poll()) != null) {
      i++;
      System.out.println("this reference object " + top + " has been enqueued now ");
      ((LargeObjectReference) top).finalizeResources();
      Field referent = Reference.class.getDeclaredField("referent");
      // referent.setAccessible(true);
      // Integer o = (Integer) referent.get(top);
      // System.out.println("this rerent object is" + o);
      top.clear();
    }
    if (refs.size() == i) System.out.println("ready to clean share resource");
  }
}
