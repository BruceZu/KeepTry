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

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public class Refs {
  /* https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ref/package-summary.html
  The reference object is on the heap
    java.lang.ref.SoftReference: memory-sensitive caches
    java.lang.ref.WeakReference: canonicalizing mappings that do not prevent their keys (or values) from being reclaimed,
    java.lang.ref.PhantomReference: Post-mortem cleanup actions which can be registered and managed by a Cleaner

   (java.lang.ref.FinalReference)
   All of these reference types:
   - refer to a single object: referent
   - immutable, no set().
   - get(),clear() the referent
     (if the reference hasn't been cleared by garbage collector invoking clear()).
   - An object is phantom reachable if it is neither strongly, softly, nor weakly reachable, it has been finalized, and
     some phantom reference refers to it.
   Finally, an object is unreachable, and therefore eligible for reclamation, when it is not reachable
   in any of the above ways. An object that is reachable via phantom references will remain until all such phantom references
   are cleared or phantom references become unreachable.

   the garbage collector clears soft and weak reference objects before enqueueing them,
   the garbage collector enqueues soft/weak reference objects to indicate their referents
   have just left the softly/weak reachable state.
   but garbage collector enqueues phantom reference objects to indicate their referents
   have entered the phantom reachable state.

   JVM guarantees
     - will not garbage collect an object as long as there are strong references to it.
     - will not throw an OutOfMemoryError until it has "cleaned up" all softly reachable objects
  */
  private Set<WeakReference> registeredObjects = new HashSet();

  public void register(Object object) {
    registeredObjects.add(new WeakReference(object));
  }

  public static void main(String[] args) {
    Reference aRef = new SoftReference(new String("foo"));
  }
}
