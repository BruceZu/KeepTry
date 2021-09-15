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

package heap;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ClosestDriversToRestaurant {
  /*
      Given a restaurant geolocation ( longitude / latitude),
      find 3 closest Dashers (drivers) near the restaurant who can be assigned for delivery,
      ordered by their distance from the restaurant.
      In case 2 Dashers are equidistant from the restaurant, use Dasher rating as tiebreaker.

  Each Dasher has 3 properties:

      Dasher ID
      Last known location [x,y]
      Rating (0 - 100). Higher the better

  Assume you have a method GetDashers() which returns a list of all Dashers.

  Input
  Restaurant Location

  Output
  List of 3 nearest Dasher IDs. Example: [11, 14, 17]

  Assume GetDashers() returns a List<Dasher> where Dasher is represented by:

  class Dasher {
      long id;
      Location lastLocation
      int rating;

      public Dasher(long id, Location lastLocation, int rating) {
          this.id = id;
          this.lastLocation = lastLocation;
          this.rating = rating;
      }
  }

  and Location is represented by:

  class Location {
      double longitude;
      double latitude;

      Location(double longitude, double latitude) {
          this.longitude = longitude;
          this.latitude = latitude;
      }
  }

  */
  class Location {
    double longitude;
    double latitude;

    Location(double longitude, double latitude) {
      this.longitude = longitude;
      this.latitude = latitude;
    }
  }

  class Dasher {
    long id;
    Location lastLocation;
    int rating;

    public Dasher(long id, Location lastLocation, int rating) {
      this.id = id;
      this.lastLocation = lastLocation;
      this.rating = rating;
    }
  }

  private List<Dasher> getDashers() {
    return null;
  }
  /*
   ----------------------------------------------------------------------------
   Use minimum heap <[ID, distance, rating]> with size 3
   Skill:
     - After offer a new item, if the heap size>3 then pullout one
     - use dx^2+dy^2 represent the distance
   Note
    - the rating is int assume it is [MIN_VALUE, MAX_VALUE]
    - distance is double because the location is double, so assume the distance is [MIN_VALUE, MAX_VALUE]

   O(NlogK) time  N is dasher number, K is the required the nearest dasher number
   O(K) space
   when k is a fixed number 3
   O(N) time , O(1) space
  */

  Long[] nearest3Dasher(Location restaurant /*restaurant Location*/) {
    // use double type for the array, because location is double, ID is long type. rating is int.
    Queue<double[]> q =
        new PriorityQueue<>(
            (a, b) -> {
              if (a[1] == b[1]) {
                // Select dasher with bigger rating number
                if (b[2] < a[2]) return -1;
                else if (b[2] == a[2]) return 0;
                else return 1;
              } else {
                // Default: sort by distance in ascending order
                if (a[1] < b[1]) return -1;
                else if (a[1] == b[1]) return 0;
                else return 1;
              }
            });

    for (Dasher d : getDashers()) {
      double dx = restaurant.latitude - d.lastLocation.latitude,
          dy = restaurant.longitude - d.lastLocation.longitude;
      q.offer(new double[] {d.id, dx * dx + dy * dy, d.rating});
      if (q.size() > 3) q.poll();
    }

    Long[] r = new Long[q.size()];
    q.toArray(r);
    return r;
  }
}
