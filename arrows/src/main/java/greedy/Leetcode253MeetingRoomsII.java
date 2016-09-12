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

package greedy;

import java.util.Arrays;

/**
 * <pre>
 * 253. Meeting Rooms II
 *
 * Difficulty: Medium
 * Given an array of meeting time intervals consisting of start and end times
 *
 *              [[s1,e1],[s2,e2],...] (si < ei),
 *
 * find the minimum number of conference rooms required.
 *
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return 2.
 * Company Tags:  Google Facebook
 * Tags: Heap, Greedy, Sort
 * Similar Problems
 *      (H) Merge Intervals
 *      (E) Meeting Rooms
 * ==============================================================================
 *     1 given a meeting room, ask the max meeting. meeting with shorter time has high priority.
 *         sort meetings by finish time. 'sorted'
 *         finishTime = sorted[0].finish time
 *         int meetings =1;
 *         loop 'sorted' from sorted[1]:
 *            if finishTime < i start time (available)
 *                  meetings++;
 *                  finishTime = i finish time
 *         return meetings
 *
 *     2 given meetings, ask min meeting room
 *
 *        sort meetings by start time in a array or list, 'sorted'
 *        heap (PriorityQueue): 'roomsFinishedFirstlyOnHead'
 *                              keep meeting room and finish time of the last meeting there, add sorted[0] in to
 *                              roomsFinishedFirstlyOnHead.
 *        loop sorted from sorted[1]:
 *             if head of roomsFinishedFirstlyOnHead's finish time < i meeting start time
 *                room enough, take it:
 *                      pop the head of roomsFinishedFirstlyOnHead,
 *                      mark it by i,
 *                      add the head into roomsFinishedFirstlyOnHead again ( for sort)
 *             else:
 *                need add a meeting room: add i in roomsFinishedFirstlyOnHead.
 *        return the number of roomsFinishedFirstlyOnHead.
 *
 * @see <a href ="https://leetcode.com/problems/meeting-rooms-ii/">leetcode</a>
 */

public class Leetcode253MeetingRoomsII {
    class Interval {
        int start;
        int end;
    }

    /**
     * <pre>
     *  sort meetings by start time as any meeting must be satisfied.
     *  loop sorted:
     *  if current meeting need start before the earliest finish time:
     *      need another meeting room,
     *  else
     *      just use the meeting room where the meeting has earliest finish time
     *      and update 'earliest finish time'
     */
    public int minMeetingRooms(Interval[] meetings) {
        int[] starts = new int[meetings.length];
        int[] ends = new int[meetings.length];
        for (int i = 0; i < meetings.length; i++) {
            starts[i] = meetings[i].start;
            ends[i] = meetings[i].end;
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        int rooms = 0;
        int earliestEnded = 0;
        for (int i = 0; i < starts.length; i++) {
            if (starts[i] < ends[earliestEnded])
                rooms++;
            else
                earliestEnded++;
        }
        return rooms;
    }
}
