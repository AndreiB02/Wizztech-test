package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;

    private final DcMotor arm_motor;
    Arm(@NonNull final Parameters parameters)
    {
        this.hardwareMap = parameters.hardwareMap;
        this.telemetry = parameters.telemetry;

        arm_motor = hardwareMap.get(DcMotor.class,"arm_motor");
        arm_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        arm_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void intake_position()
    {

    }
    public static class Parameters{
        public HardwareMap hardwareMap;
        public Telemetry telemetry;
    }

}
