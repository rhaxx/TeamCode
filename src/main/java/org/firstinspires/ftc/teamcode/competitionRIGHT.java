/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="competition TURN RIGHT")
public class competitionRIGHT extends OpMode
{
    /* Drive Motors */
    private DcMotor LeftRear = null;
    private DcMotor LeftFront = null;
    private DcMotor RightRear = null;
    private DcMotor RightFront = null;

    /* Continuous Rotation Servo Motors */
    private CRServo telescope = null;
    private CRServo LeftTV = null;
    private CRServo RightTV = null;

    /* Servo Motors */
    private Servo CLeft = null;
    private Servo CRight = null;

    /* OPCode Members */
    private ElapsedTime runtime = new ElapsedTime();

    /* AUTO Mode numbers */
    private double AUTORESET = 5.0; // more time to reset
    private double AUTOFORWARD1 = 2.05;
    private double AUTOTURN = 2.0;
    private double AUTOFORWARD2 = 0.924;

    /* Max/Min for tank drive */
    private double DRIVESPEED = 0.6; // ADJUST THESE NUMBERS SUPER FAST = 1 STOP = 0

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {

        telemetry.addData("Status", "INIT STARTED");

        LeftRear = hardwareMap.get(DcMotor.class, "LeftRear");
        LeftFront = hardwareMap.get(DcMotor.class, "LeftFront");
        RightRear  = hardwareMap.get(DcMotor.class, "RightRear");
        RightFront = hardwareMap.get(DcMotor.class, "RightFront");

        LeftRear.setDirection(DcMotor.Direction.FORWARD);
        LeftFront.setDirection(DcMotor.Direction.FORWARD);
        RightRear.setDirection(DcMotor.Direction.REVERSE);
        RightFront.setDirection(DcMotor.Direction.REVERSE);

        telescope = hardwareMap.get(CRServo.class, "telescope");
        LeftTV = hardwareMap.get(CRServo.class, "LeftTV");
        RightTV = hardwareMap.get(CRServo.class, "RightTV");
        CLeft = hardwareMap.get(Servo.class, "CLeft");
        CRight = hardwareMap.get(Servo.class, "CRight");

        telescope.setDirection(CRServo.Direction.REVERSE);
        RightTV.setDirection(CRServo.Direction.REVERSE);

        telemetry.addData("Status", "INIT COMPLETE. STARTING AUTO. ROBOT RESETTING");

        /* RESET ROBOT AND OPEN HANDS */
        runtime.reset();
        while (getRuntime() < AUTORESET) {
            LeftRear.setPower(0);
            LeftFront.setPower(0);
            RightRear.setPower(0);
            RightFront.setPower(0);
            telescope.setPower(0);
            LeftTV.setPower(0);
            RightTV.setPower(0);
            CLeft.setPosition(1); // Open
            CRight.setPosition(0); // Open
        }
        telemetry.addData("Status", "ROBOT RESET COMPLETE");

        /* Closing the hands */
        telemetry.addData("Status", "CLOSING THE CLAW");
        runtime.reset();
        while (getRuntime() > 2.0) {
            CLeft.setPosition(0.8);
            CRight.setPosition(0.2);
        }
        telemetry.addData("Status", "CLOSING THE CLAW COMPLETE");

        /* Move forward */
        telemetry.addData("Status", "ROBOT MOVING FORWARD");
        runtime.reset();
        while (getRuntime() < AUTOFORWARD1) {
            LeftRear.setPower(0.2);
            LeftFront.setPower(0.2);
            RightRear.setPower(0.2);
            RightFront.setPower(0.2);
        }
        telemetry.addData("Status", "ROBOT MOVING FORWARD COMPLETE");

        /* Turn clockwise */
        telemetry.addData("Status", "ROBOT TURNING CLOCKWISE");
        runtime.reset();
        while (getRuntime() < AUTOTURN) {
            LeftRear.setPower(0.2);
            LeftFront.setPower(0.2);
            RightRear.setPower(-0.2);
            RightFront.setPower(-0.2);
        }
        telemetry.addData("Status", "ROBOT TURNING CLOCKWISE COMPLETE");

        /* Move forward again */
        telemetry.addData("Status", "ROBOT MOVING FORWARD 2");
        runtime.reset();
        while (getRuntime() < AUTOFORWARD2) {
            LeftRear.setPower(0.2);
            LeftFront.setPower(0.2);
            RightRear.setPower(0.2);
            RightFront.setPower(0.2);
        }
        telemetry.addData("Status", "ROBOT MOVING FORWARD 2 COMPLETE");

        telemetry.addData("Status", "ROBOT PUTTING BOX DOWN");
        runtime.reset();
        while (getRuntime() < 2) {
            /* Extend the hands */
            telescope.setPower(1);
            /* Open the hands */
            CLeft.setPosition(1);
            CRight.setPosition(0);
        }
        telemetry.addData("Status", "ROBOT PUTTING BOX DOWN COMPLETE");

        /* Go reverse a bit */
        telemetry.addData("Status", "ROBOT GOING IN REVERSE");
        runtime.reset();
        while (getRuntime() < AUTOFORWARD2) {
            telescope.setPower(-1);
            LeftRear.setPower(-0.2);
            LeftFront.setPower(-0.2);
            RightRear.setPower(-0.2);
            RightFront.setPower(-0.2);
        }
        telemetry.addData("Status", "ROBOT GOING IN REVERSE COMPLETE");

        /* Reset for teleop */
        runtime.reset();
        while (getRuntime() < 5) {
            LeftRear.setPower(0);
            LeftFront.setPower(0);
            RightRear.setPower(0);
            RightFront.setPower(0);
            LeftTV.setPower(0);
            RightTV.setPower(0);
            telescope.setPower(0);
            CLeft.setPosition(1); // OPEN
            CRight.setPosition(0); // OPEN
        }
        telemetry.addData("Status", "AUTO MODE COMPLETE");

    }

