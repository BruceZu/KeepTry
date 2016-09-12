package string;

import java.util.Scanner;

/**
 * It is guaranteed that  and  consist of lowercase English letters.
 *
 * @see <a href="https://www.hackerrank.com/challenges/making-anagrams">hacker rank</a>
 */
public class HackerrankMakingAnagrams {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String a = s.nextLine();
        String b = s.nextLine();
        int[] times = new int[26];
        for (int i = 0; i < a.length(); i++) {
            times[a.charAt(i) - 'a']++;
        }
        for (int i = 0; i < b.length(); i++) {
            times[b.charAt(i) - 'a']--;
        }
        int r = 0;
        for (int i = 0; i < times.length; i++) {
            r += Math.abs(times[i]);
        }
        System.out.println(r);
    }
}
