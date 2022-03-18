//  Copyright 2022 The KeepTry Open Source Project
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

package two_pointer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
Fixed window size sliding window
Estimated approach
*/
public class RequestRateLimiter {
  // Map<String URL> , RequestRateLimiterPerAPI limiter>
  static Map<String, RequestRateLimiterPerAPI> proxy = new ConcurrentHashMap<>();

  public boolean offer(String URL) {
    proxy.putIfAbsent(URL, new RequestRateLimiterPerAPI(URL));
    return proxy.get(URL).shouldOffer(URL);
  }
}
/*
 How about the rule also based on customer?
 customer session then per URL
 where to keep the status? end user can access from different nodes
    - distributed cache?
*/
class RequestRateLimiterPerAPI {
  // configuration
  private int WIN_WIDTH = 500; // in milliseconds
  private int LIMIT_REQUESTS = 10; // per window

  //  fixed pre window and current window
  private Long preWStart = null;
  private int preWCounter = 0;

  private Long wStart = null;
  private int wCounter = 0;

  private String api = "";

  public RequestRateLimiterPerAPI(String URL) {
    api = URL;
  }

  private boolean filter(String URL) {
    return api.endsWith(URL);
  }
  // O(1) time and space
  public synchronized boolean shouldOffer(String URL) {
    if (!filter(URL)) return false;
    long req = System.currentTimeMillis();
    if (wStart == null) {
      init(req);
      return true;
    }
    if (inCurrentWin(req)) {
      if (judge(req)) {
        wCounter++;
        return true;
      } else return false;
    }
    //  |-----pre window -----|----- cur window -----]----- next 1st window -----[----- next 2nd
    // window -----]
    // req time >=  window start time + 2* window, in next 2nd window
    if (req - wStart >= 2 * WIN_WIDTH) {
      init(req);
      return true;
    }
    //  window start time + window < req time <  window start time + 2* window
    // in next 1st window
    preWStart = wStart;
    preWCounter = wCounter;

    wStart += WIN_WIDTH;
    if (judge(req)) {
      wCounter++;
      return true;
    } else return false;
  }

  private boolean inCurrentWin(long requestTime) {
    return requestTime - wStart <= WIN_WIDTH;
    //
  }

  // assume req is in current window
  private double percentOfCurrentWin(long req) {
    return (req - wStart) / WIN_WIDTH;
    //
  }

  private void init(long req) {
    wStart = req;
    wCounter = 1;

    preWStart = wStart - WIN_WIDTH;
    preWCounter = 0;
  }
  // assume req is in current window
  private boolean judge(long req) {
    double per = percentOfCurrentWin(req);
    return preWCounter * (1 - per) + wCounter * per <= LIMIT_REQUESTS;
  }
}

/*-----------------------------------------------------------------------------
Not fixed sliding window

Compared with fixed sliding window:
cons: O(m*n) space. M is API number, n is the limitation of request in given time window
pros: accurate result
*/
class RequestRateLimiterSlidingWindow {
  private Map<String, Deque<Integer>> his; // history request access time logs

  public RequestRateLimiterSlidingWindow() {
    his = new ConcurrentHashMap<>();
  }

  public boolean shouldReject(String key, int window, int max) {
    synchronized (key) {
      int currentTime = (int) System.currentTimeMillis() / 1000; // seconds
      int curWstartTime = currentTime - window + 1;
      his.putIfAbsent(key, new LinkedList<>());
      Deque<Integer> logs = his.get(key);
      while (logs.size() > 0 && logs.peekFirst() < curWstartTime) logs.removeFirst();
      if (logs.size() >= max) return true;
      logs.addLast(currentTime);
      return false;
    }
  }

  public static void main(String[] args) throws InterruptedException {
    RequestRateLimiterSlidingWindow test = new RequestRateLimiterSlidingWindow();

    System.out.println(test.shouldReject("device_info", 30, 3));
    System.out.println(test.shouldReject("device_info", 30, 3));
    System.out.println(test.shouldReject("device_info", 30, 3));

    // Return true
    System.out.println(test.shouldReject("device_info", 30, 3));
    System.out.println(test.shouldReject("device_info", 30, 3));
    Thread.sleep(30 * 1000);
    System.out.println(test.shouldReject("device_info", 30, 3));
  }
}
