//mecanum drive class
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class MecanumDrive {
	//drive speed modifiers
	private double[] speedScale = {.25, .5, .75, 1};
	private int speedIndex;
	//talons must be talon SRX's and must be wired in CAN configuration
	private CANTalon t1, t2, t3, t4;
	
	public MecanumDrive(CANTalon tal1, CANTalon tal2, CANTalon tal3, CANTalon tal4) {
		t1 = tal1;
		t2 = tal2;
		t3 = tal3;
		t4 = tal4;
		speedIndex = 2;
	}
	
	//drive method 
	//to use with joystick control, pass the outupt of the modStickIn methods for the desired axis
	//to use with autonomous control, pass the desired x, y, and rotation values
	public void drive(double x, double y, double rotate) {
		double scale = updateSpeed();
		mDrive(x*scale, y*scale, rotate*scale);
	}
	
	public void driveAtSpeed(double x, double y, double rotate, double speed){
		mDrive(x*speed, y*speed, rotate*speed);
	}
	
	//to be used with directional pad control
	public void polDrive(int angle) {
		double scale = updateSpeed();
		if(angle == 0)
			mDrive(0, scale, 0);
		else if(angle == 45)
			mDrive(scale, scale, 0);
		else if(angle == 90)
			mDrive(scale, 0, 0);
		else if(angle == 135)
			mDrive(scale, -scale, 0);
		else if(angle == 180)
			mDrive(0, -scale, 0);
		else if(angle == 225)
			mDrive(-scale, -scale, 0);
		else if(angle == 270)
			mDrive(-scale, 0, 0);
		else if(angle == 315)
			mDrive(-scale, scale, 0);
		else
			mDrive(0, 0, 0);
	}
	
	//determines the value to set each motor at based on direction, called within the drive method
	private void mDrive(double right, double forward, double clockwise) {
		double fLeftSpeed, fRightSpeed, rLeftSpeed, rRightSpeed, max;
		boolean norm = false;
		System.out.println(right + "\t" + forward + "\t" + clockwise);
		fLeftSpeed = forward + clockwise + right;
		fRightSpeed = forward - clockwise - right;
		rLeftSpeed = forward + clockwise - right;
		rRightSpeed = forward - clockwise + right;
		max = Math.abs(fLeftSpeed);
		if (Math.abs(fRightSpeed)>max) max = Math.abs(fRightSpeed);
		if (Math.abs(rLeftSpeed)>max) max = Math.abs(rLeftSpeed);
		if (Math.abs(rRightSpeed)>max) max = Math.abs(rRightSpeed);
		if (max>1) {
			norm = true;
			fLeftSpeed/=max;
			fRightSpeed/=max;
			rLeftSpeed/=max;
			rRightSpeed/=max;
		}
		t1.set(-fLeftSpeed);
		t2.set(-rLeftSpeed);
		t3.set(fRightSpeed);
		t4.set(rRightSpeed);
	}
	
	//updates the current speed value
	public double updateSpeed() {
		return speedScale[speedIndex];
	}
	
	//modifies the input of a joystick axis by adding dead zones and squaring the inputs, intended to be used with XBOX controllers or other controllers with many predefined axes
	public double modStickIn(Joystick j1, int num) {
		double joyIn = j1.getRawAxis(num);
		if(joyIn <= .05 && joyIn >= 0)
			joyIn = 0;
		else if(joyIn >= -.05 && joyIn <= 0)
			joyIn = 0;
		else if(joyIn < 0)
			joyIn = -Math.pow(joyIn, 2);
		else
			joyIn = Math.pow(joyIn, 2);
		return joyIn;
	}
	
	//sets the speed modifier to the next predefined value
	public void speedUp() {
		if(speedIndex != 3) speedIndex+=1;
		Timer.delay(.1);
	}
	
	//sets the speed modifier to the previous predefined value
	public void slowDown() {
		if(speedIndex != 0) speedIndex-=1;
		Timer.delay(.1);
	}
	
	//sets the speed to the value of the speedScale array at the given index
	public void setSpeed(int index) {
		speedIndex = (index > 3? 3 : index < 0? 0 : index);
	}
	
	
	//returns the current speed
	public double getSpeed() {
		return speedScale[speedIndex];
	}
	
	//returns the current speed index
	public double getIndex() {
		return speedIndex;
	}
}
