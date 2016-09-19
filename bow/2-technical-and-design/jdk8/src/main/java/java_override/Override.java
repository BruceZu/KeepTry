package java_override;

public class Override {
    static class Child extends Override {

        static String sf() {
            return "static child";
        }

        private String f(int i) {
            return "child:  method hiding not override f() in parent";
        }
    }

    static String sf() {
        return "static parent";
    }

    private String f(int i) {
        return "Parent";
    }

    public static void main(String[] args) {
        Override child = new Child();
        System.out.println(child.f(0)); // Parent
        Child reallyChild = new Child();
        System.out.println(reallyChild.f(0));

        System.out.println(Child.sf());
        System.out.println(Override.sf());
    }
}

