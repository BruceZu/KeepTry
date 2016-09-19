/**
 * <pre>
 *   you can not override a private or static method in Java
 *   you cannot override static method in Java because method overriding is based upon dynamic binding at runtime
 *   and static methods are bonded using static binding at compile time.
 *
 *   Though you can declare a method with same name and method signature in sub class which does look like you can override
 *   static method in Java but in reality that is method hiding.
 *   Java won't resolve method call at runtime and depending upon type of Object which is used to call static method,
 *   corresponding method will be called.
 *
 *   It means if you use Parent class's type to call static method,
 *   original static will be called from patent class, on the other hand if you use Child class's type
 *   to call static method, method from child class will be called.
 *
 *   In short you can not override static method in Java.
 *   If you use Java IDE like Eclipse or Netbeans, they will show warning that static method should be called
 *   using class name and not by using object becaues static method can not be overridden in Java.
 *
 *   We can not override private method in Java, just like we can not override static method in Java.
 *   Like static methods, private method in Java is also bonded during compile time using static binding
 *   by Type information and doesn't depends on what kind of object a particular reference variable is holding.
 *   Since method overriding works on dynamic binding, its not possible to override private method in Java.
 *
 *   private methods are not even visible to Child class, they are only visible and accessible in the class on which
 *   they are declared. private keyword provides highest level of Encapsulation in Java.
 */
package java_override;