/**
 * <pre>
 * One form of a multiway search tree that keeps the tree balanced while using small
 * secondary data structures at each node is the (2,4) tree, also known as a 2-4 tree
 * or 2-3-4 tree. This data structure achieves these goals by maintaining two simple
 * properties (see Figure 11.23):
 * Size Property: Every internal node has at most four children.
 * Depth Property: All the external nodes have the same depth.
 *
 *  Two nodes that are children of the same parent are siblings.
 *  A node v is external if v has no children.
 *  A node v is internal if it has one or more children.
 *  External nodes are also known as leaves.
 */
package treesimple.bst._24trees;