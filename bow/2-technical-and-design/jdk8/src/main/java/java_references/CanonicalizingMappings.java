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

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class CanonicalizingMappings {
  /*
  A "canonicalized" mapping: keep one instance of the object in question in memory
  and all others look up that particular instance via pointers or some such mechanism.
  E.g.
  - a Map (or HashTable) that maps from the employee number to a weak reference to the corresponding
       Employee object
  - WeakHashMap
  */
  private Map<Long, WeakReference<Object>> employee = new HashMap<>();
}
