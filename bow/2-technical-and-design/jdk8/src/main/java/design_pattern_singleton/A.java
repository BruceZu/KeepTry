package design_pattern_singleton;

class B {
  // http://www.artima.com/insidejvm/ed2/lifetype.html
  // enum instance fields are not "initialized  by a compile-time constant expression".
  // They can't be.

  // because only String and primitive types are possible types for a compile-time constant
  // expression.
  // That means that the class will be initialized when INSTANCE is first accessed
  // (which is  exactly the desired effect).

  // if the field is 'static final', the initialzation of the field is happened
  // at complie-time

  public static final String FOO = "foo";
  public static final Object obj = new Object(); // initialised at runtime

  static {
    System.out.println("initializing B");
  }
}

public class A {
  public static void main(String[] args) {
    System.out.println(B.FOO);
    System.out.println(B.obj);
  }
}
