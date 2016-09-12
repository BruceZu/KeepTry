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

package treesimple.binarytree;

import java.util.Stack;

/**
 * <pre>
 * valid a given char array is the result of go through a binary tree in pre_order.
 * The way should not construct a binary tree, e.g.
 * "9,3,4,#,#,1,#,#,2,#,6,#,#", ＃ is null node, is a binary tree as,
 *
 *                 9
 *              /    |
 *            3       2
 *           /  |   /  |
 *         4    1   #   6
 *        / |  / \     /  |
 *       #  #  #  #    #  #
 */
public class ValidPreOrderArray {
    private static final char END = '#';
    private static int i = 0;// Index of next char wait check
    private static char[] ar;

    /**
     * <pre>
     *  According to pre-order.
     *  Assume it is valid:
     *    Keep left children in stack
     *    Till to the left bottom left #.
     *    Turn to right child to valid the right sub binary tree.
     *  If it is a valid right sub binary tree, then  according to pre-order go to valid right sub binary tree
     *  start with parent's right sibling.
     *
     *  Check points:
     *    before recursively valid:
     *    pop top element of 'lefts', current node's parent.
     *    if the 'lefts' is empty and all elements is checked, then the input char array is valid
     *    else not valid:
     *        1 still there is elements left
     *        2 still need element to check, meet ArrayIndexOutOfBoundsException.
     */
    private static boolean isBinaryTreeStart() {
        if (ar[i] == END) {
            i++;
            return true;
        }

        Stack lefts = new Stack();
        while (ar[i] != END) {
            lefts.push(ar[i++]);
        }
        i++;

        while (true) {
            if (isBinaryTreeStart()) {
                lefts.pop();
                if (lefts.empty()) {
                    return true;
                }
                continue;
            }
            return false;
        }
    }

    public static boolean valid(char[] in) {
        ar = in;
        if (ar == null) {
            return true;
        }

        i = 0;
        try {
            return isBinaryTreeStart() && i == ar.length;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}
