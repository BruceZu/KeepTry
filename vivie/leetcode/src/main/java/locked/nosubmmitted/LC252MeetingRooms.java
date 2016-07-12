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

package locked.nosubmmitted;

import java.util.Arrays;

/**
 * 252. Meeting Rooms
 * https://leetcode.com/problems/meeting-rooms/
 * Difficulty: Easy
 * Given an array of meeting time intervals consisting of start and
 * end times [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all meetings.
 * <p/>
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return false.
 * <p/>
 * Hide Company Tags: Facebook
 * Hide Tags: Sort
 * Hide Similar Problems (H) Merge Intervals (M) Meeting Rooms II
 */

public class LC252MeetingRooms {
    /**
     * Definition for an interval.
     */
    class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    /**
     * the fast one should beat 99%  not shared code
     */

    /**
     * beat 87.96%
     */
    public boolean canAttendMeetings(Interval[] intervals) {
        int len = intervals.length;
        if (len == 0) {
            return true;
        }
        int[] begin = new int[len];
        int[] stop = new int[len];
        for (int i = 0; i < len; i++) {
            begin[i] = intervals[i].start;
            stop[i] = intervals[i].end;
        }
        Arrays.sort(begin);
        Arrays.sort(stop);
        int endT = 0;
        for (int i = 1; i < len; i++) {
            if (begin[i] < stop[i - 1]) {
                return false;
            }
        }
        return true;
    }
}

