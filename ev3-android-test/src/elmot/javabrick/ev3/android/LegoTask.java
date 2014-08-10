package elmot.javabrick.ev3.android;

import elmot.javabrick.ev3.MotorFactory;
import elmot.javabrick.ev3.android.usb.EV3BrickUsbAndroid;

import java.io.IOException;


public class LegoTask extends LegoTaskBase {
    public LegoTask(Ev3Activity ev3Activity) {
        super(ev3Activity);
    }

    protected void runBrick(EV3BrickUsbAndroid brick) throws IOException, InterruptedException {
        publishProgress("Tone play");
        brick.SYSTEM.playTone(50, 330, 300);
        publishProgress("Tone played");

        brick.MOTOR.direction(MotorFactory.MOTORSET.A, MotorFactory.DIR.FORWARD);
        brick.MOTOR.powerTime(0, MotorFactory.MOTORSET.A, -50, 0, 1000, 0, MotorFactory.BRAKE.COAST);
        Thread.sleep(1000);
        brick.MOTOR.stop(MotorFactory.MOTORSET.A, MotorFactory.BRAKE.COAST);

        publishProgress("Go!");
        brick.MOTOR.powerTime(0, MotorFactory.MOTORSET.BC, 100, 0, 1500, 0, MotorFactory.BRAKE.COAST);
        brick.MOTOR.waitForCompletion(0, MotorFactory.MOTORSET.BC);

        brick.MOTOR.powerTime(0, MotorFactory.MOTORSET.B, 50, 0, 1500, 0, MotorFactory.BRAKE.COAST);
        brick.MOTOR.powerTime(0, MotorFactory.MOTORSET.C, -50, 0, 1500, 0, MotorFactory.BRAKE.COAST);
        brick.MOTOR.waitForCompletion(0, MotorFactory.MOTORSET.BC);

        brick.SYSTEM.playTone(50, 700, 300);

        publishProgress("Catch!");
        brick.MOTOR.resetTacho(0, MotorFactory.MOTORSET.A);
        brick.MOTOR.powerTime(0, MotorFactory.MOTORSET.A, 100, 0, 1000, 0, MotorFactory.BRAKE.BRAKE);
        Thread.sleep(500);

        brick.MOTOR.direction(MotorFactory.MOTORSET.A, MotorFactory.DIR.FORWARD);
        brick.MOTOR.powerStep(0, MotorFactory.MOTORSET.A, -50, 0, 700, 0, MotorFactory.BRAKE.COAST);
        Thread.sleep(1500);

        brick.MOTOR.stop(MotorFactory.MOTORSET.A, MotorFactory.BRAKE.COAST);
        brick.SYSTEM.playTone(50, 140, 1300);
    }

}
