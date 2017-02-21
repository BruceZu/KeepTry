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

package tree.binarytree;


import java.util.InputMismatchException;


public class TernaryExpressionToBT {
    public static TreeNode convert_loop(char[] expr) {
        if (expr == null || expr.length < 1) {
            return null;
        }
        if (expr.length == 1) {
            return new TreeNode(expr[0]);
        }
        if ((expr.length - 1) % 4 != 0) {
            throw new InputMismatchException("wrong expression");
        }
        int start = 0, end = expr.length - 1;

        TreeNode<Character> root = new TreeNode<>(expr[start]);
        root.right = new TreeNode<>(expr[end]);

        start += 2;
        end -= 2;
        TreeNode<Character> cur = root;

        while (start != end) {
            TreeNode<Character> node = new TreeNode<>(expr[start]);
            node.right = new TreeNode<>(expr[end]);

            cur.left = node;
            cur = node;

            start += 2;
            end -= 2;
        }
        cur.left = new TreeNode(expr[start]);// care
        return root;
    }


    public static TreeNode convert_recursion(char[] expr) {
        if (expr == null || expr.length < 5) {
            return null;
        }
        TreeNode<Character> root = new TreeNode<>(expr[0]);
        root.right = new TreeNode<>(expr[expr.length - 1]);
        root.left = convert(expr, 2, expr.length - 3);
        return root;
    }

    private static TreeNode convert(char[] expr, int start, int end) {
        if (start == end) {
            return new TreeNode<>(expr[start]);
        }
        TreeNode<Character> root = new TreeNode<>(expr[start]);
        root.right = new TreeNode<>(expr[end]);
        root.left = convert(expr, start + 2, end - 2);
        return root;
    }

    public static void main(String[] args) {
        TreeNode.drawingInOrder(convert_recursion("x?a?b:c:y".toCharArray()));
        TreeNode.drawingInOrder(convert_loop("m?x?a?b:c:y:n".toCharArray()));
    }
}
