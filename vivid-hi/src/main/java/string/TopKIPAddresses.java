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

package string;import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Find Top K IP addresses
 * Given millions of log, an example log file is "Abdfdfd\tdfdfd\t1.23.132.21\n" and at the end of the log it will have an IP address.
 * You are asked to select the Top K IP addresses
 * Follow Up:
 * What if one machine can't store the whole log files
 * What if one machine can't store the whole IP address
 */

public class TopKIPAddresses {
    static List<String> getTopKIP(int k, String pathToLogFile) {
        List<String> re = new LinkedList();
        String line;
        Pattern p = Pattern.compile(".*((\\d{1,3}){1}(\\.\\d{1,3}){3})");
        try (BufferedReader bReader = new BufferedReader(new FileReader(new File(pathToLogFile)));) {
            int i = 1;
            while ((line = bReader.readLine()) != null && i <= k) {
                Matcher m = p.matcher(line);
                if (m.find()) {
                    re.add(m.group(1));
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }

    static List<String> getTopKIP2(int k, String source) {
        List<String> re = new LinkedList();
        String line;
        Pattern p = Pattern.compile(".*((\\d{1,3}){1}(\\.\\d{1,3}){3})");
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(source.getBytes())));) {
            int i = 1;
            while ((line = bReader.readLine()) != null && i <= k) {
                Matcher m = p.matcher(line);
                if (m.find()) {
                    re.add(m.group(1));
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }
}
