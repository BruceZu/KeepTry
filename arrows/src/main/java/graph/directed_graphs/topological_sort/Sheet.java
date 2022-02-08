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

package graph.directed_graphs.topological_sort;

import java.util.HashMap;
import java.util.Map;

/*
Today we’re going to build a basic spreadsheet application like Google sheets
or Excel but much simpler. Our spreadsheet, let’s call it OpenSheet,
will only support cells which hold either integers or formulas that sum two cells.

You are tasked with writing a program that handles this functionality for OpenSheet.
You can make any decisions you want regarding how this program is organized,
but there must be some sort of setter/getter methods that can be called by the application
for any given cell. All inputs will be strings.

Example of how your getter/setter could look
  set_cell("C1", "45")
  set_cell("B1", "10")
  set_cell("A1", "=C1+B1") // no -, *, / , only 2 terms
  get_cell("A1") # should return 55 in this case

  get_cell("D1") should return 0, since D1 is not set

We will not be implementing all of a spreadsheet's features, but we need the
ability for a cell to refer to other cells in formulas, and to sum values
from other cells.

All inputs will be well-formed, i.e. no need to write input validation code.

When in doubt, bias towards simplicity.
*/
/*
Clarify:
Assume the value has only 2 format:
 - only digit
 - "=C1+ZB10" => 2 cell location.
 - a sum/value cell can be referred by other sum cell?
 https://i.imgur.com/6Y6GG2S.png
 https://i.imgur.com/vSqjOea.png
*/
/*
Idea:
  one dimension array to represent the sheet
  location using format like 'ZB10': row letter String and column digit String
  a map keep the cells relation
  a map keep the cells location and value:  Assume it is a sparse sheet, not use array which need relocate memory
  in advance and its size is restricted by the Integer.MAX
 */
public class Sheet {
  private Map<String, String> sheet;

  /*
   E.g.: format  "A1"  -> sum(C1, B1)
   key will be "A1", value is ["C1","B1"]
  */
  private Map<String, String[]> map; // web structure

  public Sheet() {
    sheet = new HashMap<>(); // keep in mind: initial value is 0;
    map = new HashMap<>();
  }

  // Assume the str is in valid format like "=A1+B4, =ZA100+BB88"
  private String[] parse(String str) {
    str = str.substring(1); // remove =
    return str.split("\\+");
  }
  /*
  DFS update all refer cells
  */
  private void evaluateRefers(String cell) {
    for (Map.Entry<String, String[]> e : map.entrySet()) {
      if (e.getValue()[0].equals(cell) || e.getValue()[1].equals(cell)) {
        udpateCell(e.getKey(), e.getValue());
        evaluateRefers(e.getKey());
      }
    }
  }

  private void udpateCell(String sumCell, String[] cells) {
    // convert relation value to integer value
    String strv1 = sheet.getOrDefault(cells[0], "0");
    String strv2 = sheet.getOrDefault(cells[1], "0");
    // Assume sum is not a big Integer
    String v = String.valueOf(Integer.valueOf(strv1) + Integer.valueOf(strv2));
    sheet.put(sumCell, v);
  }
  /*
  Assume the input are all valid format:
     1: "=C1+B1"
     2: all digit
  Detect circle


  Not introduced circle, then update current value
  then update all refers cells

  Return
    - false if failed to save by circle
    - true if successful saved the value and update all refers cells
  */
  public boolean set(String key, String value) {
    if (value.startsWith("=")) {
      //  update relation map
      String[] twoCells = parse(value);

      String[] oldV = null;
      if (map.containsKey(key)) oldV = map.get(key);

      map.put(key, twoCells);
      if (findCircle(key)) {
        if (oldV != null) map.put(key, oldV);
        return false; // invalid relation
      }
      udpateCell(key, twoCells);
    } else {
      sheet.put(key, value);
    }
    evaluateRefers(key);
    return true;
  }

  private boolean findCircle(String cell) {
    Map<String, Boolean> visited = new HashMap<>();
    return dfs(cell, visited);
  }

  private boolean dfs(String cell, Map<String, Boolean> vis) {
    if (vis.containsKey(cell)) return vis.get(cell);
    vis.put(cell, true);
    if (map.containsKey(cell)) {
      for (String c : map.get(cell)) {
        dfs(c, vis);
      }
    }
    vis.put(cell, false);
    return false;
  }

  public String get(String key) {
    return sheet.containsKey(key) ? sheet.get(key) : "0";
  }

  public static void main(String[] args) {
    /*
    set_cell("C1", "45")
    set_cell("B1", "10")
    set_cell("A1", "=C1+B1") // no -, *, / , only 2 terms
    get_cell("A1") # should return 55 in this case
    */
    Sheet os = new Sheet();
    os.set("C1", "45");
    os.set("B1", "10");
    os.set("A1", "=C1+B1");

    System.out.println(os.get("A1").equals("55"));
    System.out.println(os.get("D1").equals("0"));
  }
}
