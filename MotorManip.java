//motor manipulator class
import edu.wpi.first.wpilibj.Talon;

public class MotorManip{
	protected CANTalon t1;
	
	public MotorManip(int deviceID) {
		t1 = new CANTalon(deviceID));
	}
	
	//sets the motor forward at a given speed
	protected void motorForward(double speed) {
		t1.set(speed);
	}

	//sets the motor in reverse at a given speed
	protected void motorReverse(double speed) {
		t1.set(-speed);
	}
	
	//stops the motor
	protected void motorStop() {
		t1.set(0);
	}
}
