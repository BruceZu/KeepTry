package array.logParse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static array.logParse.OnlineTimeParse.calculateOnlineTime;

public class Lytmus_LogParser {
    public static void main(String[] args)
            throws FileNotFoundException, IOException {
        String filename = "input_1.txt";
        if (args.length > 0) {
            filename = args[0];
        }

        String answer = parseFile(filename);
        System.out.println(answer);
    }

    static String parseFile(String filename)
            throws FileNotFoundException, IOException {
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

        return parseLines(allLines.toArray(new String[allLines.size()]));
    }

    static String parseLines(String[] lines) {
        /**
         * Your code goes here
         */
        long[] onLineAndTotal = calculateOnlineTime(lines);
        long online = onLineAndTotal[0];
        long total = onLineAndTotal[1];
        String r = new DecimalFormat("#.##").format(online * 100 / total);
        return r + "%";
    }
}
