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

package nosubmmitted;

/**
 * 277. Find the Celebrity
 * https://leetcode.com/problems/find-the-celebrity/
 * <p/>
 * Difficulty: Medium <pre>
 * Suppose you are at a party with n people (labeled from 0 to n - 1) and among them,
 * there may exist one celebrity. The definition of a celebrity is that all the other n - 1 people
 * know him/her but he/she does not know any of them.
 * <p/>
 * Now you want to find out who the celebrity is or verify that there is not one. The only thing
 * you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of
 * whether A knows B. You need to find out the celebrity (or verify there is not one) by asking
 * as few questions as possible (in the asymptotic sense).
 * <p/>
 * You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement
 * a function int findCelebrity(n), your function should minimize the number of calls to knows.
 * <p/>
 * Note: There will be exactly one celebrity if he/she is in the party. Return the celebrity's label
 * if there is a celebrity in the party. If there is no celebrity, return -1.
 * <p/>
 * Hide Company Tags: LinkedIn Facebook
 * Hide Tags: Array
 */
public class LC277FindtheCelebrity {
    /* The knows API is defined in the parent class Relation.
          boolean knows(int a, int b); */
    class Relation {
        boolean knows(int a, int b) {
            return true; // To-Do
        }
    }

    class Solution extends Relation {


        /**
         * beat 97
         * i think it minimizes the call of "bool knows(a, b)"
         */
        public int findCelebrity4(int n) {
            int cand = 0;  // set 0 as current candidate
            int prevOneKnowsCand = -1;
            for (int i = 1; i < n; ++i) { // if cand knows i, celebrity must not be this cand, set cand to i. otherwise, i must not be the celebrity, skip it.
                if (knows(cand, i)) {
                    prevOneKnowsCand = cand;  // at least we've already known that "cand" knows the future candidate i
                    cand = i;
                }
            }  // at this point, cand does not know anyone coming after him
            for (int i = 0; i < cand; ++i) {
                if (knows(cand, i)) {
                    return -1;
                }
            }  // at this point, cand does not know anyone else
            for (int i = 0; i < n; ++i) {
                if (i != cand && i != prevOneKnowsCand) {  // these 2 ppl know candidate for sure
                    if (!knows(i, cand)) {
                        return -1;
                    }
                }
            }
            return cand;
        }

        /** beat 97
         *
         * @param n
         * @return
         */
        public int findCelebrity6(int n) {
            int cand = 0;  // set 0 as current candidate
            // int prevOneKnowsCand = -1;
            for (int i = 1; i < n; ++i) { // if cand knows i, celebrity must not be this cand, set cand to i. otherwise, i must not be the celebrity, skip it.
                if (knows(cand, i)) {
                    // prevOneKnowsCand = cand;  // at least we've already known that "cand" knows the future candidate i
                    cand = i;
                }
            }  // at this point, cand does not know anyone coming after him
            for (int i = 0; i < cand; ++i) {
                if (knows(cand, i)) {
                    return -1;
                }
            }  // at this point, cand does not know anyone else
            for (int i = 0; i < n; ++i) {
                // if (i!=cand && i!=prevOneKnowsCand) {  // these 2 ppl know candidate for sure
                if (!knows(i, cand)) {
                    return -1;
                } // }
            }
            return cand;
        }

        /**
         * beat 85.3
         */
        public int findCelebrity(int n) {
            if (n <= 1)
                return -1;
            int celebrity = 0, i;
            for (i = 1; i < n; i++) {// Exclusion way to find candidate
                if (knows(celebrity, i))
                    celebrity = i;
            }
            for (i = 0; i < n; i++) { // Check whether candidate is valid
                if (i == celebrity || (knows(i, celebrity) && !knows(celebrity, i)))
                    continue;
                else
                    break;
            }
            if (i == n)
                return celebrity;
            else
                return -1;
        }

        /**
         * beat 85
         * <p/>
         * The first loop is to exclude n - 1 labels that are not possible to be a celebrity.
         * After the first loop, x is the only candidate. The second and third loop is to
         * verify x is actually a celebrity by definition.
         * <p/>
         * The key part is the first loop. To understand this you can think the knows(a,b)
         * as a a < b comparison, if a knows b then a < b, if a does not know b, a > b.
         * Then if there is a celebrity, he/she must be the "maximum" of the n people.
         * <p/>
         * However, the "maximum" may not be the celebrity in the case of no celebrity at all.
         * Thus we need the second and third loop to check if x is actually celebrity by definition.
         * <p/>
         * The total calls of knows is thus 3n at most. One small improvement is that in the
         * second loop we only need to check i in the range [0, x). You can figure that out yourself easily.
         *
         * @param n
         * @return
         */
        public int findCelebrity2(int n) {
            int x = 0;
            for (int i = 0; i < n; ++i) if (knows(x, i)) x = i;
            for (int i = 0; i < x; ++i) if (knows(x, i)) return -1;
            for (int i = 0; i < n; ++i) if (!knows(i, x)) return -1;
            return x;
        }

        /**
         * beat 85
         */
        public int findCelebrity3(int n) {


            int celebrity = 0, ask = celebrity + 1;
            /*use this loop to get one potential celebrity*/
            while (ask < n)

            {
                /*if a knows b, that means a cannot be a celebrity*/
                if (knows(celebrity, ask)) {
                    celebrity = ask;
                    ask = ask + 1;
                } else {
                    /*if a doesn't know b, that means b cannot be a celebrity*/
                    ask = ask + 1;
                }
            }
            /*check if the potential celebrity is a "real" celebrity*/
            for (
                    int i = 0;
                    i < n; i++)

            {
                if (i == celebrity) continue;
                 /*if there is anyone who doesn't know the celebrity or the celebrity knows someelss, then it cannot be a celebrity*/
                if (!knows(i, celebrity) || knows(celebrity, i)) return -1;
            }

            return celebrity;
        }
    }
}