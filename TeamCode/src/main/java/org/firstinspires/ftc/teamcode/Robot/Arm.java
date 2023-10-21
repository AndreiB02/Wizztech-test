package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Arm {
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;

    private final DcMotor arm_motor;
    private final ScheduledExecutorService scheduler;
    Arm(@NonNull final Parameters parameters)
    {
        this.hardwareMap = Objects.requireNonNull(parameters.hardwareMap, "HardwareMap was not set up");
        this.telemetry = Objects.requireNonNull(parameters.telemetry, "Telemetry was not set");
        this.scheduler = Objects.requireNonNull(parameters.scheduler, "Scheduler was not set");

        arm_motor = hardwareMap.get(DcMotor.class,"arm_motor");
        arm_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        arm_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private ScheduledFuture<?> lastArmMove;
    public ScheduledFuture<?> raiseArm(int targetPositionValue, double raisePower)
    {
        if(!Utils.isDone(lastArmMove) && !lastArmMove.cancel(true))
            return null;

        int initialPosition = arm_motor.getCurrentPosition();

        if(targetPositionValue == initialPosition)
            return null;
        arm_motor.setTargetPosition(targetPositionValue);
        arm_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm_motor.setPower(targetPositionValue > initialPosition ? raisePower : -raisePower);

        lastArmMove = Utils.poll(scheduler, () -> !arm_motor.isBusy(), () -> arm_motor.setPower(0), 10 , TimeUnit.MICROSECONDS);

        return lastArmMove;
    }

    public int getCurrentPosition() {
        return arm_motor.getCurrentPosition();
    }

    public void move(double speed) {
        arm_motor.setPower(speed);
    }

    public void intake_position()
    {

    }
    public static class Parameters{
        public HardwareMap hardwareMap;
        public Telemetry telemetry;
        public ScheduledExecutorService scheduler;
    }

}
