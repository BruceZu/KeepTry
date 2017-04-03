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
 * https://github.com/carrotsearch/hppc/wiki/Scatter-Or-Hash
 * https://github.com/carrotsearch/hppc/blob/master/hppc-examples/src/test/java/com/carrotsearch/hppc/examples/HppcExample_006_HashAndScatterMaps.java
 * How to handle Collisions?
 * 1) Separate Chaining-> HashMap
 * 2) Open Addressing （closed hashing）-> Hash Table
 *     https://en.wikipedia.org/wiki/Open_addressing
 *     https://en.wikipedia.org/wiki/Hash_table#Collision_resolution
 *     http://quiz.geeksforgeeks.org/hashing-set-3-open-addressing/
 *
 *     There are literally thousands of named hashing functions.
 *     Some are secure, but comparatively slow to calculate.
 *     Some are very fast, but have more collisions.
 *     Some are close to perfectly uniformly distributed, but very hard to implement.
 *
 *     If there’s one rule in programming it’s this: there will always be trade-offs.
 */
package hash;