    /* Code to run ONCE when the driver hits PLAY */
    @Override
    public void start() {
        runtime.reset();
    }

    /* Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        // double drive = gamepad1.left_stick_y;
        // double turn = gamepad1.right_stick_x;
        // leftPower    = Range.clip(drive + turn, -DRIVESPEED, DRIVESPEED) ;
        // rightPower   = Range.clip(drive - turn, -DRIVESPEED, DRIVESPEED) ;

        // TANK DRIVE
        double LEFTDRIVE = gamepad1.left_stick_y;
        double RIGHTDRIVE = gamepad1.right_stick_y;
        leftPower    = Range.clip(LEFTDRIVE, -DRIVESPEED, DRIVESPEED) ;
        rightPower   = Range.clip(RIGHTDRIVE, -DRIVESPEED, DRIVESPEED) ;

        // Send calculated power to wheels
        LeftRear.setPower(leftPower);
        LeftFront.setPower(leftPower);
        RightRear.setPower(rightPower);
        RightFront.setPower(rightPower);

        /* Controller 2 Buttons */
        if (gamepad2.a) { // CLOSE
            CLeft.setPosition(0.8);
            CRight.setPosition(0.2);
        }
        if (gamepad2.b) { // OPEN
            CLeft.setPosition(1);
            CRight.setPosition(0);
        }
        LeftTV.setPower(gamepad2.left_stick_y);
        RightTV.setPower(gamepad2.left_stick_y);
        telescope.setPower(gamepad2.right_stick_y);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    /* Code to run ONCE after the driver hits STOP */
    @Override
    public void stop() {
        telemetry.addData("Status", "RESETTING ROBOT");
        LeftRear.setPower(0);
        LeftFront.setPower(0);
        RightRear.setPower(0);
        RightFront.setPower(0);
        LeftTV.setPower(0);
        RightTV.setPower(0);
        telescope.setPower(0);
        CLeft.setPosition(1);
        CRight.setPosition(0);
        telemetry.addData("Status", "RESET COMPLETE");
    }

}
