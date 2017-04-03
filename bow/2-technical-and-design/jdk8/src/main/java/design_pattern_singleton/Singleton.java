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

/**
 * Former is called lazy loading because Singleton instance is
 * created only when client calls getInstance() method while later is called early loading
 *
 * because Singleton instance is created when class is loaded into memory.

  */
public class Singleton {
    // static final
    // initialized when class is loaded into memory.
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {
    }

    // static factory method
    public static Singleton getSingleton() {
        return INSTANCE;
    }
}