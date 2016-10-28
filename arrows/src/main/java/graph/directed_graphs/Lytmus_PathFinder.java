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

package graph.directed_graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static graph.directed_graphs.AllPathWithoutCircle.allPathsOf;
import static graph.directed_graphs.AllPathWithoutCircle.parseLinesToGraph;

public class Lytmus_PathFinder {

    public static void main(String[] args)
            throws IOException {
        String filename = "input_1.txt";
        if (args.length > 0) {
            filename = args[0];
        }

        List<String> answer = parseFile(filename);
        System.out.println(answer);
    }

    static List<String> parseFile(String filename)
            throws IOException {
        /*
         *  Don't modify this function
    	 */
        BufferedReader input = new BufferedReader(new FileReader(filename));
        List<String> allLines = new ArrayList();
        String line;
        while ((line = input.readLine()) != null) {
            allLines.add(line);
        }
        input.close();

        return parseLines(allLines);
    }

    static List<String> parseLines(List<String> lines) {
        /**
         *  Your code goes here
         */
        Vertex [] fromAndTo=  parseLinesToGraph(lines);
        Vertex from =fromAndTo[0],to= fromAndTo[1];
        return allPathsOf(from, to);
    }
}
