//  Copyright 2017 The keepTry Open Source Project
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

package java_io;

import java.io.*;

public class JavaDataInputStream {
    /**
     * This is incorrect, an InputStream only reads bytes and the DataInputStream extends it
     * so you can read Java primitives as well.
     * <p>
     * A DataInputStream should only be used to read data that was previously written by a DataOutputStream.
     * If that's not the case, your DataInputStream is not likely to "understand" the data you are reading
     * and will return random data.
     * Therefore, you should know exactly what type of data was written by the corresponding DataOutputStream
     * in which order.
     *
     * @param args
     */
    public static void main(String[] args) {

        String file = "/home/go-along-road-quick-on-time-4-ye/state.txt";
        try (
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                DataInputStream in = new DataInputStream(new FileInputStream(file));
        ) {
            out.writeFloat(Float.valueOf(1));
            out.writeInt(2);
            out.writeDouble(Double.valueOf(3));
            byte d = Double.valueOf(4).byteValue();
            out.writeByte(d);
            out.flush();

            System.out.println(in.readFloat());
            System.out.println(in.readInt());
            System.out.println(in.readDouble());
            System.out.println(Double.valueOf(in.readByte()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
