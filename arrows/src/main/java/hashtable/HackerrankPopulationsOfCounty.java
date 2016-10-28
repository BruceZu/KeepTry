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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class HackerrankPopulationsOfCounty {

    static class CountyWithPopulations implements Comparable {
        String name;
        int pop;

        public CountyWithPopulations(String name, int pop) {
            this.name = name;
            this.pop = pop;
        }

        public CountyWithPopulations addVote(int pop) {
            this.pop += pop;
            return this;
        }

        public int compareTo(Object oth) {
            CountyWithPopulations o = (CountyWithPopulations) oth;
            if (this.pop < o.pop) {
                return 1;
            } else if (this.pop > o.pop) {
                return -1;
            }
            return 0;
        }
    }

    static void top5Counties(String[] cities) {
        Map<String, CountyWithPopulations> map = new HashMap();
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            int lastCom = city.lastIndexOf(",", city.length() - 1);
            int secondLastCom = city.lastIndexOf(",", lastCom - 1); // care
            String country = city.substring(secondLastCom + 1, lastCom);
            int pop = Integer.valueOf(city.substring(lastCom + 1));
            CountyWithPopulations cv = map.get(country);
            if (cv == null) {
                map.put(country, new CountyWithPopulations(country, pop));
            } else {
                map.put(country, cv.addVote(pop));
            }
        }
        List<CountyWithPopulations> result = new ArrayList(map.values());
        Collections.sort(result);
        for (int i = 0; i < Math.min(5, result.size()); i++) {
            System.out.println(result.get(i).name);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int _cities_size = 0;
        _cities_size = Integer.parseInt(in.nextLine().trim());
        String[] _cities = new String[_cities_size];
        String _cities_item;
        //String format:  <name><city/town>,<county name>,<populations number>
        for (int _cities_i = 0; _cities_i < _cities_size; _cities_i++) {
            try {
                _cities_item = in.nextLine();
            } catch (Exception e) {
                _cities_item = null;
            }
            _cities[_cities_i] = _cities_item;
        }
        top5Counties(_cities);
    }
}


