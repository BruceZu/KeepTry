package array;

import java.util.Scanner;

/**
 * each stick is a positive integer.
 * sticks are left (of non-zero length)
 * calcuate the length of smallest sticks (excluding zero-length sticks)
 * @see <a href="https://www.hackerrank.com/challenges/cut-the-sticks">hacker rank</a>
 */
public class HackerrankCuttheSticks {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int arr[] = new int[n];
        int r = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
            if (arr[i] != 0) {
                r++;
                min = Math.min(min, arr[i]);
            }

        }
        System.out.println(r);
        while (true) {
            r = 0;
            int lastmin = min;
            min = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (arr[i] != 0) {
                    arr[i] -= lastmin;
                    if (arr[i] != 0) {
                        min = Math.min(min, arr[i]);
                        r++;
                    }
                }
            }
            if (r == 0) {
                break;
            } else {
                System.out.println(r);
            }
        }
    }
}
