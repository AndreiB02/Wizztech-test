package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.ScheduledExecutorService;

public class Gripper {
    private final double RELEASE_POSITION = -1.0;
    private final double GRAB_POSITION = 1.0;

    private final Telemetry  telemetry;
    private final HardwareMap hardwareMap;

    private final Servo servo_left;
    private final Servo servo_right;


    Gripper(@NonNull final Parameters parameters)
    {
        this.telemetry = parameters.telemetry;
        this.hardwareMap = parameters.hardwareMap;

        servo_left = hardwareMap.get(Servo.class,"servo_left");
        servo_left.setDirection(Servo.Direction.FORWARD);

        servo_right = hardwareMap.get(Servo.class, "servo_right");
        servo_right.setDirection(Servo.Direction.REVERSE);
    }
    public void grab()
    {
        servo_left.setPosition(GRAB_POSITION);
        servo_right.setPosition(GRAB_POSITION);
    }
    public void release()
    {
        servo_left.setPosition(RELEASE_POSITION);
        servo_right.setPosition(RELEASE_POSITION);
    }
    public void stopGripper()
    {
        servo_left.close();
        servo_right.close();
    }
    public static class Parameters
    {
        public HardwareMap hardwareMap;
        public Telemetry telemetry;
    }
}
