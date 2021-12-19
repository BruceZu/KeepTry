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

package java_httpclient;

import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
/*
API Key Info
Your API key is:

8211fe7eeeeeedb309fa52635cf3
This key will be used to authorize you to access the dataset andresult APIs.

HubSpot Project
It’s your first day at HubSpot, and you’re in charge of writing the logic to send invitations to a special two-day event in each country for HubSpot’s partners in those countries. We need to find the dates that’ll work best based on survey results that partners have sent in and determine how many people can attend.

You’re provided with an API that gives you a list of partners, their countries, and which dates they’re available in ISO 8601 format.

Another team will send out the invitations, but you need to tell them when we should host the event and who should attend by POSTing to an API.

The date you send in for the country should be the starting date of the two day period where the most partners can make it for both days in a row. In case of multiple dates with the same number of partners, pick the earlier date. If there are no two days in a row when any partners can make it, return null.

API Docs and Example
To get the list of partners, send an HTTP GET to:

https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=8211fe7eeeeeedb309fa52635cf3

Here’s a sample response with 10 partners:

{
            "partners": [
          {
            "firstName": "Darin",
            "lastName": "Daignault",
            "email": "ddaignault@hubspotpartners.com",
            "country": "United States",
            "availableDates": [
            "2017-05-03",
            "2017-05-06"
            ]
          },
          {
            "firstName": "Crystal",
            "lastName": "Brenna",
            "email": "cbrenna@hubspotpartners.com",
            "country": "Ireland",
            "availableDates": [
            "2017-04-27",
            "2017-04-29",
            "2017-04-30"
            ]
          },
          {
            "firstName": "Janyce",
            "lastName": "Gustison",
            "email": "jgustison@hubspotpartners.com",
            "country": "Spain",
            "availableDates": [
            "2017-04-29",
            "2017-04-30",
            "2017-05-01"
            ]
          },
          {
            "firstName": "Tifany",
            "lastName": "Mozie",
            "email": "tmozie@hubspotpartners.com",
            "country": "Spain",
            "availableDates": [
            "2017-04-28",
            "2017-04-29",
            "2017-05-01",
            "2017-05-04"
            ]
          },
          {
            "firstName": "Temple",
            "lastName": "Affelt",
            "email": "taffelt@hubspotpartners.com",
            "country": "Spain",
            "availableDates": [
            "2017-04-28",
            "2017-04-29",
            "2017-05-02",
            "2017-05-04"
            ]
          },
          {
            "firstName": "Robyn",
            "lastName": "Yarwood",
            "email": "ryarwood@hubspotpartners.com",
            "country": "Spain",
            "availableDates": [
            "2017-04-29",
            "2017-04-30",
            "2017-05-02",
            "2017-05-03"
            ]
          },
          {
            "firstName": "Shirlene",
            "lastName": "Filipponi",
            "email": "sfilipponi@hubspotpartners.com",
            "country": "Spain",
            "availableDates": [
            "2017-04-30",
            "2017-05-01"
            ]
          },
          {
            "firstName": "Oliver",
            "lastName": "Majica",
            "email": "omajica@hubspotpartners.com",
            "country": "Spain",
            "availableDates": [
            "2017-04-28",
            "2017-04-29",
            "2017-05-01",
            "2017-05-03"
            ]
          },
          {
            "firstName": "Wilber",
            "lastName": "Zartman",
            "email": "wzartman@hubspotpartners.com",
            "country": "Spain",
            "availableDates": [
            "2017-04-29",
            "2017-04-30",
            "2017-05-02",
            "2017-05-03"
            ]
          },
          {
            "firstName": "Eugena",
            "lastName": "Auther",
            "email": "eauther@hubspotpartners.com",
            "country": "United States",
            "availableDates": [
            "2017-05-04",
            "2017-05-09"
            ]
          }
            ]
          }
POST a JSON body to:

https://candidate.hubteam.com/candidateTest/v3/problem/result?userKey=8211fe7eeeeeedb309fa52635cf3

For the list of partners above, the proper API response to send would look like this:

{
            "countries": [
          {
            "attendeeCount": 1,
            "attendees": [
            "cbrenna@hubspotpartners.com"
            ],
            "name": "Ireland",
            "startDate": "2017-04-29"
          },
          {
            "attendeeCount": 0,
            "attendees": [],
            "name": "United States",
            "startDate": null
          },
          {
            "attendeeCount": 3,
            "attendees": [
            "omajica@hubspotpartners.com",
            "taffelt@hubspotpartners.com",
            "tmozie@hubspotpartners.com"
            ],
            "name": "Spain",
            "startDate": "2017-04-28"
          }
            ]
          }
API Guidelines
If your answer is correct, the API will return 200 OK. If the request is malformatted or incorrect, the API will return 400 along with a message indicating if the response is of the wrong structure or incorrect.

If you get a 5xx response, let us know and we’ll help you out.

The candidate.hubteam.com domain is set up with a permissive cross-origin policy, so you can POST to it from any location in a browser if you choose to implement in an in-browser JS solution.

Evaluation
When you’re done, this page will update with a form to upload your code. We’ll evaluate you based on three things:

First and foremost, if you complete the project within three hours.
Next, the time from when you click the start button below to the time you submit a correct solution.
Finally, the quality of code you submit. We’re looking for simplicity, clarity and readability over cleverness or flexbility.
We think you should be able to complete this project in a single sitting, so try to allocate a single block if you can.
*/
public class HttpClientJSONTime {
  /*
  Solution
   - HttpClient, HttpRequest, HttpResponse.BodyHandlers,
   - JSON: GSON,JsonObject,JsonElement,JsonPrimitive
   - Time:DateTimeFormatter, Period, Duration, LocalDate, LocalDateTime
   */
  public static void main(String[] args) throws IOException, InterruptedException, ParseException {
    String URI_PRE = "https://candidate.hubteam.com/candidateTest/v3/problem/";
    String KEY_GET = "dataset?userKey=";
    String KEY_POST = "result?userKey=";
    String keyValue = "8211fe7eeeeeedb309fa52635cf3";

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder(URI.create(URI_PRE + KEY_GET + keyValue)).GET().build();
    HttpResponse<String> getFeedback = client.send(request, HttpResponse.BodyHandlers.ofString());
    int statusCode = getFeedback.statusCode();
    if (statusCode != 200) {
      throw new RuntimeException("Got non 200 response code for GET! " + statusCode);
    }

    JsonObject map = new Gson().fromJson(getFeedback.body(), JsonObject.class);
    JsonElement partners = map.get("partners");
    Map<String, Map<String, Set<JsonObject>>> countrys = new HashMap<>();
    if (partners != null && partners.isJsonArray()) {
      JsonArray array = partners.getAsJsonArray();
      array.forEach(
          (a) -> {
            JsonObject o = a.getAsJsonObject();
            String country = o.get("country").getAsString();
            countrys.putIfAbsent(country, new HashMap<>());
            Map<String, Set<JsonObject>> timeLine = countrys.get(country);

            JsonArray dates = o.get("availableDates").getAsJsonArray();
            dates.forEach(
                (d) -> {
                  String date = d.getAsString();
                  timeLine.putIfAbsent(date, new HashSet<>());
                  timeLine.get(date).add(o);
                });
          });
    }

    JsonObject ans = new JsonObject();
    JsonArray v = new JsonArray();
    ans.add("countries", v);

    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    for (Map.Entry<String, Map<String, Set<JsonObject>>> e : countrys.entrySet()) {
      Map.Entry<String, Set<JsonObject>>[] dPs = new Map.Entry[e.getValue().size()];
      // dPs can be a TreeMap with same time complexity.
      int i = 0;
      for (Map.Entry<String, Set<JsonObject>> entry : e.getValue().entrySet()) {
        dPs[i++] = entry;
      }
      Arrays.sort(dPs, Comparator.comparing(Map.Entry::getKey));

      int attendeeCount = 0;
      JsonArray attendees = new JsonArray();
      String startDate = null;
      for (i = 0; i < dPs.length - 1; i++) {
        LocalDate a = LocalDate.parse(dPs[i].getKey(), f);
        LocalDate b = LocalDate.parse(dPs[i + 1].getKey(), f);
        if (Period.between(a, b).getDays() == 1) {
          int count = 0;
          JsonArray ems = new JsonArray();

          for (JsonObject p : dPs[i].getValue()) {
            if (dPs[i + 1].getValue().contains(p)) {
              count++;
              ems.add(p.get("email"));
            }
          }
          if (count > attendeeCount) {
            attendeeCount = count;
            attendees = ems;
            startDate = dPs[i].getKey();
          }
        }
      }

      JsonObject obj = new JsonObject();
      obj.add("name", new JsonPrimitive(e.getKey()));
      obj.add("attendeeCount", new JsonPrimitive(attendeeCount));
      obj.add("attendees", attendees);
      obj.add("startDate", startDate == null ? null : new JsonPrimitive(startDate));
      v.add(obj);
    }

    String body = ans.toString();

    request =
        HttpRequest.newBuilder(URI.create(URI_PRE + KEY_POST + keyValue))
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
    HttpResponse<?> postFeedback = client.send(request, HttpResponse.BodyHandlers.discarding());
    if (postFeedback.statusCode() != 200) {
      throw new RuntimeException(
          "Got non 200 response code for POST! " + postFeedback.statusCode());
    }
  }
}
