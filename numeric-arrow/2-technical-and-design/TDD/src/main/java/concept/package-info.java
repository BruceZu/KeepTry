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
 *
 *         src                                                  Junit test class
 *        -------------------------------------------------------------------------------------------
 *        Set empty                                   |  test emptyTest()
 *        coding:  data structure  boolean isEmpty;   |
 *     2                                              |    add add() method, add test case in emptyTest()
 *                                                    |    Set empty
 *                                                    |    Set one
 *                                                    |    Set many
 *         update add() using   isEmpty               |
 *                                                    |    test sizeTest()
 *                                                    |    assert(true, many.size()>1);
 *                                                    |
 *        add size()                                  |
 *        replace the
 *                  'boolean isEmpty'
 *        with
 *                  'int numberOfElements'
 *        update add() method;
 *                                                     |
 *     3                                               |    refactoring: abstract out the  Set empty, one and many to setup();
 *     4 further more
 *                                                     |  test containsTest()
 *                                                     |
 *
 *        Object[] elements with a default capacity
 *        contains(Object) method, add loop in it check .equal();
 *
 *     5 continue:                                      |  testNotAddDuplicatedElement()
 *
 *           update add():
 *           if contains() ...
 *           else: elements[numberOfElements++] = newValue;
 *
 *     6                                                |  testCapacity():
 *                                                      |   need  add a constructor( int capacity)
 *                                                      |   exception: outOfArrayIndex
 *
 *           refactoring the old constructor to call the
 *           new one;
 *
 *           update the add() to add checking capacity logic
 *           if(size()==elements.length){
 *               Object[] newArray = new Object[elements.length*2];
 *               System.arraycopy.(elements,0,newArray,newArray.length);
 *               elements = newArray;
 *           }
 *                                                      |  corner case: the default capacity is 0;
 *           so upate it to be
 *              Object[] newArray = new Object[elements.length*2  + 1];
 *
 *      7                                               |  removeTest()
 *                                                      | create Set Object in the removeTest()
 *         add method remove()
 *         make a loop to check if it exists
 *           elements[i] = elments[numberOfElements-1];
 *           elements[numberOfElements] = null;
 *           numberOfElements--; // minus minus
 *
 *
 *   ask "what do you feel?"
 *
 * @see <a href="https://en.wikipedia.org/wiki/Test-driven_development">wiki</a>
 * @see <a href="http://stackoverflow.com/questions/8162423/what-is-positive-test-and-negative-test-in-unit-testing">positive test and negative test-</a>
 */
package concept;