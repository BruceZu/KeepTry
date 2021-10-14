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

package array;

import java.util.*;

public class Verification {
  /*
  Question comes from https://www.1point3acres.com/bbs/thread-802430-1-1.html
     each line:
     merchant_id,field_name,field_value'
     merchant_id: unique
     field_name: country:US|JP|FR
                 business_type: individual|company
                 or one of the values from the table below such as
                 first_name, support_email. need not validate field_name
     more info may be provided than is required for verification
    all input are valid
    input line maybe in any order. inputs for different merchants may also be interleaved

    individual table
       fields   country1  country2  country3
                  US        JP        FR
        SSN        x
        tax_id               x         x
    companies table
       fields   country1  country2  country3
                    US        JP        FR
      director_name                      x

  Output
    print one line per merchant_id in lexicographical order
    if all required fields have been provided according to country and business_type
    print
    merchant_id:VERIFIED
    otherwise
    merchant_id:UNVERIFIED:fieldname1,fieldname2,...
    comma separated list of all the required fields not provided for the merchant in lexicographical order
     - any or both of country and business_type
     - those in related tables

   Ex1:
    input
     1
     acct_123,business_type,company
    output
     acct_123:UNVERIFIED:country

   Ex2:
   input:
    8
    acct_123,country,US
    acct_123,business_type,individual
    acct_123,first_name,Jane
    acct_123,last_name,Doe
    acct_123,date_of_birth,01011970
    acct_123,social_security_number,123456789
    acct_123,email,test@example.com
    acct_123,phone,555555555
   output:
    acct_123:VERIFIED

   EX3:
    input:
    12
    acct_123,tax_id_number,12345689
    acct_123,country,FR
    acct_123,business_type,company
    acct_456,business_type,individual
    acct_456,country,JP
    acct_456,first_name,Mei
    acct_456,last_name,Sato
    acct_456,first_name_kana,Mei
    acct_456,last_name_kana,Sato
    acct_456,data_of_birth,01011970
    acct_456,tax_id_number,123456
    acct_456,email,test@example.com

    output
    acct_123:UNVERIFIED:director_name,name,phone
    acct_456:VERIFIED


      */
  // assume it is available,
  // key is country value+" "+business_type,
  // values are required fields sorted in lexicographically order
  private static Map<String, List<String>> required = new HashMap<>();

  static {
    // country individual
    List<String> fs =
        Arrays.asList(
            "first_name", "last_name", "date_of_birth", "social_security_number", "email", "phone");
    Collections.sort(fs);
    required.put("US individual", fs);

    fs =
        Arrays.asList(
            "first_name",
            "last_name",
            "first_name_kana",
            "last_name_kana",
            "date_of_birth",
            "tax_id_number",
            "email");
    Collections.sort(fs);
    required.put("JP individual", fs);

    fs = Arrays.asList("first_name", "last_name", "tax_id_number", "email", "phone");
    Collections.sort(fs);
    required.put("FR individual", fs);
    // country company
    fs = Arrays.asList("name", "employer_id_number", "email", "phone");
    Collections.sort(fs);
    required.put("US company", fs);

    fs = Arrays.asList("name", "tax_id_number", "phone");
    Collections.sort(fs);
    required.put("JP company", fs);

    fs = Arrays.asList("name", "director_name", "tax_id_number", "phone");
    Collections.sort(fs);
    required.put("FR company", fs);
  }

  public static List<String> verify_merchants(List<String> lines) {
    // will keep parsed merchant and provided field value pairs
    Map<String, Map<String, String>> input = new HashMap<>();
    for (String line : lines) {
      String[] cuts = line.split(",");
      input.putIfAbsent(cuts[0], new HashMap<>());
      input.get(cuts[0]).put(cuts[1], cuts[2]);
    }

    List<String> merchants = new ArrayList<>(input.keySet());
    Collections.sort(merchants);

    List<String> r = new ArrayList<>(merchants.size());
    StringBuilder mr;
    for (String m : merchants) {
      Map<String, String> kv = input.get(m);
      mr = new StringBuilder();
      if (!kv.containsKey("country") || !kv.containsKey("business_type")) {
        mr.append(m).append(":UNVERIFIED:");
        if (!kv.containsKey("country")) mr.append("country,");
        if (!kv.containsKey("business_type")) mr.append("business_type,");
        mr.deleteCharAt(mr.length() - 1);
        r.add(mr.toString());
        continue;
      }
      for (String f : required.get(kv.get("country") + " " + kv.get("business_type"))) {
        if (!kv.containsKey(f)) {
          if (mr.isEmpty()) {
            mr.append(m).append(":UNVERIFIED:");
          }
          mr.append(f).append(",");
        }
      }
      if (mr.isEmpty()) r.add(m + ":VERIFIED");
      else {
        mr.deleteCharAt(mr.length() - 1);
        r.add(mr.toString());
      }
    }
    return r;
  }

  public static void main(String[] args) {

    System.out.println(
        Arrays.toString(verify_merchants(Arrays.asList("acct_123,business_type,company")).toArray())
            .equals("[acct_123:UNVERIFIED:country]"));

    System.out.println(
        Arrays.toString(
                verify_merchants(
                        Arrays.asList(
                            "acct_123,country,US",
                            "acct_123,business_type,individual",
                            "acct_123,first_name,Jane",
                            "acct_123,last_name,Doe",
                            "acct_123,date_of_birth,01011970",
                            "acct_123,social_security_number,123456789",
                            "acct_123,email,test@example.com",
                            "acct_123,phone,555555555"))
                    .toArray())
            .equals("[acct_123:VERIFIED]"));

    System.out.println(
        Arrays.toString(
                verify_merchants(
                        Arrays.asList(
                            "acct_123,tax_id_number,12345689",
                            "acct_123,country,FR",
                            "acct_123,business_type,company",
                            "acct_456,business_type,individual",
                            "acct_456,country,JP",
                            "acct_456,first_name,Mei",
                            "acct_456,last_name,Sato",
                            "acct_456,first_name_kana,Mei",
                            "acct_456,last_name_kana,Sato",
                            "acct_456,date_of_birth,01011970",
                            "acct_456,tax_id_number,123456",
                            "acct_456,email,test@example.com"))
                    .toArray())
            .equals("[acct_123:UNVERIFIED:director_name,name,phone, acct_456:VERIFIED]"));
  }
}
