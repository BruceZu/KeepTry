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

package game;

import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;

public class TicTacToeTest {
    private TicTacToe game;

    @Before
    public void setup() {
        game = new TicTacToe(6, 3);
    }

    @Test(timeout = 90000L, expected = IllegalArgumentException.class)
    public void testValidNumberOfMarksInlineToWin() {
        TicTacToe game = new TicTacToe(6, 2);
    }

    @Test(timeout = 90000L, expected = IllegalArgumentException.class)
    public void testValidNumberOfMarksInlineToWin2() {
        TicTacToe game = new TicTacToe(6, 7);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testValidMarkLocation() {
        game.mark(-1, 0);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testValidMarkLocation2() {
        game.mark(1, 7);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testValidMarkLocation3() {
        game.mark(2, 2);
        game.mark(2, 2);
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testNextPlayer() {
        int player = game.currentPlayer();
        game.nextPlayer();
        Truth.assertThat(player).isEqualTo(game.nextPlayer());
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testSuccessOnLeftTopToRightDownDiagonal() {
        // row line
        game.mark(1, 1);
        game.mark(2, 2);
        Truth.assertThat(game.whoWin()).isEqualTo(TicTacToe.None);

        game.mark(3, 3);
        Truth.assertThat(game.whoWin()).isNotEqualTo(TicTacToe.None);
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testSuccessOnRightTopToLeftDownDiagonal() {
        // row line
        game.mark(1, 4);
        game.mark(2, 3);
        Truth.assertThat(game.whoWin()).isEqualTo(TicTacToe.None);

        game.mark(3, 2);
        Truth.assertThat(game.whoWin()).isNotEqualTo(TicTacToe.None);
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testSuccessOnColumn() {
        // row line
        game.mark(1, 1);
        game.mark(3, 1);
        game.mark(4, 1);
        Truth.assertThat(game.whoWin()).isEqualTo(TicTacToe.None);

        game.mark(5, 1);
        Truth.assertThat(game.whoWin()).isNotEqualTo(TicTacToe.None);
    }

    @Test(timeout = 90000L, expected = Test.None.class)
    public void testSuccessOnRow() {
        // row line
        game.mark(2, 1);
        game.mark(2, 3);
        game.mark(2, 4);
        Truth.assertThat(game.whoWin()).isEqualTo(TicTacToe.None);

        game.mark(2, 5);
        Truth.assertThat(game.whoWin()).isNotEqualTo(TicTacToe.None);
    }
}
