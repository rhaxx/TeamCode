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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static android.os.SystemClock.sleep;

@TeleOp(name="competition OLD CODE")
//@Disabled
public class competition extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    /* Drive Motors */
    private DcMotor RightRear = null;
    private DcMotor RightFront = null;
    private DcMotor LeftRear = null;
    private DcMotor LeftFront = null;

    /* Continuous Rotation Servo Motors */
    private CRServo telescope = null;
    private CRServo RightTV = null;
    private CRServo LeftTV = null;

    /* Servo Motors */
    private Servo CRight = null;
    private Servo CLeft = null;

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        LeftRear = hardwareMap.get(DcMotor.class, "LeftRear");
        LeftFront = hardwareMap.get(DcMotor.class, "LeftFront");
        RightRear  = hardwareMap.get(DcMotor.class, "RightRear");
        RightFront = hardwareMap.get(DcMotor.class, "RightFront");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        LeftRear.setDirection(DcMotor.Direction.FORWARD);
        LeftFront.setDirection(DcMotor.Direction.FORWARD);
        RightRear.setDirection(DcMotor.Direction.REVERSE);
        RightFront.setDirection(DcMotor.Direction.REVERSE);

        telescope = hardwareMap.get(CRServo.class, "telescope");
        RightTV = hardwareMap.get(CRServo.class, "RightTV");
        LeftTV = hardwareMap.get(CRServo.class, "LeftTV");
        CRight = hardwareMap.get(Servo.class, "CRight");
        CLeft = hardwareMap.get(Servo.class, "CLeft");

        RightTV.setDirection(CRServo.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /* Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY */
    @Override
    public void init_loop() {
        LeftRear.setPower(0);
        LeftFront.setPower(0);
        RightRear.setPower(0);
        RightFront.setPower(0);
        LeftTV.setPower(0);
        RightTV.setPower(0);
        telescope.setPower(0);
        CLeft.setPosition(0);
        CRight.setPosition(1);
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
        double drive = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        leftPower    = Range.clip(drive + turn, -0.5, 0.5) ;
        rightPower   = Range.clip(drive - turn, -0.5, 0.5) ;

        // Send calculated power to wheels
        LeftRear.setPower(leftPower);
        LeftFront.setPower(leftPower);
        RightRear.setPower(rightPower);
        RightFront.setPower(rightPower);

        /* Controller 2 Buttons */
        if (gamepad2.a) { // CLOSE
            CLeft.setPosition(0.2);
            CRight.setPosition(0.8);
        }
        if (gamepad2.b) { // OPEN
            CLeft.setPosition(0);
            CRight.setPosition(1);
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
        CLeft.setPosition(0);
        CRight.setPosition(1);
        telemetry.addData("Status", "RESET COMPLETE");
    }

}
