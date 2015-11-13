// Copyright (C) 2014 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Entries {
    static final Logger log = LoggerFactory.getLogger(Entries.class);

    private final short maxSize;
    private short currentMaxIndex;
    private Entry[] entries;

    private short size() {// note: size is not same as index
        return (short) (currentMaxIndex + 1);
    }

    private boolean needMove(short given, short element, boolean descending) {
        if (descending) {
            return given > element;
        }
        return given < element;
    }

    private boolean needNotAdd(Entry end, Entry given, boolean descending) {
        if (descending) {
            return end.score >= given.score;
        }
        return end.score <= given.score;
    }

    public Entries(short maxEntriesNumber) {
        this.maxSize = maxEntriesNumber;
        entries = new Entry[maxSize];
        currentMaxIndex = -1;
        log.info("Entries capability is " + this.entries.length);
    }

    /**
     * Default in order of Top to down
     */
    public void addInOrder(Entry entry, boolean descending) {
        if (size() == maxSize && needNotAdd(entries[maxSize - 1], entry, descending)) {
            return;
        }
        //move and find the right room for the new one
        if (size() != maxSize) {
            currentMaxIndex++;
        }
        short i = currentMaxIndex;

        for (; i > 0 && needMove(entry.score, entries[i - 1].score, descending); i--) {
            entries[i] = entries[i - 1];
        }
        entries[i] = entry;
    }

    public Entry delete(short index) throws Exception {
        if (index < 0 || index > currentMaxIndex) {
            throw new IndexOutOfBoundsException("out of the border of this Array");
        }
        Entry re = entries[index];
        for (short j = index; j < currentMaxIndex; j++) {
            entries[j] = entries[j + 1]; // j+1  , not ++j
        }
        entries[currentMaxIndex] = null;
        currentMaxIndex--;
        return re;
    }

    public String toString() {
        return Arrays.toString(entries);
    }
}
