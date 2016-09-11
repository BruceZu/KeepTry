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

package design_pattern.factory.factory_method;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;
import java.util.Spliterator;
import java.util.stream.Stream;

public class Scenario {
    /**
     * The strength of this pattern is that you don’t need to know what type
     * of collections you’re using,
     * each collection will provide an Iterator through the factory method iterator()
     *
     * @see <a href="http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/BeanFactory.html">spring case</a>
     */
    public static void main(String[] args) {
        Iterator it = new ArrayList<>().iterator();
        Stream st = new ArrayList<>().stream();
        Spliterator sp = new ArrayList<>().spliterator();
        // how about these:
        javax.xml.parsers.DocumentBuilderFactory.newInstance();
        javax.xml.transform.TransformerFactory.newInstance();
        javax.xml.xpath.XPathFactory.newInstance();
        Currency.getInstance(Locale.CHINA);
    }
}