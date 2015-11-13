import charter3.TicTacToe;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by brucezu on 11/13/15.
 */
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
       game.mark(-1,0);
    }
    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testValidMarkLocation2() {
        game.mark(1,7);
    }

    @Test(timeout = 90000L, expected = IndexOutOfBoundsException.class)
    public void testValidMarkLocation3() {
        game.mark(2,2);
        game.mark(2,2);
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
}
