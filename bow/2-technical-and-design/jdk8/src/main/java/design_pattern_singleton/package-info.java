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

/**
 * take in account:
 *   1 serialize
 *   2 clone
 *   3 reflection
 *
 * ==================
 * There are many classes in JDK which is implemented using Singleton pattern
 * like java.lang.Runtime which provides getRuntime() method
 * to get access of it and used to get free memory and total memory in Java.
 * <pre>
 * Singleton is regarded as anti pattern because of difficulty it present to mock its behaviour,
 * so if you are TDD practitioner better avoid using Singleton pattern.
 *
 * Any class which you want to be available to whole application
 * and whole only one instance is viable is candidate of becoming Singleton.
 *
 * Another example is a utility classes like Popup in GUI application,
 * if you want to show popup with message you can have one PopUp class on whole GUI application
 * and anytime just get its instance, and call show() with message.
 *
 *
 * Java.awt.Toolkit with getDefaultToolkit()
 * Java.awt.Desktop with getDesktop()
 */
package design_pattern_singleton;