package string;

import java.util.Scanner;

/**
 * only space and 26 letter(lower case and upper case)
 * @see <a href="https://www.hackerrank.com/challenges/pangrams">hacker rank</a>
 */
public class HackerrankPangrams {
    public static void main(String[] args) {
        String s = new Scanner(System.in).nextLine();
        int count = 0;
        boolean[] have = new boolean[26];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') { // here
                int index = Character.toLowerCase(s.charAt(i)) - 'a';
                if (!have[index]) {
                    have[index] = true;

                    if (++count == 26) {
                        System.out.println("pangram");
                        return;
                    }
                }
            }
        }
        System.out.println("not pangram");
    }
}
