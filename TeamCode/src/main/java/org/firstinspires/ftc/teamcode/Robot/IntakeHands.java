package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeHands {
    private final Servo inHandLeft, inHandRight;
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private final double GRAB_POSITION_LEFT=0.62;
    private final double RELEASE_POSITION_LEFT=0.75;
    private final double GRAB_POSITION_RIGHT=0.45;
    private final double RELEASE_POSITION_RIGHT=0.55;
    private final double MAX_ROTATION=1;

    IntakeHands(@NonNull Parameters parameters)
    {
        this.hardwareMap = parameters.hardwareMap;
        this.telemetry = parameters.telemetry;

        inHandLeft = hardwareMap.get(Servo.class,"inhandleft");
        inHandLeft.setDirection(Servo.Direction.FORWARD);

        inHandRight = hardwareMap.get(Servo.class,"inhandright");
        inHandRight.setDirection(Servo.Direction.REVERSE);


    }
    public void setRotationPercentage(int percentage) {
        inHandLeft.setPosition((double)((percentage * MAX_ROTATION) / 100));
        inHandRight.setPosition((double)((percentage * MAX_ROTATION) / 100));
    }
    public void grabLeft() {
        inHandLeft.setPosition(GRAB_POSITION_LEFT);
    }
    public void grabRight() {
        inHandRight.setPosition(GRAB_POSITION_RIGHT);
    }

    public void releaseLeft() {
        inHandLeft.setPosition(RELEASE_POSITION_LEFT);
    }
    public void releaseRight() {
        inHandRight.setPosition(RELEASE_POSITION_RIGHT);
    }
    public void stopIntake() {
        inHandLeft.close();
        inHandRight.close();
    }
    public static class Parameters
    {
        HardwareMap hardwareMap;
        Telemetry telemetry;
    }


}
