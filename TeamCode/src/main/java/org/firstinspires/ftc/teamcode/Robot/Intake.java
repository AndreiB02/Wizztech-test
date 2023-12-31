package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private final double RELEASE_POSITION_LEFT = 0.5,RELEASE_POSITION_RIGHT = 0.4;
    private final double GRAB_POSITION_LEFT = 0.0,GRAB_POSITION_RIGHT = 0.0;

    private final Telemetry telemetry;
    private final HardwareMap hardwareMap;

    private final Servo intake_right;
    private final Servo intake_left;

    Intake(@NonNull final Parameters parameters) {
        this.telemetry = parameters.telemetry;
        this.hardwareMap = parameters.hardwareMap;

        intake_right = hardwareMap.get(Servo.class, "intake_right");
        intake_left = hardwareMap.get(Servo.class, "intake_left");

        intake_right.setDirection(Servo.Direction.FORWARD);
        intake_left.setDirection(Servo.Direction.REVERSE);
    }

    public void grab() {
        intake_right.setPosition(GRAB_POSITION_RIGHT);
        intake_left.setPosition(GRAB_POSITION_LEFT);
    }

    public void release() {
        intake_right.setPosition(RELEASE_POSITION_RIGHT);
        intake_left.setPosition(RELEASE_POSITION_LEFT);
    }

    public void stopIntake() {
        intake_right.close();
        intake_left.close();
    }

    public static class Parameters {
        public HardwareMap hardwareMap;
        public Telemetry telemetry;
    }

}
