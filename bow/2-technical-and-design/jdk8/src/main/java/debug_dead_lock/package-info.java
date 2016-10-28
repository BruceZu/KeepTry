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
 * ==cpu==
 * the jstack utility can be used to retrieve a stack trace for each thread in a process.
 * The jstack utility will print deadlocks if they exist:
 * $ jstack -l <VMID>
 * == memory ==
 * 1 the jstat utility to print utilization statistics for each Java generation
 * 2 The visualgc utility is a freely downloadable tool from Sun, which can be used to
 * graphically monitor the following Java runtime
 * subsystems:
 * – Classloader activity
 * – Garbage collection activity
 * – Hotspot compiler activity
 * To use visualgc, you can run visualgc with the process id of the Java process to monitor:
 *
 * == jconsole
 * The jconsole utility can be used to summarize
 * heap utilization, system utilization, class
 * loader activity and much much more
 * Also allows you to enable and disable options
 * on a live JVM, which can be useful for setting
 * profiling flags after startup (jinfo provides
 * similar capabilities from the command line)
 *
 * http://prefetch.net/presentations/DebuggingJavaPerformance.pdf
 */
package debug_dead_lock;