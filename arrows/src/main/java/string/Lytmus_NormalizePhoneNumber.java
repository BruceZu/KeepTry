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

package string;

public class Lytmus_NormalizePhoneNumber {

    /**
     * Normalize a given phone number.
     * <pre>
     * This method declaration must be kept unmodified.
     *
     * @param phoneNumber: A string containing a phone number.
     * @return String with the normalized phone number if the input phone is
     * valid, or '' otherwise.
     */
    public static String normalizeTelephoneNumber(String phoneNumber) {
        // Write your implementation here

        char[] number = phoneNumber.toCharArray();
        StringBuilder st = new StringBuilder();
        int digitSize = 0;
        for (char c : number) {
            if (Character.isDigit(c)) {
                digitSize++;
                st.append(c);
                if (digitSize == 3 || digitSize == 7) {
                    st.append("-");
                }
                if(digitSize>10){
                    return "";
                }
            }
        }
        if (digitSize != 10) {
            return "";
        }
//        st.insert(3, "-");
//        st.insert(7, "-");
        return st.toString();
    }

    // This tests your code with the examples given in the question,
    // and is provided only for your convenience.
    public static void main(String[] args) {
        String[] phoneNumbers = {"(650) 555-1234", "65.0555.1234",
                "65/05/5512/34", "(650) 555-1234 x111",
                "(650) 555-123"};
        for (String phoneNumber : phoneNumbers) {
            System.out.println(
                    normalizeTelephoneNumber
                            (phoneNumber));
        }
    }
}