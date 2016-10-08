package array.logParse;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static common_lib.Common.LinesOf;
import static common_lib.Common.getLocalFile;

public class OnlineTimeParse {
    /**
     * (02/03/2002-14:00:00) :: START
     * (02/03/2002-14:00:00) :: CONNECTED
     * (02/03/2002-14:08:00) :: DISCONNECTED
     * (02/03/2002-14:10:00) :: CONNECTED
     * (02/03/2002-14:15:00) :: SHUTDOWN
     */
    public static long[] calculateOnlineTime(String[] lines) {
        long startTime = -1; // -1 not start
        long conTime = -1;   // -1 not connected

        long total = 0;
        long totalOnline = 0;

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");

        for (String curLine : lines) {
            String[] parsed = curLine.split("::");
            String timeStr = parsed[0].trim();
            long time = -1;
            try {
                time = df.parse(timeStr.substring(timeStr.indexOf("(") + 1, timeStr.indexOf(")"))).getTime();
            } catch (ParseException e) {
                // assume the time and format always is valid
            }
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
        return new long[]{totalOnline, total};
    }

    public static void main(String[] args) {
        File f = getLocalFile("log.txt", OnlineTimeParse.class);
        if (!(f.exists() && f.canRead())) {
            return;
        }
        List<String> lines = LinesOf(f);
        long[] onLineAndTotal = calculateOnlineTime(lines.toArray(new String[lines.size()]));
        long online = onLineAndTotal[0];
        long total = onLineAndTotal[1];

        System.out.println(String.format("totalOnline: %d ms and total %d ms so percent: %s %%",
                online,
                total,
                new DecimalFormat("#.##").format(online * 100 / total)));
    }
}
