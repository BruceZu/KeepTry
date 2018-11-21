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

package array;

public class CompareStrings {
  /** lexicographic ordering refer {@link java.lang.String#compareTo(String)} } */

  /**
   * version like 1.2.3.6 version like 1.12.7.11.21
   *
   * @return 0 if they are same, -1 if v1 > v2, 1 if v1< v2
   */
  public static int compareVersion(String version1, String version2) {
    // todo corner cases
    int endIndexInV1 = version1.indexOf(".");
    int endIndexInV2 = version2.indexOf(".");
    int beginIndexInV1 = 0;
    int beginIndexInV2 = 0;
    while (endIndexInV1 != -1 && endIndexInV2 != -1) {
      int currentNumInV1 = Integer.valueOf(version1.substring(beginIndexInV1, endIndexInV1));
      int currentNumInV2 = Integer.valueOf(version2.substring(beginIndexInV2, endIndexInV2));
      if (currentNumInV1 < currentNumInV2) {
        return 1;
      } else if (currentNumInV1 > currentNumInV2) {
        return -1;
      }
      beginIndexInV1 = endIndexInV1 + 1;
      beginIndexInV2 = endIndexInV2 + 1;
      endIndexInV1 = version1.indexOf(".", endIndexInV1 + 1);
      endIndexInV2 = version2.indexOf(".", endIndexInV2 + 1);
    }
    if (endIndexInV1 == -1 && endIndexInV2 == -1) {
      return 0;
    }
    if (endIndexInV1 == -1 && endIndexInV2 != -1) {
      return -1;
    }
    return 1;
  }

  public static void main(String[] args) {
    System.out.println(compareVersion("1.21.3.6", "1.21.3"));
  }
}
