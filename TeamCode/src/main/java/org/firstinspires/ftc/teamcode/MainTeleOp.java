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
    public double RAISE_POWER =  1;

    private boolean isActiveLeftHand = true, isActiveRightHand = true ;
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
            robot.outtake.setRotationPercentage(87,89);
        } else if (controller1.rightBumper()) {
            robot.outtake.setRotationPercentage(15,17);
        }

        // ----------- controlling the intake system -----------
        if (controller1.dpadUpOnce()) {
            robot.intake.release();
        } else if (controller1.dpadDownOnce()) {
            robot.intake.grab();
        }

        // ----------- controlling the plane launching system -----------
        if(controller1.XOnce()){
            robot.plane.release();
        }

//        // ----------- controlling the intake hands blocking system -------
        if (controller1.dpadLeftOnce()) {
            if(isActiveLeftHand) {
                robot.intakeHands.releaseLeft();
                isActiveLeftHand = false;
            }
            else {
                robot.intakeHands.grabLeft();
                isActiveLeftHand = true;
            }
        }
        if (controller1.dpadRightOnce()) {
            if(isActiveRightHand){
                robot.intakeHands.releaseRight();
                isActiveRightHand = false;
            }
            else {
                robot.intakeHands.grabRight();
                isActiveRightHand = true;
            }
        }


        // -------- setting target value for slider ----------
        if(!Utils.isDone(lastLeftMove) || !Utils.isDone(lastRightMove) || !Utils.isDone(lastLeftMoveRaiser) || !Utils.isDone(lastRightMoveRaiser)) { return ; }
        else if (controller1.AOnce()) { raise_value = 0; }
        else if (controller1.BOnce()) { raise_value = (int)(MAX_POSITION / 2); }
        else if (controller1.YOnce()) { raise_value = MAX_POSITION; }
        else if(raiser_value_raiser<=4000 && controller1.right_trigger != 0.0){ raiser_value_raiser = 400; telemetry.addData("set raiser value to 400",400);}
        else if(raiser_value_raiser>=0 && controller1.left_trigger !=0.0){raiser_value_raiser = 0;}
        else { return;}

        lastLeftMove = robot.slider.raiseLeftSlider(raise_value, RAISE_POWER);
        lastRightMove = robot.slider.raiseRightSlider(raise_value, RAISE_POWER);

        lastLeftMoveRaiser = robot.raiser.raiseLeftRaiser(raiser_value_raiser,RAISE_POWER);
        lastRightMoveRaiser = robot.raiser.raiseRightRaiser(raiser_value_raiser,RAISE_POWER);

        telemetry.addData("SliderLeft position", robot.slider.left_slider.getCurrentPosition());
        telemetry.addData("SliderRight position", robot.slider.right_slider.getCurrentPosition());
        telemetry.update();
    }

}
