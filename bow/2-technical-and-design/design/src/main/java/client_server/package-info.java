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

/**
 * // Write Program to create Thread Dead Lock.

 //  you want get some return values from collector thread?
 HashMap  map = new HashMap();

 map= Collections.syhcronizes(map);   // theread safe
 ConcurrentHashMap,  ---> better performance ,  introduce a name segments,  lock all the map ,  only lock part


 //HashMap :
 Array , capacity can be  enlarged
 add  object ,     hash value,  map to index of array.

 [1 , 2 ,3 ,4 ,5 ]
 ---> list to keep the objects  with same hash value 2
 hash value as index of array.

 how to gurante to make hashvalue in the range of index ?

 hash value    8,
  size


 //    how many data table
 //    data





 class TestDeadLock{
 class T extends Thread {
 Object a;
 Object b;

 public T (Object one, Object another){
 this.a = one;
 this.b = another;
 }

 @Override
 void run(){
 shynchronize (a) {
 Thread.sleep(50);
 shynchronize (b) {
 // do some thing;
 }
 }
 }
 }

 public static void main(String[] args){
 Object a,b;
 new T(a, b).start(); // first  a , ---> b
 new T(b, a).start(); // second b , ---> a

 // 1   5 ms
 // 2   priority

 Jstack snapshop  of jvm

 }
 }



 // 1.Give example of many to many relation ship.

 mechandise (produce products and seel in many delears)
 delears   (sell products created by mechandise)




 ///2. Create tables for Many to Many

 table :    depends scenario where how the business logic ,

 product_delears
 [product_ID, delarsId]

 product
 [product_ID, mechandise_id,  name,  price, numbers]

 mechandise
 [mechandise_id,  name, address, telephone,  product_ID]

 delears
 [delarsId, address, name]


 // Restweb service

 // Product Resource Service

 // Client 1, Client 2, Client 3 viewing Product A

 //Client 1 Update some Value sucess

 // Client 2 trying to update and it should not overwrite client 1 update.
 // in sql :   update table A where [condition  to make sure the rows's status he try upate is just same as what he is seeing ]
 // productid,  updated_time indicate the last time it is updated .
 //  active,  when some table  or row is changes ,  give use some hints pop  some information : someting is changes / related to current screen.
 //  reviewer is read code line of some class ,  at the same time the owner of this class pushed a new commit, Gerrit give a hint to the reviewer, "please udpate the page.."
 //


 //  how to know which clients is using the updated information
 //  complicated
 //conflict resoultion,   this mutithread environment,  where we need lock
 // e.g.  booking house room ,  book  a room  at the same time another one book this same room ?
 // 1 2 get update the db  at the same time,  we can have the one one get resouce ,  trascations. then can upadate in order
 //  room [ status :  booked/ empty ];   update romm there  [add condition:   booked==empty]
 //  exception  ---->    clinet ; hint" sorry room is booked"
 //


 Node js .   Polymer based on Node Js


 */
package client_server;