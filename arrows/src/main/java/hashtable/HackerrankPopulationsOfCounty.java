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

package hashtable;

import java.util.*;


public class HackerrankPopulationsOfCounty {

    static class PopulationOfCounty implements Comparable {
        String county; // used for print
        int Populations;

        public PopulationOfCounty(String name, int pop) {
            this.county = name;
            this.Populations = pop;
        }

        public PopulationOfCounty addPopulation(int pop) {
            this.Populations += pop;
            return this;
        }

        // descending order
        public int compareTo(Object oth) {
            PopulationOfCounty o = (PopulationOfCounty) oth;
            if (this.Populations < o.Populations) {
                return 1;
            } else if (this.Populations > o.Populations) {
                return -1;
            }
            return 0;
        }
    }

    static void top5Counties(String[] cities) {
        Map<String, PopulationOfCounty> map = new HashMap();
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            // parse county
            int lastCom = city.lastIndexOf(",", city.length() - 1);
            int secondLastCom = city.lastIndexOf(",", lastCom - 1); // care
            String county = city.substring(secondLastCom + 1, lastCom).trim(); // substring is [)
            // parse population
            int pop = Integer.valueOf(city.substring(lastCom + 1).trim());

            PopulationOfCounty cp = map.get(county);
            map.put(county, cp == null ? new PopulationOfCounty(county, pop) : cp.addPopulation(pop));
        }

        // sort
        List<PopulationOfCounty> result = new ArrayList(map.values());
        Collections.sort(result);

        // print top 5
        for (int i = 0; i < Math.min(5, result.size()); i++) {
            System.out.println(result.get(i).county);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int cityNum = Integer.parseInt(in.nextLine().trim());
        String[] cities = new String[cityNum];
        String city;
        //String format:  <county><city/town>,<county county>,<populations number>
        // E.g.     San Jose, Santa Clara, 120000
        for (int i = 0; i < cityNum; i++) {
            try {
                city = in.nextLine();
            } catch (Exception e) {
                city = null;
            }
            cities[i] = city;
        }
        top5Counties(cities);
    }
}
