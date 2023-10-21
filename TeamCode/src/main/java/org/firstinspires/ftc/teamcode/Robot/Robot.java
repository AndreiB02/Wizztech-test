package org.firstinspires.ftc.teamcode.Robot;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.concurrent.ScheduledExecutorService;


@RequiresApi(api = Build.VERSION_CODES.N)
public class Robot {

    public Telemetry telemetry;

    public Wheels wheels;
    public Gripper gripper;
    public Arm arm;

    public Imu imu;

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
        Arm.Parameters arm_parameters = new Arm.Parameters();
        arm_parameters.hardwareMap = hardwareMap;
        arm_parameters.telemetry = telemetry;
        arm_parameters.scheduler = scheduler;
        arm = new Arm(arm_parameters);

         //----- parsing the parameters for initializing the Gripper class ----
        Gripper.Parameters gripper_parameters = new Gripper.Parameters();
        gripper_parameters.hardwareMap = hardwareMap;
        gripper_parameters.telemetry = telemetry;
        gripper_parameters.scheduler = scheduler;
        gripper = new Gripper(gripper_parameters);
    }


    public void stopRobot() {
        wheels.stopEngines();
    }

}