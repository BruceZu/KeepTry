/**
 * <pre>
 * does not have any mutating methods.
 * can't have objects (instances)
 * cannot be changed after construction,it cannot have its field values changed.
 * make it final and all the fields private and final (without setters),don't have to worry about things like thread safety.
 *
 * Declare the class as final so it can’t be extended.
 * Make all fields private so that direct access is not allowed.
 * Don’t provide setter methods for variables
 * Make all mutable fields final so that it’s value can be assigned only once.
 * Initialize all the fields via a constructor performing deep copy.
 * Perform cloning of objects in the getter methods to return a copy rather than returning the actual object reference.
 *
 * The Sun (Oracle) documentation has an excellent checklist on how to make an immutable object.
 *
 * Don't provide "setter" methods — methods that modify fields or objects referred to by fields.
 * Make all fields final and private.
 * Don't allow subclasses to override methods. The simplest way to do this is to declare the class as final.
 * A more sophisticated approach is to make the constructor private and construct instances in factory methods.
 * If the instance fields include references to mutable objects, don't allow those objects to be changed:
 * Don't provide methods that modify the mutable objects.
 * Don't share references to the mutable objects. Never store references to external, mutable objects passed to the constructor;
 * if necessary, create copies, and store references to the copies. Similarly, create copies of your internal mutable objects
 * when necessary to avoid returning the originals in your methods.
 * From: http://download.oracle.com/javase/tutorial/essential/concurrency/imstrat.html
 *
 * In order for your class to be truly immutable, it must meet the following cirteria:
 *
 * All class members are declared final.
 * All variables used in a class at the class level must be instantiated when the class is constructed.
 * No class variable can have a setter method.
 * This is implied from the first statement, but want to make it clear that you cannot change the state of the class.
 * All child object must be immutable as well, or their state never changed in the immutable class.
 * If you have a class with mutable properties, you must lock it down. Declare it private, and ensure you never change it's state.
 * To learn a little more take a look at my blog post: http://keaplogik.blogspot.com/2015/07/java-immutable-classes-simplified.html
 *
 * immutable class is a class which once created, it’s contents can not be changed.
 * Immutable objects are the objects whose state can not be changed once constructed. Example- String & all java wrapper classes.
 * Mutable objects are the objects whose state can be changed once constructed.
 *
 * cases:
 * String; LocalDate, LocalTime and LocalDateTime classes (since 1.8)
 *
 * All primitive wrapper classes (such as Boolean, Character, Byte, Short, Integer, Long, Float, and Double) are immutable.
 *
 * Money and Currency API (slated for Java9) should be immutable, too.
 *
 * Incidentally, the array-backed Lists (created by Arrays.asList(myArray)) are structurally-immutable.
 * @see <a href = "http://stackoverflow.com/questions/5124012/examples-of-immutable-classes"></a>
 */
package immutable;