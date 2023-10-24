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

    private int raise_value;

    private int MAX_POSITION_LEFT;
    private int MAX_POSITION_RIGHT= 4250;

    public double RAISE_POWER =  0.5;
    private ScheduledFuture<?> lastRightMove,lastLeftMove;

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

        // -------getting controller movement input---------
        double x = controller1.left_stick_x;
        double y = controller1.left_stick_y;
        double r = -controller1.right_stick_x;

        robot.wheels.move(x,y,r,true);

        //--------setting target value for slider----------
        if(!Utils.isDone(lastLeftMove) || !Utils.isDone(lastRightMove)) { return ; }
        else if (controller1.XOnce()) { raise_value = 4276; }
        else if (controller1.AOnce()) { raise_value = 0; }
        else { return;}

//        if(controller1.dpadDownOnce()) {
//            telemetry.addData("SliderLeft position", robot.slider.getCurrentPositionLeft());
//            telemetry.addData("SliderRight position", robot.slider.getCurrentPositionRight());
//        }

        //-----------moving the slider-------
        lastLeftMove = robot.slider.raiseLeftSlider(0, RAISE_POWER);
        lastRightMove = robot.slider.raiseRightSlider(raise_value, 0.7);

        telemetry.addData("SliderLeft position", robot.slider.getCurrentPositionLeft());
        telemetry.addData("SliderRight position", robot.slider.getCurrentPositionRight());

        telemetry.update();
    }

}