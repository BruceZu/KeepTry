package treesimple.bst;

import java.util.Stack;

public class BSTNextBiggest {
    static class Solution {

        private Stack<TreeNode> track;

        public Solution(TreeNode root) {
            track = new Stack<>();
            TreeNode node = root;
            while (node != null) { // care
                track.push(node);
                node = node.right;
            }
        }

        // whether we have a next biggest number
        public boolean hasNext() {
            return !track.isEmpty();
        }

        //the next biggest number
        public int next() {

            if (!hasNext()) {
                return -1; // ???
            }

            TreeNode node = track.pop();
            int r = node.val;

            node = node.left;
            while (node != null) {
                track.push(node);
                node = node.right;
            }
            return r;
        }
    }

    /*---------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        TreeNode root = TreeNode.testTree();
        Solution i = new Solution(root);
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
