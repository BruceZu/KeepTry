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
 * Set:
 *  What is the objective:
 *     1  distinguish elements
 *     2  the order does not matter
 *
 *  Steps:
 *     1 default status
 *        test emptyTest()
 *        Set empty
 *        coding:  data structure  boolean isEmpty;
 *     2  add add() method add test case in emptyTest()
 *        Set empty
 *        Set one
 *        Set many
 *
 *        test sizeTest()
 *        replace the
 *                  'boolean isEmpty'
 *        with
 *                  'int numberOfElements'
 *        and update add() method;
 *        assert(true, many.size()>1);
 *
 *     3 refactoring: abstract out the  Set empty, one and many to setup();
 *     4 further more for testing add logical, it is not enough to only know
 *       the set is empty or not and size is right or not
 *       test containsTest()
 *       and add:
 *
 *              Object[] elements
 *              contains(Object) method, add loop in it check .equal();
 *     5 continue: testNotAddDuplicatedElement()
 *
 *           in add() add
 *           check contains()
 *           if false: elements[numberOfElements++] = newValue;
 *     6  testCapbility:
 *           add a construture with a default capacity value and
 *           refactoring the default construture to use new one;
 *           exception: outOfArrayIndex
 *           update the add() to add checking capcity logic
 *           if(size()==elements.length){
 *               Object[] newArray = new Object[elements.length*2];
 *               System.arraycopy.(elements,0,newArray,newArray.length);
 *               elements = newArray;
 *           }
 *           corner case: the default capcacity is 0;
 *           so upate it to be  Object[] newArray = new Object[elements.length*2  + 1];
 *
 *      7 removeTest
 *         create Set Object in the removeTest()
 *         add method remove()
 *         make a loop to check if it exists
 *           elements[i] = elments[numberOfElements-1];
 *           numberOfElements--; // minus minus
 *
 *   what do you feeling?
 *
 * @see <a href="https://en.wikipedia.org/wiki/Test-driven_development">wiki</a>
 * @see <a href="http://stackoverflow.com/questions/8162423/what-is-positive-test-and-negative-test-in-unit-testing">positive test and negative test-</a>
 */
package concept;