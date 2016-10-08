package array.logParse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static string.Common.getLocalFile;

public class OnlineTimeParse {
    /**
     * (02/03/2002-14:00:00) :: START
     * (02/03/2002-14:00:00) :: CONNECTED
     * (02/03/2002-14:08:00) :: DISCONNECTED
     * (02/03/2002-14:10:00) :: CONNECTED
     * (02/03/2002-14:15:00) :: SHUTDOWN
     */

    public static void main(String[] args) {
        File f = getLocalFile("log.txt", OnlineTimeParse.class);
        try {
            if (f.exists() && f.canRead()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                long startTime = -1; // -1 not start
                long conTime = -1;   // -1 not connected

                long total = 0;
                long totalOnline = 0;
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
                while (true) {
                    String curLine = br.readLine();
                    if (curLine == null) {
                        break;
                    }
                    String[] parsed = curLine.split("::");
                    String timeStr = parsed[0].trim();
                    long time = df.parse(timeStr.substring(timeStr.indexOf("(") + 1, timeStr.indexOf(")"))).getTime();

                    switch (parsed[1].trim()) {
                        case "START":
                            startTime = time;
                            break;
                        case "CONNECTED":
                            conTime = time;
                            break;
                        case "DISCONNECTED"://assume the status sequence is in an logic order, there is a connected before it
                            totalOnline += time - conTime;
                            conTime = -1;
                            break;
                        case "SHUTDOWN"://assume the status sequence is in an logic order, there is start before it or also there is an connected before it
                            total += time - startTime;
                            startTime = -1;
                            if (conTime != -1) {
                                totalOnline += time - conTime;
                            }
                            break;
                    }
                }
                System.out.println(String.format("totalOnline: %d ms and total %d ms so percent: %s %%",
                        totalOnline, total, new DecimalFormat("#.##").format(totalOnline * 100 / total)));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
