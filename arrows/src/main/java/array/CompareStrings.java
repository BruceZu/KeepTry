package array;

public class CompareStrings {
    /**
     * lexicographic ordering refer
     * {@link java.lang.String#compareTo(String)}  }
     */


    /**
     * version like 1.2.3.6
     * version like 1.12.7.11.21
     * @return 0 if they are same, -1 if v1 > v2, 1 if v1< v2
     */
    public static int compareVersion(String version1, String version2) {
        // todo corner cases
        int endIndexInV1 = version1.indexOf(".");
        int endIndexInV2 = version2.indexOf(".");
        int beginIndexInV1 = 0;
        int beginIndexInV2 = 0;
        while (endIndexInV1 != -1 && endIndexInV2 != -1) {
            int currentNumInV1 = Integer.valueOf(version1.substring(beginIndexInV1, endIndexInV1));
            int currentNumInV2 = Integer.valueOf(version2.substring(beginIndexInV2, endIndexInV2));
            if (currentNumInV1 < currentNumInV2) {
                return 1;
            } else if (currentNumInV1 > currentNumInV2) {
                return -1;
            }
            beginIndexInV1 = endIndexInV1 + 1;
            beginIndexInV2 = endIndexInV2 + 1;
            endIndexInV1 = version1.indexOf(".", endIndexInV1 + 1);
            endIndexInV2 = version2.indexOf(".", endIndexInV2 + 1);
        }
        if (endIndexInV1 == -1 && endIndexInV2 == -1) {
            return 0;
        }
        if (endIndexInV1 == -1 && endIndexInV2 != -1) {
            return -1;
        }
        return 1;
    }

    public static void main(String[] args) {
        System.out.println(compareVersion("1.21.3.6", "1.21.3"));
    }
}
