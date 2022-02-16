//  Copyright 2022 The KeepTry Open Source Project
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

package stack;

import java.util.Stack;
import java.util.regex.Pattern;

public class Leetcode591TagValidator {
  /*
   Given a string representing a code snippet,
   implement a tag validator to parse the code and return whether it is valid.

    A code snippet is valid if all the following rules hold:

    The code must be wrapped in a valid closed tag.
    A closed tag (not necessarily valid) has exactly the following format :
       <TAG_NAME>TAG_CONTENT</TAG_NAME>. Among them,
       <TAG_NAME> is the start tag, and </TAG_NAME> is the end tag.
       The TAG_NAME in start and end tags should be the same.

    A closed tag is valid if and only if the TAG_NAME and TAG_CONTENT are valid.

    A valid TAG_NAME only contain upper-case letters, and has length in range [1,9].

    A valid TAG_CONTENT may contain other valid closed tags, cdata and any characters (see note1)
      EXCEPT
      - unmatched <,
      - unmatched start and end tag,
      - unmatched or closed tags with invalid TAG_NAME.

    A < is unmatched if you cannot find a subsequent >. And when you find a < or </,
        all the subsequent characters until the next > should be parsed as
        TAG_NAME (not necessarily valid).

    A start tag is unmatched if no end tag exists with the same TAG_NAME,
        and vice versa. However, you also need to consider the issue of unbalanced
        when tags are nested.

    The cdata has the following format : <![CDATA[CDATA_CONTENT]]>.
        The range of CDATA_CONTENT is defined as the characters between
        <![CDATA[ and the first subsequent ]]>.
        CDATA_CONTENT may contain any characters.
        The function of cdata is to forbid the validator to parse CDATA_CONTENT, so even it has some
        characters that can be parsed as tag (no matter valid or invalid), you should treat it as regular characters.



    Input: code = "<DIV>This is the first line <![CDATA[<div>]]></DIV>"
    Output: true
    Explanation:
    The code is wrapped in a closed tag : <DIV> and </DIV>.
    The TAG_NAME is valid, the TAG_CONTENT consists of some characters and cdata.
    Although CDATA_CONTENT has an unmatched start tag with invalid TAG_NAME, it should be considered as plain text, not parsed as a tag.
    So TAG_CONTENT is valid, and then the code is valid. Thus return true.


    Input: code = "<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>"
    Output: true
    Explanation:
    We first separate the code into : start_tag|tag_content|end_tag.
    start_tag -> "<DIV>"
    end_tag -> "</DIV>"
    tag_content could also be separated into : text1|cdata|text2.
    text1 -> ">>  ![cdata[]] "
    cdata -> "<![CDATA[<div>]>]]>", where the CDATA_CONTENT is "<div>]>"
    text2 -> "]]>>]"
    The reason why start_tag is NOT "<DIV>>>" is because of the rule 6.
    The reason why cdata is NOT "<![CDATA[<div>]>]]>]]>" is because of the rule 7.


    Input: code = "<A>  <B> </A>   </B>"
    Output: false
    Explanation: Unbalanced. If "<A>" is closed, then "<B>" must be unmatched, and vice versa.


    Constraints:

    1 <= code.length <= 500
    code consists of English letters, digits, '<', '>', '/', '!', '[', ']', '.', and ' '.
  */
  /*
  Watch:
   How to define the valid tag:
   no <TAG/>
   unmatched > is fine
   2 any characters:
    - `valid TAG_CONTENT may contain other valid closed tags, cdata and any characters`
    - `CDATA_CONTENT may contain any characters`
  no limitation on cdata quantity?

   how to know the range of cdata?
    'The range of CDATA_CONTENT is defined as the characters between
     <![CDATA[ and the first subsequent ]]>.'
  Note: `The code must be wrapped in a valid closed tag`.
         so cdata must be in valid closed tag, every thing must be in a valid closed tag.

  */
  /*
    Required properties.
     -1- The code should be wrapped in valid closed tag.
     -2- The TAG_NAME should be valid.
     -3- The TAG_CONTENT should be valid.
     -4- The cdata should be valid.
     -5- All the tags should be closed. i.e. each start-tag should have
         a corresponding end-tag and vice-versa and the order of the tags should be correct as well.

      Idea:
       check char one by one
       if find <
         - firstly check if it is  <![CDATA[, if so, checking the `first subsequent ]]>`
         - else check > => start tag or end tag
         with a stack to checking the tag balance

       Time and space O(N)
  */
  public boolean isValid(String code) {
    if (code == null || code.length() <= 6) return false;
    int i = 0;
    Stack<String> tags = new Stack<>();
    int L = "<![CDATA[".length();
    while (i < code.length()) {
      // `The code must be wrapped in a valid closed tag`
      if (i > 0 && tags.isEmpty()) return false;
      if (code.charAt(i) != '<') {
        i++;
        continue;
      }
      if (i + L <= code.length() && code.substring(i, i + L).equals("<![CDATA[")) {
        // `The code must be wrapped in a valid closed tag`
        if (tags.isEmpty()) return false;
        i += L;
        int j = code.indexOf("]]>", i);
        if (j < 0) return false;
        i = j + 3;
        continue;
      } else {
        int j = code.indexOf(">", i + 1);
        if (j < 0) return false;
        // <tag> or </tag>
        String tagName =
            code.charAt(i + 1) == '/' ? code.substring(i + 2, j) : code.substring(i + 1, j);
        if (isValidTagName(tagName)) {
          if (code.charAt(i + 1) != '/') {
            // tags stack only keep start tag
            tags.push(tagName);
          } else {
            if (tags.isEmpty() || !tags.peek().equals(tagName)) return false;
            tags.pop();
          }
        } else return false;
        i = j + 1;
        continue;
      }
    }
    return tags.isEmpty();
  }

