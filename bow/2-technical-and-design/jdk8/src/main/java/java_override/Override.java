package java_override;

public class Override {
    static class Child extends Override {

        static String sf() {
            return "static child";
        }

        private String f(int i) {
            return "child:  method hiding not override f() in parent";
        }

        // note the return type is required to be also same as that in parent
        public String overrided() {
            return "child";
        }
    }

    static String sf() {
        return "static parent";
    }

    private String f(int i) {
        return "Parent";
    }

    public String overrided() {
        return "parent";
    }

    public static void main(String[] args) {
        Override child = new Child();
        System.out.println(child.overrided());

        //
        System.out.println(child.f(0)); // Parent

        Child reallyChild = new Child();
        System.out.println(reallyChild.f(0));

        System.out.println(Child.sf());
        System.out.println(Override.sf());
    }
}

