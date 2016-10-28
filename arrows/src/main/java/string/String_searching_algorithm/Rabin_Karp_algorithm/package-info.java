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

/**
 * <pre>
 *   Pattern string length is m
 *   Source text length is n
 *   Find the index of the first matched substring in text
 *   Runtime:
 *
 *
 *      int hashOfPattern = hash(pattern string);           //  m
 *      for(int i=0; i<= n-m; i++){                         //  n-m+1
 *        int currentStringHash = hash(current substring);  //  1. rolling hash method,
 *                                                          //  here it would be rehash according to pre one and start from i =1
 *        if( currentStringHash == hashOfPattern){
 *           if(current substing equal to pattern string){
 *                                                          //  m. only execute once.
 *                                                          //  Assume it is an effective rolling hash method good enough
 *                                                          // to avoid hash collisions.
 *              return i;
 *           }
 *        }
 *      }
 *   In worse case the whole runtime is m + (n-m)*1 + 1*m = n+m
 *   it is O(n+m)
 *
 *   If the hash function is not effective. The worse case runtime is O(nm).
 *
 *   • If a sufficiently large prime number q is used for the
 * hash function, the hashed values of two different
 * patterns will usually be distinct.
 * • If this is the case, searching takes O(N) time, where
 * N is the number of characters in the larger body of
 * text.
 * • It is always possible to construct a scenario with a
 * worst case complexity of O(MN). This, however, is
 * likely to happen only if the prime number q used for
 * hashing is small.
 * =======
 * The Rabin–Karp algorithm is inferior for single pattern searching to
 * Knuth–Morris–Pratt algorithm, Boyer–Moore string search algorithm and other
 * faster single pattern string searching algorithms,
 * because of its slow worst case behavior.
 * However, it is an algorithm of choice for multiple pattern search.
 * find any of a large number, say k, fixed length (fixed length m) patterns in a text of n length.
 * A practical application of the algorithm is detecting plagiarism.
 * Assume hash table checks whether a substring hash equals any of the pattern hashes in O(1) time.
 * Runtime: km + (n-m)*1 + m = n+km
 * O(n+km).
 *
 *
 * <a href="https://www.youtube.com/watch?v=KG44VoDtsAA">youtube</a>
 * <a href="https://en.wikipedia.org/wiki/Rabin%E2%80%93Karp_algorithm">wiki robin karp algorithm</a>
 * <a href="https://brilliant.org/wiki/pigeonhole-principle-definition/">wiki pigeonhole-principle</a>
 * <a href="https://en.wikipedia.org/wiki/Horner%27s_method">wiki Horner rule</a>
 * <a href="https://en.wikipedia.org/wiki/Hash_function">wiki Hush function</a>
 * Hashing is incredibly useful and but does have some downfalls, namely, collisions.
 * <a href="https://en.wikipedia.org/wiki/Rolling_hash">wiki rolling hash</a>
 * have to calculate each shift even you know by the fist char or the last char that current substring
 * is not an candidate.
 * <a href="https://en.wikipedia.org/wiki/Rabin_fingerprint">wiki fingerprint</a>
 * ====== It is critical to select base b and modulo p
 *  Without '% q' when the m is very big, comparision will not be done in O(1) time.
 *  Comparison will need O(number if digits) operations.
 *  The number if digits is proportional to the m.
 *
 * this p should be chosen so that it and b are relatively prime;
 * this is easily accomplished by having b or p be prime.
 *
 * q = closestPrime(system.getRegisterSize());
 *
 *
 * ====
 * long hashOf(String s, long q){
 *      long hash = 0;
 *      for( i = 0; i < s.length; i++){
 *          hash = (2 * hash + s[i] ) % q;
 *      }
 *      return hash;
 *  }
 *
 *  rolling hash:
 *    n = text length
 *    m = pattern string length
 *    h = 2^(m-1) % q;
 *    hash      = ( s[i] * 2 ^(m-1) + s[i+1] * 2 ^ (m -2) + ... ... + s[i+m-1] ) % q
 *    next hash =  ((hash - s[i] * h)* 2 + s[i+m] % q) % q
 * ===
 * The mod function (% in Java) is particularly useful
 * in this case due to several of its inherent properties:
 * - [(x mod q) + (y mod q)] mod q = (x+y) mod q
 * - (x mod q) mod q = x mod q
 *
 * ===
 *  It still cannot guarantee
 *    1 if 2 strings are different, computed hash values are also different.
 *    2 if 2 strings are same, computed hash values are also same.
 *  Because using Horner's rule can only compare short strings,
 *  for the long string need 'mod q' to avoid overflow, but this may cause collisions.
 *  So still need double check to avoid spurious hits.
 *  But if we are lucky, collisions are rare enough.
 */
package string.String_searching_algorithm.Rabin_Karp_algorithm;