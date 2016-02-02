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

import org.junit.Assert;
import org.junit.Test;

import java.text.Collator;

public class PalindromeTest {
    @Test(timeout = 3000L, expected = Test.None.class)
    public void testPalindrome() {
        StringBuffer b = new StringBuffer()
                .append("A man, a plan, a canal, Panama!")
                .append("Amor, Roma")
                .append("race car")
                .append("stack cats")
                .append("step on no pets")
                .append("taco cat")
                .append("put it up")
                .append("Was it a car or a cat I saw?")
                .append("No 'x' in Nixon")
                .append("In girum imus nocte et consumimur igni")
                .append("edivider ")
                .append("noon ")
                .append("civic ")
                .append("radar ")
                .append("level ")
                .append("rotor ")
                .append("kayak ")
                .append("reviver ")
                .append("racecar ")
                .append("redder ")
                .append("madam ")
                .append("refer ")
                .append("Eva, can I stab bats in a cave?")
                .append("Mr. Owl ate my metal worm")
                .append("Was it a car or a cat I saw?")
                .append("A nut for a jar of tuna")
                .append("Do geese see God?")
                .append("Ma is as selfless as I am")
                .append("On a clover, if alive erupts a vast pure evil, a fire volcano")
                .append("Dammit, I'm mad!")
                .append("Dog, as a devil deified, lived as a god.")
                .append("Not so, Boston.")
                .append("A Toyota's a Toyota")
                .append("Go hang a salami, I'm a lasagna hog")
                .append("A Santa lived as a devil at NASA")
                .append("An igloo! Cool, Gina!")
                .append("Rats live on no evil star")
                .append("Live on time, emit no evil")
                .append("Step on no pets")
                .append("Able was I ere I saw Elba")
                .append("A man, a plan, a canal - Panama!")
                .append("Madam, I'm Adam")
                .append("Madam in Eden, I'm Adam")
                .append("Doc, note: I dissent. A fast never prevents a fatness. I diet on cod")
                .append("Never odd or even")
                .append("Rise to vote, sir")
                .append("You have no name, Manon Eva Huoy!")
                .append("racecar")
                .append("gohangasalamiimalasagnaho")
                .append("588")
                .append("313")
                .append("191")
                .append("Is it crazy how saying sentences backwards creates backwards sentences saying how crazy it is?");
        Assert.assertEquals(Palindrome.findTheLongestPalindromicSubStrOf(b.toString()),
                "Doc, note: I dissent. A fast never prevents a fatness. I diet on cod");
        Assert.assertEquals(Collator.getInstance().compare(Palindrome.findTheLongestPalindromicSubStrOf(b.toString()),
                "Doc, note: I dissent. A fast never prevents a fatness. I diet on cod"), 0);
    }
}
