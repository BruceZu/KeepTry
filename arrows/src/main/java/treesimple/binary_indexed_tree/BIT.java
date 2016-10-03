package treesimple.binary_indexed_tree;

import java.util.Arrays;

/**
 * Note: idx: index of binary indexed tree, start from 1;
 */
public class BIT {
    private final int[] bitree;

    private void build(int[] origin) {
        for (int i = 0; i < origin.length; i++) {
            // Note
            add(i, origin[i]);
        }
    }

    BIT(int[] origin) {
        // Note
        bitree = new int[origin.length + 1];
        build(origin);
    }

    public void add(int index, int v) {
        int idx = index + 1; // Note
        while (idx < bitree.length) {
            bitree[idx] += v;
            idx += idx & -idx;
        }
    }

    public int sum(int to) {
        int sum = 0;
        // Note
        int idx = to + 1;
        while (idx > 0) {
            sum += bitree[idx];
            idx -= idx & -idx;
        }
        return sum;
    }

    public int sum(int from, int to) {
        return sum(to) - sum(from - 1);
    }

    /*------------------------common founctions ----------------*/
    @Override
    public String toString() {
        return Arrays.toString(bitree);
    }

    public static void main(String[] args) {
        int[] test = new int[]{1, 2, 3, 4, 5, 5, 4, 3, 2, 1};
        BIT bit = new BIT(test);
        System.out.println(bit.toString());
        System.out.println(bit.sum(0));
        System.out.println(bit.sum(1));
        System.out.println(bit.sum(2));
        System.out.println(bit.sum(3));
        System.out.println(bit.sum(4));
        System.out.println(bit.sum(5));
        System.out.println(bit.sum(6));
        System.out.println(bit.sum(7));
        System.out.println(bit.sum(8));
        System.out.println(bit.sum(9));

        System.out.println(bit.sum(0, 2));
        System.out.println(bit.sum(4, 5));
    }
}
