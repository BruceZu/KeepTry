//  Copyright 2022 The KeepTry Open Source Project
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

package string;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WordsWrap {

  /*
  given workd list and max length
  connect words with "-" but not extends the max length
  Assume maxLen > max word length

  Arrays.copyOfRange(ws, from, i)
  String.join("-", iterable)
  */
  public List<String> wordWrap(String[] ws, int maxLen) {
    List<String> result = new LinkedList<>();
    int i = 0;
    while (i < ws.length) {
      int len = maxLen; // left allowed length
      int from = i;
      while (i < ws.length) {
        if (len < ws[i].length()) break;
        len -= ws[i].length() + 1;
        i++;
      }

      result.add(String.join("-", Arrays.asList(Arrays.copyOfRange(ws, from, i))));
    }
    return result;
  }
}
