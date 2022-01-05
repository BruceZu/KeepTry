package OOD;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

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
/*
/*
You have been tasked with

parsing menus from a large restaurant group. Each menu is streamed to clients via a provided interface.
You must design object(s) that represents a menu and can be instantiated with data from the provided interface
Your design should contain an appropriate class structure to contain the parsed data, as well as a function
or set of functions to perform the parsing.

Consumers will use your object(s) to access a complete representation of the data sent by the menu
stream after it has finished loading. Your objects should provide easy access to the full representation
of the menu.
It should be possible to reconstruct the menu stream from your object.


The menu stream represents a list of menu items. Each item in the stream is a menu item, and each item will
be separated by an empty string. The attributes of each item are as follows:
  Line 0: The ID of the item
  Line 1: The item type, either CATEGORY, ENTREE or OPTION
  Line 2: The name of the item
  Line 3: The price of the item for ENTREE and OPTION. Not present for CATEGORY items.
  Any other line: A list of item IDs that are linked to the current item. OPTIONs do not have any linked items.


E.g.:

4
ENTREE
Spaghetti
10.95
2
3
------ empty line
1
CATEGORY
Pasta
4
5
------
2
OPTION
Meatballs
1.00
------
3
OPTION
Chicken
2.00
------
5
ENTREE
Lasagna
12.00
------
6
ENTREE
Caesar Salad
9.75
3
------
 */
abstract class Menu {
  double ID;
  String name;

  public Menu(double ID, String name) {
    this.ID = ID;
    this.name = name;
  }

  abstract String getType();

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    return b.append(ID)
        .append("\n")
        .append(getType())
        .append("\n")
        .append(name)
        .append("\n")
        .toString();
  }
}

abstract class WithNext extends Menu {
  List<Menu> list;

  public WithNext(double ID, String name) {
    super(ID, name);
  }

  public void put(List<Menu> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (Menu n : list) {
      b.append(n.ID).append("\n");
    }
    return super.toString() + b;
  }
}

class Category extends WithNext {
  public Category(double ID, String name) {
    super(ID, name);
  }

  @Override
  String getType() {
    return Category.class.getSimpleName().toUpperCase();
  }
}

interface Price {
  void put(float price);
}

class Entree extends WithNext implements Price {
  float price;

  public Entree(double ID, String name) {
    super(ID, name);
  }

  @Override
  String getType() {
    return Entree.class.getSimpleName().toUpperCase();
  }

  @Override
  public void put(float price) {
    this.price = price;
  }
}

class Option extends Menu implements Price {
  float price;

  public Option(double ID, String name) {
    super(ID, name);
  }

  @Override
  String getType() {
    return Option.class.getSimpleName().toUpperCase();
  }

  @Override
  public void put(float price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return super.toString() + price + "\n";
  }
}

interface Parser {
  Set<Menu> parsing(MenuStream stream);

  List<String> parsing(Set<Menu> categorySet);
}

interface MenuStream {
  String nextLine();
}

enum Type {
  CATEGORY,
  ENTREE,
  OPTION
}

public class MenuParserImp implements Parser {
  public Set<Menu> parsing(MenuStream stream) {
    Set<Menu> result = new HashSet<>();
    Map<Double, List<Double>> nexts = new HashMap<>();
    Map<Double, Menu> map = new HashMap<>();
    double ID;
    Type type;
    String name;
    float price = 0;
    String cur = null;
    List<Double> list = null;
    while (true) {
      ID = Double.valueOf(stream.nextLine());
      type = Type.valueOf(stream.nextLine());
      name = stream.nextLine();
      if (!type.equals(Category.class.getSimpleName().toUpperCase()))
        price = Float.valueOf(stream.nextLine());
      if (!type.equals(Option.class.getSimpleName().toUpperCase())) {
        list = new ArrayList<>();
        while (true) {
          cur = stream.nextLine();
          if (!(cur == null || cur.isEmpty())) {
            list.add(Double.valueOf(stream.nextLine()));
          } else break;
        }
      } else {
        cur = stream.nextLine(); // empty line or null
      }
      Menu m = null;
      switch (type) {
        case CATEGORY:
          m = new Category(ID, name);
          break;
        case ENTREE:
          Entree e = new Entree(ID, name);
          e.put(price);
          m = e;
          break;
        case OPTION:
          Option o = new Option(ID, name);
          o.put(price);
          m = o;
          break;
        default:
          // todo
      }

      if (m instanceof Category) result.add(m);
      map.put(ID, m);
      if (!type.equals(Option.class.getSimpleName().toUpperCase())) {
        nexts.put(ID, list);
        if (cur == null) break;
      }
    }
    for (double id : nexts.keySet()) {
      WithNext withn = (WithNext) map.get(id);
      List<Menu> l = new ArrayList<>();
      withn.put(l);

      for (double n : nexts.get(id)) l.add(map.get(n));
    }
    return result;
  }

  // Category set
  @Override
  public List<String> parsing(Set<Menu> menus) {
    List<String> ans = new ArrayList<>();
    Set<Menu> vis = new HashSet<>();
    for (Menu m : menus) {
      f(m, ans, vis);
    }
    ans.remove(ans.size() - 1);
    ans.add(null);
    return ans;
  }

  private void f(Menu m, List<String> ans, Set<Menu> vis) {
    if (vis.contains(m)) return;
    vis.add(m);
    ans.add(m.toString() + "\n");
    if (m instanceof WithNext) {
      WithNext wn = (WithNext) m;
      for (Menu n : wn.list) {
        f(n, ans, vis);
      }
    }
  }

  public static void main(String[] args) {
   // Todo
  }
}