  private boolean isValidTagName(String tag) {
    if (1 <= tag.length() && tag.length() <= 9) {
      for (char c : tag.toCharArray()) {
        if (!Character.isUpperCase(c)) return false;
      }
      return true;
    } else {
      return false;
    }
  }

  /* --------------------------------------------------------------------------
    RegEx
     how to describe nested relation: valid closed tags can contain itself?
     no-way.

  () Parentheses are used to define the scope and precedence of the operators (among other uses).
     For example, gray|grey and gr(a|e)y are equivalent patterns which both describe
     the set of "gray" or "grey".
  [...] Matches any single character in brackets.
  [^...] Matches any single character not in brackets.

  check the validity of the code string directly(except the nesting of the inner tags)
  no way to make sure the inner tags balance relation
  https://leetcode.com/problems/tag-validator/Figures/591/591_Tag_Validator.PNG

   <([A-Z]{1,9})>([^<]*((</?[A-Z]{1,9}>)|(<!\[CDATA\[(.*?)]]>))?[^<]*)*</\1>

   back-referencing will let the matching process take a very large amount of CPU time.
   So use the regex only to check the validity of the TAG_CONTENT, TAG_NAME and the cdata.
   check the presence of the outermost closed tags by making use of a stack

   need not manually check the validity of TAG_CONTENT, TAG_NAME and the cdata,
   since it is already done by the regex expression.

   only check the inner closed tags and balance of them.
  */
  /*
   with Pattern: match all input `code` string?
  */
  Stack<String> tags = new Stack<>();
  boolean findTag = false;

  public boolean isValid_(String code) {
    String regex = "";
    regex = "<([A-Z]{1,9})>([^<]*((</?[A-Z]{1,9}>)|(<!\\[CDATA\\[(.*?)]]>))?[^<]*)*(</\\1>)"; // 0
    regex = "<[A-Z]{1,9}>([^<]*((</?[A-Z]{1,9}>)|(<!\\[CDATA\\[(.*?)]]>))?[^<]*)*"; // 1
    regex = "<[A-Z]{1,9}>([^<]*(<((/?[A-Z]{1,9}>)|(!\\[CDATA\\[(.*?)]]>)))?)*"; // 2
    regex = "<[A-Z]{1,9}>([^<]*(<((/?[A-Z]{1,9}>)|(!\\[CDATA\\[(.*?)]]>))[^<]*)?)*"; // 3
    regex =
        "^(<[A-Z]{1,9}>)([^<]*(<((/?[A-Z]{1,9}>)|(!\\[CDATA\\[(.*?)]]>))[^<]*)?)*(</[A-Z]{1,9}>)$"; // 4

    /*
    - 1, 2, 3, 4 works. 0 is too slow
    - all of them has not the back-referencing "</\1>"
    - the 2 has not the right part "[^<]*", the 3 has it.
    - the 1 is <tag>|<cdata>, while 2 and 3 is <tag|cdata>
    - the 4 make sure
    */
    if (!Pattern.matches(regex, code)) return false;
    Stack<String> tags = new Stack<>();
    boolean findFirstTag = false;
    for (int i = 0; i < code.length(); i++) {
      if (findFirstTag && tags.isEmpty()) return false;
      if (code.charAt(i) == '<') {
        if (code.charAt(i + 1) == '!') {
          i = code.indexOf("]]>", i + 1); // pattern make sure cdata is valid
          continue;
        }
        boolean isEndTag = false;
        if (code.charAt(i + 1) == '/') {
          i++;
          isEndTag = true;
        }
        int closeIndex = code.indexOf('>', i + 1); // pattern make sure tag is valid
        String tagName = code.substring(i + 1, closeIndex);
        if (isEndTag) {
          if (!tags.isEmpty() && tags.peek().equals(tagName)) tags.pop();
          else return false; // pattern can not make sure tag balance
        } else {
          findFirstTag = true;
          tags.push(tagName);
        }
        i = closeIndex;
      }
    }
    return tags.isEmpty();
  }
  // ---------------------------------------------------------------------------
  public static void main(String[] args) {
    Leetcode591TagValidator t = new Leetcode591TagValidator();
    System.out.println(t.isValid("<DIV>This is the first line <![CDATA[<div>]]><DIV>")); // false
    System.out.println(t.isValid("<DIV>This is the first line <![CDATA[<div>]]></DIV>")); // true
    System.out.println(t.isValid("<![CDATA[wahaha]]]><![CDATA[]> wahaha]]>")); // false
    System.out.println(t.isValid("<A></A><B></B>")); // false
    System.out.println("-----"); // true
    System.out.println(t.isValid_("<DIV>This is the first line <![CDATA[<div>]]><DIV>")); // false
    System.out.println(t.isValid_("<DIV>This is the first line <![CDATA[<div>]]></DIV>")); // true
    System.out.println(t.isValid_("<![CDATA[wahaha]]]><![CDATA[]> wahaha]]>")); // false
    System.out.println(t.isValid_("<A></A><B></B>")); // false
  }
}
