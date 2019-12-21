/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj2.command;

/**
 * A base for subsystems that handles registration in the constructor, and provides a more intuitive
 * method for setting the default command.
 */
public abstract class SubsystemBase implements Subsystem
{
  private String name;
  private String subsystem;

  /**
   * Constructor.
   */
  public SubsystemBase() {
    String cName = this.getClass().getSimpleName();
    this.name = cName.substring(cName.lastIndexOf('.') + 1);
    SimpleCmdScheduler.getInstance().registerSubsystem(this);
  }

  /**
   * Gets the name of this Subsystem.
   *
   * @return Name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this Subsystem.
   *
   * @param name name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the subsystem name of this Subsystem.
   *
   * @return Subsystem name
   */
  public String getSubsystem() {
    return subsystem;
  }

  /**
   * Sets the subsystem name of this Subsystem.
   *
   * @param subsystem subsystem name
   */
  public void setSubsystem(String subsystem) {
    this.subsystem = subsystem;
  }

}
