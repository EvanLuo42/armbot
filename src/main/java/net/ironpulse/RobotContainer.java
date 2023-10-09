// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package net.ironpulse;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.RunCommand;
import net.ironpulse.Constants.OperatorConstants;
import net.ironpulse.commands.Autos;
import net.ironpulse.commands.ExampleCommand;
import net.ironpulse.drivers.gyros.Pigeon2Gyro;
import net.ironpulse.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import net.ironpulse.subsystems.SwerveSubsystem;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    private final SwerveSubsystem swerveSubsystem =
            new SwerveSubsystem(new Pigeon2Gyro(Constants.SensorConstants.GYRO_PORT));

    private final CommandXboxController driverController =
            new CommandXboxController(OperatorConstants.DRIVER_CONTROLLER_PORT);
    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();

        swerveSubsystem.setDefaultCommand(
                new RunCommand(
                        () -> swerveSubsystem.drive(
                                new Translation2d(
                                        driverController.getLeftY() * Constants.SwerveConstants.MAX_SPEED,
                                        driverController.getLeftX() * Constants.SwerveConstants.MAX_SPEED
                                ),
                                driverController.getRightX() * Constants.SwerveConstants.MAX_SPEED,
                                false
                        ),
                        swerveSubsystem
                )
        );
    }
    
    
    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings()
    {
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        new Trigger(exampleSubsystem::exampleCondition)
                .onTrue(new ExampleCommand(exampleSubsystem));
        
        // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
        // cancelling on release.
        driverController.b().whileTrue(exampleSubsystem.exampleMethodCommand());
    }
    
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // An example command will be run in autonomous
        return Autos.exampleAuto(exampleSubsystem);
    }
}
