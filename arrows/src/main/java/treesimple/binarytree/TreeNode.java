package treesimple.binarytree;

import static java.lang.Math.max;

public class TreeNode<E> {
    private static void allocate(TreeNode<Object> n /* start from root */,
                                 TreeNode<Object> parent,
                                 Object[][] out,
                                 int[] maxLengthOfValues,
                                 int y, int[] x) {
        TreeNode left = n.left;
        if (left != null) {
            allocate(left, n, out, maxLengthOfValues, y + 2, x);
        }
        Object v = n.v;
        out[y][x[0]] = v;
        if (parent != null) {
            if (parent.left == n) {
                if (isLeaf(n) || out[y - 1][x[0] + 1] != null) {
                    out[y - 1][x[0]] = "/";
                } else {
                    out[y - 1][x[0] + 1] = "/";
                }
            } else {
                if (isLeaf(n) || out[y - 1][x[0] - 1] != null) {
                    out[y - 1][x[0]] = "\\";
                } else {
                    out[y - 1][x[0] - 1] = "\\";
                }
            }
        }
        x[0]++;
        TreeNode right = n.right;
        maxLengthOfValues[0] = max(maxLengthOfValues[0], v.toString().length());
        if (right != null) {
            allocate(right, n, out, maxLengthOfValues, y + 2, x);
        }
    }

    private static int size(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }

    private static boolean isLeaf(TreeNode n) {
        return n.left == null && n.right == null;
    }

    private static int height(TreeNode n) {
        if (n == null || isLeaf(n)) {
            return 0;
        }
        int subTreeHeight_l = 0;
        if (n.left != null) {
            subTreeHeight_l = height(n.left);
        }
        int subTreeHeight_r = 0;
        if (n.right != null) {
            subTreeHeight_r = height(n.right);
        }
        return max(subTreeHeight_l, subTreeHeight_r) + 1;
    }

    E v;
    TreeNode left;
    TreeNode right;

    public TreeNode(E x) {
        v = x;
    }

    public static void drawingInOrder(TreeNode root) {
        if (root == null) {
            System.out.println("tree is null");
            return;
        }
        int high = 2 * height(root) + 1;
        int width = size(root);
        Object[][] XYCoordinates = (Object[][]) new Object[high][width];
        int[] maxLengthOfValues = new int[1];
        allocate(root, null, XYCoordinates, maxLengthOfValues, 0, new int[1]);
        System.out.println("------------------------------------------------------");
        for (int y = 0; y < high; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(" ");
                Object v = XYCoordinates[y][x];
                if (v == null) {
                    System.out.format("%" + maxLengthOfValues[0] + "s", " ");
                } else {
                    System.out.format("%" + maxLengthOfValues[0] + "s", v);
                }
            }
            System.out.println(" ");
        }
    }
}