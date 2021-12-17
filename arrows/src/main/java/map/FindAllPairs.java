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

package map;

public class FindAllPairs {
  /*
    Question 1 from ServiceNow
    find all pairs in an integer array， pair sum == target value，target is integer,
    each element can be used at most once.
    input array [1,2,1,2,3] target 3
    output  [1,2][1,2]
    My solution is O(n) time and space with a map<element, index list>

    Follow up:
       when the input array too big to be consumed in memory how to handle it?
       my solution is to sort it then use 2 pointers from both said, o(nlogn) time and O(N) space.
  */

  /*
   Question 2 from Palo Alto network
   find all possible pairs in a positive integer array， pair sum == target value，target is integer
   input [3,5,1,7,4] sum =8 output {3,5},{1,7}
   input [1,1,7] sum =8 output {1,7},{1,7}
   input [1,1,7,7] sum =8 output {1,7},{1,7},{1,7},{1,7}
   input [5,5,5] sum =10 output 3 pairs {5_0,5_1} {5_0,5_2} {5_1,5_2}   n*(n-1)/2
   input [1] sum =10 output {}
   input [] sum =10 output {}


   HashMap<key, [first index, count]>
   for current key
   if(map.contains(sum-k)}
     if (map.get(sum-k)[0]< map.get(k)[0]{
       // calculate the pairs:
       // pairs number = map.get(sum-k)[1] * map.get(k)[1]
     }else if(key + key ==sum && map.get(k)[1]>1){
       // calculate the pairs:
       // m =  map.get(k)[1];
       // pairs number = m*(m-1)/2
     }
   }

  // O(N^2) runtime and space
   Follow up:
      need not change the algorithm or code?
       - when the element value extend to negative value? no
       - the type of element  => double?
       - the type of element => BigInteger? no
       - it is not array, the element distributed in many files
         each files unique element can not be consumed in memory
         in DB(which type of DB? write intensive or read intensive? accept lost some data?)
         Assume use MySql:
             need a table (key (unique constrains | primary key), index, count)
             index is based on the handled index
             key, index, count are all BigInteger
        assume 20 machine, each machine 4 cpu
        in total we have 10000 files
           20*4 files can be handled at the same time with MapReduce or Spark?
        how long it will need to finishing convert file to the map table in DB?
        Once the map table is ready to apply the above algorithm in `SQL Stored Procedures`
        create a service and UI page and end  global user from all the work to access it. Assume we have 5 member like you,
        we can use any existing software and cloud service, how many times we can get it ready? weeks? months? years?
        - Design System
            UI
            backend
              storage
            Network
            Data center
              hardware: disk|RAM
            DNS, Certificate, public IP.

        - develop
        - test
        - deploy: cloud platform selection| hybrid?
        ....
  */
}
