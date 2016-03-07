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

public class Charter7 {

    // concurrency, allowing multi processes to share use of a data structure in a thread-safe manner.
    // blocking, a call to retrieve an element from an empty collection waits until some other process inserts an element.
    // Similarly, a call to insert into a full blocking structure must wait until room becomes available.
    //
    //                       Interfaces        |           Properties                   |   Storage
    // Class                 Queue, Deque, List|  Capacity Limit, Thread-Safe, Blocking |  Array, Linked List
    //
    // ArrayBlockingQueue    X      -       -      X(Circularly)    X            X           X         -
    //
    // LinkedBlockingQueue   X      -       -      X                X            X           -         X
    //
    // ConcurrentLinkedQueue X      -       -      -                X            -           -         X
    //
    // ArrayDeque            X      X       -      -                -            -           X         -
    //
    // LinkedBlockingDeque   X      X       -      x                X            X           -         X
    //
    // ConcurrentLinkedDeque X      X       -      -                X            -           -         X
    //
    // ArrayList             -      -       X      -                -            -           X         -
    // LinkedList            X      X       X      -                -            -           -         X
}
