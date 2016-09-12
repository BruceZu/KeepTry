//  Copyright 2016 The Sawdust Open Source Project
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
/**
 * <pre>
 *
 * Exception
 * 1 what is ?
 * 2 difference?
 * 3 how to process them
 *
 * =1==
 * Checked   - Prone to happen. Checked in Compile time.
 * UnChecked - Due to Bad data. Checked in Run time.
 *
 * Checked Exception:
 * that can be thrown by a method is part of the method's public programming interface.
 * Those who call a method must know about the exceptions that a method can throw so that they can decide what to do about them.
 * These exceptions are as much a part of that method's programming interface as its parameters and return value.
 *
 *
 * unchecked:
 * All subclass of <strong> RuntimeException  +  Error </strong>.
 * An Error is a subclass of Throwable that indicates serious problems that a reasonable application should not try to catch.
 * Most such errors are abnormal conditions.
 * E.g. The ThreadDeath error, though a "normal" condition, is also a subclass of Error because most applications should not try to catch it.
 *
 * A method is not required to declare in its throws clause any subclasses of Error that might be thrown during the execution of the method
 * since these errors are abnormal conditions that should never occur. That is, Error and its subclasses are regarded as unchecked exceptions
 * for the purposes of compile-time checking of exceptions.
 *
 * checked:
 * All other Exceptions + Throwables are checked.
 * The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions.
 * Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution of the method
 * or constructor and propagate outside the method or constructor boundary.
 *
 * Throwable and any subclass of Throwable that is not also a subclass of either <strong> RuntimeException or Error </strong> are regarded as checked exceptions.
 *
 * =3== how to process:
 * Checked exception:  Java enforce a catch or declared requirement for checked exceptions
 *
 * Unchecked Exceptions: RuntimeExceptions as a general rule should <strong> not</strong> be try-catched.
 * They generally signal a programming error and should be left alone.
 * Instead the programmer should check the <strong> error condition </strong> before invoking some code which might result in a RuntimeException。
 * <strong>But</strong> there are times when pre-invocation error checking is not worth the effort。
 * Runtime exceptions represent problems that are the result of a <strong>programming problem</strong>, and as such, the API client code cannot reasonably
 * be expected to recover from them or to handle them in any way. Such problems include arithmetic exceptions;
 * pointer exceptions and indexing exceptions
 * </pre>
 */
package exception;