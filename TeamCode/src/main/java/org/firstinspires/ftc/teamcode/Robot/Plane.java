package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

public class Plane {

    private final Telemetry telemetry;
    private final HardwareMap hardwareMap;
    private final Servo servo;


    private final double RELEASE_POSITION = 0.0;
    private final double GRAB_POSITION = 0.0;

    Plane(@NonNull final Parameters parameters) {
        this.telemetry = Objects.requireNonNull(parameters.telemetry, "Telemetry was not set");
        this.hardwareMap = Objects.requireNonNull(parameters.hardwareMap, "hardwareMap was not set");

        servo = hardwareMap.get(Servo.class, "plane_servo");
    }

    public void grab() {
        servo.setPosition(GRAB_POSITION);
    }

    public void release() {
        servo.setPosition(RELEASE_POSITION);
    }

    public static class Parameters {
        Telemetry telemetry;
        HardwareMap hardwareMap;
    }
}
