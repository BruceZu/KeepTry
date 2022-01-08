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

package OOD;

import java.util.Set;

/*
OOD
  user cases(who do what)
    1 role: (time, people, groups, system, subsystem):
    2 data and relation: object
    3 operation and rule

  data flow: search <> storage <>
UML
*/
// ignore dealer
interface Player {
  boolean hit();
  void offer(Set<Card> cards);
  int values();
}

interface Deck {
  void shuffle();
  Set<Card> next(int num);
}

class Card {
  int face, value, suit;
}

// encapsulates the basic sequence of play
public class GameBlackjack {
  private Deck deck;

  public GameBlackjack(Deck deck) {
    this.deck = deck;
  }

  public int start(int roundNum, int playerNum) {
    while (roundNum >= 1) {}

    // who will win;
    return 0;
  }
}
