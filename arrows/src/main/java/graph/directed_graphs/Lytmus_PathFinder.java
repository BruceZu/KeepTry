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
