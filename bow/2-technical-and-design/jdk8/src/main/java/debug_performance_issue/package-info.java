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
 *
 *  == resource and heap or GC tuning.
 *
 *  == bottlenecks profiler - bottle neck
 *   In most cases (particularly if the performance problem is easy to reproduce),
 *   a profiler is the most effective tool for that,
 *   as it will give you detailed statistics on execution time,
 *   breaking it down to single methods
 *
 *
 *  hook up a profiler to investigate GC/CPU perf and memory consumption
 *  VisualVM is good and free
 *  == Deal with the hot spots one at a time

 */
package debug_performance_issue;