//  Copyright 2017 The keepTry Open Source Project
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

package generics;

import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
1>
 Provider<? extend T>
 Consumer<? super T>
2>
T extends A, U extends A,
List<? extends T>  lt
List<? extends A>  la=lt; // okay
la.add(new T()) // NO
la.add(new U()) // NO
la.add(new A()) // NO


 List<U> ul = new ArrayList();
 List<A> al =ul // NO
 al.add(new U()) // YES
 al.add(new T()) // YES
 al.add(new A()) // YES

 Collection<?> c
 for (Object o : a) c.add(o); //  NO

 f(Collection<Object> p){} //
 f(Collection<?> p){} // read only



3> null, Object ,? and T
Collection<?> c = new ArrayList<String>();
c.add(new Object()); // NO
// c is read only
// unknown but is an object.  safe to assign the result of get() to a variable of type Object

4> replace List<Shape> with List<? extends Shape> which is what you really want



*/
public class Generics<objArr> {
  static void fo(Collection<Object> p) {}

  static Collection<?> fx(Collection<?> p) {
    return p;
  }

  static void t() {
    Collection<Object> p = new ArrayList<>();
    fo(p);
    Collection<Integer> i = new ArrayList<>();
    // fo(i); // NO Collection<Object> does not accept  Collection<Integer>
    fx(i); // Collection<?> accept  Collection<Integer>
    fx(p);
    //  p=fx(p); // NO

  }

  List<List<? extends Number>> ll = new ArrayList<List<? extends Number>>();

  private static <E> void privateHelper(List<E> list /*Got int the fly*/, int i, int j) {
    list.set(i, list.set(j, list.get(i)));
  }

  public static void swap(List<?> list, int i, int j) {
    // assign List<?>  to  List<E>
    privateHelper(list, i, j);
    System.out.println(list.toString());
  }

  public static <T> void copy(List<? super T> consumer, List<? extends T> provider) {}

  public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
    T max = list.get(0);
    for (T cur : list) {
      if (cur.compareTo(cur) > 0) {
        max = cur;
      }
    }
    return max;
  }

  public static <T> List<? super T> copy(List<? extends T> src) {
    List<? super T> dest = new ArrayList<>();
    for (T t : src) {
      dest.add(t);
    }
    return dest;
  }

  public static <T> Collection<T> select(Class<T> c, String sqlStatement)
      throws InstantiationException, IllegalAccessException {
    T item = c.newInstance();
    return null;
  }

  public static <T> T writeAll(Collection<T> c, Sink<? super T> s) {
    T last = null;
    for (T t : c) {
      last = t;
      s.flush(last);
    }
    return last;
  }

  public static void main(String[] args) throws ParseException {

    Sink<Object> sink = null;
    Collection<String> c = null;
    String str = writeAll(c, sink);

    Class a = new ArrayList<Integer>().getClass();
    Class b = new ArrayList<String>().getClass();
    System.out.println(a == b); // true

    List<Integer>[] A = new ArrayList[1];
    ArrayList<Integer> l = new ArrayList();
    l.add(1);
    l.add(2);

    A[0] = l;
    for (List<Integer> e : A) {
      for (Integer i : e) {
        System.out.println(i);
      }
    }

    // 4 runtime will erase the generic type
    // illegal
    //        if (listArray instanceof ArrayList<Integer>[]) {
    //            System.out.println("true");
    //        }
    if (A instanceof ArrayList[]) {
      System.out.println("true");
    }
    if (A instanceof ArrayList<?>[]) {
      System.out.println("true");
    }

    swap(l, 0, 1);

    // Type variables don't exist at run time
    Collection cs = new ArrayList<String>();
    // if (cs instanceof Collection<String>) {  ); // NO
    Collection<String> cstr = (Collection<String>) cs; // NO

    List<Integer> li = new ArrayList<>();
    li.add(new Integer(3));

    List<?>[] lsa = new List<?>[10];
    Object o = lsa;
    Object[] oa = (Object[]) o;
    // Correct.
    oa[1] = li;
    // Run time error, but cast is explicit.
    String s = (String) lsa[1].get(0); // java.lang.Integer cannot be cast to java.lang.String
    System.out.println(s);

    List<?>[] lsas = new ArrayList<?>[10];
    //  lsas =new ArrayList<String>[10]; // NO
    lsas[0] = new ArrayList<String>();

    Pair<Integer, Integer>[] intPairArr = new Pair[10];
    Object[] objArr = intPairArr;
    objArr[0] = new Pair("", ""); // should fail, but would succeed
    // at runtime, after type erasure, objArr would have the dynamic type Pair[]

    ArrayList raws = new ArrayList();
    ArrayList<String> strs = new ArrayList();
    raws = strs;
    strs = raws;

    ArrayList<?> any = new ArrayList<Long>();
    any = strs;
    // strs = any; // NO

    Collection<?> cal = new ArrayList<String>();
    List<? extends Number> list = new ArrayList<Long>();
    Comparator<? super String> cmp = new RuleBasedCollator("< a< b< c< d");
  }

  //  Comparable<? super T>;E
  //  API uses a type parameter T as an argument:  (? super T).
  //  API returns T: (? extends T).

  // 5
  // https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.4
  // http://www.angelikalanger.com/GenericsFAQ/JavaGenericsFAQ.pdf

}

interface Sink<T> {
  void flush(T t);
}

class Pair<X, Y> {
  private X first;
  private Y second;

  public Pair(X a1, Y a2) {
    first = a1;
    second = a2;
  }

  public X getFirst() {
    return first;
  }

  public Y getSecond() {
    return second;
  }

  public void setFirst(X arg) {
    first = arg;
  }

  public void setSecond(Y arg) {
    second = arg;
  }
}

class Name extends Pair<String, String> {

  public Name(String a1, String a2) {
    super(a1, a2);
  }
}
