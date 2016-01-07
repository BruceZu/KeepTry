//  Copyright 2016 The Minorminor Open Source Project
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

package charter3.exercises;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/*
Suppose you are designing a multiplayer game that has n ≥ 1000 players, numbered
1 to n, interacting in an enchanted forest. The winner of this game is the
first player who can meet all the other players at least once (ties are allowed).
Assuming that there is a method meet(i, j), which is called each time a player i
meets a player j (with i 6= j), describe a way to keep track of the pairs of meeting
players and who is the winner.
 */


public class C323 {
    private static Logger log = LoggerFactory.getLogger(Player.class);

    class Player {
        private Logger log = LoggerFactory.getLogger(Player.class);
        private int id;
        private Set<Integer> metPlayers;
        private int aim;

        public Player(int id, int totalPlayerNumber) {
            this.id = id;
            this.aim = totalPlayerNumber - 1;
            metPlayers = Collections.newSetFromMap(new ConcurrentHashMap(aim));
        }

        public void met(int playerId) {
            metPlayers.add(playerId);
            if (isWinner()) {
                running = false;
                log.info(String.format("**** %s win by the time %s ****",
                        id,
                        new SimpleDateFormat("MMM/dd/yyyy HH:mm").format(new Date(System.currentTimeMillis()))));
            }
        }

        public boolean isWinner() {
            return metPlayers.size() == aim;
        }

        boolean haveMet(Integer id) {
            return metPlayers.contains(id);
        }
    }

    private int playerNumbers;
    private Map<Integer, Player> players;
    private volatile boolean running = true;

    private void meeting(Player me, Player met) {
        me.met(met.id);
        met.met(me.id);
        //System.out.println(String.format("track: %s met %s",me.id ,met.id));
    }

    private int mockMeetNext(Player me) {
        int next;
        do {
            ThreadLocalRandom r = ThreadLocalRandom.current();
            next = r.nextInt(playerNumbers);

        } while (me.haveMet(next));
        return next;
    }

    public C323(int numbers) {
        playerNumbers = numbers;
        players = new HashMap(playerNumbers);
        for (int i = 0; i < playerNumbers; i++) {
            players.put(i, new Player(i, playerNumbers));
        }
    }

    public void play() {
        final CountDownLatch startPlay = new CountDownLatch(1);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                int myId = Integer.valueOf(threadName.substring(threadName.lastIndexOf("-") + 1));
                Player me = players.get(myId);

                try {
                    startPlay.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (running) {
                    meeting(me, players.get(mockMeetNext(me)));
                }
            }
        };
        for (int i = 0; i < playerNumbers; i++) {
            new Thread(r, "C323 Enchanted Forest-" + i).start();
        }
        startPlay.countDown();
    }

    public static void main(String[] args) {
        new C323(2000).play();
    }
}
