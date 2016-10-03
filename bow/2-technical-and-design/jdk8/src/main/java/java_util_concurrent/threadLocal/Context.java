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

package java_util_concurrent.threadLocal;

class Context {
    private static final ThreadLocal<Boolean> FORWARDED_EVENT =
            new ThreadLocal<Boolean>() {
                @Override
                protected Boolean initialValue() {
                    return false;
                }
            };

    private Context() {
    }

    static Boolean isForwardedEvent() {
        return FORWARDED_EVENT.get();
    }

    static void setForwardedEvent(Boolean b) {
        FORWARDED_EVENT.set(b);
    }

    static void unsetForwardedEvent() {
        FORWARDED_EVENT.remove();
    }

    public static void main(String[] args) {
        final int[] count =new int[]{8};
        Runnable r = new Runnable() {

            void trigger() {
                if (!Context.isForwardedEvent()) {
                    // break the infinitely looping events.
                    System.out.println("== triggered create Forward Event for: " + Thread.currentThread().getName());
                    count[0]--;
                }
            }

            @Override
            public void run() {
                while (count[0]>0) {
                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String t = Thread.currentThread().getName();
                    if (t.equalsIgnoreCase("addForwarded") || t.equalsIgnoreCase("deleteForwarded")) {
                        Context.setForwardedEvent(true);
                        System.out.println("precess " + t + " - start");
                        try {
                            Thread.currentThread().sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        trigger();
                        try {
                            Thread.currentThread().sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("precess " + t + " - done");
                        Context.setForwardedEvent(false);

                    } else {
                        System.out.println("precess " + t + " - start");
                        try {
                            Thread.currentThread().sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        trigger();
                        try {
                            Thread.currentThread().sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("precess " + t + " - done");
                    }
                }
            }
        };
        new Thread(r, "addForwarded").start();
        new Thread(r, "deleteForwarded").start();
        new Thread(r, "add").start();
        new Thread(r, "delete").start();
    }
}
