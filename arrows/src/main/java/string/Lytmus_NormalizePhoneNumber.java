package string;

public class Lytmus_NormalizePhoneNumber {

    /**
     * Normalize a given phone number.
     * <pre>
     * This method declaration must be kept unmodified.
     *
     * @param phoneNumber: A string containing a phone number.
     * @return String with the normalized phone number if the input phone is
     * valid, or '' otherwise.
     */
    public static String normalizeTelephoneNumber(String phoneNumber) {
        // Write your implementation here

        char[] number = phoneNumber.toCharArray();
        StringBuilder st = new StringBuilder();
        int digitSize = 0;
        for (char c : number) {
            if (Character.isDigit(c)) {
                digitSize++;
                st.append(c);
                if (digitSize == 3 || digitSize == 7) {
                    st.append("-");
                }
                if(digitSize>10){
                    return "";
                }
            }
        }
        if (digitSize != 10) {
            return "";
        }
//        st.insert(3, "-");
//        st.insert(7, "-");
        return st.toString();
    }

    // This tests your code with the examples given in the question,
    // and is provided only for your convenience.
    public static void main(String[] args) {
        String[] phoneNumbers = {"(650) 555-1234", "65.0555.1234",
                "65/05/5512/34", "(650) 555-1234 x111",
                "(650) 555-123"};
        for (String phoneNumber : phoneNumbers) {
            System.out.println(
                    normalizeTelephoneNumber
                            (phoneNumber));
        }
    }
}