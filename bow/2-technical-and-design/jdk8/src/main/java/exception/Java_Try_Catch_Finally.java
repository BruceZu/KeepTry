package exception;

public class Java_Try_Catch_Finally {
    // finally block will execute even if you put a return statement in the try block or catch block
    // but finally block won't run if you call System.exit() from try or catch block.
    public static void main(String[] args) {
        try {
            // return;
            throw new NullPointerException("");

        } catch (Exception e) {
            return;
            // System.exit(0);

        } finally {
            System.out.println("I run even there is return in try or catch block \n" +
                    "I will not run if it is instead System.exit(0)");
        }
    }
}


