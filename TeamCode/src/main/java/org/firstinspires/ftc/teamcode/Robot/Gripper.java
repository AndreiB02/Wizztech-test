package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Gripper {
    private final double RELEASE_POSITION = 0.0;
    private final double GRAB_POSITION = 0.0;

    private final Telemetry  telemetry;
    private final HardwareMap hardwareMap;

    private final Servo servo_left;
    private final Servo servo_right;

    Gripper(@NonNull final Parameters parameters, Telemetry telemetry)
    {
        this.telemetry = parameters.telemetry;
        this.hardwareMap = parameters.hardwareMap;

        servo_left = hardwareMap.get(Servo.class,"servo_left");
        servo_right = hardwareMap.get(Servo.class, "servo_right");


    }
    public void grab()
    {
        servo_left.setPosition();
        servo_right.setPosition();
    }
    public void release()
    {
        servo_left.setPosition();
        servo_right.setPosition();
    }
    public static class Parameters
    {
        public HardwareMap hardwareMap;
        public Telemetry telemetry;
    }
}