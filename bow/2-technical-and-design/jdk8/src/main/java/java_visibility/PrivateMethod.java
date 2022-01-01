//  Copyright 2021 The KeepTry Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package java_visibility;

public class PrivateMethod {
  private String name;

  public PrivateMethod(String name) {
    this.name = name;
  }

  private String getName() {
    return name;
  }

  public String getYourName(PrivateMethod y) {
    return y.getName();
  }

  public String getYourName(In y) {
    return y.getName();
  }

  /*
  Instances of same class can access private members of each other
  “Access control is compile time feature and checked when you compile your program.
   It is applied at class level and not at instance level.”
   In Java Access modifiers are not only a compile time mechanism, they  are enforced
   at runtime too, because Java also has a runtime typesystem, and it can
   dynamically (at runtime) create classes. So it needs to enforce access at runtime too
   for types it doesn't know about at compile time.
   */
  public static void main(String[] args) {
    PrivateMethod a = new PrivateMethod("a"), b = new PrivateMethod("b");
    System.out.println(a.getYourName(b));
  }

  static class In {
    public String name;

    public In(String name) {
      this.name = name;
    }

    private String getName() {
      return name;
    }
  }
}

class Child extends PrivateMethod {
  public Child(String name) {
    super(name);
  }

  @Override
  public boolean equals(Object o) {
    PrivateMethod other = (PrivateMethod) o;
    //  other.getName(); // does not work
    //  this.getName();  // does not work
    return false;
  }
}

/* https://blogs.oracle.com/javamagazine/post/quiz-yourself-accessibility-of-private-methods-in-java

In the general case, a private member is visible anywhere inside the enclosing top-level curly
braces that surround the declaration. This means that private members in an enclosing class are
visible inside the enclosed classes, and that the private members of enclosed classes are visible
inside the outer classes.

Further, it means that private members of sibling nested classes are visible to all the siblings.
This often causes surprises since it’s natural to expect nested curly braces to create nested
scopes. However, where private members are concerned and when the nested curly braces are part
of the class definitions, this is not the case.

Java SE 11 under “Determining accessibility,”
https://docs.oracle.com/javase/specs/jls/se11/html/jls-6.html#jls-6.6.1
https://docs.oracle.com/javase/specs/jls/se17/html/jls-6.html#jls-6.6.1
which says the following about private members:

A member (class, interface, field, or method) of a reference type, or a constructor
of a class type, is accessible only if the type is accessible and the member or constructor
is declared to permit access:

If the member or constructor is declared public, then access is permitted.
All members of interfaces lacking access modifiers are implicitly public.

Otherwise, the member or constructor is declared private, and access is permitted if and only if
it occurs within the body of the top level type that encloses the declaration of
the member or constructor.

Notice that this phrasing does not mention any of the nested classes; it simply says access
is permitted from inside the body of the top-level type. Since everything in the nested classes
is (by definition) enclosed by the top-level type, access is permitted.

It is decided by type not by object instance.
*/
class B {
  class BB {
    private int m3() {
      return 0;
    }
  }

  public int doIt() {
    return new BB().m3() + 1; // yeah, class can assess inner class object private method
  }
}

class C {
  class C1 {
    private static int m4() {
      return 0;
    }
  }

  class C2 {
    public int doIt() {
      return C1.m4() + 1;
      // C2 and C1 are inner class of a same outer class, C2 can asscess C1 private static
      // method
    }
  }

  public static void main(String[] args) {
    C1.m4();
  }
}
