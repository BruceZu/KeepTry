/**
 * <pre>
 * a red-black tree is a binary search tree with nodes
 * colored red and black in a way that satisfies the following properties:
 *
 *      Root Property:     The root is black.
 *      External Property: Every external node is black.
 *      Red Property:      The children of a red node are black. implies that on any path from the root to a leaf,
 *                         red nodes must not be adjacent.However, any number of black nodes may appear in a sequence.
 *      Depth Property:    All external nodes have the same black depth, defined as the
 *                         number of proper ancestors that are black.
 *
 *
 *  @see <a href="https://www.cs.auckland.ac.nz/software/AlgAnim/red_black.html">red-black tree</a>
 *  @see <a href="https://www.cs.auckland.ac.nz/software/AlgAnim/red_black.html">wiki</a>
 */
package treesimple.bst.redblacktree;