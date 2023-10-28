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

public class Raiser {
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;

    public final DcMotor left_raiser, right_raiser;
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> lastRightMoveRaiser = null, lastLeftMoveRaiser = null;
    Raiser(@NonNull final Parameters parameters)
    {
        this.hardwareMap = Objects.requireNonNull(parameters.hardwareMap, "HardwareMap was not set up");
        this.telemetry = Objects.requireNonNull(parameters.telemetry, "Telemetry was not set");
        this.scheduler = Objects.requireNonNull(parameters.scheduler, "Scheduler was not set raiser");

        left_raiser = hardwareMap.get(DcMotor.class,"left_raiser");
        left_raiser.setDirection(DcMotorSimple.Direction.FORWARD);
        left_raiser.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_raiser.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        right_raiser = hardwareMap.get(DcMotor.class,"right_raiser");
        right_raiser.setDirection(DcMotorSimple.Direction.REVERSE);
        right_raiser.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_raiser.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public ScheduledFuture<?> raiseLeftRaiser(int targetPositionValue, double raisePower)
    {
        if(!Utils.isDone(lastLeftMoveRaiser) && !lastLeftMoveRaiser.cancel(true))
            return null;

        int initialPosition = left_raiser.getCurrentPosition();

        if(targetPositionValue == initialPosition)
            return null;
        left_raiser.setTargetPosition(targetPositionValue);
        left_raiser.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_raiser.setPower(targetPositionValue > initialPosition ? raisePower : -raisePower);

        lastLeftMoveRaiser = Utils.poll(scheduler, () -> !left_raiser.isBusy(), () -> left_raiser.setPower(0), 10 , TimeUnit.MICROSECONDS);

        return lastLeftMoveRaiser;
    }
    public ScheduledFuture<?> raiseRightRaiser(int targetPositionValue, double raisePower)
    {
        if(!Utils.isDone(lastRightMoveRaiser) && !lastRightMoveRaiser.cancel(true))
            return null;

        int initialPosition = right_raiser.getCurrentPosition();

        if(targetPositionValue == initialPosition)
            return null;
        right_raiser.setTargetPosition(targetPositionValue);
        right_raiser.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_raiser.setPower(targetPositionValue > initialPosition ? raisePower : -raisePower);

        lastRightMoveRaiser = Utils.poll(scheduler, () -> !right_raiser.isBusy(), () -> right_raiser.setPower(0), 10 , TimeUnit.MICROSECONDS);

        return lastRightMoveRaiser;
    }

    public int getCurrentPositionLeft() {
        return  left_raiser.getCurrentPosition();
    }
    public int getCurrentPositionRight() {
        return  right_raiser.getCurrentPosition();
    }

    public void stopRaiser()
    {
        left_raiser.setPower(0.0);
        right_raiser.setPower(0.0);
    }
    public static class Parameters{
        public HardwareMap hardwareMap;
        public Telemetry telemetry;
        public ScheduledExecutorService scheduler;
    }

}
