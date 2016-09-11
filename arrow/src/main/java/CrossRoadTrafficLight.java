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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;

class Car implements Runnable {
    private static Logger log = LoggerFactory.getLogger(Car.class);
    private CountDownLatch startRun;
    private final CountDownLatch driveOverCarsNumber;

    private final String roadName;
    private final String carNumber;

    Car(CountDownLatch startRun, CountDownLatch driveOverCarsNumber, String roadName, String carNumber) {
        this.startRun = startRun;
        this.driveOverCarsNumber = driveOverCarsNumber;
        this.roadName = roadName;
        this.carNumber = carNumber;
    }

    public void run() {
        try {
            startRun.await();
            driveOverTheCrossRoad();
            driveOverCarsNumber.countDown();
        } catch (InterruptedException ex) {
        }
    }

    void driveOverTheCrossRoad() {
        log.info("              car: " + carNumber + " driver over " + roadName);
    }
}

public class CrossRoadTrafficLight {
    private static Logger log = LoggerFactory.getLogger(CrossRoadTrafficLight.class);

    private final BlockingDeque<String> carsOfARoad = new LinkedBlockingDeque();
    private final BlockingDeque<String> carsOfBRoad = new LinkedBlockingDeque();
    private Future<?> findCarsOnA, findCarsOnB;
    private ExecutorService roadService;

    private void pedestrianOn(String roadName) {
        log.info("pedestrian is walk across road" + roadName);
    }

    private void mockCarsOnCrossRoad() {
        findCarsOnA = roadService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        carsOfARoad.addLast(String.valueOf(ThreadLocalRandom.current().nextInt(600)));
                        Thread.currentThread().sleep(ThreadLocalRandom.current().nextLong(300));
                    }
                } catch (InterruptedException e) {

                }
            }
        });
        findCarsOnB = roadService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        carsOfBRoad.addLast(String.valueOf(ThreadLocalRandom.current().nextInt(600)));
                        Thread.currentThread().sleep(ThreadLocalRandom.current().nextLong(300));
                    }
                } catch (InterruptedException e) {

                }
            }

        });
    }

    private void init() {
        roadService = Executors.newFixedThreadPool(4);
        mockCarsOnCrossRoad();
    }

    private void close() {
        findCarsOnA.cancel(true);
        findCarsOnB.cancel(true);
        roadService.shutdown();
        log.info(" GAME OVER ");
    }

    private void start() throws InterruptedException {

        int keepTest = 0;
        while (keepTest++ < 10) {
            Thread.currentThread().sleep(500);

            int currentCarsOfRoadA = carsOfARoad.size();
            CountDownLatch aRoadGreen = new CountDownLatch(1);
            CountDownLatch ARoadCarsClean = new CountDownLatch(currentCarsOfRoadA);
            for (int i = 1; i <= currentCarsOfRoadA; ++i) {
                roadService.submit(new Car(aRoadGreen, ARoadCarsClean, "A", carsOfARoad.poll()));
            }

            int currentCarsOfRoadB = carsOfBRoad.size();
            CountDownLatch bRoadGreen = new CountDownLatch(1);
            CountDownLatch BRoadCarsClean = new CountDownLatch(currentCarsOfRoadB);
            for (int i = 1; i <= currentCarsOfRoadB; ++i) {
                roadService.submit(new Car(bRoadGreen, BRoadCarsClean, "B ", carsOfBRoad.poll()));
            }

            if (currentCarsOfRoadA != 0) {
                log.info("        pedestrian is walk across road A");
                aRoadGreen.countDown();
                ARoadCarsClean.await();
                log.info("        cars on road A is clean \n\n");
            }

            if (currentCarsOfRoadB != 0) {
                log.info("         pedestrian is walk across road B");
                bRoadGreen.countDown();
                BRoadCarsClean.await();
                log.info("         cars on road B is clean \n\n");
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        CrossRoadTrafficLight test = new CrossRoadTrafficLight();
        test.init();
        test.start();
        test.close();
    }
}
