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

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DeckOfPokerCardsTest {
    // expected initial cards in order
    private String expected = "Clubs A, Clubs 2, Clubs 3, Clubs 4, Clubs 5, Clubs 6, Clubs 7, Clubs 8, Clubs 9," +
            " Clubs 10, Clubs J, Clubs Q, Clubs K," +
            " Hearts A, Hearts 2, Hearts 3, Hearts 4, Hearts 5, Hearts 6, Hearts 7, Hearts 8, Hearts 9, " +
            "Hearts 10, Hearts J, Hearts Q, Hearts K," +
            " Diamonds A, Diamonds 2, Diamonds 3, Diamonds 4, Diamonds 5, Diamonds 6, Diamonds 7, Diamonds 8, Diamonds 9," +
            " Diamonds 10, Diamonds J, Diamonds Q, Diamonds K, " +
            "Spades A, Spades 2, Spades 3, Spades 4, Spades 5, Spades 6, Spades 7, Spades 8, Spades 9," +
            " Spades 10, Spades J, Spades Q, Spades K," +
            " Queen , King ";

    // expected sorted cards with default UNIT power value;
    private String sorted = "Diamonds 2, Diamonds 3, Diamonds 4, Diamonds 5, Diamonds 6, Diamonds 7, Diamonds 8, Diamonds 9," +
            " Diamonds 10, Diamonds J, Diamonds Q, Diamonds K, Diamonds A," +
            " Spades 2, Spades 3, Spades 4, Spades 5, Spades 6, Spades 7, Spades 8, Spades 9," +
            " Spades 10, Spades J, Spades Q, Spades K, Spades A," +
            " Hearts 2, Hearts 3, Hearts 4, Hearts 5, Hearts 6, Hearts 7, Hearts 8, Hearts 9," +
            " Hearts 10, Hearts J, Hearts Q, Hearts K, Hearts A," +
            " Clubs 2, Clubs 3, Clubs 4, Clubs 5, Clubs 6, Clubs 7, Clubs 8, Clubs 9," +
            " Clubs 10, Clubs J, Clubs Q, Clubs K, Clubs A," +
            " Queen , King ";
    // expected sorted cards after reverse the value of UNIT power;
    private String reversed = "King , Queen ," +
            " Clubs 2, Clubs 3, Clubs 4, Clubs 5, Clubs 6, Clubs 7, Clubs 8, Clubs 9," +
            " Clubs 10, Clubs J, Clubs Q, Clubs K, Clubs A, " +
            "Hearts 2, Hearts 3, Hearts 4, Hearts 5, Hearts 6, Hearts 7, Hearts 8, Hearts 9," +
            " Hearts 10, Hearts J, Hearts Q, Hearts K, Hearts A," +
            " Spades 2, Spades 3, Spades 4, Spades 5, Spades 6, Spades 7, Spades 8, Spades 9," +
            " Spades 10, Spades J, Spades Q, Spades K, Spades A," +
            " Diamonds 2, Diamonds 3, Diamonds 4, Diamonds 5, Diamonds 6, Diamonds 7, Diamonds 8, Diamonds 9," +
            " Diamonds 10, Diamonds J, Diamonds Q, Diamonds K, Diamonds A";

    private Card[] getItsCards(DeckOfPokerCards deck) {
        try {
            Field f = DeckOfPokerCards.class.getDeclaredField("cards");
            f.setAccessible(true);
            return (Card[]) f.get(deck);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void testConstructor() {
        Assert.assertEquals(expected, new DeckOfPokerCards().toString());
    }

    @Test(timeout = 30l, expected = Test.None.class)
    public void testShuffle() {
        DeckOfPokerCards deck = new DeckOfPokerCards();
        DeckOfPokerCards afterShuffle = deck.shuffle();
        Assert.assertNotEquals(expected, afterShuffle.toString());

        Card[] cards = getItsCards(deck);
        Card[] shuffledCards = getItsCards(afterShuffle);
        Arrays.sort(cards);
        Arrays.sort(shuffledCards);
        Assert.assertTrue(Arrays.equals(cards, shuffledCards));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void testSUITName() {
        DeckOfPokerCards deck = new DeckOfPokerCards();

        Card[] cards = getItsCards(deck);
        Arrays.sort(cards);
        Assert.assertEquals(sorted, deck.toString());

        SUIT.Diamonds.setPower(5);
        SUIT.Spades.setPower(4);
        SUIT.Hearts.setPower(3);
        SUIT.Clubs.setPower(2);
        SUIT.Queen.setPower(1);
        SUIT.King.setPower(0);

        Arrays.sort(cards);
        Assert.assertEquals(reversed, deck.toString());

        SUIT.Clubs.resetToDefaultPower();
        Arrays.sort(cards);
        Assert.assertEquals(sorted, deck.toString());
    }
}
