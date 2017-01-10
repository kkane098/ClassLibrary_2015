//pneumatic manipulator class
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class PneumaticManip {
	//single valve solenoid
	protected Solenoid sol;
	//double valve solenoid
	protected DoubleSolenoid dSol;
	protected String status;
	
	public PneumaticManip(Solenoid sol) {
		this.sol = sol;
		status = "reverse";
	}
	
	public PneumaticManip(DoubleSolenoid dSol) {
		this.dSol = dSol;
		status = "reverse";
	}
	
	//fires a single valve solenoid
	protected void fire() {
		sol.set(true);
		Timer.delay(1);
		sol.set(false);
	}
	
	//extends the cylinder(double valve)
	protected void extend() {
		//sets the solenoid to a predefined final value that corresponds to the extended position
		dSol.set(DoubleSolenoid.Value.kReverse);
		status = "forward";
	}
	
	//retracts the cylinder(double valve)
	protected void retract() {
		//sets the solenoid to a predefined final value that corresponds to the retracted position
		dSol.set(DoubleSolenoid.Value.kForward);
		status = "reverse";
	}
	
	//returns the current position of the cylinder
	public String getStatus() {
		return status;
	}
}
