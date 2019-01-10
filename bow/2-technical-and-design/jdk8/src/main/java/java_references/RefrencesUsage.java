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
import java.util.ArrayList;
import java.util.List;

public class RefrencesUsage {
    public static void main(String[] args) {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        List<LargeObjectReference> refList = new ArrayList<>();
        List<Object> objList = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            Object largeObject = new Object();
            objList.add(largeObject);
            LargeObjectReference r = new LargeObjectReference(largeObject, queue);
            boolean b = r.get() == null;
            System.out.println("it is null now? " + b); // true
            refList.add(r);
        }
        // not enqueue
        objList = null;
        // not enqueue
        System.gc();
        // yes enqueue now
        for (PhantomReference<Object> r : refList) {
            System.out.println("this reference object has been enqueued now?" + r.isEnqueued());
        }
        Reference<?> topRef;
        while ((topRef = queue.poll()) != null) {
            ((LargeObjectReference) topRef).finalizeResources();
            topRef.clear();
        }
    }
}

class LargeObjectReference extends PhantomReference<Object> {

    public LargeObjectReference(Object referent, ReferenceQueue<? super Object> q) {
        super(referent, q);
    }

    public void finalizeResources() {
        // free resources? how?
        System.out.println("I am clearing it ...");
    }
}
