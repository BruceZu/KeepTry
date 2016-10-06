package string;

/**
 * Note:
 * 1   if needle is empty ""
 * 2   i <=
 * 3   need boolean found
 * //todo:
 * <br><a href= "https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string_search_algorithm">Boyer–Moore string search algorithm </a>
 */
public class Leetcode28ImplementstrStr {
    public static int strStr(String haystack, String needle) {
        if (haystack == null || needle == null || needle.length() > haystack.length()) {
            return -1;
        }

        if (needle.equals("")) { // 1 corner case, special
            return 0;
        }

        for (int i = 0; i <= haystack.length() - needle.length(); i++) { // 2

            if (haystack.charAt(i) == needle.charAt(0)) {
                // can use haystack.substring(i,needle.length());
                boolean found = true;
                for (int j = 0; j < needle.length(); j++) { //
                    if (haystack.charAt(i + j) != needle.charAt(j)) {
                        found = false;
                        break;
                    }
                }
                if (found) { // 3
                    return i;
                }
            }
        }
        return -1;
    }
    /* --------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {
        System.out.println(strStr("", ""));
        System.out.println(strStr("", "a"));
        System.out.println(strStr("a", ""));
        System.out.println(strStr("a", "a"));
        System.out.println(strStr("mississippi", "issip"));
    }
}
