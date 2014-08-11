package elmot.javabrick.ev3.net;

import elmot.javabrick.ev3.EV3Brick;
import elmot.javabrick.ev3.PORT;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author elmot
 */
public class MainHTIRSeeker {
    @Ignore
    @Test
    public void doTest() throws IOException, InterruptedException {
        EV3Brick ev3Brick = EV3Base.openBlock();
        for (long startMs = System.currentTimeMillis(); System.currentTimeMillis() - startMs < 10000; ) {
            int data = ev3Brick.HT_IR_SEEKER.read(0, PORT.P3);
            System.out.println("data = " + data);

        }
    }
}