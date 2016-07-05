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

package tree.binarytree.bst;

import java.util.Random;

public class SimpleAddDeleteBST {
    static class Node {
        Integer v;
        Node left, right;

    }

    /**
     * <pre>
     *
     * find pre-order.
     * if it exist, insert the ‘add’ as its left child which has not right child.
     * else: not found. So add the ‘add’ directly there as a new leaf
     *
     * Node:
     *  corner case: root ==null, tree is null
     *  Do not need care about parent
     *
     * a rule. e.g.
     *            left children <= parent;  right children > parent.
     *            then insert it in as a left child which has no right child
     *
     *                        p(it)
     *                         /
     *                        it
     *                      /   \
     *                old left    null
     *                   /\
     *
     *             if, then again add it, then again
     *
     *                          p(it)
     *                           /
     *                          it
     *                         /  \
     *                       it    null
     *                      /   \
     *                old left    null
     *                   /\
     */
    static Node add(Node root, Integer add) {
        if (root == null) {
            Node r = new Node();
            r.v = add;
            return r;
        }
        if (root.v == add) {
            Node left = root.left;
            Node newLeft = new Node();
            newLeft.v = add;
            root.left = newLeft;
            newLeft.left = left;
        } else if (root.v < add) {
            if (root.right != null) {
                add(root.right, add);
            } else {
                root.right = new Node();
                root.right.v = add;
            }
        } else {
            if (root.left != null) {
                add(root.left, add);
            } else {
                root.left = new Node();
                root.left.v = add;
            }
        }
        return root;
    }


    /**
     * <pre>
     *
     * Node:
     *  A> the 'delete' is not leaf: the 'replaced by' maybe left child of parent or right child of parent.
     *
     *
     *       delete                    delete
     *        /                           \
     *    replaced by(left child)      replaced by (right child)
     *     /  \                         /  \
     *    left null                  null right
     *
     *
     *       delete                          delete
     *        /                               \
     *      left                             right
     *     /  \                               /
     *     replace by(right child)     replace by(left child)
     *       / \                            / \
     *   left  null                     null right
     *
     *
     *
     *  B> But if it is leaf, need care the parent. parent maybe null when delete the root which has no children.
     *       p              p
     *      /                \
     *    delete            delete     ==> has not affect on root.
     *    /   \            /  \
     *   null null          null null
     *
     *    delete (root)    => will change the root.
     *    /   \
     *   null null
     *
     *
     *
     * Corner cases:
     *      delete null tree;
     *      delete the root and the root is a leaf. this will affect return root.
     *      delete the node which is a leaf, this need the parent.
     *      delete the node which has one or two child. this case need not parent.  delete = replace:
     *          1 if it has left sub tree, find the left sub-tree's rightest child.
     *            replace the rightest child which its left sub-tree(maybe null)
     *            replace the 'delete'.v with that of the rightest child.
     *          2 else if has right sub tree. find the right sub tree's leftest child.
     *            replace the leftest child with its right sub tree(may be null)
     *            replace the 'delete'.v with that of the leftest child.
     *      does not find the 'delete' after recursion all the tree. do nothing.
     */
    private static void findAndDelete(Node p, Node cur, Integer delete) {
        if (cur.v == delete) {
            if (cur.left != null) {
                Node pre = cur;
                Node I = cur.left;

                while (I.right != null) {
                    pre = I;
                    I = I.right;
                }

                if (pre.left == I) {
                    pre.left = I.left;
                } else {
                    pre.right = I.left;
                }

                cur.v = I.v;

                I.v = null;
                I.left = null;
            } else if (cur.right != null) {
                Node pre = cur;
                Node I = cur.right;
                while (I.left != null) {
                    pre = I;
                    I = I.left;
                }
                if (pre.left == I) {
                    pre.left = I.right;
                } else {
                    pre.right = I.right;
                }
                cur.v = I.v;

                I.v = null;
                I.right = null;

            } else {
                if (p.left == cur) {
                    p.left = null;
                } else {
                    p.right = null;
                }
            }
        } else if (cur.v < delete) {
            if (cur.right != null) {
                findAndDelete(cur, cur.right, delete);
            }
        } else {
            if (cur.left != null) {
                findAndDelete(cur, cur.left, delete);
            }
        }
    }

    static Node delete(Node root, Integer delete) {
        if (root == null
                || root.left == null && root.right == null && root.v == delete) {
            return null;
        }
        findAndDelete(null, root, delete);
        return root;
    }

    //in order
    static void print(Node root) {
        if (root == null) {
            System.out.print("null ");
            return;
        }
        if (root.left != null) {
            print(root.left);
        }
        System.out.print(root.v + " ");
        if (root.right != null) {
            print(root.right);
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        int i = 0;
        Node r = add(null, 7);
        while (i++ < 15) {
            r = add(r, random.nextInt(100));
        }
        print(r);
        System.out.println();
        while (r != null) {
            r = delete(r, random.nextInt(100));
            print(r);
            System.out.println();
        }
    }
}
