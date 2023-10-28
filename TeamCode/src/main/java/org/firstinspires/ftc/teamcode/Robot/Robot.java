package org.firstinspires.ftc.teamcode.Robot;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.concurrent.ScheduledExecutorService;


@RequiresApi(api = Build.VERSION_CODES.N)
public class Robot {

    public Telemetry telemetry;

    public Wheels wheels;
    public Outtake outtake;
    public Intake intake;
    public Slider slider;
    public Plane plane;
    public IntakeHands intakeHands;
    public Raiser raiser;


  //  public Imu imu;

    public Robot(final HardwareMap hardwareMap, final Telemetry t, ScheduledExecutorService scheduler) {
        telemetry = t;

        // --------- initializing the imu sensor --------
//         BNO055IMU imu_sensor = hardwareM ap.get(BNO055IMU.class, "imu_sensor");
//         imu_sensor.initialize(new BNO055IMU.Parameters());

        // ----- parsing the parameters for initializing the Wheels class ----
        Wheels.Parameters wheels_parameters = new Wheels.Parameters();
        wheels_parameters.hardwareMap = hardwareMap;
        wheels_parameters.telemetry = telemetry;
        wheels_parameters.scheduler = scheduler;
        wheels_parameters.rpm = 435;
        wheels = new Wheels(wheels_parameters);

        // ----- parsing the parameters for initializing the Arm class ----
        Slider.Parameters slider_parameters = new Slider.Parameters();
        slider_parameters.hardwareMap = hardwareMap;
        slider_parameters.telemetry = telemetry;
        slider_parameters.scheduler = scheduler;
        slider = new Slider(slider_parameters);

         //----- parsing the parameters for initializing the Outtake class ----
        Outtake.Parameters outtake_parameters = new Outtake.Parameters();
        outtake_parameters.hardwareMap = hardwareMap;
        outtake_parameters.telemetry = telemetry;
        outtake = new Outtake(outtake_parameters);

        //----- parsing the parameters for initializing the Intake class ----
        Intake.Parameters intake_parameters = new Intake.Parameters();
        intake_parameters.hardwareMap = hardwareMap;
        intake_parameters.telemetry = telemetry;
        intake = new Intake(intake_parameters);

        //---- parsing the parameters for initializing the IntakeHands class ---
        IntakeHands.Parameters intakeHands_parameters = new IntakeHands.Parameters();
        intakeHands_parameters.hardwareMap = hardwareMap;
        intakeHands_parameters.telemetry = telemetry;
        intakeHands = new IntakeHands(intakeHands_parameters);

        Plane.Parameters  plane_parameters = new Plane.Parameters();
        plane_parameters.telemetry = telemetry;
        plane_parameters.hardwareMap = hardwareMap;
        plane = new Plane(plane_parameters);

        Raiser.Parameters raiser_parameters=  new Raiser.Parameters();
        raiser_parameters.telemetry = telemetry;
        raiser_parameters.hardwareMap = hardwareMap;
        raiser_parameters.scheduler = scheduler;
        raiser = new Raiser(raiser_parameters);
    }

    public void stopRobot() {
        wheels.stopEngines();
        slider.stopSlider();
//        gripper.stopGripper();
        plane.stopPlane();
    }

}