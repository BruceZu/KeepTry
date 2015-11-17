import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;

public class VolumeOfRainLeftBetweenWallsTest {

    private int[] intPut_MaxAtLeft = {5, 4, 3, 2, 1};
    private int[] intPut_MaxAtRight = {1, 2, 3, 4, 5};
    private int[] intPut_MaxAtMiddle = {1, 2, 3, 4, 5, 4, 3, 2, 1};

    private int[] caseInterview = {2, 5, 1, 2, 3, 4, 7, 7, 6};
    private int[] caseInterview2 = {2, 5, 1, 3, 1, 2, 1, 7, 7, 6};
    private int[] caseInterview3 = {6, 1, 4, 6, 7, 5, 1, 6, 4};


    @Test(timeout = 3000L, expected = Test.None.class)
    public void test() {
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(intPut_MaxAtLeft), 0);
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(intPut_MaxAtMiddle), 0);
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(intPut_MaxAtRight), 0);

        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(caseInterview), 10);
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(caseInterview2), 17);
        Assert.assertEquals(VolumeOfRainLeftBetweenWalls.calculateTheVolumeOfRainOf(caseInterview3), 13);
    }
}
