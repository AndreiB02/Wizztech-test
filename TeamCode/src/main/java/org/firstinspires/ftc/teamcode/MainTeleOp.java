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

    private int raise_value = 0;

    private int MAX_POSITION = 4100;
    public double RAISE_POWER =  1;
    private ScheduledFuture<?> lastRightMove, lastLeftMove;

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
        robot.wheels.move(x,-y,-r,true);


        //--------setting target value for slider----------
        if(!Utils.isDone(lastLeftMove) || !Utils.isDone(lastRightMove)) { return ; }
        else if (controller1.AOnce()) { raise_value = 0; }
        else if (controller1.XOnce()) { raise_value = (int)(MAX_POSITION / 4); }
        else if (controller1.BOnce()) { raise_value = (int)(MAX_POSITION / 2); }
        else if (controller1.YOnce()) { raise_value = MAX_POSITION; }
        else { return;}

        //-----------moving the slider-------
        lastLeftMove = robot.slider.raiseLeftSlider(raise_value, RAISE_POWER);
        lastRightMove = robot.slider.raiseRightSlider(raise_value, RAISE_POWER);

        telemetry.addData("SliderLeft position", robot.slider.left_slider.getCurrentPosition());
        telemetry.addData("SliderRight position", robot.slider.right_slider.getCurrentPosition());
        telemetry.update();
    }

}