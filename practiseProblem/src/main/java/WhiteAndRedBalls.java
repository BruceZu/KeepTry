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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  There are M white balls and N red balls in a bag,
  pick up one ball each time, if it is a red ball, get it out, else put it back and pick one again.
  on the second time, get it out regardless of it's color.

  Find out the Probability that the last ball is a white ball
 */
public class WhiteAndRedBalls {
    private static Logger log = LoggerFactory.getLogger(WhiteAndRedBalls.class);

    private static String printStep(int step) {
        StringBuilder r = new StringBuilder().append("-*-");
        while (step > 0) {
            r.append("-*-");
            step--;
        }
        return r.toString();
    }

    public static float getProbabilityOfLastOneIsWhite(int red, int white, int step) {

        if (white == 0) {
            return 0.0f;
        }
        if (red == 0) {
            return 1.0f;
        }
        if (white == 1 && red == 1) {
            return 1.0f / 2.0f + 1.0f / 4.0f;
        }

        float r = red;
        float w = white;

        float rw = (r / (r + w)) * (w / (r - 1 + w)) * getProbabilityOfLastOneIsWhite(red - 1, white - 1, step + 1);

        float rr = 0;
        if (r >= 2) {
            rr = (r / (r + w)) * ((r - 1) / (r - 1 + w)) * getProbabilityOfLastOneIsWhite(red - 2, white, step + 1);
        }

        float ww = (w / (r + w)) * (w / (r + w)) * getProbabilityOfLastOneIsWhite(red, white - 1, step + 1);
        float wr = (w / (r + w)) * (r / (r + w)) * getProbabilityOfLastOneIsWhite(red - 1, white, step + 1);

        log.info(String.format(printStep(step) + "[ r: %d , w: %d ] = %.7f \n", (int) r, (int) w, rw + rr + ww + wr));
        return rw + rr + ww + wr;
    }
}
