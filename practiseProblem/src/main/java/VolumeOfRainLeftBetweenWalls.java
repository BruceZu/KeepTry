/**
 * question comes from http://blog.jobbole.com/50705/
 */
public class VolumeOfRainLeftBetweenWalls {

    public static int calculateTheVolumeOfRainOf(int[] walls) {
        int v = 0;
        int left_highest_wall_value = walls[0];
        int right_highest_wall_value = walls[walls.length - 1];

        int i = 1, j = walls.length - 2;
        while(i <= j ) {
            if (left_highest_wall_value <= right_highest_wall_value) {
                if (walls[i] >= left_highest_wall_value) {
                    left_highest_wall_value = walls[i];
                    i++;
                    continue;
                }
                v += left_highest_wall_value - walls[i];
                i++;
                continue;
            }


            if (walls[j] >= right_highest_wall_value) {
                right_highest_wall_value = walls[j];
                j--;
                continue;
            }
            v += right_highest_wall_value - walls[j];
            j--;
        }

        return v;
    }
}

