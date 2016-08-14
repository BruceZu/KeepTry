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

import java.util.Random;

enum SUIT {
    Diamonds(0), Spades(1), Hearts(2), Clubs(3), Queen(4), King(5);
    private int power;

    SUIT(int power) {
        this.power = power;
    }

    int power() {
        return power;
    }

    void setPower(int newPower) {
        this.power = newPower;
    }

    public void resetToDefaultPower() {
        Diamonds.setPower(0);
        Spades.setPower(1);
        Hearts.setPower(2);
        Clubs.setPower(3);
        Clubs.Queen.setPower(4);
        Clubs.King.setPower(5);
    }
}

/**
 * Diamonds <pre>
 * id      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
 * value  14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
 * name    A, 2, 3, 4, 5, 6, 7, 8, 9, 10,  J,  Q,  K
 * Spades
 * id      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
 * value  14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
 * name    A, 2, 3, 4, 5, 6, 7, 8, 9, 10,  J,  Q,  K
 * Hearts
 * id      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
 * value  14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
 * name    A, 2, 3, 4, 5, 6, 7, 8, 9, 10,  J,  Q,  K
 * Clubs
 * id      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
 * value  14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
 * name    A, 2, 3, 4, 5, 6, 7, 8, 9, 10,  J,  Q,  K
 * Queen
 * id                                                 14
 * value                                              14
 * name
 * King
 * id                                                    15
 * value                                                 15
 * name
 */
class Card implements Comparable {
    private static final String[] ID_TO_NAME;
    public static final int POWER = 13;
    public static final int QUEEN_ID = 14;
    public static final int KING_ID = 15;

    static {
        ID_TO_NAME = new String[16];
        ID_TO_NAME[1] = "A";
        for (int i = 2; i <= 10; i++) {
            ID_TO_NAME[i] = "" + i;
        }
        ID_TO_NAME[11] = "J";
        ID_TO_NAME[12] = "Q";
        ID_TO_NAME[13] = "K";

        ID_TO_NAME[QUEEN_ID] = "";
        ID_TO_NAME[KING_ID] = "";
    }

    int id;
    SUIT suit;

    public int value() {
        return id == 1 ? 14 : id;
    }

    public String name() {
        StringBuilder r = new StringBuilder();
        return r.append(suit.name()).append(" ").append(ID_TO_NAME[id]).toString();
    }

    public Card(SUIT suit, int id) {
        this.id = id;
        this.suit = suit;
    }

    @Override
    public int compareTo(Object o) {
        Card c = (Card) o;
        int v = this.suit.power() * POWER + this.value();
        int ov = c.suit.power() * POWER + c.value();
        return v < ov ? -1 : v > ov ? 1 : 0;
    }
}

public class DeckOfPokerCards {
    Card[] cards;

    public DeckOfPokerCards() {
        cards = new Card[54];
        for (int id = 1; id <= Card.POWER; id++) {
            cards[0 * Card.POWER + id - 1] = new Card(SUIT.Clubs, id);
            cards[1 * Card.POWER + id - 1] = new Card(SUIT.Hearts, id);
            cards[2 * Card.POWER + id - 1] = new Card(SUIT.Diamonds, id);
            cards[3 * Card.POWER + id - 1] = new Card(SUIT.Spades, id);
        }

        cards[52] = new Card(SUIT.Queen, Card.QUEEN_ID);
        cards[53] = new Card(SUIT.King, Card.KING_ID);
    }

    public DeckOfPokerCards shuffle() {
        Random r = new Random();
        for (int i = 2; i < cards.length; i++) {
            int swapWith = r.nextInt(i);

            Card tmp = cards[i];
            cards[i] = cards[swapWith];
            cards[swapWith] = tmp;
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < cards.length - 1; i++) {
            r.append(cards[i].name()).append(", ");
        }
        return r.append(cards[cards.length - 1].name()).toString();
    }
}
