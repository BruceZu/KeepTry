package design_BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 */
public class Test {
    public static void main(String[] args) {
        BlockingQueue a = new ArrayBlockingQueue(2);
        BlockingQueue b = new LinkedBlockingDeque(2);
    }
}
