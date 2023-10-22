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

    private int FINAL_POSITION = 6000;

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

        double x = controller1.left_stick_x;
        double y = controller1.left_stick_y;
        double r = -controller1.right_stick_x;

        if(!Utils.isDone(lastLeftMove) || !Utils.isDone(lastRightMove)) { return ; }
        else if (controller1.YOnce()) { raise_value = 4200; }
        else if (controller1.BOnce()) { raise_value = 3000; }
        else if (controller1.XOnce()) { raise_value = 1400; }
        else if (controller1.AOnce()) { raise_value = 0; }
        else if (raise_value <= 4000 && controller1.right_trigger != 0.0) {
            raise_value = (int) (raise_value + controller1.right_trigger * 1000);
        } else if (raise_value >= 0 && controller1.left_trigger != 0.0) {
            raise_value = (int) (raise_value - controller1.left_trigger * 1000);
        } else { return ; }

        if(controller1.dpadDownOnce())
        {
            telemetry.addData("SliderLeft position", robot.slider.getCurrentPositionLeft());
            telemetry.addData("SliderRight position", robot.slider.getCurrentPositionRight());
        }

        // afisarea de pozitie a encoderului


        lastLeftMove = robot.slider.raiseLeftSlider(raise_value, RAISE_POWER);
        lastRightMove = robot.slider.raiseRightSlider(raise_value, RAISE_POWER);

        telemetry.addData("SliderLeft position", robot.slider.getCurrentPositionLeft());
        telemetry.addData("SliderRight position", robot.slider.getCurrentPositionRight());

        robot.wheels.move(x,y,r,true);

        telemetry.update();
    }

}