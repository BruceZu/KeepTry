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

package nosubmmitted;

import java.util.Arrays;

/**
 * 253. Meeting Rooms II
 * https://leetcode.com/problems/meeting-rooms-ii/
 * Difficulty: Medium <pre>
 * Given an array of meeting time intervals consisting of start and end times
 * [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
 * <p/>
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return 2.
 * <p/>
 * Hide Company Tags:  Google Facebook
 * Hide Tags: Heap, Greedy, Sort
 * Hide Similar Problems (H) Merge Intervals (E) Meeting Rooms
 * <p/>
 */


public class LC253MeetingRoomsII {
    /**
     * the fast one should beat 98%, no share its code
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
     * beat 85.5% <pre>
     * <p/>
     * To understand why it works, first let’s define two events:
     * Meeting Starts Meeting Ends
     * Next, we acknowledge three facts: The numbers of the intervals give chronological orders When an ending event occurs,
     * there must be a starting event has happened before that,
     * where “happen before” is defined by the chronological orders given by the intervals Meetings
     * that started which haven’t ended yet have to be put into different meeting rooms,
     * and the number of rooms needed is the number of such meetings
     * <p/>
     * So, what this algorithm works as follows:
     * <p/>
     * for example, we have meetings that span along time as follows:
     * <p/>
     * |_____|
     *       |______|
     * |________|
     *         |_______|
     * Then, the start time array and end time array after sorting appear like follows:
     * <p/>
     * ||    ||
     *       |   |   |  |
     * Initially, endsItr points to the first end event, and we move i which
     * is the start event pointer. As we examine the start events,
     * we’ll find the first two start events happen before the end event that
     * endsItr points to, so we need two rooms (we magically created two rooms),
     * as shown by the variable rooms. Then, as i points to the third start event,
     * we’ll find that this event happens after the end event pointed by endsItr,
     * then we increment endsItr so that it points to the next end event.
     * What happens here can be thought of as one of the two previous meetings ended,
     * and we moved the newly started meeting into that vacant room,
     * thus we don’t need to increment rooms at this time and move both
     * of the pointers forward. Next, because endsItr moves to the next end event,
     * we’ll find that the start event pointed by i happens before the end
     * event pointed by endsItr. Thus, now we have 4 meetings started but only one ended,
     * so we need one more room. And it goes on as this.
     *
     *
     */
    public int minMeetingRooms(Interval[] intervals) {
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i].start;
            ends[i] = intervals[i].end;
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        int rooms = 0;
        int endsItr = 0;
        for (int i = 0; i < starts.length; i++) {
            if (starts[i] < ends[endsItr])
                rooms++;
            else
                endsItr++;
        }
        return rooms;
    }
    /**
     * question on above
     * Thanks for @magicyuli 's effort. But I didn't fully understand. I shall share a bit of my thought:

     In https://leetcode.com/discuss/50911/ac-java-solution-using-min-heap,
     if solution by @jeantimex is the greatest and clearest, solution by @pinkfloyda is crazy.
     I really spent a lot time and thought about why he can use such a solution.

     To understand the solution, we need first understand the mindset from @jeantimex.
     That is, for example you are the meeting manager, there are 3 room having meeting,
     room A ends at 5, room B ends at 7, room C ends at 9: if there’s a meeting [6,11] to be arranged,
     you will put it in room A because A ends before this meeting,
     then your meeting schedule updates as: room A ends at 11,
     B at 7, C at 9; if there’s a meeting [4, 11] to be arranged,
     you have no way but to add another room D, then your meeting schedule updates as:
     roomA ends at 5, B at 7, C at 9, D at 11.
     So the mindset is sort the meetings by start time.
     Iterate through the meetings,
     add each meeting in the room that finishes earliest,
     if not possible, put it in a new room.

     In this solution, we sort the meetings by start time as start list to have the same iteration sequence.
     We sort the meetings by end time to end list
     so that it makes sure each elements represents the meeting that
     ends earliest in its room compared to other meeting that are behind
     it in the end list. Take an example:

     [2,3],[0,4],[6,7],[0,9][5,8]

     so the start and end lists are:

     st: 0, 0, 2, 4, 5

     ed: 3, 4, 7, 8, 9

     Suppose i is the index of st list, which is the start time of meeting
     you want to add in to the schedule. Suppose firstEnd is the index of ed list,
     which is the first end time of in your meeting rooms.
     You first add a meeting that finishes earliest in a room.
     But wait, you may ask, what meeting are you talking about?
     what about the start time of the meeing?
     You have saperated start and end time of a meeting so that meeting is lost! Damn,
     I had the same confusion too. First imagine that you know which meeting it is, so,
     for the first meeting that finishes the earliest,
     you iterate throught the meeting in the start list,
     you see: @ i = 0, this meeting starts at 0,
     before the earliest-end meeting (which is pointed by index firstEnd, which ends at time 3),
     what do you do? You can do nothing else but to add another room,
     room B. When does room B finishes?
     It finishes at a time that is behind index firstEnd in the end list.
     Which one? I don’t know.
     But the only thing I know is the earlist meeting that finishes is at 3.
     You then increase i = 1, you see it starts 0, before the earliest-end meeting.
     Even the learliest-end meeting finishes after this meeting, so you have no way to
     use a existing room for it because it will have time collision.
     So as long as you have a meeting whose start time is earlier
     than the earliest-end meeting finish time, you add a room, room C.
     Then you go to i = 2, which finishes at 2 < 3 (pointed by firstEnd in end list),
     add a room, room A. Why room A? I just want to give it a name A ok? Alright,
     you remember you don’t know the meeting which ends the earliest,
     know you encounter it, actually,
     this room A is the first room that provides the meeting room for
     the earliest-end meeting.
     You see this whole process happens silently and internally.
     Hence, up to i=2, you added three room,
     and you know the earliest-end meeting finishes at 3.
     Before you encouter a meeting that starts no earlier than earliest-end meeting,
     you must have encountered the earliest-end meeting’s
     start time and silently put that meeting in one of the room you added.

     You then increase i=3, found start time = 4 which is no earlier
     than earliest-end meeting. Now your time is 4, you have passed
     the earliest-end meeting, so you increase the firstEnd index to
     find the next earliest-end meeting among all rooms,
     which ends at 4. And you now found you can put this meeting
     right after this earliest-end meeting with no need to add
     new room. And then you update firstEnd=3 and go to i=5 and
     found the next meeting starts earlier than all the meeings among the existing rooms, you add a room.
     */
}
