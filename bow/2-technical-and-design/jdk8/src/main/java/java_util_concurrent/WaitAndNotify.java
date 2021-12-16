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

package java_util_concurrent;

import java.util.Stack;

import static java.lang.Thread.sleep;

/**
 * 2 stacks work as a queue more consumers more producers make the running time of get be O(1).
 * blocking when there is no message.
 */
public class WaitAndNotify {
  public static void main(String args[]) {
    final int FULL_SIZE = 2;
    final Stack in = new Stack();
    final Stack out = new Stack();
    final Object monitor = new Object();
    final Object lockAllGet = new Object();
    final Object lockAllPut = new Object();

    Runnable put =
        new Runnable() {
          @Override
          public void run() {
            try {
              while (true) {
                putMessage();
              }
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }

          private void putMessage() throws InterruptedException {
            synchronized (lockAllPut) {
              // wait out is empty then do move from in to out stack
              while (in.size() == FULL_SIZE) { // while not if
                synchronized (monitor) {
                  monitor.wait();
                }
                moveAll();
              }

              synchronized (in) {
                in.push(new java.util.Date().toString());
                System.out.println("added");
              }
            }
          }

          private void moveAll() {
            synchronized (in) { // that is why use lockAllPut and lockAllGet
              synchronized (out) {
                while (!in.empty()) {
                  out.push(in.pop());
                }
                System.out.println("moved all data from in to out");
              }
            }
          }
        };

    Runnable get =
        new Runnable() {
          @Override
          public void run() {
            try {
              while (true) {
                getMessage();
              }
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }

          private synchronized String getMessage() throws InterruptedException {
            synchronized (lockAllGet) {
              while (out.empty()) { // while not if
                synchronized (monitor) {
                  monitor.notify();
                }
                sleep(10l);
              }

              String message = (String) out.peek();
              out.pop();
              System.out.println("get");
              return message;
            }
          }
        };

    new Thread(put).start();
    new Thread(put).start();

    new Thread(get).start();
    new Thread(get).start();
    new Thread(get).start();
  }
}
