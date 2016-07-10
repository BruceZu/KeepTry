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

package enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Please submit solutions to the following Java programming questions.
 * Feel free to use standard Java APIs in your solutions and also to compile/test
 * your code. However, the solutions should represent your own original work.
 *
 * We will be examining your solutions for correctness, efficiency, and
 * good programming practice.
 *
 * QUESTION #2:
 *
 * //
 * // The following Java code is responsible for creating an HTML "SELECT"
 * // list of U.S. states, allowing a user to specify his or her state. This might
 * // be used, for instance, on a credit card transaction screen.
 * //
 * // Please rewrite this code to be "better". Submit your replacement code, and
 * // please also submit a few brief comments explaining why you think your code
 * // is better than the sample.
 * //
 * // (For brevity, this sample works for only 5 states. The real version would
 * // need to work for all 50 states. But it is fine if your rewrite shows only
 * // the 5 states here.)
 * //
 *
 * public class StateUtils {
 *
 *  //
 *  // Generates an HTML select list that can be used to select a specific
 *  // U.S. state.
 *  //
 *  public static String createStateSelectList(){
 *   return "<select name=\"state\">\n"
 *      + "<option value=\"Alabama\">Alabama</option>\n"
 *      + "<option value=\"Alaska\">Alaska</option>\n"
 *      + "<option value=\"Arizona\">Arizona</option>\n"
 *      + "<option value=\"Arkansas\">Arkansas</option>\n"
 *      + "<option value=\"California\">California</option>\n"
 *      // more states here
 *      + "</select>\n";
 *  }
 *
 *  //
 *  // Parses the state from an HTML form submission, converting it to
 *  // the two-letter abbreviation.
 *  //
 *  public static String parseSelectedState(String s){
 *       if (s.equals("Alabama"))     { return "AL"; }
 *       if (s.equals("Alaska"))      { return "AK"; }
 *       if (s.equals("Arizona"))     { return "AZ"; }
 *       if (s.equals("Arkansas"))    { return "AR"; }
 *       if (s.equals("California"))  { return "CA"; }
 *       // more states here
 *  }
 *
 *  //
 *  // Displays the full name of the state specified by the two-letter code.
 *  //
 *  public static String displayStateFullName(String abbr) {
 *
 *      if (abbr.equals("AL")) { return "Alabama";    }
 *      if (abbr.equals("AK")) { return "Alaska";     }
 *      if (abbr.equals("AZ")) { return "Arizona";    }
 *      if (abbr.equals("AR")) { return "Arkansas";   }
 *      if (abbr.equals("CA")) { return "California"; }
 *      // more states here
 *  }
 *
 * }
 */

public class Enums {
    private static Logger log = LoggerFactory.getLogger("StateUtils");

    /**
     * <pre>
     * Why you think your code is better than the sample?
     * Answer:
     *      Keep all information of states full name and abbrev in one place - enum STATE.
     *      This makes the code concise, easy to read and maintenance.
     */
    private enum STATE {
        Alabama("AL"), Alaska("AK"), Arizona("AZ"), Arkansas("AR"), California("CA");

        String abbrev;

        STATE(String abbrev) {
            this.abbrev = abbrev;
        }
    }

    /**
     * Generates an HTML select list that can be used to select a specific
     * U.S. state.
     */
    public static String createStateSelectList() {
        StringBuilder r = new StringBuilder();
        r.append("<select name=\"state\">\n");
        for (STATE s : STATE.values()) {
            String name = s.name();
            r.append("<option value=\"").append(name).append("\">")
                    .append(name)
                    .append("</option>\n");
        }
        return r.append("</select>\n").toString();
    }

    /**
     * Parses the state from an HTML form submission, converting it to
     * the two-letter abbreviation.
     *
     * @param s full name of state
     * @return abbrev name of state, or null when there is no related abbrev.
     */
    public static String parseSelectedState(String s) {
        try {
            return Enum.valueOf(STATE.class, s).abbrev;
        } catch (IllegalArgumentException e) {
            log.error("Error state name: " + s);
            return null;
        }
    }

    /**
     * Displays the full name of the state specified by the two-letter code.
     *
     * @param abbr  abbrev name of state
     * @return full name of state, or null when there is no related full name.
     */
    public static String displayStateFullName(String abbr) {
        for (STATE state : STATE.values()) {
            if (state.abbrev.equals(abbr)) {
                return state.name();
            }
        }
        log.error("Error state abbrev: " + abbr);
        return null;
    }
}
