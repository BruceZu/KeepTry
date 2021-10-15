//  Copyright 2021 The KeepTry Open Source Project
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

package map;

import java.util.*;

public class MutualRanking {
  /*
  Mutual Ranking
  Question comes from https://www.1point3acres.com/bbs/thread-769296-1-1.html
                      https://www.1point3acres.com/bbs/thread-674646-1-1.html
                      https://www.1point3acres.com/bbs/thread-793699-1-1.html
                      https://www.1point3acres.com/bbs/thread-806739-1-1.html


   user and wishlist represented with Map<Character, Character[]> wishlist
   a, [b,c,d]
   b, [a,c,d]
   c, [d,a]
   d, [a,c]
   index 0 is the highest rank

   question 1: boolean hasMutualFirstChoice(String username)
               mutual at the first rank
   question 2: boolean hasMutualRanking(char, int)，
               hasMutualRanking(a, 1) = true
               explain:   a's  ranking 1 is c， c's ranking 1 is a.
               hasMutualRanking(a, 0) = true

   question 3-1
              changePair(char user, int index) ：swap user rank index with index-1
              return matched all users before and after swap with same mutual ranking

              changePair(c,1) then c ranking list is [a,d]
              result is [a, d], because before swap result is a, after swap result is d


              a -> [b, c, d],
              b -> [a, d, c],
              c -> [d, a, b],
              d -> [a, c].

              changePair(c, 1)
              result is a b d

              Every wishlist entry in the network is either "mutually ranked" or "not mutually ranked"
              depending on the rank the other user gives that user's apartment in return.

              The most common operation in the network is incrementing the rank of a single wishlist entry on a single user.
              This swaps the entry with the entry above it in that user's list.
              Imagine that, when this occurs, the system must recompute the "mutually-ranked-ness" of any pairings that
              may have changed.

              Write a function that takes a username and a rank representing the entry whose rank is being bumped up.
              Return an array of the users whose pairings with the given user *would* gain or lose mutually-ranked
              status as a result of the change, if it were to take place.
              Call your function changed_pairings()
              data = {
               'a': ['c', 'd'],
               'b': ['d', 'a', 'c'],
               'c': ['a', 'b'],
               'd': ['c', 'a', 'b'],
              }
              if d's second choice becomes their first choice, a and d will no longer be a mutually ranked pair
              changed_pairings('d', 1) // returns ['a']

              if b's third choice becomes thei‍‌‍‌‌‍‍‌‍‍‌‍‌‍‍‍‍‌r second choice, c and b will become a mutually ranked pair (mutual second-choices)
              changed_pairings('b', 2) // returns ['c']

              if b's second choice becomes their first choice, no mutually-ranked pairings are affected
              changed_pairings('b', 1) // returns []

              if d's second choice becomes their first choice, a and d will no longer be a mutually ranked pair
              if b's third choice becomes their second choice, c and b will become a mutually ranked pair (mutual second-choices)
              if b's second choice becomes their first choice, no mutually-ranked pairings are affected

    question 3-2
     after swap（rank， rank -1)
     return the changed part of mutual list.


   question 4 Anti-ra‍‌‍‌‌‍‍‌‍‍‌‍‌‍‍‍‍‌nk
     boolean hasAntiMutualRank(char user)
     a: [b,c,d]  b is a's first rank，
     b: [d,c,a]  a is b's last rank

   boolean hasAntiMutualRankWithSwapOption(char user) can swap
     assume: index 0 and 1 swap
             index N-1 and N-1 swap


    */
  private Map<Character, Character[]> wish;

  public MutualRanking(Map<Character, Character[]> wishList) {
    wish = wishList;
  }

  public boolean hasMutualFirstChoice(char user) {
    return hasMutualRanking(user, 0, 0, false);
  }
  // rank is 0-based
  public boolean hasMutualRanking(char user, int rank) {
    return hasMutualRanking(user, rank, rank, false);
  }

  private boolean hasMutualRanking(char user, int myRank, int muRank) {
    return hasMutualRanking(user, myRank, muRank, false);
  }

