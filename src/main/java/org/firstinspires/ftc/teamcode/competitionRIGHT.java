package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/* THIS CODE WORKS PERFECTLY.
* DO NOT CHANGE THIS CODE.
* USE THIS CODE IF AUTO FAILS. */

@TeleOp(name="TURN RIGHT")
public class competitionRIGHT extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private double maxSpeed = 0.4;
    private double autoMaxSpeed = 0.1;
    private double leftClawClose = 0.9;
    private double leftClawOpen = 1;
    private double rightClawClose = 0.1;
    private double rightClawOpen = 0;
    private double stage1 = 60000; // 20,000 is 0.5 seconds
    private double stage2 = 40000;
    private double stage3 = 30000;

    /* LIST OF MOTORS */
    private DcMotor LeftRear = null;
    private DcMotor LeftFront = null;
    private DcMotor RightRear = null;
    private DcMotor RightFront = null;
    private CRServo telescope = null;
    private CRServo LeftTV = null;
    private CRServo RightTV = null;
    private Servo CLeft = null;
    private Servo CRight = null;

    /* new stuff here */
    private CRServo Grab = null;
    private CRServo Tilt = null;
    private DcMotor Extend = null;

    @Override /* INIT CODE */
    public void init() {

        telemetry.addData("Status", "INITIALIZING ROBOT");

        LeftRear = hardwareMap.get(DcMotor.class, "LeftRear");
        LeftFront = hardwareMap.get(DcMotor.class, "LeftFront");
        RightRear  = hardwareMap.get(DcMotor.class, "RightRear");
        RightFront = hardwareMap.get(DcMotor.class, "RightFront");

        LeftRear.setDirection(DcMotor.Direction.REVERSE);
        LeftFront.setDirection(DcMotor.Direction.REVERSE);
        RightRear.setDirection(DcMotor.Direction.FORWARD);
        RightFront.setDirection(DcMotor.Direction.FORWARD);

        telescope = hardwareMap.get(CRServo.class, "telescope");
        LeftTV = hardwareMap.get(CRServo.class, "LeftTV");
        RightTV = hardwareMap.get(CRServo.class, "RightTV");
        CLeft = hardwareMap.get(Servo.class, "CLeft");
        CRight = hardwareMap.get(Servo.class, "CRight");

        LeftTV.setDirection(CRServo.Direction.REVERSE);
        RightTV.setDirection(CRServo.Direction.FORWARD);

        /* new stuff here */
        //Grab = hardwareMap.get(CRServo.class, "Grab"); // when you scan it on the phone, change the name to "Grab" okay? yup
        //Tilt = hardwareMap.get(CRServo.class, "Tilt"); // etc
        Extend = hardwareMap.get(DcMotor.class, "Extend"); // etc

        LeftRear.setPower(0);
        LeftFront.setPower(0);
        RightRear.setPower(0);
        RightFront.setPower(0);
        LeftTV.setPower(0);
        RightTV.setPower(0);
        telescope.setPower(0);
        CLeft.setPosition(1);
        CRight.setPosition(0);
        //Grab.setPower(0);
        //Tilt.setPower(0);
        Extend.setPower(0);

        telemetry.addData("Status", "INITIALIZATION COMPLETE");
        telemetry.addData("Status", runtime.seconds());
        runtime.reset();
        /* AUTO MODE CODE STARTS HERE */
        for (int i = 0; i < stage1; i++) {
            LeftRear.setPower(autoMaxSpeed);
            LeftFront.setPower(autoMaxSpeed);
            RightRear.setPower(autoMaxSpeed);
            RightFront.setPower(autoMaxSpeed);
            CLeft.setPosition(leftClawClose);
            CRight.setPosition(rightClawClose);
        }
        telemetry.addData("Status", runtime.seconds());
        runtime.reset();
        for (int i = 0; i < stage2; i++) {
            LeftRear.setPower(autoMaxSpeed);
            LeftFront.setPower(autoMaxSpeed);
            RightRear.setPower(-autoMaxSpeed);
            RightFront.setPower(-autoMaxSpeed);
            CLeft.setPosition(leftClawClose);
            CRight.setPosition(rightClawClose);
        }
        telemetry.addData("Status", runtime.seconds());
        runtime.reset();
        for (int i = 0; i < stage3; i++) {
            LeftRear.setPower(autoMaxSpeed);
            LeftFront.setPower(autoMaxSpeed);
            RightRear.setPower(autoMaxSpeed);
            RightFront.setPower(autoMaxSpeed);
            CLeft.setPosition(leftClawOpen);
            CRight.setPosition(rightClawOpen);
        }
        telemetry.addData("Status", runtime.seconds());
        /* AUTO MODE CODE ENDS HERE*/
    }

    /* Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY */
    @Override
    public void init_loop() { }

    /* Code to run ONCE when the driver hits PLAY */
    @Override
    public void start() { runtime.reset(); }

    /* Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP */
    @Override
    public void loop() {

        double leftPower = 0;
        double rightPower = 0;

        double leftDrive = -gamepad1.left_stick_y;
        double rightDrive = -gamepad1.right_stick_y;

        leftPower = Range.clip(leftDrive , -maxSpeed, maxSpeed) ;
        rightPower = Range.clip(rightDrive , -maxSpeed, maxSpeed) ;

        // Send calculated power to wheels
        LeftRear.setPower(leftPower);
        LeftFront.setPower(leftPower);
        RightRear.setPower(rightPower);
        RightFront.setPower(rightPower);

        if (gamepad1.dpad_left) {
            LeftRear.setPower(0.2);
            LeftFront.setPower(-0.2);
            RightRear.setPower(0.2);
            RightFront.setPower(-0.2);
        }
        if (gamepad1.dpad_left) {
            LeftRear.setPower(-0.2);
            LeftFront.setPower(0.2);
            RightRear.setPower(-0.2);
            RightFront.setPower(0.2);
        }

        /* Controller 2 Buttons */
        if (gamepad2.a) { // CLOSE
            CLeft.setPosition(0.9);
            CRight.setPosition(0.1);
        }
        if (gamepad2.b) { // OPEN
            CLeft.setPosition(1);
            CRight.setPosition(0);
        }
        LeftTV.setPower(-gamepad2.left_stick_y);
        RightTV.setPower(-gamepad2.left_stick_y);
        telescope.setPower(-gamepad2.right_stick_y);
        if (gamepad2.dpad_down) {
            Tilt.setPower(-0.1);
        }
        if (gamepad2.dpad_up) {
            Tilt.setPower(0.1);
        }
        if (gamepad2.dpad_left) {
            Extend.setPower(-0.1);
        }
        if (gamepad2.dpad_right) {
            Extend.setPower(0.1);
        }
        if (gamepad2.y) {
            Grab.setPower(-0.1);
        }
        if (gamepad2.x) {
            Grab.setPower(0.1);
        }

    }

    /* Code to run ONCE after the driver hits STOP */
    @Override
    public void stop() { }

}
