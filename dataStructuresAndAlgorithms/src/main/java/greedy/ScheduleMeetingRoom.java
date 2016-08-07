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

/**
 * <pre>
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
 *   using stack?
 */
public class ScheduleMeetingRoom {
}
