package treesimple.bst;

/**
 * <pre>
 *
 * The next largest value comes from one of two places:
 * First, if the current node has a right child.
 *              1 move to that right child then,
 *              2 as long as you can see a left child, move to it.
 *
 *              In other words (S and D are the source and destination):
 *
 *                 S2
 *               /               \
 *             1                  7
 *                           /       \
 *                           5       8
 *                       /    \
 *                     D3      6
 *                       \
 *                        4
 *
 *  Otherwise (the current node has no right child).
 *         move up to the parent continuously (so nodes need a right, left and parent pointer)
 *         until the node you moved from was a left child.
 *
 *         if reach root and you still haven't moved up from a left child, your original node was already the highest
 *
 *                1
 *                          \
 *                                   D6
 *                       /             \
 *                    2                  7
 *                          \
 *                           4
 *                         /   \
 *                        3     S5
 */
public class TreeNodeWithP {
    int v;
    TreeNodeWithP parent;
    TreeNodeWithP left;
    TreeNodeWithP right;
}
