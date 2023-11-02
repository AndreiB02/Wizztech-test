package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    private final double RESET_POSITION = 0.0;
    private final double MAX_ROTATION = 1.0;

    private final Telemetry  telemetry;
    private final HardwareMap hardwareMap;

    private final Servo outtake_left;
    private final Servo outtake_right;

    Outtake(@NonNull final Parameters parameters)
    {
        this.telemetry = parameters.telemetry;
        this.hardwareMap = parameters.hardwareMap;

        outtake_left = hardwareMap.get(Servo.class,"servo_left");
        outtake_left.setDirection(Servo.Direction.FORWARD);

        outtake_right = hardwareMap.get(Servo.class, "servo_right");
        outtake_right.setDirection(Servo.Direction.REVERSE);
    }
    public void setRotationPercentage(int percentageLeft, int percentageRight) {
        outtake_left.setPosition((double)((percentageLeft * MAX_ROTATION) / 100));
        outtake_right.setPosition((double)((percentageRight * MAX_ROTATION) / 100));
    }

   public void setDefaultPosition() {
        setRotationPercentage(35, 37);
   }

    public void resetPosition() {
        outtake_left.setPosition(RESET_POSITION);
        outtake_right.setPosition(RESET_POSITION);
    }
    public double getPosition(){
        return outtake_left.getPosition();
    }
    public void stopOuttake() {
        outtake_left.close();
        outtake_right.close();
    }
    public static class Parameters {
        public HardwareMap hardwareMap;
        public Telemetry telemetry;
    }
}
