package java_override;

/**
 * A method signature is part of the method declaration.
 * It is the combination of the method name and the parameter list.
 * <pre>
 * The reason for the emphasis on just the method name and parameter list is because of overloading.
 * It's the ability to write methods that have the same name but accept different parameters.
 * The Java compiler is able to discern the difference between the methods through their method signatures.
 *
 * 2 methods are only difference in return type will not be allowed to pass compile.
 */
public class Overloading {
    private String f(int in) {
        return in + "";
    }

    private String f(String in) {
        return in;
    }

//    private int f(String in) {
//        return 1;
//    }
}
