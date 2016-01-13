//  Copyright 2016 The Minorminor Open Source Project
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

import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
getContent in K length

Imagine there is an address: "213MaryAve,Chalrottesville,VA 22903".
You are asked to return a smaller string with limitation of size.
But there are three rules:

Number is more important than chars
The content ahead is more important than the content after
Rule 1 is higher priority than rule 2.
If K = 5, the return should be
21322
If K = 11, the return should be
213Mar22903

Please do it in two passes.
 */
public class KLengthContentOfAddress {
    static String getSubStr(int length, String from) {
        Matcher m = Pattern.compile("^(\\d+)\\D.*\\D(\\d+)$").matcher(from);
        LinkedHashSet<String> numbers = new LinkedHashSet(2);
        StringBuilder result = new StringBuilder();

        m.find();
        String houseNumber = m.group(1);
        if (length <= houseNumber.length()) {
            return houseNumber.substring(0, length);
        }

        result.append(houseNumber);
        String zipCode = m.group(2);
        if (length <= houseNumber.length() + zipCode.length()) {
            return result.append(zipCode.substring(0, length = houseNumber.length())).toString();
        }

        result.append(zipCode);
        String noNumber = from.substring(m.end(1), m.start(2));
        String wordCharacterOnly = Pattern.compile("[^\\w]").matcher(noNumber).replaceAll("");

        int numberLength = houseNumber.length() + zipCode.length();
        assert length <= numberLength + wordCharacterOnly.length();
        return result.append(wordCharacterOnly.substring(0, length - numberLength)).toString();
    }
}
