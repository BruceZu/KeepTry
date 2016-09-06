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

package abstract_class_interface;

import java.io.Serializable;

public interface My_Interface extends Serializable, Cloneable {
    // fields:
    //       default:  final static
    final static String my_name = ""; // must initialize field value.

    // method:
    //      default: public abstract
    //          final   <---conflict--->  abstract
    //          static  <---conflict--->  abstract
    //          static  <---conflict--->  final
    public abstract String getMy_name();

    static String my_method() {
        return my_name;
    }
}