  public Character[] changePair(char user, int rank) {
    char u = user;
    int r = rank;
    if (wish.containsKey(u) && wish.get(u).length > r) {
      List<Character> ans = new ArrayList<>();
      Character[] iwish = wish.get(u);
      for (int i = 0; i <= iwish.length; i++) {
        if (hasMutualRanking(u, i)) ans.add(iwish[i]); // as usual
      }
      if (r > 0) {
        // swap index and index-1
        if (hasMutualRanking(u, r, r - 1)) ans.add(iwish[r]);
        if (hasMutualRanking(u, r - 1, r)) ans.add(iwish[r - 1]);
      }

      Character[] a = new Character[ans.size()];
      return ans.toArray(a);
    }
    return new Character[0];
  }
  /*
  after swap（rank， rank -1)
  the changed part of mutual list. do not change the provided wishlist map
  */
  Character[] changed_pairings(char user, int index) {
    char u = user;
    int i = index;
    if (wish.containsKey(u) && wish.get(u).length > i) {
      ArrayList<Character> r = new ArrayList<>();
      Character[] iwish = wish.get(u);
      if (i > 0) {
        if (hasMutualRanking(u, i) != hasMutualRanking(u, i, i - 1)) r.add(iwish[i]);
        if (hasMutualRanking(u, i - 1) != hasMutualRanking(u, i - 1, i)) r.add(iwish[i - 1]);
      }
      Character[] a = new Character[r.size()];
      return r.toArray(a);
    }
    return new Character[0];
  }

  /*
  a: [b,c,d]  b is a's first rank，
  b: [d,c,a]  a is b's last rank
   */
  boolean hasAntiMutualRank(char user) {
    return hasMutualRanking(user, 0, 0, true);
  }

  private boolean hasMutualRanking(char user, int uRank, int muRank, boolean anti) {
    if (uRank < 0) return false;
    if (wish.containsKey(user) && wish.get(user).length > uRank) {
      char mu = wish.get(user)[uRank];
      return wish.containsKey(mu)
          && wish.get(mu).length > muRank
          && wish.get(mu)[anti ? (wish.get(mu).length - 1 - muRank) : muRank].equals(user);
    }
    return false;
  }
  /*
  Followup: can swap
  assume:
  index 0 and 1 swap
  index N-1 and N-1 swap
   */
  boolean hasAntiMutualRankWithSwapOption(char user) {
    return false;
  }

  public static void main(String[] args) {
    Map<Character, Character[]> wish = new HashMap<>();
    wish.put('a', new Character[] {'b', 'c', 'd'});
    wish.put('b', new Character[] {'a', 'c', 'd'});
    wish.put('c', new Character[] {'d', 'a'});
    wish.put('d', new Character[] {'a', 'c'});

    MutualRanking t = new MutualRanking(wish);
    System.out.println(t.hasMutualRanking('a', 1));
    System.out.println(t.hasMutualRanking('a', 9) == false);
    System.out.println(t.hasMutualRanking('a', 0));
    System.out.println(Arrays.toString(t.changePair('a', 1)).equals("[b, c]"));
    System.out.println(Arrays.toString(t.changePair('a', 9)).equals("[]"));

    wish = new HashMap<>();
    wish.put('a', new Character[] {'b', 'c', 'd'});
    wish.put('b', new Character[] {'a', 'd', 'c'});
    wish.put('c', new Character[] {'d', 'a', 'b'});
    wish.put('d', new Character[] {'a', 'c'});
    t = new MutualRanking(wish);
    System.out.println(Arrays.toString(t.changePair('c', 1)).equals("[a, b, d]"));

    wish = new HashMap<>();
    wish.put('a', new Character[] {'b', 'c', 'd'});
    wish.put('b', new Character[] {'a', 'c'});
    wish.put('c', new Character[] {'d', 'a'});
    wish.put('d', new Character[] {'c', 'b'});
    t = new MutualRanking(wish);
    System.out.println(Arrays.toString(t.changed_pairings('a', 1)).equals("[c, b]"));
    System.out.println(Arrays.toString(t.changed_pairings('a', 2)).equals("[c]"));

    wish = new HashMap<>();
    wish.put('a', new Character[] {'b', 'c', 'd'});
    wish.put('b', new Character[] {'d', 'c', 'a'});
    wish.put('c', new Character[] {'d', 'a'});
    wish.put('d', new Character[] {'c', 'b'});
    t = new MutualRanking(wish);
    System.out.println((t.hasAntiMutualRank('a')));
  }
}
