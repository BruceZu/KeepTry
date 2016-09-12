package array;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @see <a href="https://www.hackerrank.com/challenges/find-digits">hacker rank</a>
 */
public class HackerrankFindDigits {

    private static void calculate(Scanner s) {
        Integer t = s.nextInt(); // here

        char[] digits = String.valueOf(t).toCharArray();
        int r = 0;
        Set<Integer> divisible = new HashSet();
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] == '0') { // here
                continue;
            }
            if (divisible.contains((int) digits[i])) { // care need (int)
                r++;
            } else if (t % (digits[i] - '0') == 0) { // here
                divisible.add((int) digits[i]); // here can just save (int) digits[i]
                r++;
            }
        }
        System.out.println(r);
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int tests = s.nextInt();
        for (int i = 0; i < tests; i++) {
            calculate(s);
        }
    }
}
