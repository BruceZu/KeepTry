/**
 * Unless bounds are involved, generic types are invariant with respect to the parameterized type.
 * So you can’t do <strong>covariant</strong> ArrayLists like this:
 * ArrayList<Clazz> ary = new ArrayList<SubClazz>(); // Error!
 */
package generics;
