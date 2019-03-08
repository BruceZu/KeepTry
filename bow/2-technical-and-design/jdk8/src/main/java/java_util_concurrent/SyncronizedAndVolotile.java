//  Copyright 2019 The KeepTry Open Source Project
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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SyncronizedAndVolotile {
    private static AtomicLong atomicLong = new AtomicLong(0);
    private static boolean common;
    private static volatile boolean valotiled;
    private static boolean synced;

    public static synchronized void changeSynced() {
        synced = !synced; // with 'syncronized' to make sure atomic operation and visibility
    }

    public static synchronized boolean getSyncincrease() {
        return synced; // read also need synchronized
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable[] rarray =
                new Runnable[] {
                    () -> {
                        while (atomicLong.get() != 0) {}
                        System.out.println("end atomicLong");
                    },
                    () -> {
                        while (!common) {}
                        System.out.println("end common");
                    },
                    () -> {
                        while (!valotiled) {}
                        System.out.println("end valotiled");
                    },
                    () -> {
                        while (!getSyncincrease()) {}
                        System.out.println("end synced");
                    }
                };

        for (Runnable r : rarray) {
            Thread thread = new Thread(r);
            thread.start();
        }

        TimeUnit.SECONDS.sleep(4);

        common = !common;
        valotiled = !valotiled;
        changeSynced();
        atomicLong.getAndIncrement();

        System.out.println("\ncommon: " + common);
        System.out.println("valotiled: " + valotiled);
        System.out.println("synced: " + getSyncincrease());
        System.out.println("atomic: " + atomicLong.get());
        System.out.println("\nBut the thread with common is still false and not end yet");
    }
}
