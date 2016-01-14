//  Copyright 2016 The Minorminor Open Source Project
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

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Add interval -- add new interval and merge it
 * Query -- see if an intege is within interval
 */
public class Intervals {
    private class Interval {
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        private int start;
        private int end;

        boolean canMerge(Interval i) {
            return !(this.start > i.end || this.end < i.start);
        }

        Interval merge(Interval i) {
            if (canMerge(i)) {
                return new Interval(Math.min(i.start, this.start), Math.max(i.end, this.end));
            }
            return null;
        }

        int compareCannotMerge(Interval i) {
            assert (!canMerge(i)) == true;
            return this.start > i.end ? 1 : -1;
        }

        boolean contain(int i) {
            return i >= start && i <= end;
        }

        public String toString() {
            return String.format("[%s, %s]", start, end);
        }
    }

    private LinkedList<Interval> intervals = new LinkedList<>();

    public void add(int start, int end) {
        int min = Math.min(start, end);
        Interval newI = new Interval(min, start == min ? end : start);
        if (intervals.size() == 0) {
            intervals.add(newI);
            return;
        }

        int canMergeIndex = 0;
        for (int i = 0; i < intervals.size(); i++) {
            Interval current = intervals.get(i);
            if (current.canMerge(newI)) {
                canMergeIndex = i;
                break;
            }
            if (current.compareCannotMerge(newI) == 1) {
                intervals.add(i, newI);
                return;
            }
            if (i == intervals.size() - 1) {
                intervals.add(newI);
                return;
            }
        }

        intervals.set(canMergeIndex, intervals.get(canMergeIndex).merge(newI));

        for (int i = canMergeIndex + 1; i < intervals.size(); i++) {
            Interval current = intervals.get(i);
            if (current.canMerge(intervals.get(canMergeIndex))) {
                intervals.remove(i);
                i--;
                intervals.set(canMergeIndex, intervals.get(canMergeIndex).merge(current));
                continue;
            }
            break;
        }
    }

    public boolean query(int q) {
        for (Interval i : intervals) {
            if (i.contain(q)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return Arrays.toString(intervals.toArray());
    }
}
