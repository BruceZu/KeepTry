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

package algorithm.sort;

public class InsertSort {
    public static <T extends Comparable> void sortByInsert(T[] given) {
        //ascending
        for (int j = 1; j < given.length; j++) {
            if (given[j].compareTo(given[j - 1]) >= 0) {
                continue;// not break
            }

            //need move them and insert to a location
            T temp = given[j];
            int i = j - 1;
            for (; i >= 0 && temp.compareTo(given[i]) < 0; ) {
                given[i + 1] = given[i];
                i--;
            }
            given[i + 1] = temp;
        }
    }
}
