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

import static com.google.common.truth.Truth.assertThat;

import charter3.InsertSort;
import org.junit.Test;

import java.util.Arrays;

public class InsertSortTest {
    private int length = 6;
    private Integer[] given = {1, 5, 9, 3, 6, 2};

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testIntegerArray() {
        InsertSort.sortByInsert(given);
        assertThat(Arrays.toString(given)).isEqualTo("[1, 2, 3, 5, 6, 9]");

        int it = 9;
        for (int i = length - 1; i >= 0; i--) {
            given[i] = it--;
        }
        InsertSort.sortByInsert(given);
        assertThat(Arrays.toString(given)).isEqualTo("[4, 5, 6, 7, 8, 9]");
    }
}
