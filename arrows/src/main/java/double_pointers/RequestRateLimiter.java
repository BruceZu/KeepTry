//  Copyright 2021 The KeepTry Open Source Project
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

package double_pointers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestRateLimiter {
  // Map<String URL> , RequestRateLimiterPerAPI limiter>
  static Map<String, RequestRateLimiterPerAPI> proxy = new ConcurrentHashMap<>();

  public boolean offer(String URL) {
    proxy.putIfAbsent(URL, new RequestRateLimiterPerAPI(URL));
    return proxy.get(URL).shouldOffer(URL);
  }
}

class RequestRateLimiterPerAPI {
  private int WIN_WIDTH = 500; // in milliseconds
  private int LIMIT_REQUESTS = 10; // per window

  private int preWCounter = 0;
  private Long preWStart = null;
  private int wCounter = 0;
  private Long wStart = null;
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
