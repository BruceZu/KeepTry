//  Copyright 2018 The KeepTry Open Source Project
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

package dp.hoteltrip;

/**
 * <pre>
 * You are going on a long trip. You start along the road at mile post 0.
 * Along the road that you wil travel there are n hotels at mile posts a1 < a2 < ... < an
 * ( ai is measured from the start of the trip).
 * When you choose to stop, you must stop at one of these hotels
 * (but you can choose which hotels you want to stop at).
 * You must stop at the last hotel, which is your destination. You decide that the
 * ideal distance to travel per day is 300 miles (plus or minus a few is ok);
 * so if x is the number of miles traveled in one day,
 * you assign a cost function of (300- x )^ 2 that you want to minimize.
 *
 *
 * (30 points)
 * Design a dynamic programming algorithm to determine your total cost when you choose
 * to stop at those hotels which minimize the total cost function.
 * (Hint: Let Ci be the minimum cost if you were to start at mile 0 and complete your trip at hotel i .)
 *
 * (a) (15 points) Give a recursive rule for computing Ci .
 * (b) (10 points) Explain how you can use dynamic programming to compute Ci in polynomial time.
 * (c) (5 points) Analyze the running time of your dynamic programming algorithm.
 *
 * Let's let hotel 0 denote our starting point. As a basis, let C0 = 0. In general, to compute
 * Ci , we consider all possible places k , 0 <= k < i , that we might have stopped the night before. The
 * cost of having k as the previous stop is the minimum cost of getting to hotel k , followed by the
 * cost of traveling in one day from k to i , a distance of ( ai- ak ). Minimizing over all k gives the
 * following rule:
 *
 *  Ci = min   0 <= k < i    (Ck + (300 - ( ai- ak ))^2)
 *
 * It takes linear time to calculate each value of C , for a total time of O ( n^ 2 ).
 *
 * <pre>
 * One greedy heuristic, it does not work, is each day stop at the hotel up ahead nearest to 300 miles from where
 * you started travel that day. Alas, this doesn't give the best schedule for hotels at mile posts 290,
 * 300, 580, since it's better to stop at 290 the first night
 * (for a total cost of 10^2 + 10^2 = 200)
 * rather than 300 the first night
 * (for a total cost of 0^2 + 20^2 = 400).
 *
 * This can be made to run in linear time, since each day we need only consider hotels up to
 * the next one > 300 miles ahead; and even that hotel will only be considered twice (today and
 * tomorrow).
 */
public class RoadTripHotels {
  public static int costOfRoadTrip(int[] a, int idealdistance) {
    // Todo corner case checking
    Integer[] minCost = new Integer[a.length];
    int[] lastHotel = new int[a.length];

    topDownRecurse(a, minCost, lastHotel, a.length - 1, idealdistance);
    printSeletedHotel(lastHotel);
    return minCost[a.length - 1];
  }

  private static int topDownRecurse(
      int[] a, Integer[] minCostTill, int[] yesterdayHotel, int i, int idealdistance) {
    if (minCostTill[i] != null) return minCostTill[i];
    int minCostOfi = 0;
    int yesterdayHotelToi = -1;
    if (i != 0) {
      minCostOfi = Integer.MAX_VALUE;
      for (int j = 0; j < i; j++) {
        int costIfromJ =
            topDownRecurse(a, minCostTill, yesterdayHotel, j, idealdistance)
                + (int) Math.pow(idealdistance - (a[i] - a[j]), 2);
        if (minCostOfi > costIfromJ) {
          yesterdayHotelToi = j;
          minCostOfi = costIfromJ;
        }
      }
    }
    minCostTill[i] = minCostOfi;
    yesterdayHotel[i] = yesterdayHotelToi;
    return minCostTill[i];
  }

  private static void printSeletedHotel(int[] lastHotel) {
    int days = 0;
    int to = lastHotel.length - 1;
    while (to != -1) {
      System.out.print(to + " <-- ");
      to = lastHotel[to];
      days++;
    }
    System.out.println("need find hotels in the road trip: " + (days-2));
  }

  public static void main(String[] args) {
    System.out.println(costOfRoadTrip(new int[] {0, 5, 7, 10, 14, 19}, 6));
    System.out.println(costOfRoadTrip(new int[] {0, 5, 9, 11, 14}, 5));
    System.out.println(costOfRoadTrip(new int[] {0, 290, 300, 580}, 300));
    System.out.println("===bottom up===");
    System.out.println(bottomUp(new int[] {0, 5, 7, 10, 14, 19}, 6));
    System.out.println(bottomUp(new int[] {0, 5, 9, 11, 14}, 5));
    System.out.println(bottomUp(new int[] {0, 290, 300, 580}, 300));
  }

  public static int bottomUp(int[] a, int idealdistance) {
    // Todo corner case checking
    Integer[] minCostTill = new Integer[a.length];
    int[] yesterdayHotel = new int[a.length];
    minCostTill[0] = 0;
    yesterdayHotel[0] = -1;
    for (int i = 1; i < a.length; i++) {
      int minCostOfi = Integer.MAX_VALUE;
      int lastHotelToi = -1;
      for (int j = 0; j < i; j++) {
        int costIfromJ =
            topDownRecurse(a, minCostTill, yesterdayHotel, j, idealdistance)
                + (int) Math.pow(idealdistance - (a[i] - a[j]), 2);
        if (minCostOfi > costIfromJ) {
          lastHotelToi = j;
          minCostOfi = costIfromJ;
        }
      }
      minCostTill[i] = minCostOfi;
      yesterdayHotel[i] = lastHotelToi;
    }
    printSeletedHotel(yesterdayHotel);
    return minCostTill[a.length - 1];
  }
}
