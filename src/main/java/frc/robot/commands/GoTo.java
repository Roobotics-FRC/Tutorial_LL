package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class GoTo extends Command {
    private final CommandSwerveDrivetrain drivetrain;
    private final SwerveRequest.FieldCentric drive;
    private final double maxSpeed;

    private final PIDController xPidController = new PIDController(0.1, 0.0, 0.0);
    private final PIDController yPidController = new PIDController(0.1, 0.0, 0.0);
    private final PIDController yawPidController = new PIDController(0.1, 0.0, 0.0);
    

    public GoTo(CommandSwerveDrivetrain drivetrain, SwerveRequest.FieldCentric drive, double maxSpeed) {
        this.drivetrain = drivetrain;
        this.drive = drive;
        this.maxSpeed = maxSpeed;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        config();
        setSetpoint(0, 0, 0);
    }

    @Override
    public void execute() {
        // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() ->
            drive.withVelocityX(-0.5* maxSpeed) // Drive forward with negative Y (forward)
                .withVelocityY(0) // Drive left with negative X (left)
                .withRotationalRate(0) // Drive counterclockwise with negative X (left)
        );
    }

    @Override
    public boolean isFinished() {
        return xPidController.atSetpoint() && yPidController.atSetpoint() && yawPidController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
    }

    private void config() {
        xPidController.reset();
        yPidController.reset();
        yawPidController.reset();

        xPidController.setTolerance(0.01);
        yPidController.setTolerance(0.01);
        yawPidController.setTolerance(0.01);
        yawPidController.enableContinuousInput(-180, 180);
    }

    private void setSetpoint(double x, double y, double yaw) {
        xPidController.setSetpoint(x);
        yPidController.setSetpoint(y);
        yawPidController.setSetpoint(yaw);
    }
}