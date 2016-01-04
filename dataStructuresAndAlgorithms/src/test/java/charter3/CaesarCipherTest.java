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

package charter3;

import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;

public class CaesarCipherTest {

    private String message;
    private String encrpt;

    @Before
    public void setup() {
        message = "Hi General Washington: " +
                "command:  tomorrow morning 5:00" +
                "let us meet together at A river" +
                "you need take 5000 solders, and enough gun and resource" +
                "we will attack enemy at 30 past 8:00" +
                "Security level ABCDUVWXYZ";
        encrpt = "Kl Jhqhudo Zdvklqjwrq: frppdqg:" +
                "  wrpruurz pruqlqj 5:00ohw xv phhw wrjhwkhu dw D ulyhubrx qhhg wdnh 5000 vroghuv," +
                " dqg hqrxjk jxq dqg uhvrxufhzh zloo dwwdfn hqhpb dw 30 sdvw 8:00Vhfxulwb ohyho DEFGXYZABC";
    }

    @Test(timeout = 10000L, expected = Test.None.class)
    public void testEncryptChar() {

        Truth.assertThat(CaesarCipher.encrypt("ABCDWXYZ")).isEqualTo("DEFGZABC");
        Truth.assertThat(CaesarCipher.encrypt("abcdwxyz")).isEqualTo("defgzabc");
        Truth.assertThat(CaesarCipher.encrypt(message)).isEqualTo(encrpt);
    }

    @Test(timeout = 10000L, expected = Test.None.class)
    public void testDecryptChar() {
        Truth.assertThat(CaesarCipher.decrypt("DEFGZABC")).isEqualTo("ABCDWXYZ");
        Truth.assertThat(CaesarCipher.decrypt("defgzabc")).isEqualTo("abcdwxyz");
        Truth.assertThat(CaesarCipher.decrypt(encrpt)).isEqualTo(message);

        Truth.assertThat(CaesarCipher.decrypt2("DEFGZABC")).isEqualTo("ABCDWXYZ");
        Truth.assertThat(CaesarCipher.decrypt2("defgzabc")).isEqualTo("abcdwxyz");
        Truth.assertThat(CaesarCipher.decrypt2(encrpt)).isEqualTo(message);


        Truth.assertThat(CaesarCipher.decrypt3("DEFGZABC")).isEqualTo("ABCDWXYZ");
        Truth.assertThat(CaesarCipher.decrypt3("defgzabc")).isEqualTo("abcdwxyz");
        Truth.assertThat(CaesarCipher.decrypt3(encrpt)).isEqualTo(message);
        Truth.assertThat(CaesarCipher.decrypt3(encrpt)).isEqualTo(message);
    }

    @Test(timeout = 10000L, expected = Test.None.class)
    public void test() {
        Truth.assertThat(CaesarCipher.decrypt(CaesarCipher.encrypt(message))).isEqualTo(message);
        Truth.assertThat(CaesarCipher.decrypt2(CaesarCipher.encrypt(message))).isEqualTo(message);
        Truth.assertThat(CaesarCipher.decrypt3(CaesarCipher.encrypt(message))).isEqualTo(message);

        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt(message))).isEqualTo(message);
        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt2(message))).isEqualTo(message);
        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt3(message))).isEqualTo(message);

        Truth.assertThat(CaesarCipher.decrypt(CaesarCipher.encrypt("ABCDWXYZ"))).isEqualTo("ABCDWXYZ");
        Truth.assertThat(CaesarCipher.decrypt2(CaesarCipher.encrypt("ABCDWXYZ"))).isEqualTo("ABCDWXYZ");
        Truth.assertThat(CaesarCipher.decrypt3(CaesarCipher.encrypt("ABCDWXYZ"))).isEqualTo("ABCDWXYZ");

        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt("ABCDWXYZ"))).isEqualTo("ABCDWXYZ");
        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt2("ABCDWXYZ"))).isEqualTo("ABCDWXYZ");
        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt3("ABCDWXYZ"))).isEqualTo("ABCDWXYZ");

        Truth.assertThat(CaesarCipher.decrypt(CaesarCipher.encrypt("defgzabc"))).isEqualTo("defgzabc");
        Truth.assertThat(CaesarCipher.decrypt2(CaesarCipher.encrypt("defgzabc"))).isEqualTo("defgzabc");
        Truth.assertThat(CaesarCipher.decrypt3(CaesarCipher.encrypt("defgzabc"))).isEqualTo("defgzabc");

        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt("defgzabc"))).isEqualTo("defgzabc");
        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt2("defgzabc"))).isEqualTo("defgzabc");
        Truth.assertThat(CaesarCipher.encrypt(CaesarCipher.decrypt3("defgzabc"))).isEqualTo("defgzabc");
    }
}
