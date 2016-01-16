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

public class DiffAdjacentCharactersTest {
    @Test(timeout = 3000000l, expected = Test.None.class)
    public void test() {
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("ab"), "ab");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("abc"), "abc");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("aaa"), "None");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("aabb"), "abab");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("aaab"), "None");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("aaabb"), "ababa");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("aaaab"), "None");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("aaaabbc"), "ababaca");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("6666665555544441"), "6454656565656414");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("abbcccddddeeeeeeeeee"), "ededededecececebebea");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("google"), "gogoel");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharactersOf("elggoooo"), "ogogoeol");

        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("ab"), "ab");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("abc"), "abc");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("aaa"), "None");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("aabb"), "abab");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("aaab"), "None");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("aaabb"), "ababa");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("aaaab"), "None");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("aaaabbc"), "ababaca");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("6666665555544441"), "4565656565641464");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("abbcccddddeeeeeeeeee"), "ebebededededeaececec");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("google"), "gogole");
        Assert.assertEquals(new DiffAdjacentCharacters().diffAdjacentCharacters("elggoooo"), "olgogoeo");
    }
}
