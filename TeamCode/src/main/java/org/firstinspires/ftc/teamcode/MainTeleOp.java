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
    private int raiser_value_raiser = 0;
    private int MAX_POSITION = 4100;
    private int RAISER_MAX_VALUE = 16000;
    public double RAISE_POWER =  1;

    private boolean isActiveHand = true, isActiveIntake = true;
    private ScheduledFuture<?> lastRightMove, lastLeftMove;
    private ScheduledFuture<?> lastRightMoveRaiser, lastLeftMoveRaiser;

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

        // ------- getting controller movement input ---------
        double x = controller1.left_stick_x;
        double y = controller1.left_stick_y;
        double r = -controller1.right_stick_x;
        robot.wheels.move(x,-y,-r,true);

        // ----------- controlling the outtake system -----------
        if (controller1.leftBumper()) {
            robot.outtake.setRotationPercentage(98,100);
        } else if (controller1.rightBumper()) {
            robot.outtake.setRotationPercentage(15,17);
        }

//        // ----------- controlling the intake system -----------
//        if (controller1.dpadUpOnce()) {
//            robot.intake.release();
//        } else if (controller1.dpadDownOnce()) {
//            robot.intake.grab();
//        }
//        if (controller1.dpadUpOnce()) {
//            if(isActiveIntake) {
//                robot.intake.release();
//                isActiveIntake= false;
//            }
//            else {
//                robot.intake.grab();
//                isActiveIntake = true;
//            }
//        }

//        // ----------- controlling the plane launching system -----------
//        if(controller1.XOnce()){
//            robot.plane.release();
//        }

//        // ----------- controlling the intake hands blocking system -------
        if (controller1.dpadLeftOnce()) {
            if(isActiveHand) {
                robot.intakeHands.releaseLeft();
                robot.intakeHands.releaseRight();
                isActiveHand = false;
            }
            else {
                robot.intakeHands.grabLeft();
                robot.intakeHands.grabRight();
                isActiveHand = true;
            }
        }



        // -------- setting target value for slider ----------
        if(!Utils.isDone(lastLeftMove) || !Utils.isDone(lastRightMove) || !Utils.isDone(lastLeftMoveRaiser) || !Utils.isDone(lastRightMoveRaiser)) { return ; }
        else if (controller1.AOnce()) {
            raise_value = 0;
            robot.outtake.setDefaultPosition();
        }
        else if (controller1.BOnce()) {
            raise_value = (int)(MAX_POSITION / 2);
            robot.outtake.setDefaultPosition();
        }
        else if (controller1.YOnce()) { raise_value = MAX_POSITION; }
        else if(controller1.dpadUpOnce()){ raiser_value_raiser = RAISER_MAX_VALUE;}
        else if(controller1.dpadDownOnce()){raiser_value_raiser = 0;}
        else if(controller1.XOnce()){raiser_value_raiser = -2000;}
        else { return;}



        lastLeftMove = robot.slider.raiseLeftSlider(raise_value, RAISE_POWER);
        lastRightMove = robot.slider.raiseRightSlider(raise_value, RAISE_POWER);

        lastLeftMoveRaiser = robot.raiser.raiseLeftRaiser(raiser_value_raiser,RAISE_POWER);
        lastRightMoveRaiser = robot.raiser.raiseRightRaiser(raiser_value_raiser,RAISE_POWER);

        telemetry.addData("SliderLeft position", robot.slider.left_slider.getCurrentPosition());
        telemetry.addData("SliderRight position", robot.slider.right_slider.getCurrentPosition());

        telemetry.addData("Raiser position", robot.raiser.left_raiser.getCurrentPosition());
        telemetry.update();
    }

}
