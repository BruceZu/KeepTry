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

/*
Your previous Plain Text content is preserved below:

Part 1

   In an HTTP request, the Accept-Language header describes the list of languages that the
   requester would like content to be returned in. The header takes the form of a comma-separated
   list of language tags. For example:

    Accept-Language: en-US, fr-CA, fr-FR

   means that the reader would accept:
   1. English as spoken in the United States (most preferred)
   2. French as spoken in Canada
   3. French as spoken in France (least preferred)

   We're writing a server that needs to return content in an acceptable language for the
   requester, and we want to make use of this header. Our server doesn't support every possible
   language that might be requested (yet!), but there is a set of languages that we do support.

   Write a function that receives two arguments: an Accept-Language header value as
   a string and
   a set of supported languages, and returns
   the list of language tags that will work for the request.

   The language tags should be returned in descending order of preference (the same order as they appeared in the header).

   In addition to writing this function, you should use tests to demonstrate that it's correct,
   either via an existing testing system or one you create.
   Examples:
   parse_accept_language(
     "en-US, fr-CA, fr-FR",# the client's Accept-Language header, a string
     ["fr-FR", "en-US"]    # the server's supported languages, a set of strings )
   returns: ["en-US","fr-FR"]

   parse_accept_language("fr-CA, fr-FR", ["en-US", "fr-FR"])
   returns: ["fr-FR"]

   parse_accept_language("en-US", ["en-US", "fr-CA"])
   returns: ["en-US"]

Part 2

   Accept-Language headers will often also include a language tag that is not region-specific
   for example, a tag of "en" means "any variant of English".
   Extend your function to support these language tags by letting them match all specific variants of the language.

   Examples:
   parse_accept_language("en", ["en-US", "fr-CA", "fr-FR"])
   returns: ["en-US"]

   parse_accept_language("fr", ["en-US", "fr-CA", "fr-FR"])
   returns: ["fr-CA", "fr-FR"]

   parse_accept_language("fr-FR, fr", ["en-US", "fr-CA", "fr-FR"])
   returns: ["fr-FR", "fr-CA"]

Part 3
   Accept-Language headers will sometimes include a "wildcard" entry, represented by an asterisk,
   which means "all other ‍‌‍‌‌‍‌‌‍‍‌‍‍‌‍‍‌‌Languages". Extend your function to support the wildcard
   entry.
   Examples:
   parse_accept_language("en-US, *", ["en-US", "fr-CA", "fr-FR"])
   returns: ["en-US", "fr-CA", "fr-FR"]

   parse_accept_language("fr-FR, fr, *", ["en-US", "fr-CA", "fr-FR"])
   returns: ["fr-FR", "fr-CA", "en-US"]
 */
import java.util.*;
import java.util.stream.*;

public class HttpHeader {
  public static List<String> parseAcceptLanguage(String required, List<String> supported) {
    List<String> r = new ArrayList<String>();
    if (required == null || supported == null) return r;
    List<String> list = new ArrayList<>(supported);

    String[] ls = required.split(",\\s+");
    for (String l : ls) {
      if (l.length() == 5) {
        if (list.contains(l)) {
          r.add(l);
          list.remove(l);
        }
      } else if (l.length() == 2) {
        List<String> matchs = new ArrayList<>();
        for (String s : list) {
          if (s.startsWith(l)) {
            matchs.add(s);
          }
        }
        for (String s : matchs) {
          r.add(s);
          list.remove(s);
        }
      } else if (l.equals("*")) {
        for (String left : list) {
          r.add(left);
        }
      } else {
        throw new RuntimeException("not supported tag");
      }
    }
    return r;
  }

  public static void main(String[] args) {
    System.out.println(
        parseAcceptLanguage("en-US, fr-CA, fr-FR", Arrays.asList("fr-FR", "en-US"))
            .toString()
            .equals("[en-US, fr-FR]"));
    System.out.println(
        parseAcceptLanguage("fr-CA, fr-FR", Arrays.asList("en-US", "fr-FR"))
            .toString()
            .equals("[fr-FR]"));
    System.out.println(
        parseAcceptLanguage("en-US", Arrays.asList("en-US", "fr-CA")).toString().equals("[en-US]"));
    // part 2
    System.out.println(
        parseAcceptLanguage("en", Arrays.asList("en-US", "fr-CA", "fr-FR"))
            .toString()
            .equals("[en-US]"));
    System.out.println(
        parseAcceptLanguage("fr", Arrays.asList("en-US", "fr-CA", "fr-FR"))
            .toString()
            .equals("[fr-CA, fr-FR]"));

    System.out.println(
        parseAcceptLanguage("fr-FR, fr", Arrays.asList("en-US", "fr-CA", "fr-FR"))
            .toString()
            .equals("[fr-FR, fr-CA]"));
  }
}
