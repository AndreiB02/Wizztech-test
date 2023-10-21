package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Robot.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

@TeleOp(name="CenterStage")
public class MainTeleOp extends OpMode {
    private Robot robot;

    private Controller controller1;

    public double ARM_RAISE_POSITION;
    private int arm_raise_position;
    private ScheduledFuture<?> lastArmMove;

    @Override
    public void init(){
        robot = new Robot(
                hardwareMap,
                telemetry,
                Executors.newScheduledThreadPool(1)
        );

        controller1 = new Controller(gamepad1);

    }

    public void stop(){ robot.stopRobot();}

    @Override
    public void loop()
    {
        controller1.update();

        double x = controller1.left_stick_x;
        double y = controller1.left_stick_y;
        double r = -controller1.right_stick_x;

        if(controller1.AOnce())
        {
            arm_raise_position=1000;
            robot.arm.raiseArm(arm_raise_position,1);
        }
        if(controller1.BOnce())
        {
            arm_raise_position=-1000;
            robot.arm.raiseArm(arm_raise_position,1);
        }

        robot.wheels.move(y,x,r,true);
    }

}