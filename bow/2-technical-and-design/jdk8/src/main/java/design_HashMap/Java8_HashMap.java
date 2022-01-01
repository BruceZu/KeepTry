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

package design_HashMap;

class Key implements Comparable<Key> {

  private final int value;

  Key(int value) {
    this.value = value;
  }

  @Override
  public int compareTo(Key o) {
    return Integer.compare(this.value, o.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Key k = (Key) o;
    return value == k.value;
  }

  @Override
  public int hashCode() {
    return value; // worse case it alway return a same value. e.g. 3
  }
}

class Keys {

  public static final int MAX_KEY = 10_000_000;
  private static final Key[] KEYS_CACHE = new Key[MAX_KEY];

  static {
    for (int i = 0; i < MAX_KEY; ++i) {
      KEYS_CACHE[i] = new Key(i);
    }
  }

  public static Key of(int value) {
    return KEYS_CACHE[value];
  }
}

public class Java8_HashMap {}
