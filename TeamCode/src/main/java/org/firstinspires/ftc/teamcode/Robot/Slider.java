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

public class Slider {
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;

    private final DcMotor left_slider, right_slider;
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> lastRightMove = null, lastLeftMove = null;
    Slider(@NonNull final Parameters parameters)
    {
        this.hardwareMap = Objects.requireNonNull(parameters.hardwareMap, "HardwareMap was not set up");
        this.telemetry = Objects.requireNonNull(parameters.telemetry, "Telemetry was not set");
        this.scheduler = Objects.requireNonNull(parameters.scheduler, "Scheduler was not set");

        // TODO: check the direction of every slider
        // TODO: change the ZeroPowerBehaviour for each slider
        left_slider = hardwareMap.get(DcMotor.class,"left_slider");
        left_slider.setDirection(DcMotorSimple.Direction.REVERSE);
        left_slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        right_slider = hardwareMap.get(DcMotor.class,"right_slider");
        right_slider.setDirection(DcMotorSimple.Direction.FORWARD);
        right_slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public ScheduledFuture<?> raiseLeftSlider(int targetPositionValue, double raisePower)
    {
        if(!Utils.isDone(lastLeftMove) && !lastLeftMove.cancel(true)) {
            return null;
        }

        int initialPosition = left_slider.getCurrentPosition();

        if(targetPositionValue == initialPosition) {
            return null;
        }

        left_slider.setTargetPosition(targetPositionValue);
        left_slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_slider.setPower(targetPositionValue > initialPosition ? raisePower : -raisePower);

        lastLeftMove = Utils.poll(scheduler, () -> !left_slider.isBusy(), () -> left_slider.setPower(0), 10 , TimeUnit.MICROSECONDS);

        return lastLeftMove;
    }
    public ScheduledFuture<?> raiseRightSlider(int targetPositionValue, double raisePower)
    {
        if(!Utils.isDone(lastRightMove) && !lastRightMove.cancel(true)) {
            return null;
        }

        int initialPosition = right_slider.getCurrentPosition();

        if(targetPositionValue == initialPosition) {
            return null;
        }

        right_slider.setTargetPosition(targetPositionValue);
        right_slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_slider.setPower(targetPositionValue > initialPosition ? raisePower : -raisePower);

        lastRightMove = Utils.poll(scheduler, () -> !right_slider.isBusy(), () -> right_slider.setPower(0), 10 , TimeUnit.MICROSECONDS);

        return lastRightMove;
    }

    public int getCurrentPositionLeft() {
        return left_slider.getCurrentPosition();
    }
    public int getCurrentPositionRight() {
        return right_slider.getCurrentPosition();
    }

    public void stopSlider()
    {
        left_slider.setPower(0.0);
        right_slider.setPower(0.0);
    }
    public static class Parameters{
        public HardwareMap hardwareMap;
        public Telemetry telemetry;
        public ScheduledExecutorService scheduler;
    }

}
